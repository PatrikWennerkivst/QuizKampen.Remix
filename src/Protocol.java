import java.util.Collections;
import java.util.List;

public class Protocol {

    Database database = new Database();
    List<QuestionsAndAnswers> currentGenre;


    private static final int GENRE = 0;
    private static final int QUESTION1 = 1;
    private static final int QUESTION2 = 2;

    private int state = GENRE;

    public QuestionsAndAnswers gameProcess(String chosenGenre){
        QuestionsAndAnswers theOutput = null;

        if(state == GENRE){
            this.currentGenre = database.getGenreList(chosenGenre);
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

    public List<QuestionsAndAnswers> getGenreList(String chosenGenre){
        for(List genreList : genres){
            if(chosenGenre.equalsIgnoreCase(genreList.get(5).getGenreType())){
                return genreList;
            }
        }
        return null;
    }

}
