package Server.handler;

import Server.OmokGameBoard;
import Server.OmokRoom;

import static Server.OmokServer.*;

public class GameHandler {
    public void handleMove(String message, String userName) {
        // 메시지에서 roomID, playerOrder, X, Y 추출
        ClientHandler client = ClientHandler.findClientHandlerByUsername(userName);
        String[] parts = message.split(":");
        String roomName = parts[0];
        int playerOrder = Integer.parseInt(parts[1]);
        int x = Integer.parseInt(parts[2]);
        int y = Integer.parseInt(parts[3]);

        // roomID에 해당하는 gameLogic을 찾아 돌을 둔다
        OmokGameBoard gameBoard = findGameBoardByRoomName(roomName);
        if (gameBoard != null) {
            if (!gameBoard.isValidMove(x, y)) {
                ClientHandler player = ClientHandler.findClientHandlerByUsername(userName);
                messageHandler.sendMessageToClient(player.out, "ERROR:Invalid move");
                return;
            }
            gameBoard.makeMove(x, y, playerOrder);
            ClientHandler user = ClientHandler.findClientHandlerByUsername(userName);
            messageHandler.sendMessageToClient(user.out, "MOVE_CONFIRMED:" + x + ":" + y);  // 움직임 확인
            sendOpponentMoveUpdate(userName, roomName, playerOrder, x, y);

            if (gameBoard.checkWin(x, y, playerOrder)) {
                OmokRoom room = roomManager.findRoomByName(roomName);
                String winPlayerName = room.getPlayerName(playerOrder);
                for (String playerName : room.getPlayers()) {
                    ClientHandler player = ClientHandler.findClientHandlerByUsername(playerName);
                    messageHandler.sendMessageToClient(player.out, "GAMEOVER:" + winPlayerName);
                    roomManager.removeRoom(roomName);
                }
            } else
                sendOpponentMoveUpdate(userName, roomName, playerOrder, x, y);
        }
    }

    private OmokGameBoard findGameBoardByRoomName(String roomName) {
        // roomID에 해당하는 gameLogic을 찾아 반환
        OmokRoom room = roomManager.findRoomByName(roomName);
        if (room != null) {
            // 방에 연결된 게임 로직 반환
            return room.getGameBoard();
        } else {
            return null;
        }
    }

    private void sendOpponentMoveUpdate(String myName, String roomName, int playerOrder, int x, int y) {
        // 해당 방에 속한 다른 플레이어에게 메시지 전송 로직 추가
        OmokRoom room = roomManager.findRoomByName(roomName);
        if (room != null) {
            // 방에 속한 모든 플레이어에게 메시지 전송
            for (String playerName : room.getPlayers()) {
                if (!playerName.equals(myName)) {
                    // 자기 자신을 제외한 다른 플레이어에게 메시지 전송
                    ClientHandler opponent = ClientHandler.findClientHandlerByUsername(playerName);
                    if (opponent != null) {
                        messageHandler.sendMessageToClient(opponent.out, "OPPONENT_MOVE:" + roomName + ":" + playerOrder + ":" + x + ":" + y);
                    }
                }
            }
        }
    }
}
