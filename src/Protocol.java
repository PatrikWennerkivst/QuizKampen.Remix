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
    private static final int QUESTION1 = 0;
    private static final int QUESTION2 = 1;
    private static final int QUESTION3 = 2;
    private int state = QUESTION1;

    //Skapar två int som läser in hur många frågor det ska vara i varje runda och hur många rundor totalt
    public void gameSetup() {
        try (FileInputStream fs = new FileInputStream("game_settings.properties")) {
            prop.load(fs);
            questionsInRound = Integer.parseInt(prop.getProperty("questionsInRound"));
            roundsInGame = Integer.parseInt(prop.getProperty("roundsInGame"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Eventuellt to-do om Sigrun vill: Skapa metod som kör antalet rundor som står i properties, men max 10.
    //Ska läsa in roundsInGame och kanske loopa spelet så många gånger?
    //Felmeddelande om man vill ha fler än 10 rundor

    //Tar alltid emot en sträng från servern (i sin tur från klienten) med vald kategori och
    //skickar tillbaks en fråga åt gången och för spelet framåt, max tre frågor tillåtna
    public QuestionsAndAnswers gameProcess(String theInput) {
        QuestionsAndAnswers theOutput = null;
        Categories chosenGenre;

        if (state == QUESTION1 && questionsInRound >= 1) {
            chosenGenre = findCategory(theInput); //användarens val av kategori
            this.currentGenre = database.getListBasedOnCategory(chosenGenre); //hämtar hela kategorin med tre frågor
            Collections.shuffle(currentGenre); //shufflar frågorna
            theOutput = currentGenre.get(0); //skickar alltid fråga 1 vid första anropet
            state = QUESTION2;
        } else if (state == QUESTION2 && theInput.equalsIgnoreCase("ANSWERED")
                && questionsInRound >= 2) { //om client tryckt på knapp och åtminstone två frågor ska ställas
            theOutput = currentGenre.get(1); //så skickas fråga 2
            state = QUESTION3;
        } else if (state == QUESTION3 && theInput.equalsIgnoreCase("ANSWERED")
                && questionsInRound == 3) { //om client tryckt på knapp igen och tre frågor ska ställas
            theOutput = currentGenre.get(2); //så skickas fråga 3
            state = QUESTION1;
        } else if (questionsInRound < 1 || questionsInRound > 3) //om användaren ställt in färre än 1 eller fler än 3 frågor
            System.out.println("Välj minst 1 men max 3 frågor i game_settings-filen"); //kan göras snyggare
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


