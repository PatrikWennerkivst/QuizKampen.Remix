import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;


public class ClassicGameGUI implements ActionListener {
    private String userAlias = "User alias";
    private String categoryName = "Category name";
    private String otherUserAlias = "Other user";

    public ClassicGameGUI() {
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
        JLabel otherUserAliasLabel = new JLabel(otherUserAlias);
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

    public class ContinueButtonListener implements ActionListener {
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
}
