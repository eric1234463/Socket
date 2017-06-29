package application;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
public class FXMLDocumentController implements Initializable {

    @FXML
    private PasswordField password;
    @FXML
    private TextField username;
    @FXML
    private TextField ip;
    @FXML
    private ImageView ivImage;

    private Client client;
    private Networking application;
   
    
    public void setApp(Networking application, Client client){
        this.application = application;
        this.client = client;
    }
    
    @FXML
    protected void handleSubmitButtonAction(ActionEvent event) throws IOException {
        if (this.client.Login(ip.getText(), username.getText(), password.getText())) {
            this.application.goToHome();
        };
    }
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Image icon = new Image(getClass().getResourceAsStream("Assets/icon.png"));
        ivImage.setImage(icon);
    }

}
