import View.IntroSceneController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main extends Application {
    public Scene introScene;

    private final Logger LOG = LogManager.getLogger();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage){
        try {
            /* Initialize Intro Scene*/
            FXMLLoader introSceneFxml = new FXMLLoader(getClass().getResource("IntroScene.fxml"));
            Parent firstSceneRoot = introSceneFxml.load();
            primaryStage.setTitle("Disney Maze Game");
            introScene = new Scene(firstSceneRoot, 936, 526);
            IntroSceneController introSceneCont = introSceneFxml.getController();
            introSceneCont.setPrimaryStage(primaryStage);
            primaryStage.setScene(introScene);
            primaryStage.show();
        }
        catch (Exception e){
            LOG.error(e);
        }
    }
}
