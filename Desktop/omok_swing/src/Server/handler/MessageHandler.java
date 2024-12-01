package Server.handler;

import java.io.PrintWriter;

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
        } else if (message.startsWith("MOVE:")) {
            gameHandler.handleMove(message.substring("MOVE:".length()), userName);
        }
        return userName;
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
