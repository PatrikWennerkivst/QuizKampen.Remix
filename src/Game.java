import java.util.List

// klass som gör att två spelare kan spela mot varandra och turas om
//DETTA MOTSVARAR ServerSideGameThreadLess i TTT

public class Game extends Thread {

    private Player player1;
    private Player player2;
    private Player currentPlayer;
    private List<QuestionsAndAnswers> questionsAndAnswers;


    public Game(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
        this.currentPlayer = player1;
        this.player1.setOpponent(player2);
        this.player2.setOpponent(player1);

    }

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
    private void switchPlayer() {
        currentPlayer = (currentPlayer) % 2;
    }

    //görs idag i i server! OBS detta är bara System out.
    // Ställer en fråga till den aktuella spelaren
    private void askQuestion(int questionIndex) {
        System.out.println("Player " + (currentPlayer) + ": " + questionsAndAnswers.get(questionIndex).getQuestion());

        // rätt svar visas
        System.out.println("Correct answer: " + questionsAndAnswers.get(questionIndex).getRightAnswer());
    }
}




//public class Game extends Thread{
//    private int currentPlayer;
//    private List<QuestionsAndAnswers> questionsAndAnswers;
//
//    //startar med första spelaren och hämtar frågor från aktuell kategori
//    public Game(Database db, Categories category) {
//        currentPlayer = 0;
//        questionsAndAnswers = db.getListBasedOnCategory(category);
//    }


//    //varje spelare svarar på tre frågor
//    public void playRound() {
//
//        for (int i = 0; i < 3; i++) {
//            askQuestion(i);
//        }
//        // växlar till nästa spelare efter tre frågor
//        switchPlayer();
//    }
//
//    private void askQuestion(int questionIndex) {
//
//        // Ställer en fråga till den aktuella spelaren
//        System.out.println("Player " + (currentPlayer + 1) + ": " + questionsAndAnswers.get(questionIndex).getQuestion());
//
//        // rätt svar visas
//        System.out.println("Correct answer: " + questionsAndAnswers.get(questionIndex).getRightAnswer());
//    }


//
//    private void switchPlayer() {
//        // Växlar till nästa spelare (0 -> 1 eller 1 -> 0)
//        currentPlayer = (currentPlayer + 1) % 2;
//    }
//}


//       /*public void main(String[] args) {
//            Database db = new Database();
//            Categories category = Categories.JORDEN_RUNT;
//            TwoPlayers game = new TwoPlayers(db, category);
//            game.playRound();
//            game.playRound();
//
//}
//
//        */
//
