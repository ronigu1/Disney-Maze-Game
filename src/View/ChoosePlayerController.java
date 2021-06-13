package View;

import Model.MyModel;
import ViewModel.MyViewModel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ChoosePlayerController {
    private Stage primaryStage;
    private Scene nextScene;
    private MyViewController viewCon;
    private String ChosenMovie;

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void setScene(Scene scene) {
        this.nextScene = scene;
    }

    public void startPlayerChooserController() throws Exception {
        //ViewModel -> Model
        MyModel model = new MyModel();
        model.startServers();
        MyViewModel viewModel = new MyViewModel(model);
        model.addObserver(viewModel);

        FXMLLoader myViewFxml = new FXMLLoader(getClass().getResource("src/View/MyView.fxml"));
        Parent finalSceneRoot = myViewFxml.load();
        nextScene =  new Scene(finalSceneRoot,1280,720);
        viewCon = myViewFxml.getController();

        // Set chosen movie CharactersUrl for the next scene
        setChosenCharactersUrl();

        viewCon.setViewModel(viewModel);
        // viewModel.addObserver(view);
        viewModel.addObserver(viewCon);

        primaryStage.setScene(nextScene);
        primaryStage.show();
    }

    private void setChosenCharactersUrl(){
        String playerURL = "resources/Images/PlayerCharacter/"+ChosenMovie+".png";
        String goalURL = "resources/Images/GoalCharacter/"+ChosenMovie+".png";
        String playerGoalURL= "resources/Images/SolutionCharacters/"+ChosenMovie+".png";
        String goalGifURL= "resources/Images/FinalGif/"+ChosenMovie+".gif";
        viewCon.setCharactersAccordingToUserChoice(playerURL, goalURL, playerGoalURL, goalGifURL);
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
}
