import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;
import java.util.List;

public class ClientGUI extends JFrame implements ActionListener {


    Socket socket;
    PrintWriter out;
    ObjectOutputStream sender;
    ObjectInputStream in;


    private String userMessage = "";
    private QuestionsAndAnswers qAndA = null;
    boolean isQuestionReceived = false;

    private String userAlias = "User alias";
    private String categoryName = "Category name";
    private String otherUserAlias = "Other user";

    private Categories currentCategory;
    private Database database = new Database();

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

    //Detta är är logik som annars skulle legat i en Client klass
    ClientGUI() {
        try {
            socket = new Socket("127.0.0.1", 23478);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new ObjectInputStream(socket.getInputStream());

            chooseCategoryGUI();

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
           /* try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            */
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

            } else if (clickCounter == 2) {
                    System.out.println("Startar ny omgång...");

           } else {
                //Stänger ner fönstret när nextQuestionButton trycks ner
                ((JFrame) SwingUtilities.getWindowAncestor(continueButton)).dispose();
                startRoundGUI();
            }
        });

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
           /* try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            */
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

        totalScoreBoardPanel.add(new JLabel(userAlias));
        totalScoreBoardPanel.add(new JLabel(categoryName));
        totalScoreBoardPanel.add(new JLabel(otherUserAlias));

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
            chooseCategoryGUI();
            //Stänger ner fönstret när nextQuestionButton trycks ner
           // ((JFrame) SwingUtilities.getWindowAncestor(continueButton)).dispose();
        }
    }
}
