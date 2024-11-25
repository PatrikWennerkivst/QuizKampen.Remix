import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

//DETTA MOTSVARAR TicTacToeServer i TTT
public class Server extends Thread {

    private Socket socket;
    private MultiUser multiUser;
    private Database database;
    private Categories categories;

    public Server(Socket socket, MultiUser multiUser) {
        this.socket = socket;
        this.multiUser = multiUser;
    }

    // Läser in kategori som en String från Client och får ett fråge objekt från protocol via gameProcess
    // Som sedan skickas till heja fråge objektet vidare till MulitUser
    public void run() {
        try (ObjectOutputStream writer = new ObjectOutputStream(socket.getOutputStream());
             BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            multiUser.addWriter(writer);
            String inputLine;
            QuestionsAndAnswers questionsAndAnswers;

            Protocol protocol = new Protocol();

            while ((inputLine = reader.readLine()) != null) {
                System.out.println(inputLine);
                questionsAndAnswers = protocol.gameProcess(inputLine);
                multiUser.print(questionsAndAnswers);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Ta in spelarens namn från ClientGUI
    public void createPlayer(String playerName) {
        try {
            ServerSocket listener = new ServerSocket(8901);
            while (true) {
                Player player1 = new Player(listener.accept(), "Spelare 1"); //Skapar en instans av spelare 1 TODO: Den ska ta in riktiga namnet från ClientGUI
                Player player2 = new Player(listener.accept(), "Spelare 2"); //Skapar en instans av spelare 2  TODO: Lös hur motståndarens namn ska tas in
                Game game = new Game(player1, player2);
                game.startGame(database, categories);
            }
        } catch (Exception e) {
            System.out.println("Det gick inte att skapa ett spel i server");
        }

    }
}
