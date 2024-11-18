import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI extends JFrame implements ActionListener {

    String userAlias = "User Alias";
    String otherUserAlisar = "opponents alias";
    String categoryName = "Name of category";

    public GUI(){
        JFrame frame = new JFrame();
        JPanel startPanel = new JPanel(new BorderLayout());
        JPanel welcomePanel = new JPanel(new GridLayout(2,1));
        JLabel welcomeLable = new JLabel("Welcome to Quiz kampen Remix!");
        JLabel userAliasLable = new JLabel(userAlias);
        JButton classicGameButton = new JButton("Classic game");

        frame.setTitle("Quiz kampen REMIX:");
        frame.add(startPanel);
        frame.setSize(400,600);
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
        StartClassicGame startClassicGame = new StartClassicGame(userAlias, categoryName, otherUserAlisar);
        startClassicGame.starClassicGame();
    }

    public class StartClassicGame implements ActionListener{

        private String userAlias;
        private String categoryName;
        private String otherUserAlias;

        public StartClassicGame(String userAlias, String categoryName, String otherUserAlias) {
            this.userAlias = userAlias;
            this.categoryName = categoryName;
            this.otherUserAlias = otherUserAlias;
        }

        public void starClassicGame() {
            JFrame gameFrame = new JFrame("Classic game mode");
            JPanel wholeGamePanel = new JPanel(new GridLayout(4, 1));
            JPanel topGamePanel = new JPanel(new BorderLayout());
            JPanel userInfoPanel = new JPanel(new GridLayout(1, 3));
            JPanel answersPanel = new JPanel(new GridLayout(2, 2));

            JLabel userAliasLabel = new JLabel(userAlias);
            JLabel categoryLabel = new JLabel(categoryName);
            JLabel otherUserAliasLabel = new JLabel(otherUserAlisar);
            JLabel timer = new JLabel("Timer goes here");

            JButton gameQuesiton = new JButton("Question: bla bla bla bla bla bla bla");
            JButton rigthAwnser = new JButton("Right");
            JButton wrongAwnser1 = new JButton("Wrong 1");
            JButton wrongAwnser2 = new JButton("Wrong 2");
            JButton wrongAwnser3 = new JButton("Wrong 3");
            JButton continueButton = new JButton("Continue");

            gameFrame.add(wholeGamePanel);
            wholeGamePanel.setBackground(Color.blue);
            wholeGamePanel.add(topGamePanel);
            wholeGamePanel.add(userInfoPanel);
            wholeGamePanel.add(answersPanel);
            wholeGamePanel.add(continueButton);

            topGamePanel.add(userInfoPanel,BorderLayout.NORTH);
            userInfoPanel.add(userAliasLabel);
            userInfoPanel.add(categoryLabel);
            userInfoPanel.add(otherUserAliasLabel);
            topGamePanel.add(gameQuesiton,BorderLayout.CENTER);
            topGamePanel.add(timer,BorderLayout.SOUTH);

            answersPanel.add(rigthAwnser);
            answersPanel.add(wrongAwnser1);
            answersPanel.add(wrongAwnser2);
            answersPanel.add(wrongAwnser3);

            rigthAwnser.addActionListener(new RightAnswerListener(rigthAwnser, continueButton));
            wrongAwnser1.addActionListener(new WrongButtonListener(wrongAwnser1,wrongAwnser2,wrongAwnser3,continueButton));
            wrongAwnser2.addActionListener(new WrongButtonListener(wrongAwnser1,wrongAwnser2,wrongAwnser3,continueButton));
            wrongAwnser3.addActionListener(new WrongButtonListener(wrongAwnser1,wrongAwnser2,wrongAwnser3,continueButton));

            gameFrame.setSize(400,600);
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

            public RightAnswerListener( JButton rightAnswer, JButton continueButton) {
                this.rightAnswer = rightAnswer;
                this.continueButton = continueButton;
            }

            @Override
            public void actionPerformed(ActionEvent e) {
                // Byt färg på knappen till grön
                rightAnswer.setBackground(Color.GREEN);

                // Gör continue-knappen synlig
                continueButton.setVisible(true);
            }
        }

        private class WrongButtonListener implements ActionListener{
            private JButton wrongAwnser1;
            private JButton wrongAwnser2;
            private JButton wrongAwnser3;
            private JButton continueButton;

            public WrongButtonListener(JButton wrongAwnser1, JButton wrongAwnser2,
                                       JButton wrongAwnser3, JButton continueButton){

                this.wrongAwnser1 = wrongAwnser1;
                this.wrongAwnser2 = wrongAwnser2;
                this.wrongAwnser3 = wrongAwnser3;
                this.continueButton = continueButton;
            }

            @Override
            public void actionPerformed(ActionEvent e) {
               if(e.getSource()== wrongAwnser1) {
                   wrongAwnser1.setBackground(Color.red);
                   continueButton.setVisible(true);
               }
               else if (e.getSource()== wrongAwnser2) {
                   wrongAwnser2.setBackground(Color.red);
                   continueButton.setVisible(true);
               } else if (e.getSource() == wrongAwnser3) {
                   wrongAwnser3.setBackground(Color.red);
                   continueButton.setVisible(true);
               }
            }
        }
    }
}

public void main(String[] args) {
        GUI gui = new GUI();
    }

