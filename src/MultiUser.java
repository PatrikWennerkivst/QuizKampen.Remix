import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
/*
        Denna klass agerar brygga mellan varje uppstartad server.
 */

//DETTA MOTSVARAR ServerSidePlayer i TTT
public class MultiUser {
    private List<ObjectOutputStream> writerList = new ArrayList<>();
    MultiUser opppnent;
    Socket socket;
    String playerName;

   /* public MultiUser(Socket socket, String playerName){
        this.socket = socket;
        this.playerName = playerName;
    }

    */

    public void addWriter(ObjectOutputStream createConnection) {
        writerList.add(createConnection);
    }
        //Vad är tanken med denna?? kan vi ta bort den?
    /*public void removeWriter(ObjectOutputStream createConnection) {
        writerList.remove(createConnection);
    }

     */

    //Byt ut String message till ett objekt av QuestionAndAnwsers
    public void print(QuestionsAndAnswers message) {
        try{
            for (ObjectOutputStream createConnection : writerList) {
                createConnection.writeObject(message);
                createConnection.reset(); // Sigrun tyckte vi skulle göra detta
            }
        } catch (IOException e) {
           e.printStackTrace();
        }
    }
}

