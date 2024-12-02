package Server;

public class OmokGameBoard {
    private int[][] board;  // 2차원 배열로 보드 상태 표현
    private static final int BOARD_SIZE = 19;
    private static final int WINNING_CONDITION = 5;


    public OmokGameBoard() {
        initializeBoard();
    }

    private void initializeBoard() {
        // 보드 초기화: 모든 위치를 빈 공간(2)으로 설정
        board = new int[BOARD_SIZE][BOARD_SIZE];
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                board[i][j] = 2;  // 2는 빈 공간을 나타냄
            }
        }
    }

    public int[][] getBoard() {
        return board;
    }

    public void printBoard() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }

    public boolean isValidMove(int x, int y) {
        // 유효한 좌표인지 확인: 범위 내이고 빈 공간인 경우에만 유효
        return x >= 0 && x < BOARD_SIZE && y >= 0 && y < BOARD_SIZE && board[x][y] == 2;
    }

    public void makeMove(int x, int y, int playerOrder) {
        // 돌을 놓는 메서드
        if (isValidMove(x, y)) {
            board[x][y] = playerOrder;
        }
    }

    public boolean checkWin(int x, int y, int playerOrder) {
        return checkDirection(x, y, playerOrder, 1, 0) || // 가로
                checkDirection(x, y, playerOrder, 0, 1) || // 세로
                checkDirection(x, y, playerOrder, 1, 1) || // 대각선 \
                checkDirection(x, y, playerOrder, 1, -1);  // 대각선 /
    }

    private boolean checkDirection(int x, int y, int playerOrder, int dx, int dy) {
        int count = 0; // 현재 위치의 돌을 세기 위해 초기화
        count += countConsecutiveStones(x, y, playerOrder, dx, dy, 1); // 오른쪽 방향
        count += countConsecutiveStones(x, y, playerOrder, -dx, -dy, 1); // 왼쪽 방향

        return count > WINNING_CONDITION;
    }

    private int countConsecutiveStones(int x, int y, int playerOrder, int dx, int dy, int length) {
        if (length == WINNING_CONDITION) {
            return length; // 이길 조건 충족
        }

        int newX = x + dx;
        int newY = y + dy;

        if (newX < 0 || newX >= BOARD_SIZE || newY < 0 || newY >= BOARD_SIZE || board[newX][newY] != playerOrder) {
            return length;
        }

        return countConsecutiveStones(newX, newY, playerOrder, dx, dy, length + 1);
    }

}

