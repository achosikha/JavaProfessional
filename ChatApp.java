import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import java.io.IOException;

public class ChatApp extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Network network = Network.getInstance();
        Parent root = FXMLLoader.load(getClass().getResource("chatform.fxml"));
        primaryStage.setScene(new Scene(root));

        Image myIcon = new Image ("icon.png");

        primaryStage.getIcons().add(myIcon);
        primaryStage.setTitle("MyChat");

        primaryStage.setResizable(false);
        primaryStage.show();
        primaryStage.setOnCloseRequest(request->{
            try {
                network.writeMessage("/quit");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
