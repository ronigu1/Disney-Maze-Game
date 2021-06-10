package View;

import ViewModel.MyViewModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

public class MyViewController implements Observer, IView{
    public MazeDisplayer mazeDisplayer;
    public TextField txtfldRowsNum;
    public TextField txtfldColsNum;
    public Button btnGenerateMaze;
    public Label lblPlayerRow;
    public Label lblPlayerCol;
    public Pane pane;

    private Stage stage;
    private MyViewModel viewModel;
    public static boolean mute=false;
    public StringProperty updatePlayerRow = new SimpleStringProperty();
    public StringProperty updatePlayerCol = new SimpleStringProperty();
    public javafx.scene.control.Button btnSolveMaze;


    public String getUpdatePlayerRow() {
        return updatePlayerRow.get();
    }
    public String getUpdatePlayerCol() {
        return updatePlayerCol.get();
    }
    @Override
    public void update(Observable o, Object arg) {

    }

    public void KeyPressed(KeyEvent keyEvent) {
    }

    public void generateMaze(ActionEvent actionEvent) {
    }

    public void solveMaze(ActionEvent actionEvent) {
    }

    public void MuteMaze(ActionEvent actionEvent) {
    }


    public void setOnScroll(ScrollEvent scrollEvent) {
    }

    @Override
    public void displayMaze(int[][] maze) {

    }

    @Override
    public void Save() throws IOException {

    }

    @Override
    public void Load() throws IOException, ClassNotFoundException {

    }

    @Override
    public void New() {

    }
}
