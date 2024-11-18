import java.io.*;
import java.net.Socket;

public class Client {
    /*
    Eventuellt lägga över Swing i GUI.
     */

    private String userAlias;

    public Client(){

        try(Socket socket = new Socket("127.0.0.1", 8080);
            ObjectOutputStream sender = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            ){

            System.out.println("Connected to the server.");
            String serverMessage = "";
            String userMessage = "";

        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
