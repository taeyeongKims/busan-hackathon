package Server;

import java.util.HashMap;
import java.util.Map;

public class omok_userManager {
    private Map<String, UserStats> userStatsMap;

    public omok_userManager() {
        userStatsMap = new HashMap<>();
    }

    // 사용자 추가
    public void addUser(String username) {
        if (!userStatsMap.containsKey(username)) {
            userStatsMap.put(username, new UserStats());
        }
    }

    // 사용자 승리 기록 갱신
    public void updateUserWin(String username) {
        UserStats userStats = userStatsMap.get(username);
        if (userStats != null) {
            userStats.incrementWins();
        }
    }

    // 사용자 패배 기록 갱신
    public void updateUserLoss(String username) {
        UserStats userStats = userStatsMap.get(username);
        if (userStats != null) {
            userStats.incrementLosses();
        }
    }

    // 사용자 승리 횟수 가져오기
    public int getUserWins(String username) {
        UserStats userStats = userStatsMap.get(username);
        return (userStats != null) ? userStats.getWins() : 0;
    }

    // 사용자 패배 횟수 가져오기
    public int getUserLosses(String username) {
        UserStats userStats = userStatsMap.get(username);
        return (userStats != null) ? userStats.getLosses() : 0;
    }

    // 내부 클래스: 사용자 승패 정보 저장 클래스
    private static class UserStats {
        private int wins;
        private int losses;

        public UserStats() {
            this.wins = 0;
            this.losses = 0;
        }

        public int getWins() {
            return wins;
        }

        public int getLosses() {
            return losses;
        }

        public void incrementWins() {
            wins++;
        }

        public void incrementLosses() {
            losses++;
        }
    }
}

