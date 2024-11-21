import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerListener {

    private MultiUser multiUser = new MultiUser();

    public ServerListener(){

        try(ServerSocket serverSocket = new ServerSocket(23478);) {
            while (true) {
                Socket socket = serverSocket.accept();

                Server server = new Server(socket, multiUser);
                server.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
