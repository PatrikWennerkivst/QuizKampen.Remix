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
    QuestionsAndAnswers qAndA = null;
    boolean isQuestionReceived = false;

    Client(){}

    @Override
    public void run() {
        try {
            // Setup the connection
            socket = new Socket("127.0.0.1", 23478);
            out = new PrintWriter(socket.getOutputStream(), true);
            sender = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());

            while (socket.isConnected()) {
                // Send user message if exists
                if (userMessage != null && !userMessage.isEmpty()) {
                    out.println(userMessage);
                    userMessage = null;

                    // Immediately try to read the response
                    try {
                        Object read = in.readObject();
                        if (read instanceof QuestionsAndAnswers) {
                            // Directly assign to the public variable
                            this.qAndA = (QuestionsAndAnswers) read;
                            System.out.println("Received question: " + qAndA.getQuestion());
                        }
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
    Här experimenterar jag att lägga try with resources i en metod, som anropas av GUI
    vid en actionPerformed. Om spelaren har valt ett svar, så skickas ett meddelande till
    servern "ANSWERED", så servern kan skicka ett nytt objekt med frågor och svar
     */
    //TODO: Det skulle behöva ske i en loop då den ska skicka ANSWERED exakt två gånger för att få alla tre frågor
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
    public synchronized QuestionsAndAnswers readFromServer() {
        // Wait a bit for the question to be received
        for (int i = 0; i < 50; i++) {  // 5 seconds total wait
            if (isQuestionReceived) {
                isQuestionReceived = false;  // Reset for next question
                return qAndA;
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("No questions received after waiting.");
        return null;
    }
}
