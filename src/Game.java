import java.util.List;
// klass som gör att två spelare kan spela mot varandra och turas om


//DETTA MOTSVARAR ServerSideGameThreadLess i TTT

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
