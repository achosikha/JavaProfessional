import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ChatController implements Initializable {
    public TextField userNameField;
    public ListView usersOnline;
    private Network network;
    public TextField input_field;
    public ListView<String> output_list;
    public Button send;

    public void sendMessage(ActionEvent actionEvent) throws IOException {
        network.writeMessage(input_field.getText());
        input_field.clear();
    }

    public void keyEvent(KeyEvent keyEvent) throws IOException {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            network.writeMessage(input_field.getText());
            input_field.clear();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        network = Network.getInstance();

            new Thread(()->{
                try {
                    while (true) {
                        String message = network.readMessage();
                        if (message.equals("/quit"))
                        {
                            network.close();
                            break;
                        } else if (message.equals("/nickname"))
                        {
                            network.writeMessage(userNameField.getText());
                        } else {
                            Platform.runLater(() -> {
                                output_list.getItems().add(message);
                            });
                        }
                    }
                } catch (IOException e) {
                    System.err.println("Server was broken...");
                }
            }).start();
    }

    public void getMyNickname ()
    {
        String itemSelected = output_list.getSelectionModel().getSelectedItem();
        String[] theMessage = itemSelected.split("( to ) | (: )", 2);
        Platform.runLater(() ->input_field.insertText(0, "/w " + theMessage[0] + " "));
        output_list.getSelectionModel().clearSelection();
    }
}
