import java.io.FileInputStream;
import java.util.Collections;
import java.util.List;
import java.util.Properties;


public class Protocol {

    Database database = new Database();

    Properties prop = new Properties();
    int questionsInRound;
    int roundsInGame;

    List<QuestionsAndAnswers> currentGenre;
    private static final int GENRE = 0;
    private static final int QUESTION1 = 1;
    private static final int QUESTION2 = 2;
    private int state = GENRE;

    //Setup som behöver anropas från en sammanbindande logik-metod, och sen användas på något sätt
    //Skapar två int som läser in hur många frågor det ska vara i varje runda och hur många rundor totalt
    public void gameSetup() {
        try(FileInputStream fs = new FileInputStream("game_settings.properties")) {
            prop.load(fs);
            questionsInRound = Integer.parseInt(prop.getProperty("questionsInRound")); //3
            roundsInGame = Integer.parseInt(prop.getProperty("roundsInGame")); //6
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Behöver också anropas från sammanbindande logik-metod
    public QuestionsAndAnswers gameProcess(Categories chosenGenre){
        QuestionsAndAnswers theOutput = null;

        if(state == GENRE){
            this.currentGenre = database.getListBasedOnCategory(chosenGenre);
            Collections.shuffle(currentGenre);
            state = QUESTION1;
        } else if(state == QUESTION1){
            theOutput = currentGenre.get(0);
            state = QUESTION2;
        } else if(state == QUESTION2){
            theOutput = currentGenre.get(1);
            state = GENRE;
        }
        return theOutput;
    }
}


