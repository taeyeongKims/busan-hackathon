package Client;

import GUI.ingame_gui;
import GUI.lobby_gui;
import GUI.login_gui;
import javafx.application.Platform;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class omok_client {

    private static final String SERVER_ADDRESS = "localhost"; // 서버 주소
    private static final int SERVER_PORT = 12345; // 서버 포트

    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private int playerOrder = -1;
    private PlayerOrderListener playerOrderListener;
    private String[] roomNames;
    private int opponentMovePlayerOrder;
    private int opponentMoveX;
    private int opponentMoveY;
    private ingame_gui inGameGui;
    private String roomName;
    private boolean ready = false;

    public void setInGameGui(ingame_gui inGameGui) {
        this.inGameGui = inGameGui;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getRoomName() {
        return roomName;
    }

    public omok_client(String username) {
        try {
            // 서버 연결
            socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            System.out.println("Connected to the server: " + username);

            // 서버 메시지 처리 시작
            startServerListener();

        } catch (IOException e) {
            System.err.println("Failed to connect to server: " + e.getMessage());
            Platform.runLater(() -> {
                login_gui.showErrorDialog("서버에 연결할 수 없습니다. 프로그램을 종료합니다.");
                System.exit(0);
            });
        }
    }

    public void setPlayerOrderListener(PlayerOrderListener listener) {
        playerOrderListener = listener;
        System.out.println("listener = " + listener);
    }

    private void notifyPlayerOrderUpdated() {
        if (playerOrderListener != null) {
            System.out.println("notifyPlayerOrderUpdated, playerOrder = " + playerOrder); // 로그 추가
            Platform.runLater(() -> playerOrderListener.onPlayerOrderUpdated(playerOrder));
        } else {
            System.out.println("playerOrderListener is null, cannot notify update."); // 리스너가 null일 경우 로그 추가
        }
    }

    private void startServerListener() {
        new Thread(() -> {
            try {
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    System.out.println("inputLine = " + inputLine);
                    handleServerMessage(inputLine);
                }
            } catch (IOException e) {
                System.err.println("Connection lost: " + e.getMessage());
                Platform.runLater(() -> {
                    login_gui.showErrorDialog("서버와의 연결이 끊어졌습니다. 프로그램을 종료합니다.");
                    System.exit(0);
                });
            }
        }).start();
    }

    public synchronized void sendUsernameToServer(String username) {
        if (out != null) {
            out.println("USERNAME:" + username);
        }
    }

    public void createdRoomNameToServer(String roomName) {
        out.println("ROOMNAME_CREATED:" + roomName);
    }

    public void selectedRoomNameToServer(String roomName) {
        this.roomName = roomName;
        out.println("ROOMNAME_SELECTED:" + roomName);
    }

    public void MoveStoneToServer(String roomName, int playerOrder, int x, int y) {
        out.println("MOVE:" + roomName + ":" + playerOrder + ":" + x + ":" + y);
        System.out.println("MoveStoneToServer, playerOrder = " + playerOrder);
        // 자기 돌을 놓은 후 바로 그리기 요청 (서버에서 성공적으로 처리되면 클라이언트 화면에도 반영)
        Platform.runLater(() -> inGameGui.updatePlayerMove(x, y));
    }

    public synchronized void handleServerMessage(String message) {
        if (message.startsWith("PLAYER_ORDER:")) {
            playerOrder = handlePlayerOrder(message.substring("PLAYER_ORDER:".length()));
            System.out.println("handleServerMessage, playerOrder = " + playerOrder);
            notifyPlayerOrderUpdated();
        } else if (message.startsWith("ROOM_LIST:")) {
            handleRoomList(message.substring("ROOM_LIST:".length()));
        } else if (message.startsWith("OPPONENT_MOVE:")) {
            handleMoveStone(message.substring("OPPONENT_MOVE:".length()));
        } else if (message.startsWith("GAMEOVER:")) {
            handleGameOver(message.substring("GAMEOVER:".length()));
        } else if (message.startsWith("READY:")) {
            ready = true;
        } else if (message.startsWith("ERROR:")) {
            handleError(message.substring("ERROR:".length()));
        }
    }

    public void handleRoomList(String roomListMessage) {
        roomNames = roomListMessage.split(",");
        Platform.runLater(() -> {
            // 로비 GUI 업데이트
            lobby_gui.updateRoomList(roomNames);  // 이렇게 정적 메서드를 호출합니다.
        });
    }

    public int handlePlayerOrder(String message) {
        try {
            return Integer.parseInt(message);
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
            return -1;
        }
    }

    public int getPlayerOrder() {
        return playerOrder;
    }

    public String[] getRoomList() {
        return roomNames != null ? roomNames : new String[0];
    }

    public void handleMoveStone(String message) {
        if (ready) {
            String[] parts = message.split(":");
            roomName = parts[0];
            opponentMovePlayerOrder = Integer.parseInt(parts[1]);
            opponentMoveX = Integer.parseInt(parts[2]);
            opponentMoveY = Integer.parseInt(parts[3]);

            Platform.runLater(() -> inGameGui.updateOpponentMove(opponentMovePlayerOrder, opponentMoveX, opponentMoveY));
        }
    }

    public void handleGameOver(String message) {
        String winPlayerName = message;
        Platform.runLater(() -> {
            inGameGui.showGameOverDialog(winPlayerName);
            inGameGui.goToLobby(this);
        });
        ready = false;
    }

    public void handleError(String message) {
        Platform.runLater(() -> inGameGui.showErrorMsg());
    }

    public void requestRoomList() {
        // 서버에 방 목록을 요청하는 로직
        // 예를 들어, 서버와 소켓을 통해 통신하여 방 목록을 요청할 수 있습니다.
        // 서버가 방 목록을 반환하면, 클라이언트에서 `handleRoomList()` 메서드를 호출하도록 합니다.

        String roomListMessage = "Room1,Room2,Room3";  // 예시로 받은 데이터
        handleRoomList(roomListMessage);  // 받은 데이터 처리
    }

    public void startGame(ingame_gui inGameGui) {
        this.inGameGui = inGameGui;
        setPlayerOrderListener(inGameGui); // 리스너 설정
    }
}
