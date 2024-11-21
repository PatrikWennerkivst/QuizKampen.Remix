import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class Client extends Thread {
    /*
    Eventuellt lägga över Swing i GUI.
     */

    Socket socket;
    PrintWriter out;
    ObjectOutputStream sender;
    ObjectInputStream in;

    String userMessage = "";
    QuestionsAndAnswers qAndaA;
    boolean waitingForResponse = false;

    Client(){}

    @Override
     public void run(){
        try {
            socket = new Socket("127.0.0.1", 23478);
            out = new PrintWriter(socket.getOutputStream(), true);
            sender = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());

            while(socket.isConnected()){
                if(userMessage != null && !userMessage.isEmpty()){
                    System.out.println(userMessage);
                    out.println(userMessage);
                    userMessage = null;
                    waitingForResponse = true;
                }
                else {
                    System.out.println("Det skickas inte");
                }

                if(waitingForResponse){
                    Object read = in.readObject();
                        if(read instanceof QuestionsAndAnswers){
                            this.qAndaA = (QuestionsAndAnswers) read;
                        }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
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
        if(qAndaA == null){
            System.out.println("No questions and answers received");
            return null;
        }
        return qAndaA;
    }
}
