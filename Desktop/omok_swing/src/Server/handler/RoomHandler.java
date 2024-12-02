package Server.handler;

import Server.OmokRoom;

import java.util.List;

import static Server.OmokServer.*;

public class RoomHandler {
//    게임방을 만드는 핸들링 메서드
    void handleCreateRoom(String roomname) {
        System.out.println("Created roomname: " + roomname);
        roomManager.createRoom(roomname);

        List<OmokRoom> allRooms = roomManager.getAllRooms();
        StringBuilder roomListMessage = new StringBuilder("ROOM_LIST:");
        for (OmokRoom room : allRooms) {
            roomListMessage.append(room.getName()).append(",");
        }
        messageHandler.sendMessageToAllClients(roomListMessage.toString());
        System.out.println(roomListMessage);
    }

//    클라이언트로부터 방을 선택했다는 요청을 받았을 떄, 서버에서 방과 관련된 메서드를 핸들링 하는 메서드
    void handleSelectRoom(String roomName, String userName) {
        System.out.println("Selected roomname : " + roomName);
        OmokRoom selectedRoom = roomManager.findRoomByName(roomName);
        int playerOrder = selectedRoom.addPlayer(userName);
        ClientHandler client = ClientHandler.findClientHandlerByUsername(userName);

        if (playerOrder != -1) {
            System.out.println("Players: " + selectedRoom.getPlayers());
            messageHandler.sendMessageToClient(client.out,"PLAYER_ORDER:" + playerOrder);

            if (selectedRoom.setPlayerReady(userName)) {
                for (String playerName: selectedRoom.getPlayers()) {
                    ClientHandler player = ClientHandler.findClientHandlerByUsername(playerName);
                    messageHandler.sendMessageToClient(player.out, "READY:" + roomName);
                }
            }
        } else {
            System.out.println("Room is full. Cannot join.");
        }
    }
}
