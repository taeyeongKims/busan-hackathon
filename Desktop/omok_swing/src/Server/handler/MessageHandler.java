package Server.handler;

import Server.omok_room;
import Server.omok_roomManager;
import Server.omok_server;

import java.io.PrintWriter;
import java.util.List;

import static Server.omok_server.*;

public class MessageHandler {
    public String handleClientMessage(String message, String userName) {
        // 메시지를 처리하는 로직을 여기에 추가
        if (message.startsWith("USERNAME:")) {
            userName = handleUsernameMessage(message.substring("USERNAME:".length()));
        } else if (message.startsWith("ROOMNAME_CREATED:")) {       // create room
            roomHandler.handleCreateRoom(message.substring("ROOMNAME_CREATED:".length()));
        } else if (message.startsWith("ROOMNAME_SELECTED:")) {
            roomHandler.handleSelectRoom(message.substring("ROOMNAME_SELECTED:".length()), userName);
        } else if (message.equals("REQUEST_ROOM_LIST")) {  // 방 목록 요청
            handleRoomListRequest(userName);
        } else if (message.startsWith("MOVE:")) {
            gameHandler.handleMove(message.substring("MOVE:".length()), userName);
        }
        return userName;
    }

    private void handleRoomListRequest(String userName) {
        omok_roomManager roomManager = omok_server.roomManager;
        List<omok_room> allRooms = roomManager.getAllRooms();

        StringBuilder roomListMessage = new StringBuilder("ROOM_LIST:");
        for (omok_room room : allRooms) {
            roomListMessage.append(room.getName()).append(",");
        }

        ClientHandler client = ClientHandler.findClientHandlerByUsername(userName);
        if (client != null) {
            messageHandler.sendMessageToClient(client.out, roomListMessage.toString());
        }
    }

    private String handleUsernameMessage(String username) {
        // 사용자 이름을 처리하는 로직을 여기에 추가
        System.out.println("Received username: " + username);
        userManager.addUser(username);
        return username;
    }

    void sendMessageToClient(PrintWriter clientOut, String message) {
        // 특정 클라이언트에게 메시지 보내기
        clientOut.println(message);
    }

    void sendMessageToAllClients(String message) {
        // 모든 클라이언트에게 메시지를 보내는 메소드
        for (ClientHandler client : clients) {
            sendMessageToClient(client.out, message);
        }
    }
}
