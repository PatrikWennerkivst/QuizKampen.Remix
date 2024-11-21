import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class RoundGUI implements ActionListener {
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
            ClassicGameGUI classicGameGUI = null;
            try {
                classicGameGUI = new ClassicGameGUI();
                classicGameGUI.starClassicGame();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            } catch (ClassNotFoundException ex) {
                throw new RuntimeException(ex);
            }

            //Stänger ner fönstret när nextQuestionButton trycks ner
            ((JFrame) SwingUtilities.getWindowAncestor(nextQuestionButton)).dispose();
        }
    }
}

