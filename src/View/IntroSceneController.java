package View;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class IntroSceneController {
    private Stage primaryStage;
    private Scene nextScene;

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

}
