import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerListener {

    private MultiUser multiUser = new MultiUser();

    public ServerListener() throws IOException {
        ServerSocket serverSocket = new ServerSocket(8080);
        try{
            while (true) {
                //Socket socket = serverSocket.accept();
                Server player1 = new Server(serverSocket.accept(), multiUser);
                Server player2 = new Server(serverSocket.accept(), multiUser);
                player1.start();
                player2.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
