import java.io.IOException;

public class StartClient {

    public static void main(String[] args){
        try{
            Client client = new Client(true);
            StartGUI startGUI = new StartGUI();
        }
        catch(IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }
}
