import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

// klass som funkar som databas för frågor och svar. man kan anropa hela listan eller en specifik kategori.
public class Database {
    public List<QuestionsAndAnswers> questionsAndAnswers = CreateQuestionsAndAnswers ();
//skapar en lista med alla objekt
    public List<QuestionsAndAnswers> CreateQuestionsAndAnswers (){
         List<QuestionsAndAnswers> questionsAndAnswersList = new ArrayList<>();
         questionsAndAnswersList.add(new QuestionsAndAnswers("I vilken stat i USA ligger Las Vegas?","Texas", "Arizona", "Michgan","Nevada", Categories.JORDEN_RUNT));
         questionsAndAnswersList.add(new QuestionsAndAnswers("Hur många landskap finns det i Sverige?","10", "15", "30", "25", Categories.JORDEN_RUNT));
         questionsAndAnswersList.add(new QuestionsAndAnswers("I vilket hav ligger Påskön?","Indiska oceanen", "Atlanten", "Arktiska oceanen", "Stilla havet", Categories.JORDEN_RUNT));
         questionsAndAnswersList.add(new QuestionsAndAnswers("Vilket av följande museum öppnade i Sverige den 7 maj år 2013?","Fotografiska",  "Moderna Museet", "Vasamuseet", "Abba Museet", Categories.KONST_OCH_KULTUR));
         questionsAndAnswersList.add(new QuestionsAndAnswers("Från vilket land emigrerade den amerikanske konstnären Andy Warhols familj?","Polen", "Tyskland", "Österrike", "Slovakien", Categories.KONST_OCH_KULTUR));
         questionsAndAnswersList.add(new QuestionsAndAnswers("Vem har målat tavlan Midvinterblot, som hänger på Nationalmuseum i Stockholm?", "Jens Andersson", "Frans Simonsson", "Theodor Johansson", "Carl Larsson", Categories.KONST_OCH_KULTUR));
         questionsAndAnswersList.add(new QuestionsAndAnswers("Vad avgör om fettsyror är mättade eller omättade?", "Om den är animalisk eller vegetabilisk", "Om de är flytande eller ej",  "Mängden av transfettsyror", "Fettets kemiska bindning", Categories.I_LABBET));
         questionsAndAnswersList.add(new QuestionsAndAnswers("Vilken typ av ämne klassas vanligt bordssalt som?","Grundämne", "Halvmetall","Ädelmetall",  "Kemisk förening", Categories.I_LABBET));
         questionsAndAnswersList.add(new QuestionsAndAnswers("Glykogen kan förklaras som lagrad energi, men vad består det av?","Lipider", "Blod", "Fett", "Kolhydrater", Categories.I_LABBET));
         questionsAndAnswersList.add( new QuestionsAndAnswers("Vilken är den äldsta civilisationen i världen?", "Nildalen", "Indusdalen", "Egypten", "Mesopotamien", Categories.HISTORIA));
         questionsAndAnswersList.add( new QuestionsAndAnswers("Vilket land var först med att ge kvinnor rösträtt, 1893? ","USA", "Kanada", "Sydafrika", "Nya Zeeland", Categories.HISTORIA));
         questionsAndAnswersList.add( new QuestionsAndAnswers("Vilket år startade första världskriget?","1910", "1916",  "1924", "1914", Categories.HISTORIA));
         questionsAndAnswersList.add( new QuestionsAndAnswers("Vad heter den italienska desserten som översätts till kokt grädde?","Creme Brulee", "Tiramisu", "Glass", "Pannacotta", Categories.MAT_OCH_DRYCK));
         questionsAndAnswersList.add( new QuestionsAndAnswers("I vilket land uppfanns Hawaii-pizzan?","USA", "Australien", "Holland", "Kanada", Categories.MAT_OCH_DRYCK));
         questionsAndAnswersList.add( new QuestionsAndAnswers("Från vilken region i Italien kommer Chiantiviner?","La Marche", "Rom", "Verona", "Toscana", Categories.MAT_OCH_DRYCK));
         questionsAndAnswersList.add( new QuestionsAndAnswers("Vilket är kroppens största organ","Hjärnan", "Hjärtat", "Levern","Huden", Categories.KROPP_OCH_KNOPP));
         questionsAndAnswersList.add( new QuestionsAndAnswers("Vad är det som hindrar blodet från att pumpas åt fel håll i hjärtat?","Kapilärer", "Artärer", "Vener","Klaffar", Categories.KROPP_OCH_KNOPP));
         questionsAndAnswersList.add( new QuestionsAndAnswers("Vad är de främsta orsakerna till s.k. bakfylla efter omfattande alkoholintag?","Blandning av olika drycker", "Etanolallergi", "Trötthet", "Kraftig uttorkning", Categories.KROPP_OCH_KNOPP));
         questionsAndAnswersList.add( new QuestionsAndAnswers("Vilken enhet används för att ange hastigheten på ett bredband?", "Terabit per sekund  ", "Kilobit per sekund ", "Gigabit per sekund", "Megabit", Categories.TEKNIKENS_UNDER));
         questionsAndAnswersList.add( new QuestionsAndAnswers("Vilken innovation uppfann svensken Alfred Nobel, som även grundade Nobelpriset?"," Penicillinet","Vaccinet", "Telefonen", "Dynamiten", Categories.TEKNIKENS_UNDER));
         questionsAndAnswersList.add( new QuestionsAndAnswers("Vilket tema hade operativsystemet Android på sina versioner fram till 2019, då man övergick till enbart siffror?","Frukter","Smilisar", "Städer", "Sötsaker", Categories.TEKNIKENS_UNDER));
         questionsAndAnswersList.add( new QuestionsAndAnswers("Hur många tävlande hade Squid game?","421","410", "435", "456", Categories.TV_SERIER));
         questionsAndAnswersList.add( new QuestionsAndAnswers("Var utspelar sig serien Magnum?","Borabora", "Dominikanska republiken", "Miami", "Hawaii", Categories.TV_SERIER));
         questionsAndAnswersList.add( new QuestionsAndAnswers("Hur många säsonger har serien La casa de papel?","2","6", "3", "5", Categories.TV_SERIER));
         questionsAndAnswersList.add( new QuestionsAndAnswers("Från vilket land kommer spelet Padel från?","Mexiko","Portugal", "Argentina",  "Spanien", Categories.SPORT_OCH_FRITID));
         questionsAndAnswersList.add( new QuestionsAndAnswers("Hur många paddlar används i en kajak? ","3", "2", "4", "1", Categories.SPORT_OCH_FRITID));
         questionsAndAnswersList.add( new QuestionsAndAnswers("Fotbollsspelaren Christiano Ronaldo har blivit utsedd till både Europas och världens bästa fotbollsspelare. Vilket år var det? ","2010", "2012", "2016", "2008", Categories.SPORT_OCH_FRITID));
         questionsAndAnswersList.add( new QuestionsAndAnswers("Vilket är världens största tandförsedda djur?","Elefant","Noshörningen", "Blåval",  "Kaskelot", Categories.DJUR_OCH_NATUR));
         questionsAndAnswersList.add( new QuestionsAndAnswers("Vad brukar lodjuren äta?","Lodjur är allätare", "Älgar och vildsvin","Väster", "Rådjur, harar och skogsfåglar bland annat", Categories.DJUR_OCH_NATUR));
         questionsAndAnswersList.add( new QuestionsAndAnswers("I vilket land ligger Noux nationalpark?","Frankrike","Singapore", "Finland", "Kanada", Categories.DJUR_OCH_NATUR));
         return questionsAndAnswersList;
    }

//metod som gör att man kan hämta frågor och svar för en specifik kategori
public List<QuestionsAndAnswers> getListBasedOnCategory(Categories category) {
     if (category == null || category.category == null) {
          System.out.println("Category or category name is null. Cannot filter questions.");
          return Collections.emptyList();
     }

     return questionsAndAnswers.stream()
             .filter(r -> r.getCategories() != null &&
                     r.getCategories().category != null &&
                     r.getCategories().category.equals(category.category))
             .collect(Collectors.toList());
}
//getter
     public List<QuestionsAndAnswers> getQuestionsAndAnswers() {
          return questionsAndAnswers;
     }
}
