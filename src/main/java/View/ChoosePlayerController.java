package View;

import Model.MyModel;
import ViewModel.MyViewModel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ChoosePlayerController  implements Initializable {
    private static final Logger LOG = LogManager.getLogger();
    private Stage primaryStage;
    private Scene currScene;
    private Scene nextScene;
    private MyViewController viewCon;
    private String ChosenMovie;
    @FXML
    public ImageView img_muteImageView;
    private Image muteIconDark;
    private Image unMuteIconDark;
    private boolean musicPlay=false;
    private boolean firstChoose=true;


    public void initialize(URL location, ResourceBundle resources) {
        try {
            FileInputStream muteIconDarkInput = new FileInputStream("src/main/resources/Images/muteIconDark.png");
            muteIconDark = new Image(muteIconDarkInput);
            FileInputStream unMuteIconDarkInput = new FileInputStream("src/main/resources/Images/unMuteIconDark.png");
            unMuteIconDark = new Image(unMuteIconDarkInput);
            if(!MyViewController.mute)
                img_muteImageView.setImage(unMuteIconDark);
            else
                img_muteImageView.setImage(muteIconDark);
        }catch(Exception e){
            LOG.debug("NotFoundPath",e);
        }

    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void setScene(Scene scene) {
        this.currScene = scene;
    }

    public void startPlayerChooserController(){
        if(firstChoose){
            //ViewModel -> Model
            MyModel model = new MyModel();
            model.startServers();
            MyViewModel viewModel = new MyViewModel(model);
            model.addObserver(viewModel);

            FXMLLoader myViewFxml = new FXMLLoader(getClass().getResource("../MyView.fxml"));
            Parent finalSceneRoot = null;
            try {
                finalSceneRoot = myViewFxml.load();
            } catch (IOException e) {
                LOG.debug("IOException",e);
            }
            this.nextScene = new Scene(finalSceneRoot, 865, 645);
            viewCon = myViewFxml.getController();
            viewCon.setViewModel(viewModel);

            viewCon.setPrimaryStage(primaryStage);
            viewCon.setChoosePlayerScene(currScene);

            viewModel.addObserver(viewCon);
            firstChoose = true;
        }
        // Set chosen movie CharactersUrl for the next scene
        try {
            setChosenCharactersUrl();
        } catch (Exception e) {
            LOG.debug("Exception",e);
        }
        primaryStage.setScene(nextScene);
        viewCon.setResizeEvent(nextScene);
        primaryStage.show();
    }

    private void setChosenCharactersUrl() {
        String playerURL = "src/main/resources/Images/PlayerCharacter/"+ChosenMovie+".png";
        String goalURL = "src/main/resources/Images/GoalCharacter/"+ChosenMovie+".png";
        String playerGoalURL= "src/main/resources/Images/SolutionCharacters/"+ChosenMovie+".png";
        String goalGifURL= "src/main/resources/Images/FinalGif/"+ChosenMovie+".gif";
        String winSongUrl = "src/main/resources/music/" + ChosenMovie +".mp3";
        viewCon.setCharactersAccordingToUserChoice(playerURL, goalURL, playerGoalURL, goalGifURL, winSongUrl);
    }

    /* onAction buttons for choosing a movie */
    public void beautyAndTheBeast(){
        this.ChosenMovie = "beautyAndTheBeast";
        this.startPlayerChooserController();
    }

    public void liloAndStitch(){
        this.ChosenMovie = "liloAndStitch";
        this.startPlayerChooserController();
    }

    public void mickeyMouse(){
        this.ChosenMovie = "mickeyMouse";
        this.startPlayerChooserController();
    }

    public void snowWhiteAndSevenDwarfs(){
        this.ChosenMovie = "snowWhiteAndSevenDwarfs";
        this.startPlayerChooserController();
    }

    public void theJungleBook(){
        this.ChosenMovie = "theJungleBook";
        this.startPlayerChooserController();
    }

    public void theLionKing(){
        this.ChosenMovie = "theLionKing";
        this.startPlayerChooserController();
    }

    public void theLittleMermaid(){
        this.ChosenMovie = "theLittleMermaid";
        this.startPlayerChooserController();
    }

    public void mute() {
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
