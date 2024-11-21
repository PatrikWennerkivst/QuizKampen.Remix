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
    public void run() {
        try {
            // Setup the connection
            socket = new Socket("127.0.0.1", 23478);
            out = new PrintWriter(socket.getOutputStream(), true);
            sender = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());

            while (socket.isConnected()) {
                // Step 1: Send the message to the server (e.g., category selection)
                if (userMessage != null && !userMessage.isEmpty()) {
                    System.out.println("Sending message: " + userMessage);
                    out.println(userMessage); // Send category to the server
                    userMessage = null; // Clear the message after sending it
                    waitingForResponse = true; // Indicate we're waiting for a response
                } else {
                    System.out.println("No message to send.");
                }

                // Step 2: Wait for the response (QuestionsAndAnswers object)
                if (waitingForResponse) {
                    try {
                        // Step 3: Receive the response from the server
                        Object read = in.readObject();
                        if (read instanceof QuestionsAndAnswers) {
                            // If the server sent a QuestionsAndAnswers object, process it
                            this.qAndaA = (QuestionsAndAnswers) read;
                            System.out.println("Received question: " + qAndaA.getQuestion());
                        } else {
                            // Handle unexpected responses here
                            System.out.println("Unexpected response: " + read);
                        }
                    } catch (EOFException e) {
                        // Server disconnected unexpectedly
                        System.out.println("Server connection closed unexpectedly.");
                        break; // Exit the loop
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                    // Reset the waitingForResponse flag after receiving the response
                    waitingForResponse = false;
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
