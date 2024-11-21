import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class Client {
    /*
    Eventuellt lägga över Swing i GUI.
     */

    Socket socket;
    PrintWriter out;
    ObjectOutputStream sender;
    ObjectInputStream in;
    boolean start = true;

    String userMessage;
    QuestionsAndAnswers qAndaA;

    Client(){}

    Client(boolean start) throws IOException, ClassNotFoundException {
            this.start = start;
            this.socket = new Socket(InetAddress.getLocalHost(), 8080);
            this.out = new PrintWriter(socket.getOutputStream(), true);
            this.sender = new ObjectOutputStream(socket.getOutputStream());
            this.in = new ObjectInputStream(socket.getInputStream());

            while(userMessage != null){
                out.println(userMessage);
            }
            while(in != null){
                Object read = this.in.readObject();
                if(read instanceof QuestionsAndAnswers){
                    this.qAndaA = (QuestionsAndAnswers) read;
                }
            }
    }

    /*
    Här experimenterar jag att lägga try with resources i en metod, som anropas av GUI
    vid en actionPerformed. Om spelaren har valt ett svar, så skickas ett meddelande till
    servern "ANSWERED", så servern kan skicka ett nytt objekt med frågor och svar
     */
    public void sendToServer(String userMessage){
            this.userMessage = userMessage;
    }
    public String getUserMessage(){
        return userMessage;
    }

    /*
    Här testar jag också att skapa en metod som anropas av GUI för att läsa in
    ett objekt med frågor och svar.
    Sedan får obejktet delas upp i strängar, antingen i denna klass, eller i GUI klassen
     */
    public QuestionsAndAnswers readFromServer(){
        return qAndaA;
    }
}
