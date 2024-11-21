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

            while((inputLine = reader.readLine()) != null) {
                    System.out.println(inputLine.substring(4));
                    questionsAndAnswers = protocol.gameProcess(inputLine.substring(4));
                    System.out.println(questionsAndAnswers.getQuestion());
                    multiUser.print(questionsAndAnswers);
                    //Här anropas gameProcess metoden utan att skicka in ny kategori
            }

        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    }
