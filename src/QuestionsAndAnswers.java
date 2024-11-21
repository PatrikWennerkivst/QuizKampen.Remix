import java.io.Serializable;

public class QuestionsAndAnswers implements Serializable {
// skapar objekt av frågor och svar
private String question;
private String firstAnswer;
private String secondAnswer;
private String thirdAnswer;
private String rightAnswer;
private Categories categories;

     //konstruktor
    public QuestionsAndAnswers(String question, String firstAnswer, String secondAnswer, String thirdAnswer, String rightAnswer, Categories categories) {
        this.question = question;
        this.firstAnswer = firstAnswer;
        this.secondAnswer = secondAnswer;
        this.thirdAnswer = thirdAnswer;
        this.rightAnswer = rightAnswer;
        this.categories = categories;
    }
    // getters



    public String getQuestion() {
        return question;
    }

    public String getFirstAnswer() {
        return firstAnswer;
    }

    public String getSecondAnswer() {
        return secondAnswer;
    }

    public String getThirdAnswer() {
        return thirdAnswer;
    }

    public String getRightAnswer() {
        return rightAnswer;
    }

    public Categories getCategories() {
        return categories;
    }

    //setters

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setFirstAnswer(String firstAnswer) {
        this.firstAnswer = firstAnswer;
    }

    public void setSecondAnswer(String secondAnswer) {
        this.secondAnswer = secondAnswer;
    }

    public void setThirdAnswer(String thirdAnswer) {
        this.thirdAnswer = thirdAnswer;
    }

    public void setRightAnswer(String rightAnswer) {
        this.rightAnswer = rightAnswer;
    }

    public void setCategories(Categories categories) {
        this.categories = categories;
    }
}
