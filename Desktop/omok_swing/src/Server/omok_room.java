package Server;

import java.util.ArrayList;
import java.util.List;

public class omok_room {
    private static final int MAX_PLAYERS = 2;

    private String roomname;
    private List<String> players;
    private omok_gameBoard gameBoard;
    private boolean player1Ready;
    private boolean player2Ready;
    public omok_room(String roomname) {
        this.roomname = roomname;
        this.players = new ArrayList<>();
        this.gameBoard = new omok_gameBoard();
        this.player1Ready = false;
        this.player2Ready = false;
    }

    public String getName() {
        return roomname;
    }

    public omok_gameBoard getGameBoard() {
        return gameBoard;
    }

    // 플레이어 레디 상태 설정
    public boolean setPlayerReady(String playerName) {
        if (players.contains(playerName)) {
            if (playerName.equals(players.get(0))) {
                player1Ready = true;
            } else if (playerName.equals(players.get(1))) {
                player2Ready = true;
            }
        }

        return player1Ready && player2Ready;
    }

    // 플레이어 추가
    public int addPlayer(String playerName) {
        if (players.size() < MAX_PLAYERS) {
            players.add(playerName);
            return players.size() - 1; // 성공적으로 추가되면 플레이어의 순서(0 또는 1)를 반환
        } else {
            return -1; // 룸이 이미 꽉 참
        }
    }

    public String getPlayerName(int playerOrder) {
        return players.get(playerOrder);
    }

    // 플레이어 제거
    public void removePlayer(String playerName) {
        players.remove(playerName);
    }

    // 현재 룸에 참여 중인 플레이어 리스트 반환
    public List<String> getPlayers() {
        return new ArrayList<>(players);
    }
}

