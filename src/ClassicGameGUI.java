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
    QuestionsAndAnswers qAndA = null;

    int clickCounter;

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


    public ClassicGameGUI(Client client, QuestionsAndAnswers qAndA) throws IOException, ClassNotFoundException {
        this.client = client;
        this.qAndA = qAndA;
        this.gameGui = this;
    }

    public void run(){

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

        rigthAwnser.addActionListener(l -> {
            rigthAwnser.setBackground(Color.GREEN);
            if(clickCounter<1) {
                client.sendToServer("ANSWERED");
            }
            continueButton.setVisible(true);
        });

        wrongAwnser1.addActionListener(this);
        wrongAwnser2.addActionListener(this);
        wrongAwnser3.addActionListener(this);

        continueButton.addActionListener(l -> {
            clickCounter++;
            if (clickCounter <= 2) {
                QuestionsAndAnswers qAndA = client.getQAndA();
                gameGui.gameQuesiton.setText(qAndA.getQuestion());
                gameGui.rigthAwnser.setText(qAndA.getRightAnswer());
                gameGui.wrongAwnser1.setText(qAndA.getFirstAnswer());
                gameGui.wrongAwnser2.setText(qAndA.getSecondAnswer());
                gameGui.wrongAwnser3.setText(qAndA.getThirdAnswer());
            } else {
                clickCounter = 0;
                //client.interrupt();
                RoundGUI roundGUI = new RoundGUI();
                roundGUI.startRound();
                //Stänger ner fönstret när continueButton trycks ner
                ((JFrame) SwingUtilities.getWindowAncestor(continueButton)).dispose();
            }
        });

        gameFrame.setSize(400, 600);
        gameFrame.setLocationRelativeTo(null);
        gameFrame.setVisible(true);
        continueButton.setVisible(false);
        gameFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == wrongAwnser1) {
            wrongAwnser1.setBackground(Color.red);
            if(clickCounter<1) {
                client.sendToServer("ANSWERED");
            }
            continueButton.setVisible(true);
        } else if (e.getSource() == wrongAwnser2) {
            wrongAwnser2.setBackground(Color.red);
            if(clickCounter<1) {
                client.sendToServer("ANSWERED");
            }
            continueButton.setVisible(true);
        } else if (e.getSource() == wrongAwnser3) {
            wrongAwnser3.setBackground(Color.red);
            if(clickCounter<1) {
                client.sendToServer("ANSWERED");
            }
            continueButton.setVisible(true);
        }
    }
}
