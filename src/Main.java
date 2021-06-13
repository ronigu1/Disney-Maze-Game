import View.ChoosePlayerController;
import View.IntroSceneController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    public Scene introScene;
    public Scene choosePlayerScene;
    public Scene playGameScene;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            /* Initialize Intro Scene*/
            FXMLLoader introSceneFxml = new FXMLLoader(getClass().getResource("View/IntroScene.fxml"));
            Parent firstSceneRoot = introSceneFxml.load();
            primaryStage.setTitle("Disney Maze Game");
            introScene = new Scene(firstSceneRoot, 936, 526);
            IntroSceneController introSceneCont = introSceneFxml.getController();

            /* Initialize choosePlayer Scene*/
            FXMLLoader choosePlayerFxml = new FXMLLoader(getClass().getResource("View/choosePlayer.fxml"));
            Parent secondSceneRoot = choosePlayerFxml.load();
            choosePlayerScene = new Scene(secondSceneRoot, 1920, 1080);
            ChoosePlayerController choosePlayerSceneCont = choosePlayerFxml.getController();


            /* Initialize play Scene
            FXMLLoader myViewFxml = new FXMLLoader(getClass().getResource("src/View/MyView.fxml"));
            Parent finalSceneRoot = myViewFxml.load();
            playGameScene = new Scene(finalSceneRoot,1280,720);
            MyViewController playGameSceneCont =  myViewFxml.getController();*/

            introSceneCont.setPrimaryStage(primaryStage);
            introSceneCont.setScene(choosePlayerScene);
            choosePlayerSceneCont.setPrimaryStage(primaryStage);
            choosePlayerSceneCont.setScene(choosePlayerScene);
            // choosePlayerSceneCont.setScene(playGameScene);
            // playGameSceneCont.setStageInView(primaryStage);
            primaryStage.setScene(introScene);
            primaryStage.show();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
