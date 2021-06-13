package View;

import Server.Configurations;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;

public class PropertiesController implements Initializable {

    @FXML
    public Stage stage;
    //public TextField searchingAlgorithm;
    //public TextField generator;
    public ChoiceBox<String> searchingAlgorithm;
    public ChoiceBox<String> generator;
    @FXML
    public Button btn_update;
    private Configurations Config;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Config = Configurations.getInstance();
        generator.getItems().addAll("EmptyMazeGenerator","SimpleMazeGenerator","MyMazeGenerator");
        searchingAlgorithm.getItems().addAll("BreadthFirstSearch","DepthFirstSearch", "BestFirstSearch");
        try {
            Properties properties = new Properties();
            properties.load(new FileInputStream("resources/config.properties"));

            String search= properties.getProperty("searchingAlgorithm");
            String generate= properties.getProperty("generator");
            if(search.equals("BestFirstSearch")){
                searchingAlgorithm.setValue("BestFirstSearch");}
            else if(search.equals("DepthFirstSearch")){
                searchingAlgorithm.setValue("DepthFirstSearch");}
            else if(search.equals("BreadthFirstSearch")){
                searchingAlgorithm.setValue("BreadthFirstSearch");}
            if(generate.equals("MyMazeGenerator")){
                generator.setValue("MyMazeGenerator");}
            else if(generate.equals("SimpleMazeGenerator")){
                generator.setValue("SimpleMazeGenerator");}
            else if(generate.equals("EmptyMazeGenerator")){
                generator.setValue("EmptyMazeGenerator");}

        }catch (Exception e){}}

    public void UpdateMouseClicked(){
        Config.setMazeGeneratingAlgorithm(generator.getValue());
        Config.setMazeSearchingAlgorithm(searchingAlgorithm.getValue());
        Stage stage = (Stage) btn_update.getScene().getWindow();
        stage.close();
    }
    public Stage getStage() {
        return stage;
    }
    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
