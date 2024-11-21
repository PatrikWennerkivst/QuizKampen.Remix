import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;


public class ClassicGameGUI extends Thread implements ActionListener {
    private String userAlias = "User alias";
    private String categoryName = "Category name";
    private String otherUserAlias = "Other user";
    Client client;
    ClassicGameGUI gameGui;

    JButton gameQuesiton = new JButton();
    JButton rigthAwnser = new JButton();
    JButton wrongAwnser1 = new JButton();
    JButton wrongAwnser2 = new JButton();
    JButton wrongAwnser3 = new JButton();
    JButton continueButton = new JButton("Continue");

    JFrame gameFrame = new JFrame("Classic game mode");
    JPanel wholeGamePanel = new JPanel(new GridLayout(4, 1));
    JPanel topGamePanel = new JPanel(new BorderLayout());
    JPanel userInfoPanel = new JPanel(new GridLayout(1, 3));
    JPanel answersPanel = new JPanel(new GridLayout(2, 2));

    JLabel userAliasLabel = new JLabel(userAlias);
    JLabel categoryLabel = new JLabel(categoryName);
    JLabel otherUserAliasLabel = new JLabel(otherUserAlias);
    JLabel timer = new JLabel("Timer goes here");


    public ClassicGameGUI(Client client) throws IOException, ClassNotFoundException {
        this.client = client;
        this.gameGui = this;
    }

    public void run(){

        try{
            Thread.sleep(1000);
        } catch (InterruptedException e){
            e.printStackTrace();
        }

        QuestionsAndAnswers qAndA = client.readFromServer();
        if(qAndA !=null) {
            gameQuesiton.setText(qAndA.getQuestion());
            rigthAwnser.setText(qAndA.getRightAnswer());
            wrongAwnser1.setText(qAndA.getFirstAnswer());
            wrongAwnser2.setText(qAndA.getSecondAnswer());
            wrongAwnser3.setText(qAndA.getThirdAnswer());
        } else
            System.out.println("No question found");

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

        rigthAwnser.addActionListener(new ClassicGameGUI.RightAnswerListener(rigthAwnser, continueButton));
        wrongAwnser1.addActionListener(new ClassicGameGUI.WrongButtonListener(wrongAwnser1, wrongAwnser2, wrongAwnser3, continueButton));
        wrongAwnser2.addActionListener(new ClassicGameGUI.WrongButtonListener(wrongAwnser1, wrongAwnser2, wrongAwnser3, continueButton));
        wrongAwnser3.addActionListener(new ClassicGameGUI.WrongButtonListener(wrongAwnser1, wrongAwnser2, wrongAwnser3, continueButton));
        continueButton.addActionListener(new ClassicGameGUI.ContinueButtonListener(continueButton));

        gameFrame.setSize(400, 600);
        gameFrame.setLocationRelativeTo(null);
        gameFrame.setVisible(true);
        continueButton.setVisible(false);
        gameFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    public class RightAnswerListener implements ActionListener {
        private final JButton rightAnswer;
        private final JButton continueButton;

        public RightAnswerListener(JButton rightAnswer, JButton continueButton) {
            this.rightAnswer = rightAnswer;
            this.continueButton = continueButton;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            rightAnswer.setBackground(Color.GREEN);
            continueButton.setVisible(true);
            client.sendToServer("ANSWERED");
        }
    }

    public class WrongButtonListener implements ActionListener {
        private final JButton wrongAwnser1;
        private final JButton wrongAwnser2;
        private final JButton wrongAwnser3;
        private final JButton continueButton;

        public WrongButtonListener(JButton wrongAwnser1, JButton wrongAwnser2, JButton wrongAwnser3, JButton continueButton) {
            this.wrongAwnser1 = wrongAwnser1;
            this.wrongAwnser2 = wrongAwnser2;
            this.wrongAwnser3 = wrongAwnser3;
            this.continueButton = continueButton;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == wrongAwnser1) {
                wrongAwnser1.setBackground(Color.red);
                client.sendToServer("ANSWERED");
                continueButton.setVisible(true);
            } else if (e.getSource() == wrongAwnser2) {
                wrongAwnser2.setBackground(Color.red);
                client.sendToServer("ANSWERED");
                continueButton.setVisible(true);
            } else if (e.getSource() == wrongAwnser3) {
                wrongAwnser3.setBackground(Color.red);
                client.sendToServer("ANSWERED");
                continueButton.setVisible(true);
            }
        }
    }

    public class ContinueButtonListener implements ActionListener {
        private final JButton continueButton;

        public ContinueButtonListener(JButton continueButton) {
            this.continueButton = continueButton;
        }
        int clickCounter = 0;

        @Override
        public void actionPerformed(ActionEvent e) {

            if (e.getSource() == continueButton) {
                if (clickCounter <= 2) {
                    QuestionsAndAnswers qAndA = client.readFromServer();
                    gameGui.gameQuesiton.setText(qAndA.getQuestion());
                    gameGui.rigthAwnser.setText(qAndA.getRightAnswer());
                    gameGui.wrongAwnser1.setText(qAndA.getFirstAnswer());
                    gameGui.wrongAwnser2.setText(qAndA.getSecondAnswer());
                    gameGui.wrongAwnser3.setText(qAndA.getThirdAnswer());
                    clickCounter++;
                }
                clickCounter = 0;
                RoundGUI roundGUI = new RoundGUI();
                roundGUI.startRound();
                //Stänger ner fönstret när continueButton trycks ner
                ((JFrame) SwingUtilities.getWindowAncestor(continueButton)).dispose();
            }
        }
    }
}
