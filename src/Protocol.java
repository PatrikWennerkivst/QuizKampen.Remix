import java.io.FileInputStream;
import java.util.*;

import static java.util.Collections.shuffle;


public class Protocol {

    Database database = new Database();

    Categories [] genres = {Categories.JORDEN_RUNT, Categories.KONST_OCH_KULTUR,
            Categories.I_LABBET, Categories.HISTORIA, Categories.MAT_OCH_DRYCK,
            Categories.KROPP_OCH_KNOPP, Categories.TEKNIKENS_UNDER,
            Categories.TV_SERIER, Categories.SPORT_OCH_FRITID, Categories.DJUR_OCH_NATUR};

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
    public QuestionsAndAnswers gameProcess(String theInput){
        QuestionsAndAnswers theOutput = null;
        Categories chosenGenre;

        /*Här tror jag att en justering kommer behövas med states
        Man vill skicka första frågan så fort användraen valt kategori
        Inte vänta på att gameprocess anropas en gång till
        Som det ser ut nu så tas kategorin emot, och en lista med vald kategori
        Men sen behöver metoden anropas igen för att hoppa ner till state QUESTION1
         */
        if(state == GENRE){
            chosenGenre = findCategory(theInput);
            this.currentGenre = database.getListBasedOnCategory(chosenGenre);
            shuffle(currentGenre);
            theOutput = currentGenre.get(0);
            state = QUESTION1;
        } else if(state == QUESTION1 && theInput.equalsIgnoreCase("ANSWERED")){
            theOutput = currentGenre.get(1);
            state = QUESTION2;
        } else if(state == QUESTION2){
            theOutput = currentGenre.get(2);
            state = GENRE;
        }
        return theOutput;
    }

    public Categories findCategory(String genreString){
        for(Categories genre : genres){
            if (genreString.equals(genre.category)){
                System.out.println("Kategorin: " + genre.category + " hittad");
                return genre;
            }
        }
        return null;
    }
    // Metod som slumpar listan av kategorier som sedan används i RoundGUI och läggs till i varje kategori knapp.
    public Categories randomizeCategory(){
        Random shuffle = new Random();
        int categoryIndex = shuffle.nextInt(genres.length);
        Categories shuffledCategory = genres[categoryIndex];
        return shuffledCategory;
    }
}


