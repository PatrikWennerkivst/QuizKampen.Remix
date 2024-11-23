import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class ClientGUI extends JFrame {


    Socket socket;
    PrintWriter out;
    ObjectOutputStream sender;
    ObjectInputStream in;
    private ClassicGameGUI classicGame;

    private String userMessage = "";
    private QuestionsAndAnswers qAndA = null;
    boolean isQuestionReceived = false;

    private String userAlias = "User alias";
    private String categoryName = "Category name";
    private String otherUserAlias = "Other user";

    JButton gameQuesiton = new JButton();
    JButton rigthAwnser = new JButton();
    JButton wrongAwnser1 = new JButton();
    JButton wrongAwnser2 = new JButton();
    JButton wrongAwnser3 = new JButton();
    JButton continueButton = new JButton("Continue");

    JFrame gameFrame = new JFrame("Classic game mode");
    JPanel categoryPanel = new JPanel(new GridLayout(4,1));
    JPanel wholeGamePanel = new JPanel(new GridLayout(4, 1));
    JPanel topGamePanel = new JPanel(new BorderLayout());
    JPanel userInfoPanel = new JPanel(new GridLayout(1, 3));
    JPanel answersPanel = new JPanel(new GridLayout(2, 2));

    JLabel userAliasLabel = new JLabel(userAlias);
    JLabel categoryLabel = new JLabel(categoryName);
    JLabel otherUserAliasLabel = new JLabel(otherUserAlias);
    JLabel timer = new JLabel("Timer goes here");

    private JButton categoryOne = new JButton();
    private JButton categoryTwo = new JButton();
    private JButton categoryThree = new JButton();
    private JLabel chooseCategory = new JLabel();

    int clickCounter;

    Protocol protocol = new Protocol();

    ClientGUI() {
        try {
            socket = new Socket("127.0.0.1", 23478);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new ObjectInputStream(socket.getInputStream());


            gameFrame.add(wholeGamePanel);
            wholeGamePanel.add(categoryPanel, BorderLayout.CENTER);
            categoryPanel.setBackground(Color.blue);
            categoryPanel.add(chooseCategory);
            chooseCategory.setHorizontalAlignment(SwingConstants.CENTER);

            //Anropar metoden som slumpar en kategori från Protocol och skriver ut det med hjälp av toString
            categoryOne.setText(protocol.randomizeCategory().category);
            categoryTwo.setText(protocol.randomizeCategory().category);
            categoryThree.setText(protocol.randomizeCategory().category);

            categoryPanel.add(categoryOne);
            categoryPanel.add(categoryTwo);
            categoryPanel.add(categoryThree);

            categoryOne.addActionListener(l -> choseCategory(categoryOne));
            categoryTwo.addActionListener(l -> choseCategory(categoryTwo));
            categoryThree.addActionListener(l -> choseCategory(categoryThree));

            gameFrame.setSize(400,600);
            gameFrame.setVisible(true);
            gameFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
            gameFrame.setLocationRelativeTo(null);

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

    public void choseCategory(JButton button) {
        String selectedCategory = button.getText();
        System.out.println("CategorySelecition:" + selectedCategory);
        sendToServer(selectedCategory);
        wholeGamePanel.removeAll();
        setClassicGame();
        wholeGamePanel.revalidate();
        wholeGamePanel.repaint();

    }

    public void setClassicGame() {
        clickCounter = 0;

        for (int i = 0; i < 50; i++) {  // 5 seconds total wait
            if (qAndA!=null) {
                gameQuesiton.setText(qAndA.getQuestion());
                rigthAwnser.setText(qAndA.getRightAnswer());
                wrongAwnser1.setText(qAndA.getFirstAnswer());
                wrongAwnser2.setText(qAndA.getSecondAnswer());
                wrongAwnser3.setText(qAndA.getThirdAnswer());
                break;
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if(qAndA==null) {
            System.out.println("No questions received after waiting.");
        }
        wholeGamePanel.setBackground(Color.blue);
        wholeGamePanel.add(topGamePanel);
        wholeGamePanel.add(userInfoPanel);
        wholeGamePanel.add(answersPanel);
        wholeGamePanel.add(continueButton);

        topGamePanel.add(userInfoPanel, BorderLayout.NORTH);
        userInfoPanel.add(userAliasLabel);
        userInfoPanel.add(categoryLabel);
        userInfoPanel.add(otherUserAliasLabel);
        topGamePanel.add(gameQuesiton, BorderLayout.CENTER);
        topGamePanel.add(timer, BorderLayout.SOUTH);

        answersPanel.add(rigthAwnser);
        answersPanel.add(wrongAwnser1);
        answersPanel.add(wrongAwnser2);
        answersPanel.add(wrongAwnser3);

        rigthAwnser.addActionListener(l -> {
            rigthAwnser.setBackground(Color.GREEN);
            if(clickCounter<2) {
                sendToServer("ANSWERED"); //få den att skicka ANSWEREDRIGHT och in i en räknare på servern
            }
            continueButton.setVisible(true);
        });

        wrongAwnser1.addActionListener(l -> wrongAnswerAction(wrongAwnser1));
        wrongAwnser2.addActionListener(l -> wrongAnswerAction(wrongAwnser1));
        wrongAwnser3.addActionListener(l -> wrongAnswerAction(wrongAwnser1));

        continueButton.addActionListener(l -> {
            clickCounter++;
            if (clickCounter <= 2) {
                gameQuesiton.setText(qAndA.getQuestion());
                rigthAwnser.setText(qAndA.getRightAnswer());
                wrongAwnser1.setText(qAndA.getFirstAnswer());
                wrongAwnser2.setText(qAndA.getSecondAnswer());
                wrongAwnser3.setText(qAndA.getThirdAnswer());
            } else {
                clickCounter = 0;
                System.exit(0);
            }
        });

    }
    public void wrongAnswerAction(JButton button) {
        if (button == wrongAwnser1) {
            wrongAwnser1.setBackground(Color.red);
            if(clickCounter<2) {
                sendToServer("ANSWERED");
            }
            continueButton.setVisible(true);
        } else if (button == wrongAwnser2) {
            wrongAwnser2.setBackground(Color.red);
            if(clickCounter<2) {
                sendToServer("ANSWERED");
            }
            continueButton.setVisible(true);
        } else if (button == wrongAwnser3) {
            wrongAwnser3.setBackground(Color.red);
            if(clickCounter<2) {
                sendToServer("ANSWERED");
            }
            continueButton.setVisible(true);
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



}
