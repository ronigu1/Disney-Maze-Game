package View;

import ViewModel.MyViewModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Scale;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

public class MyViewController implements Observer, IView {
    public MazeDisplayer mazeDisplayer;
    public TextField txtfldRowsNum;
    public TextField txtfldColsNum;
    public Button btnGenerateMaze;
    public Label lblPlayerRow;
    public Label lblPlayerCol;
    public Pane pane;
    int displayCounter=1;


    private Stage stage;
    private MyViewModel viewModel;
    public static boolean mute = false;
    public StringProperty updatePlayerRow = new SimpleStringProperty();
    public StringProperty updatePlayerCol = new SimpleStringProperty();
    public javafx.scene.control.Button btnSolveMaze;


    public String getUpdatePlayerRow() {
        return updatePlayerRow.get();
    }

    public String getUpdatePlayerCol() {
        return updatePlayerCol.get();
    }

    public void setUpdatePlayerRow(int updatePlayerRow) {
        this.updatePlayerRow.set(updatePlayerRow + "");
    }

    public void setUpdatePlayerCol(int updatePlayerCol) {
        this.updatePlayerCol.set(updatePlayerCol + "");
    }
    public void setPlayerPosition(int row, int col){
        mazeDisplayer.setPlayerPositionAndRedraw(row, col);
        setUpdatePlayerRow(row);
        setUpdatePlayerCol(col);
    }

    public void initialize(MyViewModel viewModel) {
        lblPlayerRow.textProperty().bind(updatePlayerRow);
        lblPlayerCol.textProperty().bind(updatePlayerCol);
    }

    public void setViewModel(MyViewModel viewModel) {
        this.viewModel = viewModel;
        this.viewModel.addObserver(this);
    }

    public void setPane(Pane pane) {
        this.pane = pane;
    }
    //getter
    public Pane getPane() {
        return pane;
    }

    @Override
    public void update(Observable o, Object arg) {
        String change = (String) arg;
            switch (change) {
            case "mazeGenerated" -> mazeGenerated();
            case "playerMoved" -> playerMoved();
            case "mazeSolved" -> mazeSolved();
            case "mazeLoaded" -> mazeLoaded();
            default -> System.out.println("Not implemented change: " + change);
            }
        }

    private void mazeLoaded() {
        mazeDisplayer.setMaze(viewModel.getMaze());
        mazeDisplayer.setMazeAndRedraw(viewModel.getMaze().getData());
        mazeDisplayer.setPlayerPositionAndRedraw(viewModel.getPlayerRow(), viewModel.getPlayerCol());
        initialize(viewModel);
        displayMaze(viewModel.getMaze().getData());

    }

    private void mazeSolved() {
        mazeDisplayer.setSolution(viewModel.getSolution());
        mazeDisplayer.drawSolution();
    }

    private void playerMoved() {
        displayMaze(viewModel.getMaze().getData());
    }

    private void mazeGenerated() {
        btnGenerateMaze.setDisable(false);
        mazeDisplayer.setMaze(viewModel.getMaze());
        int playerRowIdx = viewModel.getMaze().getStartPosition().getRowIndex();
        int playerColIdx = viewModel.getMaze().getStartPosition().getColumnIndex();
        mazeDisplayer.setPlayerStartPositionWithoutRedraw(playerRowIdx, playerColIdx);
        initialize(viewModel);
        displayMaze(viewModel.getMaze().getData());
    }
    @Override
    public void displayMaze(int[][] maze) {
        mazeDisplayer.setMazeAndRedraw(maze);
        int playerPositionRow = viewModel.getPlayerRow();
        int playerPositionColumn = viewModel.getPlayerCol();
        if (displayCounter != 1)
            mazeDisplayer.setPlayerPositionAndRedraw(playerPositionRow, playerPositionColumn);
        btnSolveMaze.setDisable(false);
        this.updatePlayerRow.set(playerPositionRow + "");
        this.updatePlayerCol.set(playerPositionColumn + "");
        displayCounter++;
    }

    public void KeyPressed(KeyEvent keyEvent) {
        viewModel.movePlayer(keyEvent.getCode());
        keyEvent.consume();
    }
/*
    public void setPlayerCharacterAccordingToUserChoice(String chosenCharacterName) throws Exception {
        mazeDisplayer.getUserChoiceOfPlayer(chosenCharacterName);
    }*/

    public void generateMaze(ActionEvent actionEvent) {
        try {
            int rows = Integer.valueOf(txtfldRowsNum.getText());
            int cols = Integer.valueOf(txtfldColsNum.getText());
            if (rows > 1 && cols > 1) {
                btnGenerateMaze.setDisable(true);
                viewModel.generateMaze(rows, cols);
/*
               to chose a song
            mazeDisplayer.audioChooser(1);
*/
            } else {
                createInformationAlert("Invalid input, The value in the rows and columns fields must be greater then 1");
            }
        } catch (NumberFormatException e) {
            createInformationAlert("Invalid input, Please enter valid value in the rows and columns fields");
        }
    }

    private void createInformationAlert(String alertMessage) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(alertMessage);
        alert.show();
    }

    public void stopServers() {
        viewModel.stopServers();
    }

    public void solveMaze(ActionEvent actionEvent) {
        viewModel.solveMaze();
        btnSolveMaze.setDisable(true);
    }

    public void MuteORUnmuteMaze(ActionEvent actionEvent) {
        if (mute) {
            mute = false;
        } else {
            mute = true;
        }
        MazeDisplayer.MuteORUnmuteMusic();
    }


    public void setOnScroll(ScrollEvent scrollEvent) {
        if (scrollEvent.isControlDown()) {
            double zoomFactor = 1.05;
            if (scrollEvent.getDeltaY() < 0) {
                zoomFactor = 2.0 - zoomFactor;
            }
            Scale newScale = new Scale();
            newScale.setPivotX(scrollEvent.getX());
            newScale.setPivotY(scrollEvent.getY());
            newScale.setX(mazeDisplayer.getScaleX() * zoomFactor);
            newScale.setY(mazeDisplayer.getScaleY() * zoomFactor);
            mazeDisplayer.getTransforms().add(newScale);
            scrollEvent.consume();
        }
    }

    public void exit() {
        System.exit(0);
    }

    public void About(ActionEvent actionEvent) {
    }

    public void Options() throws Exception {
    }

    public void Help() {
    }

    @Override
    public void Save() throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("User's previous maze", "*.maze"));
        fileChooser.setInitialFileName("mySavedMazeGame");
        File fileToSave = fileChooser.showSaveDialog(stage);
        if (fileToSave != null) {
            viewModel.saveMaze(fileToSave);
        }
    }

    @Override
    public void Load() throws IOException, ClassNotFoundException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Maze Files", "*.maze"));
        File fileToLoad = fileChooser.showOpenDialog(stage);
        if (fileToLoad != null) {
            viewModel.loadMaze(fileToLoad);
        }

    }

    @Override
    public void New() {

    }

    public void mouseClicked(MouseEvent mouseEvent) {
        mazeDisplayer.requestFocus();

    }

    public void mouseDragged(MouseEvent mouseEvent) {
        if (viewModel.getMaze() != null) {
            int maximumSize = Math.max(viewModel.getMaze().getRows(), viewModel.getMaze().getColumns());
            double mousePosX = helperMouseDragged(maximumSize, mazeDisplayer.getHeight(),
                    viewModel.getMaze().getRows(), mouseEvent.getX(), mazeDisplayer.getWidth() / maximumSize);
            double mousePosY = helperMouseDragged(maximumSize, mazeDisplayer.getWidth(),
                    viewModel.getMaze().getColumns(), mouseEvent.getY(), mazeDisplayer.getHeight() / maximumSize);
            if (mousePosX == viewModel.getPlayerCol() && mousePosY < viewModel.getPlayerRow())
                viewModel.movePlayer(KeyCode.UP);
            else if (mousePosY == viewModel.getPlayerRow() && mousePosX > viewModel.getPlayerCol())
                viewModel.movePlayer(KeyCode.RIGHT);
            else if (mousePosY == viewModel.getPlayerRow() && mousePosX < viewModel.getPlayerCol())
                viewModel.movePlayer(KeyCode.LEFT);
            else if (mousePosX == viewModel.getPlayerCol() && mousePosY > viewModel.getPlayerRow())
                viewModel.movePlayer(KeyCode.DOWN);

        }
    }

    private double helperMouseDragged(int maxsize, double canvasSize, int mazeSize, double mouseEvent, double temp) {
        double cellSize = canvasSize / maxsize;
        double start = (canvasSize / 2 - (cellSize * mazeSize / 2)) / cellSize;
        double mouse = (int) ((mouseEvent) / (temp) - start);
        return mouse;
    }
}
