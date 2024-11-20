import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class CategorySecletionGUI implements ActionListener {

    private JButton categoryOne = new JButton("Category one");
    private JButton categoryTwo = new JButton("Category two");
    private JButton categoryThree = new JButton("Category three");
    private JLabel chooseCategory = new JLabel("Choose category");

    public CategorySecletionGUI() {
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

        categoryOne.addActionListener(this);
        categoryTwo.addActionListener(this);
        categoryThree.addActionListener(this);

        gameFrame.setSize(400,600);
        gameFrame.setVisible(true);
        gameFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        gameFrame.setLocationRelativeTo(null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == categoryOne || e.getSource() == categoryTwo || e.getSource() == categoryThree) {
            ClassicGameGUI gameGUI = new ClassicGameGUI();
            gameGUI.starClassicGame();
            //Stänger ner fönstret när vilken JButton som helst trycks ner
            ((JFrame) SwingUtilities.getWindowAncestor((JButton) e.getSource())).dispose();
        }
    }
}
