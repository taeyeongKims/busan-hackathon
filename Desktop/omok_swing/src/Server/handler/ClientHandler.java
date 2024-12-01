package Server.handler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import static Server.omok_server.clients;
import static Server.omok_server.messageHandler;

// 각 클라이언트를 처리하는 스레드
public class ClientHandler extends Thread {
    private Socket clientSocket;
    PrintWriter out;
    private BufferedReader in;
    public String userName;

    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
        try {
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            // 클라이언트로부터 메시지를 읽어들이고 일단 출력
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                userName = messageHandler.handleClientMessage(inputLine, userName);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 클라이언트가 연결을 끊을 경우 Set에서 제거
            clients.remove(this);
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    static ClientHandler findClientHandlerByUsername(String username) {
        // 특정 username을 가진 클라이언트를 찾아 반환
        for (ClientHandler client : clients) {
            if (client.userName != null && client.userName.equals(username)) {
                return client;
            }
        }
        return null;
    }
}
