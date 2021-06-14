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

import java.io.FileInputStream;
import java.net.URL;
import java.util.ResourceBundle;

public class ChoosePlayerController  implements Initializable {
    private Stage primaryStage;
    private Scene currScene;
    private Scene nextScene;
    private MyViewController viewCon;
    private String ChosenMovie;
    @FXML
    private ImageView img_muteImageView;
    private Image muteIconDark;
    private Image unMuteIconDark;
    private boolean musicPlay=false;
    private boolean firstChoose=true;


    public void initialize(URL location, ResourceBundle resources) {
        try {
            FileInputStream muteIconDarkInput = new FileInputStream("resources/Images/muteIconDark.png");
            muteIconDark = new Image(muteIconDarkInput);
            FileInputStream unMuteIconDarkInput = new FileInputStream("resources/Images/unMuteIconDark.png");
            unMuteIconDark = new Image(unMuteIconDarkInput);
            if(!MyViewController.mute)
                img_muteImageView.setImage(unMuteIconDark);
            else
                img_muteImageView.setImage(muteIconDark);
        }catch(Exception e){
            e.printStackTrace();
        }

    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void setScene(Scene scene) {
        this.currScene = scene;
    }

    public void startPlayerChooserController() throws Exception {
        if(firstChoose){
            //ViewModel -> Model
            MyModel model = new MyModel();
            model.startServers();
            MyViewModel viewModel = new MyViewModel(model);
            model.addObserver(viewModel);

            FXMLLoader myViewFxml = new FXMLLoader(getClass().getResource("MyView.fxml"));
            Parent finalSceneRoot = myViewFxml.load();
            this.nextScene = new Scene(finalSceneRoot, 1280, 720);
            viewCon = myViewFxml.getController();
            viewCon.setViewModel(viewModel);

            viewCon.setPrimaryStage(primaryStage);
            viewCon.setChoosePlayerScene(currScene);

            viewModel.addObserver(viewCon);
            firstChoose = true;
        }
        // Set chosen movie CharactersUrl for the next scene
        setChosenCharactersUrl();
        primaryStage.setScene(nextScene);
    }

    private void setChosenCharactersUrl() throws Exception {
        String playerURL = "resources/Images/PlayerCharacter/"+ChosenMovie+".png";
        String goalURL = "resources/Images/GoalCharacter/"+ChosenMovie+".png";
        String playerGoalURL= "resources/Images/SolutionCharacters/"+ChosenMovie+".png";
        String goalGifURL= "resources/Images/FinalGif/"+ChosenMovie+".gif";
        String winSongUrl = "resources/music/" + ChosenMovie +".mp3";
        viewCon.setCharactersAccordingToUserChoice(playerURL, goalURL, playerGoalURL, goalGifURL, winSongUrl);
    }

    /* onAction buttons for choosing a movie */
    public void beautyAndTheBeast() throws Exception{
        this.ChosenMovie = "beautyAndTheBeast";
        this.startPlayerChooserController();
    }

    public void liloAndStitch() throws Exception{
        this.ChosenMovie = "liloAndStitch";
        this.startPlayerChooserController();
    }

    public void mickeyMouse() throws Exception{
        this.ChosenMovie = "mickeyMouse";
        this.startPlayerChooserController();
    }

    public void snowWhiteAndSevenDwarfs() throws Exception{
        this.ChosenMovie = "snowWhiteAndSevenDwarfs";
        this.startPlayerChooserController();
    }

    public void theJungleBook() throws Exception{
        this.ChosenMovie = "theJungleBook";
        this.startPlayerChooserController();
    }

    public void theLionKing() throws Exception{
        this.ChosenMovie = "theLionKing";
        this.startPlayerChooserController();
    }

    public void theLittleMermaid() throws Exception{
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
