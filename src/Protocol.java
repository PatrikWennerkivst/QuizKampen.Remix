public class Protocol {

    Database database = new Database();

    private static final int CONNECTED = 0;
    private static final int GENRE = 1;
    private static final int QUESTION1 = 2;
    private static final int QUESTION2 = 3;

    private int state = CONNECTED;

    public String process(String theInput){
        String theOutput = null;
        String chosenGenre = null;

        if(state == CONNECTED){
            //Skriva ut att man är uppkopplad till spelet
            //theOutput = "Du är uppkopplad";
            state = GENRE;
        } else if(state == GENRE){
            //Här väljer spelaren kategori
            //Då går man igenom en lista med alla kategorier, för att
            //komma åt frågor från den kategorin
            //chosenGenre = theInput;
            for(Categories genre : genres){
                if(chosenGenre.equalsIgnoreCase(genre.getGenreType())){

                    state = QUESTION1;
                }
            }
        } else if(state == QUESTION1){
            //theOutput = database.getQuestion();
            state = QUESTION2;
        } else if(state == QUESTION2){
            state = GENRE;
        }

        return theOutput;
    }

}
