package View;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.net.URL;
import java.util.ResourceBundle;


public class IntroSceneController implements Initializable {
    private Stage primaryStage;
    private Scene nextScene;
    @FXML
    private ImageView img_muteImageView;
    private Image muteIconDark;
    private Image unMuteIconDark;
    private boolean musicPlay=true;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            FileInputStream muteIconDarkInput = new FileInputStream("resources/Images/muteIconDark.png");
            muteIconDark = new Image(muteIconDarkInput);
            FileInputStream unMuteIconDarkInput = new FileInputStream("resources/Images/unMuteIconDark.png");
            unMuteIconDark = new Image(unMuteIconDarkInput);
            img_muteImageView.setImage(unMuteIconDark);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void setScene(Scene scene) {
        this.nextScene = scene;
    }

    /*Switch to "choosePlayer" scene*/
    public void newGameClicked() throws Exception {
        primaryStage.setScene(nextScene);
    }

    public void mute(){
        if(musicPlay) {
            img_muteImageView.setImage(muteIconDark);
            musicPlay = false;
        }else{
            img_muteImageView.setImage(unMuteIconDark);
            musicPlay = true;
        }
    }

}
