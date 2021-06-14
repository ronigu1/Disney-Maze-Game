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
    public void start(Stage primaryStage){
        try {
            /* Initialize Intro Scene*/
            FXMLLoader introSceneFxml = new FXMLLoader(getClass().getResource("View/IntroScene.fxml"));
            Parent firstSceneRoot = introSceneFxml.load();
            primaryStage.setTitle("Disney Maze Game");
            introScene = new Scene(firstSceneRoot, 936, 526);
            IntroSceneController introSceneCont = introSceneFxml.getController();
            introSceneCont.setPrimaryStage(primaryStage);
            primaryStage.setScene(introScene);
            primaryStage.show();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
