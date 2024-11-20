import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class RoundGUI implements ActionListener {
    private String userAlias;
    private String categoryName;
    private String otherUserAlias;
    private String[] categories = {"Category One", "Category Two", "Category Three", "Category Four", "Category Five", "Category Six"};
    JButton nextQuestionButton = new JButton("Fortsätt");

    public RoundGUI(String userAlias, String categoryName, String otherUserAlias) {
        this.userAlias = userAlias;
        this.categoryName = categoryName;
        this.otherUserAlias = otherUserAlias;
        startRound();
    }

    public void startRound() {
        JFrame gameFrame = new JFrame("Round score");
        JPanel wholeGamePanel = new JPanel(new GridLayout(3, 1));
        JPanel totalScoreBoardPanel = new JPanel(new GridLayout(1, 3));
        JPanel roundPanel = new JPanel(new GridLayout(1, 3)); // panel som håller poäng

        // Alias och kategorier
        totalScoreBoardPanel.add(new JLabel(userAlias));
        totalScoreBoardPanel.add(new JLabel(categoryName));
        totalScoreBoardPanel.add(new JLabel(otherUserAlias));

        wholeGamePanel.add(totalScoreBoardPanel);

        JPanel mainUserScorePanel = createScorePanel("User");
        JPanel categoryPanel = createCategoryPanel(categories);
        JPanel otherUserScorePanel = createScorePanel("Other User");

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

    private JPanel createScorePanel(String title) {
        JPanel scorePanel = new JPanel(new GridLayout(6, 3));
        for (int i = 0; i < 18; i++) {
            scorePanel.add(new JButton(title + "" + (i + 1)));
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
            // Skapa en ny instans av ClassicGameGUI med samma parametrar
            ClassicGameGUI classicGameGUI = new ClassicGameGUI(userAlias, categoryName, otherUserAlias);
        }
    }
}

