import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

// Denna klass agerar brygga mellan varje uppstartad server.
public class MultiUser {
    private List<ObjectOutputStream> writerList = new ArrayList<>();

    public void addWriter(ObjectOutputStream createConnection) {
        writerList.add(createConnection);
    }

    public void removeWriter(ObjectOutputStream createConnection) {
        writerList.remove(createConnection);
    }

    //Byt ut String message till ett objekt av QuestionAndAnwsers
    public void print(QuestionsAndAnswers message) {
        try{
            for (ObjectOutputStream createConnection : writerList) {
                createConnection.writeObject(message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}