package View;

import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.util.ResourceBundle;


public class IntroSceneController implements Initializable {
    private Stage primaryStage;
    private Scene choosePlayerScene;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private ImageView img_muteImageView;
    private Image muteIconDark;
    private Image unMuteIconDark;
    private boolean musicPlay=true;
    private boolean isTransitionVideoPlayed = true;

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
        MazeDisplayer.setAudio();
    }

    public void setScene(Scene scene) {
        this.choosePlayerScene = scene;
    }

    /*Switch to "choosePlayer" scene*/
    public void newGameClicked(){
        try {
            /* Initialize choosePlayer Scene*/
            FXMLLoader choosePlayerFxml = new FXMLLoader(getClass().getResource("choosePlayer.fxml"));
            Parent secondSceneRoot = choosePlayerFxml.load();
            Scene nextScene = new Scene(secondSceneRoot, 1280, 720);
            setScene(nextScene);

            ChoosePlayerController choosePlayerSceneCont = choosePlayerFxml.getController();
            choosePlayerSceneCont.setPrimaryStage(primaryStage);
            choosePlayerSceneCont.setScene(choosePlayerScene);

            if (!isTransitionVideoPlayed) {
                playTransitionMedia();
            } else
                moveToNextScene();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void playTransitionMedia(){
        isTransitionVideoPlayed = true;
        // play transition video:
        String songPath = "resources/Images/introTransitionVideo.mp4";
        String mediaUrl = new File(songPath).toURI().toString();
        Media player = new Media(mediaUrl);
        MediaPlayer mediaPlayer = new MediaPlayer(player);
        MediaView mediaView = new MediaView(mediaPlayer);
        DoubleProperty mvw = mediaView.fitWidthProperty();
        DoubleProperty mvh = mediaView.fitHeightProperty();
        mvw.bind(Bindings.selectDouble(mediaView.sceneProperty(), "width"));
        mvh.bind(Bindings.selectDouble(mediaView.sceneProperty(), "height"));
        mediaView.setPreserveRatio(true);
        anchorPane.getChildren().add(mediaView);
        if(!MyViewController.mute) {
            mute();
            musicPlay = true;
        }
        mediaPlayer.play();
        mediaPlayer.setOnEndOfMedia(() -> {
            if(musicPlay && MyViewController.mute)
                mute();
            moveToNextScene();
        });
    }

    private void moveToNextScene(){
        primaryStage.setScene(choosePlayerScene);
    }

    public void mute(){
        if(!MyViewController.mute) {
            MyViewController.mute=true;
            img_muteImageView.setImage(muteIconDark);
        }else{
            MyViewController.mute=false;
            img_muteImageView.setImage(unMuteIconDark);
        }
        MazeDisplayer.MuteORUnmuteMusic();
    }

}
