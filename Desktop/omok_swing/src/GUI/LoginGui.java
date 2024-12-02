package GUI;

import Client.OmokClient;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LoginGui extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Login");

        // Username 입력 필드와 버튼
        TextField usernameField = new TextField();
        usernameField.setPromptText("Enter your username");
        Button loginButton = new Button("Enter");

        // 로그인 버튼 클릭 이벤트
        loginButton.setOnAction(e -> {
            String username = usernameField.getText().trim();
            if (username.isEmpty()) {
                showErrorDialog("Username cannot be empty!");
            } else {
                OmokClient client = new OmokClient(username);
                client.sendUsernameToServer(username);

                // 로비로 전환
                LobbyGui lobby = new LobbyGui(client);
                lobby.start(primaryStage);
            }
        });

        VBox layout = new VBox(10, usernameField, loginButton);
        layout.setStyle("-fx-alignment: center; -fx-padding: 20;");

        Scene scene = new Scene(layout, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

//  에러 Dialog를 보여주는 메서드
    public static void showErrorDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
