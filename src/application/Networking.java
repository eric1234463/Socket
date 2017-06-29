package application;
	
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.stage.Stage;
import server.Server;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;


public class Networking extends Application {
	private Client client ;
    private Stage parentStage;
    @Override
    public void start(Stage stage) throws Exception {
        client = new Client();
        this.parentStage = stage;
        Image icon = new Image(getClass().getResourceAsStream("Assets/icon.png"));
        stage.setTitle("Socket IO File Mangement System");
        stage.getIcons().add(icon);
        goToLogin();
        this.parentStage.show();
       
    }

    public static void main(String[] args) {
        launch(args);
    }
    public void goToLogin() throws IOException{
        try {
            FXMLDocumentController login = (FXMLDocumentController) replaceSceneContent("FXMLDocument.fxml");
            login.setApp(this,client);
        } catch (Exception ex) {
            Logger.getLogger(Networking.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void goToHome() throws IOException{
        try {
            FXMLHomeController home = (FXMLHomeController) replaceSceneContent("FXMLHome.fxml");
            home.setApp(this,client);
        } catch (Exception ex) {
            Logger.getLogger(Networking.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    private Initializable replaceSceneContent(String fxml) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        InputStream in = Networking.class.getResourceAsStream(fxml);
        loader.setBuilderFactory(new JavaFXBuilderFactory());
        loader.setLocation(Networking.class.getResource(fxml));
        AnchorPane page;
        try {
            page = (AnchorPane) loader.load(in);
        } finally {
            in.close();
        } 
        Scene scene = new Scene(page);
        parentStage.setScene(scene);
        parentStage.sizeToScene();
        return (Initializable) loader.getController();
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
