import java.util.List;

// Klass som gör att två spelare kan spela mot varandra och turas om

public class Game extends Thread {

    private Player player1;
    private Player player2;
    private Player currentPlayer;
    private List<QuestionsAndAnswers> questionsAndAnswers;

    //Hämtar nuvarande kategori från Protocol, använd någonstans
    Protocol protocol = new Protocol();
    Categories currentCategory = protocol.getChosenGenre(); //vi tror att den hämtas här från Protocol

    public Game(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
        this.currentPlayer = player1;
        this.player1.setOpponent(player2);
        this.player2.setOpponent(player1);

    }

    //Få en runda att funka för båda spelarna

    //Flytta metoden som skickar frågor via multiuser från servern hit
    //När man har den kategorin så kan man köra en PlayRound och skicka till MultiUser så att båda får frågorna.
    //Vi hanterar poäng här, kanske i en lista i ClientGUI där vi här räknar och jämför length
    //Ställer frågor i ClientGUI utan att den ändrar för mycket i ClientGUI

    //Logik for 6 ronder och när de har kört så är spelet helt slut

    //I ClientGUI måste bara spelare 1 tillåtas att välja kategori först
    //I nästa rond ska bara spelare 2 tillåtas att välja sin kategori


    //startar med första spelaren och hämtar frågor från aktuell kategori
//anropas från server
    public void startGame(Database db, Categories category) {
        currentPlayer = player1;
        questionsAndAnswers = db.getListBasedOnCategory(category);
    }

    //varje spelare svarar på tre frågor
    public void playRound() {

        for (int i = 0; i < 3; i++) {
            askQuestion(i);
        }
        // växlar till nästa spelare efter tre frågor
        switchPlayer();
    }
    // Växlar till nästa spelare (0 -> 1 eller 1 -> 0), efter att tre frågor har ställts till första spelaren
    /*private void switchPlayer() {
        currentPlayer = (currentPlayer) % 2;
     */
    public void switchPlayer() {
        // Om den nuvarande spelaren är player1, byt till player2
        if (currentPlayer == player1) {
            currentPlayer = player2;
        } else {
            // Annars, byt till player1
            currentPlayer = player1;
        }
    }
    //görs idag i server! OBS detta är bara System out.
    // Ställer en fråga till den aktuella spelaren
    private void askQuestion(int questionIndex) {
        System.out.println("Player " + (currentPlayer) + ": " + questionsAndAnswers.get(questionIndex).getQuestion());

        // rätt svar visas
        System.out.println("Correct answer: " + questionsAndAnswers.get(questionIndex).getRightAnswer());
    }
}
