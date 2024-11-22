import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//Client

public class Client extends Thread{
    Socket socket;
    PrintWriter out;
    ObjectOutputStream sender;
    ObjectInputStream in;

    private String userMessage = "";
    private QuestionsAndAnswers qAndA = null;
    boolean isQuestionReceived = false;

    //@Override
    public void run() {
        try {
            // Setup the connection
            socket = new Socket("127.0.0.1", 23478);
            out = new PrintWriter(socket.getOutputStream(), true);
            //sender = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());

            while (socket.isConnected()) {
                // Send user message if exists
                if (userMessage != null && !userMessage.isEmpty()) {
                    System.out.println("Sending: " + userMessage);
                    out.println(userMessage);
                    userMessage = null;

                    // Immediately try to read the response
                    try {
                        Object read = in.readObject();  // här smäller det i StartClient
                        if (read instanceof QuestionsAndAnswers) {
                            // Directly assign to the public variable
                            setQAndA((QuestionsAndAnswers)read);
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
    public void sendToServer(String userMessage) {
        this.userMessage = userMessage;
    }

    public String getUserMessage() {
        return userMessage;
    }

    /*
    Här testar jag också att skapa en metod som anropas av GUI för att läsa in
    ett objekt med frågor och svar.
    Sedan får obejktet delas upp i strängar, antingen i denna klass, eller i GUI klassen
     */
    public void setQAndA(QuestionsAndAnswers qAndA) {
        this.qAndA = qAndA;
    }

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


        //GUI
        class GUI extends JFrame implements ActionListener {

            String userAlias = "User Alias";
            String otherUserAlias = "opponents alias";
            String categoryName = "Name of category";
            JButton classicGameButton = new JButton("Classic game");

            public GUI() {
                JFrame frame = new JFrame();
                JPanel startPanel = new JPanel(new BorderLayout());
                JPanel welcomePanel = new JPanel(new GridLayout(2, 1));
                JLabel welcomeLable = new JLabel("Welcome to Quiz kampen Remix!");
                JLabel userAliasLable = new JLabel(userAlias);

                frame.setTitle("Quiz kampen REMIX:");
                frame.add(startPanel);
                frame.setSize(400, 600);
                frame.setLocationRelativeTo(null);

                startPanel.setBackground(Color.blue);
                startPanel.add(welcomePanel, BorderLayout.NORTH);

                welcomePanel.setBackground(Color.CYAN);
                welcomePanel.add(welcomeLable);
                welcomePanel.add(userAliasLable);
                welcomeLable.setHorizontalAlignment(SwingConstants.CENTER);
                userAliasLable.setHorizontalAlignment(SwingConstants.CENTER);

                startPanel.add(classicGameButton, BorderLayout.SOUTH);
                classicGameButton.addActionListener(this);
                classicGameButton.setBackground(Color.GREEN);

                frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);

            }

            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == classicGameButton) {
                    // Startar CategorySelectionGUI när classicGameButton trycks
                    CategorySecletionGUI categorySelectionGUI = new CategorySecletionGUI();
                    categorySelectionGUI.choosCategory();
                    // Stänger ner fönstret efter att classicButton tryckts
                    ((JFrame) SwingUtilities.getWindowAncestor(classicGameButton)).dispose();
                }
            }

            public class CategorySecletionGUI implements ActionListener {

                private JButton categoryOne = new JButton("Category one");
                private JButton categoryTwo = new JButton("Category two");
                private JButton categoryThree = new JButton("Category three");
                private JLabel chooseCategory = new JLabel("Choose category");

                Protocol protocol = new Protocol();
                Client client;

                public CategorySecletionGUI() {
                    //Anropar metoden som slumpar en kategori från Protocol och skriver ut det med hjälp av toString
                    categoryOne.setText(protocol.randomizeCategory().category);
                    categoryTwo.setText(protocol.randomizeCategory().category);
                    categoryThree.setText(protocol.randomizeCategory().category);

                    categoryOne.addActionListener(this);
                    categoryTwo.addActionListener(this);
                    categoryThree.addActionListener(this);
                }

                public void choosCategory() {
                    JFrame gameFrame = new JFrame("Category Selection");
                    JPanel wholePanel = new JPanel(new BorderLayout());
                    JPanel categoryPanel = new JPanel(new GridLayout(4, 1));

                    gameFrame.add(wholePanel);
                    wholePanel.add(categoryPanel, BorderLayout.CENTER);
                    categoryPanel.setBackground(Color.blue);
                    categoryPanel.add(chooseCategory);
                    chooseCategory.setHorizontalAlignment(SwingConstants.CENTER);
                    categoryPanel.add(categoryOne);
                    categoryPanel.add(categoryTwo);
                    categoryPanel.add(categoryThree);

                    gameFrame.setSize(400, 600);
                    gameFrame.setVisible(true);
                    gameFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
                    gameFrame.setLocationRelativeTo(null);
                }

                @Override
                public void actionPerformed(ActionEvent e) {
                    if (e.getSource() == categoryOne || e.getSource() == categoryTwo || e.getSource() == categoryThree) {
                        String selectedCategory = ((JButton)e.getSource()).getText();
                        client = new Client();
                        System.out.println("CategorySelecition:" + selectedCategory);
                        client.sendToServer(selectedCategory);
                        ClassicGameGUI classicGameGUI = new ClassicGameGUI(client);
                        //Stänger ner fönstret när vilken JButton som helst trycks ner
                        ((JFrame) SwingUtilities.getWindowAncestor((JButton) e.getSource())).dispose();
                    }
                }
            }

            public class ClassicGameGUI implements ActionListener {

                private String userAlias;
                private String categoryName;
                private String otherUserAlias;
                Client client;
                QuestionsAndAnswers qAndA;

                JButton gameQuesiton;
                JButton rigthAwnser;
                JButton wrongAwnser1;
                JButton wrongAwnser2;
                JButton wrongAwnser3;

                public ClassicGameGUI(Client client) {
                    this.client = client;
                    this.userAlias = GUI.this.userAlias;
                    this.categoryName = GUI.this.categoryName;
                    this.otherUserAlias = GUI.this.otherUserAlias;
                    starClassicGame();
                }

                public void starClassicGame() {
                    JFrame gameFrame = new JFrame("Classic game mode");
                    JPanel wholeGamePanel = new JPanel(new GridLayout(4, 1));
                    JPanel topGamePanel = new JPanel(new BorderLayout());
                    JPanel userInfoPanel = new JPanel(new GridLayout(1, 3));
                    JPanel answersPanel = new JPanel(new GridLayout(2, 2));

                    JLabel userAliasLabel = new JLabel(userAlias);
                    JLabel categoryLabel = new JLabel(categoryName);
                    JLabel otherUserAliasLabel = new JLabel(GUI.this.otherUserAlias);
                    JLabel timer = new JLabel("Timer goes here");

                    if(client.qAndA!=null) {
                        System.out.println("Finally");
                        gameQuesiton.setText(qAndA.getQuestion());
                        rigthAwnser.setText(qAndA.getRightAnswer());
                        wrongAwnser1.setText(qAndA.getFirstAnswer());
                        wrongAwnser2.setText(qAndA.getSecondAnswer());
                        wrongAwnser3.setText(qAndA.getThirdAnswer());
                    } else {
                        System.out.println("No questions received");}
                    JButton continueButton = new JButton("Continue");

                    gameFrame.add(wholeGamePanel);
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

                    rigthAwnser.addActionListener(new RightAnswerListener(rigthAwnser, continueButton));
                    wrongAwnser1.addActionListener(new WrongButtonListener(wrongAwnser1, wrongAwnser2, wrongAwnser3, continueButton));
                    wrongAwnser2.addActionListener(new WrongButtonListener(wrongAwnser1, wrongAwnser2, wrongAwnser3, continueButton));
                    wrongAwnser3.addActionListener(new WrongButtonListener(wrongAwnser1, wrongAwnser2, wrongAwnser3, continueButton));
                    continueButton.addActionListener(new ContinueButtonListener(continueButton));

                    gameFrame.setSize(400, 600);
                    gameFrame.setLocationRelativeTo(null);
                    gameFrame.setVisible(true);
                    continueButton.setVisible(false);
                    gameFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);

                }

                @Override
                public void actionPerformed(ActionEvent e) {

                }

                private class RightAnswerListener implements ActionListener {

                    private JButton rightAnswer;
                    private JButton continueButton;

                    public RightAnswerListener(JButton rightAnswer, JButton continueButton) {
                        this.rightAnswer = rightAnswer;
                        this.continueButton = continueButton;
                    }

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        rightAnswer.setBackground(Color.GREEN);
                        continueButton.setVisible(true);
                    }
                }

                private class WrongButtonListener implements ActionListener {
                    private JButton wrongAwnser1;
                    private JButton wrongAwnser2;
                    private JButton wrongAwnser3;
                    private JButton continueButton;

                    public WrongButtonListener(JButton wrongAwnser1, JButton wrongAwnser2,
                                               JButton wrongAwnser3, JButton continueButton) {

                        this.wrongAwnser1 = wrongAwnser1;
                        this.wrongAwnser2 = wrongAwnser2;
                        this.wrongAwnser3 = wrongAwnser3;
                        this.continueButton = continueButton;
                    }

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (e.getSource() == wrongAwnser1) {
                            wrongAwnser1.setBackground(Color.red);
                            continueButton.setVisible(true);
                        } else if (e.getSource() == wrongAwnser2) {
                            wrongAwnser2.setBackground(Color.red);
                            continueButton.setVisible(true);
                        } else if (e.getSource() == wrongAwnser3) {
                            wrongAwnser3.setBackground(Color.red);
                            continueButton.setVisible(true);
                        }
                    }
                }
            }
            class ContinueButtonListener implements ActionListener {
                private final JButton continueButton;

                public ContinueButtonListener(JButton continueButton) {
                    this.continueButton = continueButton;
                }

                @Override
                public void actionPerformed(ActionEvent e) {
                    if (e.getSource() == continueButton) {
                        RoundGUI roundGUI = new RoundGUI();
                        roundGUI.startRound();
                        //Stänger ner fönstret när continueButton trycks ner
                        ((JFrame) SwingUtilities.getWindowAncestor(continueButton)).dispose();
                    }
                }
            }

            class RoundGUI implements ActionListener {
                private String userAlias = "User alias";
                private String categoryName = "Category name";
                private String otherUserAlias = "Other user";
                private String[] categories = {"Category One", "Category Two", "Category Three", "Category Four", "Category Five", "Category Six"};
                JButton nextQuestionButton = new JButton("Fortsätt");

                public RoundGUI() {
                    startRound();
                }

                public void startRound() {
                    JFrame gameFrame = new JFrame("Round score");
                    JPanel wholeGamePanel = new JPanel(new GridLayout(3, 1));
                    JPanel totalScoreBoardPanel = new JPanel(new GridLayout(1, 3));
                    JPanel roundPanel = new JPanel(new GridLayout(1, 3));

                    totalScoreBoardPanel.add(new JLabel(userAlias));
                    totalScoreBoardPanel.add(new JLabel(categoryName));
                    totalScoreBoardPanel.add(new JLabel(otherUserAlias));

                    wholeGamePanel.add(totalScoreBoardPanel);

                    JPanel mainUserScorePanel = createScorePanel();
                    JPanel categoryPanel = createCategoryPanel(categories);
                    JPanel otherUserScorePanel = createScorePanel();

                    roundPanel.add(mainUserScorePanel);
                    roundPanel.add(categoryPanel);
                    roundPanel.add(otherUserScorePanel);
                    wholeGamePanel.add(roundPanel);
                    nextQuestionButton.setBackground(Color.CYAN);
                    nextQuestionButton.addActionListener(this);
                    wholeGamePanel.add(nextQuestionButton);

                    gameFrame.add(wholeGamePanel);
                    gameFrame.setSize(400, 600);
                    gameFrame.setLocationRelativeTo(null);
                    gameFrame.setVisible(true);
                    gameFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
                }

                private JPanel createScorePanel() {
                    JPanel scorePanel = new JPanel(new GridLayout(6, 3));
                    for (int i = 0; i < 18; i++) {
                        scorePanel.add(new JButton());
                    }
                    return scorePanel;
                }

                private JPanel createCategoryPanel(String[] categories) {
                    JPanel categoryPanel = new JPanel(new GridLayout(categories.length, 1));
                    for (String category : categories) {
                        categoryPanel.add(new JButton(category));
                    }
                    return categoryPanel;
                }
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (e.getSource() == nextQuestionButton) {
                        //ClassicGameGUI classicGameGUI = new ClassicGameGUI();
                        //classicGameGUI.starClassicGame();
                        //Stänger ner fönstret när nextQuestionButton trycks ner
                        ((JFrame) SwingUtilities.getWindowAncestor(nextQuestionButton)).dispose();
                    }
                }
            }
        }

        public void main(String[] args) {
            GUI gui = new GUI();
        }
