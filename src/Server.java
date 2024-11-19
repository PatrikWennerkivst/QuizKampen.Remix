import java.io.*;
import java.net.Socket;

public class Server extends  Thread{

    private Socket socket;
    private MultiUser multiUser;

    public Server(Socket socket, MultiUser multiUser) {
        this.socket = socket;
        this.multiUser = multiUser;
    }

    public void run(){
        try(ObjectOutputStream writer = new ObjectOutputStream(socket.getOutputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))){

            multiUser.addWriter(writer);
            String inputLine;
            QuestionsAndAnswers questionsAndAnswers;

            Protocol protocol = new Protocol();

            if((inputLine = reader.readLine()) != null) {
                questionsAndAnswers = protocol.gameProcess(inputLine);
                multiUser.print(questionsAndAnswers);
            }
            else {
                questionsAndAnswers = protocol.gameProcess(null);
                multiUser.print(questionsAndAnswers);
            }

        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    }
