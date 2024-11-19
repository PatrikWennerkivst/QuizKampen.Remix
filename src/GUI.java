import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI extends JFrame implements ActionListener {

    String userAlias = "User Alias";
    String otherUserAlias = "opponents alias";
    String categoryName = "Name of category";
    JButton classicGameButton = new JButton("Classic game");

    public GUI(){
        JFrame frame = new JFrame();
        JPanel startPanel = new JPanel(new BorderLayout());
        JPanel welcomePanel = new JPanel(new GridLayout(2,1));
        JLabel welcomeLable = new JLabel("Welcome to Quiz kampen Remix!");
        JLabel userAliasLable = new JLabel(userAlias);

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
            if (e.getSource() == classicGameButton) {
                // Startar CategorySelectionGUI när classicGameButton trycks
                CategorySecletionGUI categorySelectionGUI = new CategorySecletionGUI();
                categorySelectionGUI.choosCategory();
            }
        }

    public class CategorySecletionGUI implements ActionListener{

       private JButton categoryOne = new JButton("Category one");
       private JButton categoryTwo = new JButton("Category two");
       private JButton categoryThree = new JButton("Category three");
       private JLabel chooseCategory = new JLabel("Choose category");

        public CategorySecletionGUI() {
            categoryOne.addActionListener(this);
            categoryTwo.addActionListener(this);
            categoryThree.addActionListener(this);
        }

        public void choosCategory(){
            JFrame gameFrame = new JFrame("Category Selection");
            JPanel wholePanel = new JPanel(new BorderLayout());
            JPanel categoryPanel = new JPanel(new GridLayout(4,1));

            gameFrame.add(wholePanel);
            wholePanel.add(categoryPanel, BorderLayout.CENTER);
            categoryPanel.setBackground(Color.blue);
            categoryPanel.add(chooseCategory);
            chooseCategory.setHorizontalAlignment(SwingConstants.CENTER);
            categoryPanel.add(categoryOne);
            categoryPanel.add(categoryTwo);
            categoryPanel.add(categoryThree);

            gameFrame.setSize(400,600);
            gameFrame.setVisible(true);
            gameFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
            gameFrame.setLocationRelativeTo(null);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == categoryOne || e.getSource() == categoryTwo || e.getSource() == categoryThree) {
                new ClassicGameGUI();
            }
        }
    }

    public class ClassicGameGUI implements ActionListener{

        private String userAlias;
        private String categoryName;
        private String otherUserAlias;

        public ClassicGameGUI() {
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
                rightAnswer.setBackground(Color.GREEN);
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

    public void main(String[] args) {
        GUI gui = new GUI();
    }
}


