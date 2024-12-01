package GUI;

import Client.PlayerOrderListener;
import Client.omok_client;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class ingame_gui implements PlayerOrderListener {

    private omok_client omokClient;
    private Canvas canvas;
    private int playerOrder;  // 0이면 흑, 1이면 백
    private String roomName;

    // 바둑판 배경 이미지 경로
    private static final String BOARD_BACKGROUND_IMAGE = "/image/omok_board.jpg"; // 바둑판 배경 이미지

    // 흑, 백 바둑돌 이미지 경로
    private static final String BLACK_STONE_IMAGE = "/image/BlackStone.gif";
    private static final String WHITE_STONE_IMAGE = "/image/WhiteStone.gif";

    // 바둑판 크기 설정
    private static final int CELL_SIZE = 30; // 각 셀의 크기
    private static final int BOARD_SIZE = 19; // 19x19 바둑판

    public ingame_gui(omok_client omokClient, String roomName) {
        this.omokClient = omokClient;
        this.roomName = roomName;
        this.omokClient.setPlayerOrderListener(this); // 리스너 설정
        this.omokClient.setInGameGui(this);
    }


    public void start(Stage primaryStage) {
        primaryStage.setTitle("Omok Game");

        // Canvas를 사용하여 바둑판을 그릴 공간 생성
        canvas = new Canvas(BOARD_SIZE * CELL_SIZE, BOARD_SIZE * CELL_SIZE);  // Canvas 크기 설정
        GraphicsContext gc = canvas.getGraphicsContext2D();

        // 바둑판 배경 그리기
        Image boardImage = new Image(getClass().getResourceAsStream(BOARD_BACKGROUND_IMAGE));
        gc.drawImage(boardImage, 0, 0, BOARD_SIZE * CELL_SIZE, BOARD_SIZE * CELL_SIZE);

        // 마우스 클릭 시 돌을 놓는 이벤트 처리
        canvas.setOnMouseClicked(event -> {
            int x = (int) event.getX() / CELL_SIZE;
            int y = (int) event.getY() / CELL_SIZE;

            System.out.println("gui start, playerOrder = " + playerOrder);
            // 클릭된 위치에 돌을 놓는 코드
            omokClient.MoveStoneToServer(roomName, playerOrder, x, y);
            updatePlayerMove(x, y);
        });

        // 레이아웃 설정: StackPane을 사용하여 Canvas를 중앙에 배치
        StackPane canvasStack = new StackPane();
        canvasStack.getChildren().add(canvas);

        // 상단 버튼 영역
        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setStyle("-fx-padding: 10;");
        // 추가 버튼을 넣고 싶다면 이곳에 버튼을 추가할 수 있습니다.

        VBox layout = new VBox(10);  // StackPane과 버튼을 수직으로 배치
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(buttonBox, canvasStack);

        Scene scene = new Scene(layout, BOARD_SIZE * CELL_SIZE + 100, BOARD_SIZE * CELL_SIZE + 150);  // Scene 크기 확장
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // 플레이어가 돌을 놓을 때 호출되는 메서드
    public void updatePlayerMove(int x, int y) {
        Platform.runLater(() -> {
            drawStone(x, y, playerOrder);  // 플레이어의 돌을 그린다
        });
    }

    // 상대방이 돌을 놓을 때 호출되는 메서드
    public void updateOpponentMove(int opponentMovePlayerOrder, int x, int y) {
        Platform.runLater(() -> {
            drawStone(x, y, opponentMovePlayerOrder);  // 상대방의 돌을 그린다
        });
    }

    // 돌을 그리는 메서드
    private void drawStone(int x, int y, int playerOrder) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        String stoneImagePath = (playerOrder == 0) ? BLACK_STONE_IMAGE : WHITE_STONE_IMAGE;
        Image stoneImage = new Image(getClass().getResourceAsStream(stoneImagePath));

        // 돌의 위치에 맞춰 그리기 (CELL_SIZE - 5는 돌의 크기 조정)
        gc.drawImage(stoneImage, x * CELL_SIZE, y * CELL_SIZE, CELL_SIZE - 5, CELL_SIZE - 5);
    }

    // 플레이어의 순서가 갱신될 때 호출되는 메서드
    public void onPlayerOrderUpdated(int playerOrder) {
        this.playerOrder = playerOrder;
        System.out.println("Player order updated: " + playerOrder);
    }

    public void showGameOverDialog(String winPlayerName) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Game Over");
            alert.setHeaderText(null);
            alert.setContentText("Game Over! Player " + winPlayerName + " wins!");
            alert.showAndWait();
        });
    }

    // 게임 중 에러 메시지를 표시하는 메서드
    public void showErrorMsg() {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("An Error Occurred");
            alert.setContentText("An unexpected error has occurred during the game.");
            alert.showAndWait();
        });
    }

    // 로비로 돌아가는 메서드
    public void goToLobby(omok_client client) {
        Platform.runLater(() -> {
            lobby_gui.showLobby(client); // 로비 화면으로 돌아가기
        });
    }
}
