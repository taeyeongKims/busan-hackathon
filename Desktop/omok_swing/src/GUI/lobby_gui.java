package GUI;

import Client.omok_client;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class lobby_gui {
    private omok_client omokClient;
    private static ListView<String> roomListView;

    public lobby_gui(omok_client omokClient) {
        this.omokClient = omokClient;
    }

    public void start(Stage primaryStage) {
        primaryStage.setTitle("Lobby");

        // 방 목록
        roomListView = new ListView<>();

        // 버튼
        Button createButton = new Button("Create");
        Button enterButton = new Button("Enter");
        Button refreshButton = new Button("Refresh");

        // 방 생성
        createButton.setOnAction(e -> {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Create Room");
            dialog.setHeaderText(null);
            dialog.setContentText("Enter room name:");

            dialog.showAndWait().ifPresent(roomName -> {
                if (!roomName.isEmpty()) {
                    omokClient.createdRoomNameToServer(roomName);
                }
            });
        });

        // 방 입장
        enterButton.setOnAction(e -> {
            String selectedRoom = roomListView.getSelectionModel().getSelectedItem();
            if (selectedRoom != null) {

                // 게임 화면으로 전환
                ingame_gui ingame = new ingame_gui(omokClient, selectedRoom);
                omokClient.selectedRoomNameToServer(selectedRoom);
                omokClient.setRoomName(selectedRoom);
                ingame.start(primaryStage);

            } else {
                showErrorDialog("Please select a room to join.");
            }
        });

        // 방 목록 새로고침
        refreshButton.setOnAction(e -> {
            // 서버로부터 방 목록을 요청
            omokClient.requestRoomList();  // 예시로 서버에 요청하는 메서드 호출
        });

        // 버튼 패널
        HBox buttonPanel = new HBox(10, createButton, enterButton, refreshButton);

        // 레이아웃 설정
        BorderPane layout = new BorderPane();
        layout.setCenter(roomListView);
        layout.setBottom(buttonPanel);

        Scene scene = new Scene(layout, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // 인스턴스 메서드로 수정
    public static void updateRoomList(String[] roomNames) {
        Platform.runLater(() -> {
            roomListView.getItems().clear();  // 기존 항목들 지우기
            roomListView.getItems().addAll(roomNames);  // 새로운 방 목록 추가
        });
    }

    public static void showErrorDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // 로비 화면을 보여주는 메서드
    public static void showLobby(omok_client client) {
        Stage primaryStage = new Stage();
        lobby_gui lobby = new lobby_gui(client);
        lobby.start(primaryStage);
    }
}