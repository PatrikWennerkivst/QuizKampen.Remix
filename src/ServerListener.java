import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

//Kopplar ihop allt
public class ServerListener {

    private MultiUser multiUser = new MultiUser();

    public ServerListener(){

        try(ServerSocket serverSocket = new ServerSocket(8888);) { //skicka in player och
            while (true) {
                //Väntar på att spelare 1 ansluter, skapar spelare 1 och skapar upp servertråd 1
                Socket socket1 = serverSocket.accept();
                System.out.println("Spelare 1 ansluten");
                Player player1 = new Player(socket1, "Spelare 1"); //Skapar en instans av spelare 1
                System.out.println("Spelare 1 är skapad");
                Server server1 = new Server(socket1, multiUser);
                server1.start();
                System.out.println("Server 1 skapad");

                //Väntar på att spelare 2 ansluter, skapar spelare 2 och skapar upp servertråd 2
                Socket socket2 = serverSocket.accept();
                System.out.println("Spelare 2 är ansluten");
                Player player2 = new Player(socket2, "Spelare 2"); //Skapar en instans av spelare 2
                System.out.println("Spelare 2 är skapad");
                Server server2 = new Server(socket2, multiUser);
                server2.start();
                System.out.println("Server 2 skapad");

                //Startar därefter spelet
                Game game = new Game(player1, player2);
                System.out.println("Spelet är skapat");
                //game.startGame(database, categories); //TODO: Behöver läsa in dessa två in i game direkt utan att skickas in
                System.out.println("Spelet har startat");

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Flyttat CreatePlayer hit och lagt in den i konstruktorn istället
        try {
            while (true) {
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Det gick inte att skapa ett spel i server");
        }
    }
}