package View;

import ViewModel.MyViewModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
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

public class MyViewController implements Observer, IView, Initializable {
    @FXML
    public MazeDisplayer mazeDisplayer;
    @FXML
    public TextField txtfldRowsNum;
    @FXML
    public TextField txtfldColsNum;
    @FXML
    public Button btnGenerateMaze;
    @FXML
    public Label lblPlayerRow;
    @FXML
    public Label lblPlayerCol;
    @FXML
    public Pane pane;
    @FXML
    int displayCounter=1;

    private Stage primaryStage;
    private Scene choosePlayerScene;
    private MyViewModel viewModel;
    public static boolean mute = false;
    public static boolean solButten=false;
    public StringProperty updatePlayerRow = new SimpleStringProperty();
    public StringProperty updatePlayerCol = new SimpleStringProperty();
    public javafx.scene.control.Button btnSolveMaze;

    public void setChoosePlayerScene(Scene choosePlayerScene) {
        this.choosePlayerScene = choosePlayerScene;
    }
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
    public void initialize() {
        lblPlayerRow.textProperty().bind(updatePlayerRow);
        lblPlayerCol.textProperty().bind(updatePlayerCol);
    }

    public void setPrimaryStage(Stage stage) {
        this.primaryStage = stage;
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
        solButten = false;
        mazeDisplayer.setMaze(viewModel.getMaze());
        mazeDisplayer.setMazeGridWithoutRedraw(viewModel.getMaze().getData());
        initialize();
        displayCounter = 2;
        displayMaze(viewModel.getMaze().getData());
    }

    private void mazeSolved() {
        mazeDisplayer.setSolution(viewModel.getSolution());
        mazeDisplayer.redraw(MyViewController.solButten);
    }

    private void playerMoved() {
        displayMaze(viewModel.getMaze().getData());
    }

    private void mazeGenerated() {
        solButten = false;
        btnGenerateMaze.setDisable(false);
        mazeDisplayer.setMaze(viewModel.getMaze());
        mazeDisplayer.setPlayerStartPositionWithoutRedraw(viewModel.getMaze().getStartPosition().getRowIndex(),viewModel.getMaze().getStartPosition().getColumnIndex());
        initialize();
        displayCounter = 1;
        displayMaze(viewModel.getMaze().getData());
    }
    @Override
    public void displayMaze(int[][] maze) {
        int startPosRow = viewModel.getPlayerRow();
        int startPosCol = viewModel.getPlayerCol();
        if(displayCounter == 1)
            mazeDisplayer.setMazeGridAndRedraw(maze);
        else if(displayCounter != 1)
            mazeDisplayer.setPlayerPositionAndRedraw(startPosRow,startPosCol);
        btnSolveMaze.setDisable(false);
        setUpdatePlayerRow(startPosRow);
        setUpdatePlayerCol(startPosCol);
        displayCounter++;
    }

    public void KeyPressed(KeyEvent keyEvent) {
        viewModel.movePlayer(keyEvent.getCode());
        keyEvent.consume();
    }
    public void setCharactersAccordingToUserChoice(String playerUrl,String GoalUrl, String playerGoalUrl, String GoalGifUrl) throws Exception {
        mazeDisplayer.setPlayer(playerUrl);
        mazeDisplayer.setGoal(GoalUrl);
        mazeDisplayer.setPlayerGoal(playerGoalUrl);
        mazeDisplayer.setGoalGif(GoalGifUrl);

    }

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
        if (solButten) {
            solButten = false;
            displayMaze(viewModel.getMaze().getData());
        } else {
            solButten = true;
            viewModel.solveMaze();
        }
/*        viewModel.solveMaze();
        btnSolveMaze.setDisable(true);*/
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
        try {
            Stage stage = new Stage();
            stage.setTitle("About");
            FXMLLoader fxmlLoader = new FXMLLoader();
            Parent root = fxmlLoader.load(getClass().getResource("About.fxml").openStream());
            Scene scene = new Scene(root, 430, 230);
            scene.getStylesheets().add(getClass().getResource("MainStyle.css").toExternalForm());
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL); //Lock the window until it closes
            stage.show();
        }catch (Exception e) { e.printStackTrace();}
    }

    public void Options(ActionEvent actionEvent) throws Exception {
        Stage stage = new Stage();
        stage.setTitle("Properties");
        FXMLLoader propFXML = new FXMLLoader(getClass().getResource("/View/Properties.fxml"));
        Parent root = propFXML.load();
        PropertiesController propController = propFXML.getController();
        propController.setStage(stage);
        Scene scene = new Scene(root, 500, 250);
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    public void Help() {
        try {
            Stage stage = new Stage();
            stage.setTitle("Help");
            FXMLLoader fxmlLoader = new FXMLLoader();
            Parent root = fxmlLoader.load(getClass().getResource("Help.fxml").openStream());
            Scene scene = new Scene(root, 500, 250);
            scene.getStylesheets().add(getClass().getResource("MainStyle.css").toExternalForm());
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL); //Lock the window until it closes
            stage.show();
        } catch (Exception e) { e.printStackTrace();}
    }

    @Override
    public void Save() throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("User's previous maze", "*.maze"));
        fileChooser.setInitialFileName("mySavedMazeGame");
        File fileToSave = fileChooser.showSaveDialog(primaryStage);
        if (fileToSave != null) {
            viewModel.saveMaze(fileToSave);
        }
    }

    @Override
    public void Load() throws IOException, ClassNotFoundException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Load maze");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Maze Files", "*.maze"));
        File fileToLoad = fileChooser.showOpenDialog(primaryStage);
        if (fileToLoad != null) {
            viewModel.loadMaze(fileToLoad);
        }

    }

    @Override
    public void New() {
        primaryStage.setScene(choosePlayerScene);
        mazeDisplayer.clearMaze(mazeDisplayer.getGraphicsContext2D());
        txtfldRowsNum.setText("");
        txtfldColsNum.setText("");
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lblPlayerRow.textProperty().bind(updatePlayerRow);
        lblPlayerCol.textProperty().bind(updatePlayerCol);
    }
}
