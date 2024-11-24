import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class StartGUI extends JFrame implements ActionListener {

    String userAlias = "User Alias";
    JButton classicGameButton = new JButton("Classic game");

    public StartGUI(){
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
                CategorySecletionGUI categorySelectionGUI = null;
                categorySelectionGUI = new CategorySecletionGUI();
                categorySelectionGUI.choosCategory();
                // Stänger ner fönstret efter att classicButton tryckts
                ((JFrame) SwingUtilities.getWindowAncestor(classicGameButton)).dispose();

            }
        }
    //public void main(String[] args) {

    //}

}



