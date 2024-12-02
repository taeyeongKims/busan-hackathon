package Server.handler;

import Server.OmokRoom;

import java.util.List;

import static Server.OmokServer.*;

public class RoomHandler {
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
