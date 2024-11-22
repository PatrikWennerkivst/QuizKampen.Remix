import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class Client extends Thread {


    Socket socket;
    PrintWriter out;
    ObjectOutputStream sender;
    ObjectInputStream in;
    private ClassicGameGUI classicGame;

    private String userMessage = "";
    private QuestionsAndAnswers qAndA = null;
    boolean isQuestionReceived = false;

    Client(){}

    @Override
    public void run() {
        try {
            // Setup the connection
            socket = new Socket("127.0.0.1", 23478);
            out = new PrintWriter(socket.getOutputStream(), true);
           // sender = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());

            while (socket.isConnected()) {
                // Send user message if exists
                if (getUserMessage() != null && !getUserMessage().isEmpty()) {
                    System.out.println(getUserMessage());
                    out.println(getUserMessage());
                    // Immediately try to read the response
                    try {
                        Object read = in.readObject();  // här smäller det i StartClient
                        if (read instanceof QuestionsAndAnswers) {
                            // Directly assign to the public variable
                            qAndA = (QuestionsAndAnswers) read;
                            classicGame.setqAndA(qAndA);
                            //classicGame = new ClassicGameGUI(this, qAndA);
                            //classicGame.start();
                            System.out.println("Received question: " + qAndA.getQuestion());
                            System.out.println(qAndA.getRightAnswer());
                        }
                        sendToServer(null);
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendToServer(String userMessage){
            this.userMessage = userMessage;
    }
    public String getUserMessage(){
        return userMessage;
    }

    public void setQAndA(QuestionsAndAnswers qAndA){
        this.qAndA = qAndA;
    }
    public synchronized QuestionsAndAnswers getQAndA() {
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

    public ClassicGameGUI getClassicGame() {
        return classicGame;
    }

    public void setClassicGame(ClassicGameGUI classicGame) {
        this.classicGame = classicGame;
    }
//GUI


}
