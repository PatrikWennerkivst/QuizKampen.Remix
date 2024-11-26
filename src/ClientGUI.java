import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;
import java.util.List;


//DETTA MOTSVARAR TicTacToeClient i TTT
public class ClientGUI extends JFrame implements ActionListener {

    private final static int PORT = 8901;
    Socket socket;
    PrintWriter out;
    ObjectOutputStream sender;
    ObjectInputStream in;

    private String userMessage = "";
    private QuestionsAndAnswers qAndA = null;
    boolean isQuestionReceived = false;

    private String playerUserName;
    private String categoryName = "Category name";
    private String opponentUserName = "Other user";

    private Categories currentCategory;
    private Database database = new Database();
    private MultiUser multiUser;
    private Server server = new Server(socket, multiUser);

    JButton gameQuesiton = new JButton();
    JButton rigthAwnser = new JButton();
    JButton wrongAwnser1 = new JButton();
    JButton wrongAwnser2 = new JButton();
    JButton wrongAwnser3 = new JButton();
    JButton continueButton = new JButton("Continue");
    JButton nextCategoryButton = new JButton("Next round");

    JFrame gameFrame = new JFrame("Classic game mode");
    JPanel categoryPanel = new JPanel(new GridLayout(4,1));
    JPanel wholeGamePanel = new JPanel(new GridLayout(4, 1));
    JPanel topGamePanel = new JPanel(new BorderLayout());
    JPanel userInfoPanel = new JPanel(new GridLayout(1, 3));
    JPanel answersPanel = new JPanel(new GridLayout(2, 2));
    JPanel userNamePanel = new JPanel(new GridLayout(3,1));

    JLabel userAliasLabel = new JLabel(playerUserName);
    JLabel categoryLabel = new JLabel(categoryName);
    JLabel otherUserAliasLabel = new JLabel(opponentUserName);
    JLabel timer = new JLabel("Timer goes here");
    JTextField playerNameText = new JTextField();

    private JButton categoryOne = new JButton();
    private JButton categoryTwo = new JButton();
    private JButton categoryThree = new JButton();
    private JLabel chooseCategory = new JLabel();
    private JButton setUserNameButton = new JButton("Välj");

    int clickCounter;

    Protocol protocol = new Protocol();

    //Startar upp anslutning till servern, startar GUI för användarnamn och tar emot frågeobjekt
    ClientGUI() {
        try {
            socket = new Socket("127.0.0.1", PORT);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new ObjectInputStream(socket.getInputStream());

            chooseUserNameGUI();

            while (socket.isConnected()) {
                // Send user message if exists
                if (getUserMessage() != null && !getUserMessage().isEmpty()) {
                    System.out.println(getUserMessage());
                    out.println(getUserMessage());
                    // Immediately try to read the response
                    try {
                        Object read = in.readObject();
                        if (read instanceof QuestionsAndAnswers) {
                            // Directly assign to the public variable
                            qAndA = (QuestionsAndAnswers) read;
                            //sparat valt kategori för att sedan kunna slumpa fram nästa fråga
                            currentCategory = qAndA.getCategories();
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

    //Metod med GUI för val av användarnamn. Samt plats där användarnamn skickas till server via createPlayer() anrop
    public void chooseUserNameGUI(){
        JLabel welcomLable = new JLabel();
        JLabel chooseUsernNameLable = new JLabel();

        wholeGamePanel.setLayout(new GridLayout(4,1));
        gameFrame.add(wholeGamePanel);
        wholeGamePanel.add(userNamePanel);
        wholeGamePanel.setBackground(Color.GRAY);

        userNamePanel.add(welcomLable);

        welcomLable.setHorizontalAlignment(SwingConstants.CENTER);
        welcomLable.setText("Quizz Kampen 2.0");
        welcomLable.setFont(new Font("Algerian", Font.BOLD, 50));

        wholeGamePanel.add(chooseUsernNameLable);
        chooseUsernNameLable.setText("\n Välj Användarnamn: ");
        chooseUsernNameLable.setFont(new Font("Rockwell Extra Bold", Font.BOLD, 16));
        chooseUsernNameLable.setHorizontalAlignment(SwingConstants.CENTER);

        wholeGamePanel.add(playerNameText);
        playerNameText.setSize(100,28);
        playerNameText.setHorizontalAlignment(SwingConstants.CENTER);
        wholeGamePanel.add(setUserNameButton);

        setUserNameButton.addActionListener(e -> {
            playerUserName = playerNameText.getText();
            server.createPlayer(playerUserName);    //Skickar användarnamn till server
            wholeGamePanel.removeAll();
            chooseCategoryGUI();
            wholeGamePanel.revalidate();
            wholeGamePanel.repaint();
        });

        gameFrame.setSize(500,600);
        gameFrame.setVisible(true);
        gameFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        gameFrame.setLocationRelativeTo(null);

    }

    //Allt Gui för val av kategori
    public void chooseCategoryGUI(){

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

        categoryOne.addActionListener(l -> sendCategoryToServer(categoryOne));
        categoryTwo.addActionListener(l -> sendCategoryToServer(categoryTwo));
        categoryThree.addActionListener(l -> sendCategoryToServer(categoryThree));

        gameFrame.setSize(400,600);
        gameFrame.setVisible(true);
        gameFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        gameFrame.setLocationRelativeTo(null);
    }
        //Här skickas val av kategori till servern samt repaintar hela GUI:t
    public void sendCategoryToServer(JButton button) {
        String selectedCategory = button.getText();
        System.out.println("CategorySelecition:" + selectedCategory);
        sendToServer(selectedCategory);
        wholeGamePanel.removeAll();
        questionGUI();
        wholeGamePanel.revalidate();
        wholeGamePanel.repaint();
    }
    // Här är GUI för att visa fårgor och svar
    public void questionGUI() {
        clickCounter = 0;

        for (int i = 0; i < 50; i++) {  // 5 seconds total wait
            if (qAndA != null) {
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
        if (qAndA == null) {
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

        // Border med samma färg som bakgrunden för alla knappar
        rigthAwnser.setBorder(new LineBorder(Color.LIGHT_GRAY, 3));
        wrongAwnser1.setBorder(new LineBorder(Color.LIGHT_GRAY, 3));
        wrongAwnser2.setBorder(new LineBorder(Color.LIGHT_GRAY, 3));
        wrongAwnser3.setBorder(new LineBorder(Color.LIGHT_GRAY, 3));

        rigthAwnser.addActionListener(l -> {
            Color lightGreen = Color.decode("#B8F89B");
            rigthAwnser.setBackground(lightGreen);
            if (clickCounter < 2) {
                sendToServer("ANSWERED"); //få den att skicka ANSWEREDRIGHT och in i en räknare på servern
            }
            continueButton.setVisible(true);

        });

        wrongAwnser1.addActionListener(l -> wrongAnswerAction(wrongAwnser1));
        wrongAwnser2.addActionListener(l -> wrongAnswerAction(wrongAwnser2));
        wrongAwnser3.addActionListener(l -> wrongAnswerAction(wrongAwnser3));

        // Behövs för att knapparna ska kunna ändra färg     
        rigthAwnser.setOpaque(true);
        wrongAwnser1.setOpaque(true);
        wrongAwnser2.setOpaque(true);
        wrongAwnser3.setOpaque(true);

        continueButton.addActionListener(l -> {
            clickCounter++;
            System.out.println("Klick ");
            if (clickCounter <= 2) {
                updateGameQuestionAndAnswers();
                //Anropar metoden som slumpar ut en ny fråga från samma kategori

           } else if (clickCounter == 3  ){
                //Stänger ner fönstret när nextQuestionButton trycks ner
                ((JFrame) SwingUtilities.getWindowAncestor(continueButton)).dispose();
                startRoundGUI();
            }
            else {
                System.out.println("hej hej nu är en runda klar");
            }
        });
        wholeGamePanel.revalidate();
        wholeGamePanel.repaint();
    }

    //lägger till frågorna i respektive knapp i questionGUI
    private void updateGameQuestionAndAnswers() {
        gameQuesiton.setText(qAndA.getQuestion());
        rigthAwnser.setText(qAndA.getRightAnswer());
        wrongAwnser1.setText(qAndA.getFirstAnswer());
        wrongAwnser2.setText(qAndA.getSecondAnswer());
        wrongAwnser3.setText(qAndA.getThirdAnswer());

        rigthAwnser.setBackground(null);
        wrongAwnser1.setBackground(null);
        wrongAwnser2.setBackground(null);
        wrongAwnser3.setBackground(null);

        getNewQuestionSameCategory();
    }

    // Metod för att hämta en ny fråga från samma kategori
    public void getNewQuestionSameCategory() {
        if (currentCategory == null) {
            System.out.println("Ingen kategori vald ännu.");
            return;
        }
        // Hämta alla frågor från samma kategori
        List<QuestionsAndAnswers> sameCategoryQuestions = database.getListBasedOnCategory(currentCategory);

        // Slumpa en ny fråga
        int randomIndex = 0;
        for (int i = 0; i < sameCategoryQuestions.size(); i++) {
            if (i == sameCategoryQuestions.size() - 1) {
                randomIndex = (int) (Math.random() * sameCategoryQuestions.size());
            }
        }

        QuestionsAndAnswers newQuestion = sameCategoryQuestions.get(randomIndex);
        // Sätt nya frågan + svar
        setQAndA(newQuestion);
        gameQuesiton.setText(newQuestion.getQuestion());
        wrongAwnser1.setText(newQuestion.getFirstAnswer());
        wrongAwnser2.setText(newQuestion.getSecondAnswer());
        wrongAwnser3.setText(newQuestion.getThirdAnswer());
        rigthAwnser.setText(newQuestion.getRightAnswer());
    }

    public void wrongAnswerAction(JButton button) {
        Color lightRed = Color.decode("#F8A39B");

        if (button.getText().equals(wrongAwnser1.getText())) {
            wrongAwnser1.setBackground(lightRed);
            if (clickCounter < 2) {
                sendToServer("ANSWERED");
            }
            continueButton.setVisible(true);
        } else if (button.getText().equals(wrongAwnser2.getText())) {
            wrongAwnser2.setBackground(lightRed);
            if (clickCounter < 2) {
                sendToServer("ANSWERED");
            }
            continueButton.setVisible(true);
        } else if (button.getText().equals(wrongAwnser3.getText())) {
            wrongAwnser3.setBackground(lightRed);
            if (clickCounter < 2) {
                sendToServer("ANSWERED");
            }
            continueButton.setVisible(true);

            // Tvinga panelen att rita om knapparna med fel svar
            answersPanel.revalidate();
            answersPanel.repaint();
        }
    }

    public void sendToServer(String userMessage) {
        this.userMessage = userMessage;
    }

    public String getUserMessage() {
        return userMessage;
    }

    public void setQAndA(QuestionsAndAnswers qAndA) {
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
    // metod som visar poängställningen i ett GUI.
    public void startRoundGUI() {
        JFrame gameFrame = new JFrame("Round score");
        JPanel wholeGamePanel = new JPanel(new GridLayout(3, 1));
        JPanel totalScoreBoardPanel = new JPanel(new GridLayout(1, 3));
        JPanel roundPanel = new JPanel(new GridLayout(1, 3));

        totalScoreBoardPanel.add(new JLabel(playerUserName));
        totalScoreBoardPanel.add(new JLabel(categoryName));
        totalScoreBoardPanel.add(new JLabel(opponentUserName));

        wholeGamePanel.add(totalScoreBoardPanel);

        JPanel mainUserScorePanel = createScorePanel();
        JPanel categoryPanel = createCategoryPanel();
        JPanel otherUserScorePanel = createScorePanel();

        roundPanel.add(mainUserScorePanel);
        roundPanel.add(categoryPanel);
        roundPanel.add(otherUserScorePanel);
        wholeGamePanel.add(roundPanel);

        nextCategoryButton.addActionListener(this);
        wholeGamePanel.add(nextCategoryButton);

        gameFrame.add(wholeGamePanel);
        gameFrame.setSize(400, 600);
        gameFrame.setLocationRelativeTo(null);
        gameFrame.setVisible(true);
        gameFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    //Metod som skapar varje spelares poäng nät
    private JPanel createScorePanel() {
        JPanel scorePanel = new JPanel(new GridLayout(6, 3));
        for (int i = 0; i < 18; i++) {
            JCheckBox userCheckBox = new JCheckBox();
            userCheckBox.setEnabled(false);
            scorePanel.add(userCheckBox);
        }
        return scorePanel;
    }
    //Metod som skapar nätet för spelade kategorier från varje runda
    private JPanel createCategoryPanel() {
        Categories[] categories = new Categories[6];
        JPanel categoryPanel = new JPanel(new GridLayout(categories.length, 1));
        for (Categories category : categories) {
            categoryPanel.add(new JButton());
        }
        return categoryPanel;
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == nextCategoryButton) {
            QuestionsAndAnswers qAa = null;
            clickCounter = 0;
            wholeGamePanel.removeAll();
            chooseCategoryGUI();
            wholeGamePanel.revalidate();
            wholeGamePanel.repaint();
            System.out.println("Hej hej här är vi");

        //Detta kommer inte att funka. måste stänga fönstret på något annat sätt
            //Stänger ner fönstret när nextQuestionButton trycks ner
            //((JFrame) SwingUtilities.getWindowAncestor(continueButton)).dispose();
        }
    }
}

