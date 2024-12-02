package Server;

import Server.handler.ClientHandler;
import Server.handler.GameHandler;
import Server.handler.MessageHandler;
import Server.handler.RoomHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

public class OmokServer {
    private static final int PORT = 12345;

    public static Set<ClientHandler> clients = new HashSet<>();
    public static OmokUserManager userManager = new OmokUserManager();
    public static OmokRoomManager roomManager = new OmokRoomManager();
    public static Set<OmokGameBoard> gameLogics = new HashSet<>();
    public static GameHandler gameHandler = new GameHandler();
    public static MessageHandler messageHandler = new MessageHandler();
    public static RoomHandler roomHandler = new RoomHandler();


    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(PORT);

            System.out.println("Omok Server is running on port " + PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected: " + clientSocket);

                // 새로운 클라이언트를 Set에 추가
                ClientHandler clientHandler = new ClientHandler(clientSocket);
                clients.add(clientHandler);

                // 클라이언트를 처리하는 스레드 시작
                clientHandler.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
