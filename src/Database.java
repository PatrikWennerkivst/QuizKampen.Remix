import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


// klass som funkar som databs för frågor och svar. man kan anropa hela listan eller en specifik kategori.
public class Database {
    public List<QuestionsAndAnswers> questionsAndAnswers = CreateQuestionsAndAnswers ();
//skapar en lista med alla objekt
    public List<QuestionsAndAnswers> CreateQuestionsAndAnswers (){
         List<QuestionsAndAnswers> questionsAndAnswersList = new ArrayList<>();
         questionsAndAnswersList.add(new QuestionsAndAnswers("I vilken stat i USA ligger Nevada?","Texas", "Arizona", "Michigan", "Texas", Categories.JORDEN_RUNT));
         questionsAndAnswersList.add(new QuestionsAndAnswers("Hur många landskap finns det i Sverige?","10", "15", "25", "25", Categories.JORDEN_RUNT));
         questionsAndAnswersList.add(new QuestionsAndAnswers("I vilket hav ligger Påskön?","Stilla havet", "Indiska oceanen", "Atlanten", "Indiska oceanen", Categories.JORDEN_RUNT));
         questionsAndAnswersList.add(new QuestionsAndAnswers("Vilket av följande museum öppnade i Sverige den 7 maj år 2013?","Fotografiska", "Abba Museet", "Moderna Museet", "Abba Museet", Categories.KONST_OCH_KULTUR));
         questionsAndAnswersList.add(new QuestionsAndAnswers("Från vilket land emigrerade den amerikanske konstnären Andy Warhols familj?","Polen", "Tyskland", "Slovakien", "Slovakien", Categories.KONST_OCH_KULTUR));
         questionsAndAnswersList.add(new QuestionsAndAnswers("Vem har målat tavlan Midvinterblot, som hänger på Nationalmuseum i Stockholm?","Carl Larsson", "Jens Andersson", "Frans Simonsson", "Carl Larsson", Categories.KONST_OCH_KULTUR));
         questionsAndAnswersList.add(new QuestionsAndAnswers("Vad avgör om fettsyror är mättade eller omättade?","Fettets kemiska bindning", "Om den är animalisk eller vegetabilisk", "Mängden av transfettsyror", "Fettets kemiska bindning", Categories.I_LABBET));
         questionsAndAnswersList.add(new QuestionsAndAnswers("Vilken typ av ämne klassas vanligt bordssalt som?","Grundämne", "Halvmetall", "Kemisk förening", "Kemisk förening", Categories.I_LABBET));
         questionsAndAnswersList.add(new QuestionsAndAnswers("Glykogen kan förklaras som lagrad energi, men vad består det av?","Lipider", "Blod", "Kolhydrater", "Kolhydrater", Categories.I_LABBET));
         questionsAndAnswersList.add( new QuestionsAndAnswers("Vilken är den äldsta civilisationen i världen?","Mesopotamien", "Nildalen", "Indusdalen", "Mesopotamien", Categories.HISTORIA));
         questionsAndAnswersList.add( new QuestionsAndAnswers("Vilket land var först med att ge kvinnor rösträtt, 1893? ","Nya Zeeland", "USA", "Kanada", "Nya Zeeland", Categories.HISTORIA));
         questionsAndAnswersList.add( new QuestionsAndAnswers("Vilket år startade första världskriget?","1910", "1916", "1914", "1914", Categories.HISTORIA));
         questionsAndAnswersList.add( new QuestionsAndAnswers("Vad heter den italienska desserten som översätts till kokt grädde?","Creme Brulee", "Pannacotta", "Tiramisu", "Pannacotta", Categories.MAT_OCH_DRYCK));
         questionsAndAnswersList.add( new QuestionsAndAnswers("I vilket land uppfanns Hawaii-pizzan?","USA", "Australien", "Kanada", "Kanada", Categories.MAT_OCH_DRYCK));
         questionsAndAnswersList.add( new QuestionsAndAnswers("Från vilken region i Italien kommer Chiantiviner?","La Marche", "Rom", "Toscana", "Toscana", Categories.MAT_OCH_DRYCK));
         questionsAndAnswersList.add( new QuestionsAndAnswers("Vilket är kroppens största organ","Hjärnan", "Huden", "Hjärtat", "Huden", Categories.KROPP_OCH_KNOPP));
         questionsAndAnswersList.add( new QuestionsAndAnswers("Vad heter bencellerna vars syfte är att stimulera produktionen av ny benvävnad","Osteocyter", "Osteoblaster", "Osteochondroser", "Osteoblaster", Categories.KROPP_OCH_KNOPP));
         questionsAndAnswersList.add( new QuestionsAndAnswers("Vad är de främsta orsakerna till s.k. bakfylla efter omfattande alkoholintag?","Kraftig uttorkning", "Blandning av olika drycker", "Etanolallergi", "Kraftig uttorkning", Categories.KROPP_OCH_KNOPP));
         questionsAndAnswersList.add( new QuestionsAndAnswers("Vilken enhet används för att ange hastigheten på ett bredband? ","Megabit per sekund", "Terabit per sekund  ", "Kilobit per sekund ", "Megabit per sekund", Categories.TEKNIKENS_UNDER));
         questionsAndAnswersList.add( new QuestionsAndAnswers("Vilken innovation uppfann svensken Alfred Nobel, som även grundade Nobelpriset?"," Penicillinet", "Dynamiten", "Telefonen", "Dynamiten", Categories.TEKNIKENS_UNDER));
         questionsAndAnswersList.add( new QuestionsAndAnswers("Vilket tema hade operativsystemet Android på sina versioner fram till 2019, då man övergick till enbart siffror? ","Frukter", "Städer", "Sötsaker", "Sötsaker", Categories.TEKNIKENS_UNDER));
         questionsAndAnswersList.add( new QuestionsAndAnswers("Hur många tävlande hade Squid game","421", "435", "456", "456", Categories.TV_SERIER));
         questionsAndAnswersList.add( new QuestionsAndAnswers("Var utspelar sig serien Magnum?","Hawaii", "Dominikanska republiken", "Miami", "Hawaii", Categories.TV_SERIER));
         questionsAndAnswersList.add( new QuestionsAndAnswers("Hur många säsonger har serien La casa de papel?","Frukter", "Städer", "Sötsaker", "Sötsaker", Categories.TV_SERIER));
         questionsAndAnswersList.add( new QuestionsAndAnswers("Från vilket land kommer spelet Padel från?","Mexiko", "Argentina", "Spanien", "Man slänger salt över axeln", Categories.SPORT_OCH_FRITID));
         questionsAndAnswersList.add( new QuestionsAndAnswers("Den svenske tennisspelaren Björn Borg har kammat hem många vinster. Hur många vinster har han totalt vunnit i Wimbledon? ","3", "7", "5", "5", Categories.SPORT_OCH_FRITID));
         questionsAndAnswersList.add( new QuestionsAndAnswers("Fotbollsspelaren Christiano Ronaldo har blivit utsedd till världens bästa fotbollsspelare. Vilket år var det? ","2008", "2012", "2016", "2008", Categories.SPORT_OCH_FRITID));
         questionsAndAnswersList.add( new QuestionsAndAnswers("Vilket är världens största tandförsedda djur?","Elefant", "Blåval", "Kaskelot", "Kaskelot", Categories.DJUR_OCH_NATUR));
         questionsAndAnswersList.add( new QuestionsAndAnswers("Vad brukar lodjuren äta?","Lodjur är allätare", "Älgar och vildsvin", "Rådjur, harar och skogsfåglar bland annat", "Rådjur, harar och skogsfåglar bland annat", Categories.DJUR_OCH_NATUR));
         questionsAndAnswersList.add( new QuestionsAndAnswers("I vilket land ligger Noux nationalpark?","Frankrike", "Finland", "Kanada", "Kanada", Categories.DJUR_OCH_NATUR));
         return questionsAndAnswersList;
    }
//metod som gör att man kan hämta frågor och svar för en specifik kategori
    public List<QuestionsAndAnswers> getListBasedOnCategory(Categories category){
       return questionsAndAnswers.stream().filter(r -> r.getCategories().category.equals(category.category)).collect(Collectors.toList());
    }
//getter
     public List<QuestionsAndAnswers> getQuestionsAndAnswers() {
          return questionsAndAnswers;
     }
}
