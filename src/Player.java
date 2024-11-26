import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

//DETTA MOTSVARAR ServerSidePlayer i TTT.
//Representerar en spelare (en Player) i spelet och kommunicerar med respektive klient genom (vi har två ClientGUI)

public class Player {

        BufferedReader input;
        Player opponent;
        Socket socket;
        String playerName;

        //Vi kan ta in input från klienten (socket och playerName)
        public Player(Socket socket, String playerName) {
            this.socket = socket;
            this.playerName = playerName;

            try {
                input = new BufferedReader(
                        new InputStreamReader(socket.getInputStream()));
            } catch (IOException e) {
                System.out.println("Det går inte att upprätta en anslutning till klienten.");
            }
        }

        //Vi tar in data från klienten
        public String receive()  {
            try {
                return input.readLine();
            } catch (IOException e) {
                System.out.println("Klienten skickar inte data till Multiuser");
                throw new RuntimeException(e);
            }
        }

        //Sätter vilken motståndaren är
        public void setOpponent(Player opponent) {
            this.opponent = opponent;
        }

        //Skickar till klienten vilken dess motståndare är som Player
        public Player getOpponent() {
            return opponent;
        }
    }