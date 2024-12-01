package Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class omok_server {
    private static final int PORT = 12345;

    private static Set<ClientHandler> clients = new HashSet<>();
    private static omok_userManager userManager = new omok_userManager();
    private static omok_roomManager roomManager = new omok_roomManager();
    private static Set<omok_gameBoard> gameLogics = new HashSet<>();


    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(PORT);

            System.out.println("Omok Server is running on port " + PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected: " + clientSocket);

                // 새로운 클라이언트를 Set에 추가
                ClientHandler clientHandler = new ClientHandler(clientSocket);
                clients.add(clientHandler);

                // 클라이언트를 처리하는 스레드 시작
                clientHandler.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 각 클라이언트를 처리하는 스레드
    private static class ClientHandler extends Thread {
        private Socket clientSocket;
        private PrintWriter out;
        private BufferedReader in;
        private String userName;

        public ClientHandler(Socket socket) {
            this.clientSocket = socket;
            try {
                out = new PrintWriter(socket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            try {
                // 클라이언트로부터 메시지를 읽어들이고 일단 출력
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    handleClientMessage(inputLine);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                // 클라이언트가 연결을 끊을 경우 Set에서 제거
                clients.remove(this);
                try {
                    clientSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        public void handleClientMessage(String message) {
            // 메시지를 처리하는 로직을 여기에 추가
            if (message.startsWith("USERNAME:")) {
                handleUsernameMessage(message.substring("USERNAME:".length()));
            } else if (message.startsWith("ROOMNAME_CREATED:")) {       // create room
                handleCreateRoom(message.substring("ROOMNAME_CREATED:".length()));
            } else if (message.startsWith("ROOMNAME_SELECTED:")) {
                handleSelectRoom(message.substring("ROOMNAME_SELECTED:".length()));
            } else if (message.startsWith("MOVE:")) {
                handleMove(message.substring("MOVE:".length()));
            }
        }

        private void handleUsernameMessage(String username) {
            // 사용자 이름을 처리하는 로직을 여기에 추가
            System.out.println("Received username: " + username);
            userName = username;
            userManager.addUser(username);
        }

        private void handleCreateRoom(String roomname) {
            System.out.println("Created roomname: " + roomname);
            roomManager.createRoom(roomname);

            List<omok_room> allRooms = roomManager.getAllRooms();
            StringBuilder roomListMessage = new StringBuilder("ROOM_LIST:");
            for (omok_room room : allRooms) {
                roomListMessage.append(room.getName()).append(",");
            }
            sendMessageToAllClients(roomListMessage.toString());
            System.out.println(roomListMessage);
        }

        private void handleSelectRoom(String roomname) {
            System.out.println("Selected roomname : " + roomname);
            omok_room selectedRoom = roomManager.findRoomByName(roomname);
            int playerOrder = selectedRoom.addPlayer(userName);
            if (playerOrder != -1) {
                System.out.println("Players: " + selectedRoom.getPlayers());

                sendMessageToClient(out,"PLAYER_ORDER:" + playerOrder);

                if (selectedRoom.setPlayerReady(userName)) {
                    for (String playerName: selectedRoom.getPlayers()) {
                        ClientHandler player = findClientHandlerByUsername(playerName);
                        sendMessageToClient(player.out, "READY:" + roomname);
                    }
                }
            } else {
                System.out.println("Room is full. Cannot join.");
            }
        }

        public void handleMove(String message) {
            // 메시지에서 roomID, playerOrder, X, Y 추출
            String[] parts = message.split(":");
            String roomName = parts[0];
            int playerOrder = Integer.parseInt(parts[1]);
            System.out.println("handleMove, playerOrder = " + playerOrder);
            int x = Integer.parseInt(parts[2]);
            int y = Integer.parseInt(parts[3]);

            // roomID에 해당하는 gameLogic을 찾아 돌을 둔다
            omok_gameBoard gameBoard = findGameBoardByRoomName(roomName);
            if (gameBoard != null) {
                if (!gameBoard.isValidMove(x, y)) {
                    sendMessageToClient(out, "ERROR:" + 001);
                    return;
                }
                gameBoard.makeMove(x, y, playerOrder);
                if (gameBoard.checkWin(x, y, playerOrder)) {
                    omok_room room = roomManager.findRoomByName(roomName);
                    String winPlayerName = room.getPlayerName(playerOrder);
                    for (String playerName : room.getPlayers()) {
                        ClientHandler player = findClientHandlerByUsername(playerName);
                        sendMessageToClient(player.out, "GAMEOVER:" + winPlayerName);
                        roomManager.removeRoom(roomName);
                    }
                } else
                    sendOpponentMoveUpdate(roomName, playerOrder, x, y);
            }
        }

        private omok_gameBoard findGameBoardByRoomName(String roomName) {
            // roomID에 해당하는 gameLogic을 찾아 반환
            omok_room room = roomManager.findRoomByName(roomName);
            if (room != null) {
                // 방에 연결된 게임 로직 반환
                return room.getGameBoard();
            } else {
                return null;
            }
        }

        private void sendOpponentMoveUpdate(String roomName, int playerOrder, int x, int y) {
            // 해당 방에 속한 다른 플레이어에게 메시지 전송 로직 추가
            omok_room room = roomManager.findRoomByName(roomName);
            if (room != null) {
                // 방에 속한 모든 플레이어에게 메시지 전송
                for (String playerName : room.getPlayers()) {
                    if (!playerName.equals(userName)) {
                        // 자기 자신을 제외한 다른 플레이어에게 메시지 전송
                        ClientHandler opponent = findClientHandlerByUsername(playerName);
                        if (opponent != null) {
                            sendMessageToClient(opponent.out, "OPPONENT_MOVE:" + roomName + ":" + playerOrder + ":" + x + ":" + y);
                        }
                    }
                }
            }
        }

        private ClientHandler findClientHandlerByUsername(String username) {
            // 특정 username을 가진 클라이언트를 찾아 반환
            for (ClientHandler client : clients) {
                if (client.userName != null && client.userName.equals(username)) {
                    return client;
                }
            }
            return null;
        }

        private void sendMessageToClient(PrintWriter clientOut, String message) {
            // 특정 클라이언트에게 메시지 보내기
            clientOut.println(message);
        }

        private void sendMessageToAllClients(String message) {
            // 모든 클라이언트에게 메시지를 보내는 메소드
            for (ClientHandler client : clients) {
                client.sendMessageToClient(client.out, message);
            }
        }
    }
}
