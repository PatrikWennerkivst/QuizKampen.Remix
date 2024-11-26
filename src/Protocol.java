import java.io.FileInputStream;
import java.util.*;

import static java.util.Collections.shuffle;


public class Protocol {

    Database database = new Database();

    Categories[] genres = {Categories.JORDEN_RUNT, Categories.KONST_OCH_KULTUR,
            Categories.I_LABBET, Categories.HISTORIA, Categories.MAT_OCH_DRYCK,
            Categories.KROPP_OCH_KNOPP, Categories.TEKNIKENS_UNDER,
            Categories.TV_SERIER, Categories.SPORT_OCH_FRITID, Categories.DJUR_OCH_NATUR};

    private Categories chosenGenre;  //Nuvarande kategori som instansvariabel

    Properties prop = new Properties();
    int questionsInRound;
    int roundsInGame;
    int questionsAsked = 0;

    List<QuestionsAndAnswers> currentGenre;
    private static final int GENRE = 0;
    private static final int QUESTION1 = 1;
    private static final int QUESTION2 = 2;
    private static final int QUESTION3 = 3;
    private int state = GENRE;

    //Initierar spelet
    public void gameSetup() {
        try (FileInputStream fs = new FileInputStream("game_settings.properties")) {
            prop.load(fs);
            questionsInRound = Integer.parseInt(prop.getProperty("questionsInRound")); //3
            roundsInGame = Integer.parseInt(prop.getProperty("roundsInGame")); //6
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

           /*Här tror jag att en justering kommer behövas med states
        Man vill skicka första frågan så fort användraen valt kategori
        Inte vänta på att gameprocess anropas en gång till
        Som det ser ut nu så tas kategorin emot, och en lista med vald kategori
        Men sen behöver metoden anropas igen för att hoppa ner till state QUESTION2
         */

    //Tar alltid emot en sträng från servern (i sin tur från klienten) med vald kategori och
    //skickar tillbaks en fråga åt gången och för spelet framåt, max tre frågor tillåtna
    public QuestionsAndAnswers gameProcess(String theInput) {
        QuestionsAndAnswers theOutput = null;
        Categories chosenGenre;

        //Användaren väljer en kategori och första frågan ställs
        if (state == GENRE) {
            chosenGenre = findCategory(theInput);
            this.currentGenre = database.getListBasedOnCategory(chosenGenre);
            shuffle(currentGenre);
            theOutput = currentGenre.get(0); //ställ fråga 1
            questionsAsked++;
            state = QUESTION1;
            //När användaren har svarat på första frågan så ställs fråga 2
        } else if (state == QUESTION1 && theInput.equalsIgnoreCase("ANSWERED")) {
            if (questionsAsked < questionsInRound) {
                theOutput = currentGenre.get(1);
                questionsAsked++;
                state = QUESTION2;
            }
            //När användaren har svarat på andra frågan ställs fråga 3
        } else if (state == QUESTION2 && theInput.equalsIgnoreCase("ANSWERED")) {
            if (questionsAsked < questionsInRound) {
                theOutput = currentGenre.get(2);
                questionsAsked++;
                state = QUESTION3;
            }
            //När användaren har svarat på tredje frågan återställs räknaren och spelet går tillbaks till GENRE
        } else if (state == QUESTION3 && theInput.equalsIgnoreCase("ANSWERED")) {
            questionsAsked = 0;
            state = GENRE;
        }
        return theOutput;
    }

    public Categories findCategory(String genreString) {
        for (Categories genre : genres) {
            if (genreString.equals(genre.category)) {
                System.out.println("Kategorin: " + genre.category + " hittad");
                return genre;
            }
        }
        return null;
    }

    // Metod som slumpar listan av kategorier som sedan används i RoundGUI och läggs till i varje kategori knapp.
    public Categories randomizeCategory() {
        Random shuffle = new Random();
        int categoryIndex = shuffle.nextInt(genres.length);
        Categories shuffledCategory = genres[categoryIndex];
        return shuffledCategory;
    }

    // Getter för att kunna använda nuvarande kategori i Game
    public Categories getChosenGenre() {
        return chosenGenre;
    }

}


