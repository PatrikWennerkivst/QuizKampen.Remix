import java.io.*;
import java.net.Socket;

public class Client {
    /*
    Eventuellt lägga över Swing i GUI.
     */

    Socket socket;
    PrintWriter out;
    ObjectOutputStream sender;
    ObjectInputStream in;

    Client() throws IOException {
            socket = new Socket("127.0.0.1", 8080);
            out = new PrintWriter(socket.getOutputStream(), true);
            sender = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
    }

    /*
    Här experimenterar jag att lägga try with resources i en metod, som anropas av GUI
    vid en actionPerformed. Om spelaren har valt ett svar, så skickas ett meddelande till
    servern "ANSWERED", så servern kan skicka ett nytt objekt med frågor och svar
     */
    public void sendToServer(String userMessage){
            out.println(userMessage);
    }

    /*
    Här testar jag också att skapa en metod som anropas av GUI för att läsa in
    ett objekt med frågor och svar.
    Sedan får obejktet delas upp i strängar, antingen i denna klass, eller i GUI klassen
     */
    public QuestionsAndAnswers readFromServer() throws IOException, ClassNotFoundException {
        Object read = in.readObject();
        if(read instanceof QuestionsAndAnswers){
            QuestionsAndAnswers newQuestion = (QuestionsAndAnswers) read;
            return newQuestion;
        }
        return null;
    }
}
