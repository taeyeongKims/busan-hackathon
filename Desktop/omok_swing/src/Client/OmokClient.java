package Client;

import GUI.IngameGui;
import GUI.LobbyGui;
import GUI.LoginGui;
import javafx.application.Platform;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class OmokClient {

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
    private IngameGui inGameGui;
    private String roomName;
    private boolean ready = false;

    public OmokClient(String username) {
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
                LoginGui.showErrorDialog("서버에 연결할 수 없습니다. 프로그램을 종료합니다.");
                System.exit(0);
            });
        }
    }

//    게임방 이름 가져오기
    public String getRoomName() {
        return roomName;
    }

//    클라이언트가 들어간 방의 게임GUI 세팅
    public void setInGameGui(IngameGui inGameGui) {
        this.inGameGui = inGameGui;
    }

//    게임방 이름 정하기
    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

//    IngameGui를 client의 리스너로 세팅하기
    public void setPlayerOrderListener(PlayerOrderListener listener) {
        playerOrderListener = listener;
        System.out.println("listener = " + listener);
    }

//    리스너가 생기면 IngameGui에 클라이언트의 번호 세팅하기
    private void notifyPlayerOrderUpdated() {
        if (playerOrderListener != null) {
            System.out.println("notifyPlayerOrderUpdated, playerOrder = " + playerOrder); // 로그 추가
            Platform.runLater(() -> playerOrderListener.onPlayerOrderUpdated(playerOrder));
        } else {
            System.out.println("playerOrderListener is null, cannot notify update."); // 리스너가 null일 경우 로그 추가
        }
    }

//    서버의 응답을 받는 메서드
    private void startServerListener() {
        new Thread(() -> {
            try {
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    handleServerMessage(inputLine);
                }
            } catch (IOException e) {
                System.err.println("Connection lost: " + e.getMessage());
                Platform.runLater(() -> {
                    LoginGui.showErrorDialog("서버와의 연결이 끊어졌습니다. 프로그램을 종료합니다.");
                    System.exit(0);
                });
            }
        }).start();
    }

//    사용자의 이름을 서버로 전송
    public synchronized void sendUsernameToServer(String username) {
        if (out != null) {
            out.println("USERNAME:" + username);
        }
    }

//    서버에 게임방을 만든다는 요청 보내기
    public void createdRoomNameToServer(String roomName) {
        out.println("ROOMNAME_CREATED:" + roomName);
    }

//    서버에 게임방을 선택했다는 요청 보내기
    public void selectedRoomNameToServer(String roomName) {
        this.roomName = roomName;
        out.println("ROOMNAME_SELECTED:" + roomName);
    }

//    서버에 바둑돌을 놓았다는 요청 보내기
    public void MoveStoneToServer(String roomName, int playerOrder, int x, int y) {
        out.println("MOVE:" + roomName + ":" + playerOrder + ":" + x + ":" + y);
    }

//    서버의 응답 메세지를 처리하는 핸들링 메서드
    public synchronized void handleServerMessage(String message) {
        if (message.startsWith("PLAYER_ORDER:")) {
            playerOrder = handlePlayerOrder(message.substring("PLAYER_ORDER:".length()));
            notifyPlayerOrderUpdated();
        } else if (message.startsWith("ROOM_LIST:")) {
            handleRoomList(message.substring("ROOM_LIST:".length()));
        } else if (message.startsWith("OPPONENT_MOVE:")) {
            handleMoveStone(message.substring("OPPONENT_MOVE:".length()));
        } else if (message.startsWith("MOVE_CONFIRMED:")) {  // 서버가 움직임 확인
            handleMoveConfirmed(message.substring("MOVE_CONFIRMED:".length()));
        } else if (message.startsWith("GAMEOVER:")) {
            handleGameOver(message.substring("GAMEOVER:".length()));
        } else if (message.startsWith("READY:")) {
            ready = true;
        } else if (message.startsWith("ERROR:")) {  // 서버가 오류 응답
            handleError(message.substring("ERROR:".length()));
        }
    }

//    바둑돌을 놓을 수 있는 곳일 때, 바둑돌을 놓는 메서드
    private void handleMoveConfirmed(String message) {
        String[] parts = message.split(":");
        int x = Integer.parseInt(parts[0]);
        int y = Integer.parseInt(parts[1]);
        Platform.runLater(() -> inGameGui.updatePlayerMove(x, y));  // 서버에서 확인된 경우만 돌 그리기
    }

//    게임방 리스트를 업데이트하는 메서드
    public void handleRoomList(String roomListMessage) {
        roomNames = roomListMessage.split(",");
        Platform.runLater(() -> {
            // 로비 GUI 업데이트
            LobbyGui.updateRoomList(roomNames);  // 이렇게 정적 메서드를 호출합니다.
        });
    }
// 플레이어의 번호를 반환하는 메서드
    public int handlePlayerOrder(String message) {
        try {
            return Integer.parseInt(message);
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
            return -1;
        }
    }
// 플레이어의 번호를 얻는 메서드
    public int getPlayerOrder() {
        return playerOrder;
    }
// 게임방 리스트를 얻는 메서드
    public String[] getRoomList() {
        return roomNames != null ? roomNames : new String[0];
    }
// 상대방이 돌을 놓을 때, 서버에서 보내는 요청을 해들링하는 메서드
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
// 게임 오버 핸들링 메서드
    public void handleGameOver(String message) {
        String winPlayerName = message;
        Platform.runLater(() -> {
            inGameGui.showGameOverDialog(winPlayerName);
            inGameGui.goToLobby(this);
        });
        ready = false;
    }
// 로직상 에러가 생겼을 때, 에러 박스가 뜨게 만드는 메서드
    public void handleError(String message) {
        Platform.runLater(() -> inGameGui.showErrorMsg());
    }

    public void requestRoomList() {
        // 서버에 방 목록을 요청하는 로직
        // 예를 들어, 서버와 소켓을 통해 통신하여 방 목록을 요청할 수 있습니다.
        // 서버가 방 목록을 반환하면, 클라이언트에서 `handleRoomList()` 메서드를 호출하도록 합니다.
        if (out != null) {
            out.println("REQUEST_ROOM_LIST"); // 서버에 방 목록 요청
        }

        String roomListMessage = "Room1,Room2,Room3";  // 예시로 받은 데이터
        handleRoomList(roomListMessage);  // 받은 데이터 처리
    }
}
