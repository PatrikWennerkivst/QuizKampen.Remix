import java.io.*;
import java.net.Socket;

//DETTA MOTSVARAR TicTacToeServer i TTT
public class Server extends  Thread{

    private Socket socket;
    private MultiUser multiUser;

    public Server(Socket socket, MultiUser multiUser) {
        this.socket = socket;
        this.multiUser = multiUser;
    }
    // Läser in kategori som en String från Client och får ett fråge objekt från protocol via gameProcess
    // Som sedan skickas till heja fråge objektet vidare till MulitUser
    public void run(){
        try(ObjectOutputStream writer = new ObjectOutputStream(socket.getOutputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))){

            multiUser.addWriter(writer);
            String inputLine;
            QuestionsAndAnswers questionsAndAnswers;

            Protocol protocol = new Protocol();

            while((inputLine = reader.readLine()) != null) {
                    System.out.println(inputLine);
                    questionsAndAnswers = protocol.gameProcess(inputLine);
                    multiUser.print(questionsAndAnswers);

            }

        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}
