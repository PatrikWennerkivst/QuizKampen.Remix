import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Random;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class CategorySecletionGUI implements ActionListener{

    private JButton categoryOne = new JButton();
    private JButton categoryTwo = new JButton();
    private JButton categoryThree = new JButton();
    private JLabel chooseCategory = new JLabel();
    Protocol protocol = new Protocol();
    Client client = new Client();
    QuestionsAndAnswers qaa;


    public CategorySecletionGUI(){
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

        //Anropar metoden som slumpar en kategori från Protocol och skriver ut det med hjälp av toString
        categoryOne.setText(protocol.randomizeCategory().category);
        categoryTwo.setText(protocol.randomizeCategory().category);
        categoryThree.setText(protocol.randomizeCategory().category);

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
            String selectedCategory = ((JButton)e.getSource()).getText();
            client.start();
            System.out.println("CategorySelecition:" + selectedCategory);
            client.sendToServer(selectedCategory);
            ClassicGameGUI classic = null;
            try {
                classic = new ClassicGameGUI(client, qaa);
                client.setClassicGame(classic);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            } catch (ClassNotFoundException ex) {
                throw new RuntimeException(ex);
            }
            //Stänger ner fönstret när vilken JButton som helst trycks ner
            ((JFrame) SwingUtilities.getWindowAncestor((JButton) e.getSource())).dispose();
        }
    }
}
