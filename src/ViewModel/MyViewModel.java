package ViewModel;

import Model.IModel;
import Model.MovementDirection;
import algorithms.mazeGenerators.Maze;
import algorithms.search.Solution;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

public class MyViewModel extends Observable implements Observer {

    private IModel model;

    public MyViewModel(IModel model) {
        this.model = model;
        this.model.assignObserver(this); //Observe the Model for it's changes

    }

    @Override
    public void update(Observable o, Object arg) {
        String change = (String) arg;
        if (o == model) {
            switch (change) {
                case "mazeGenerated" -> mazeGenerated();
                case "playerMoved" -> playerMoved();
                case "mazeSolved" -> mazeSolved();
                case "mazeLoaded" -> mazeLoaded();
                default -> System.out.println("Not implemented change: " + change);

            }
        }
    }

    //function that update the view(call view.update()):

    private void mazeLoaded() {
        setChanged();
        notifyObservers("mazeLoaded");
    }

    private void playerMoved() {
        setChanged();
        notifyObservers("playerMove");
    }

    private void mazeSolved() {
        setChanged();
        notifyObservers("mazeSolved");
    }

    private void mazeGenerated() {
        setChanged();
        notifyObservers("mazeGenerated");
    }

    public void generateMaze(int rows, int cols) {
        model.generateMaze(rows, cols);
    }

    public Maze getMaze() {
        return model.getMaze();
    }

    public void solveMaze() {
        model.solveMaze();
    }

    public Solution getSolution() {
        return model.getSolution();
    }

    public void stopServers() {
        model.stopServers();
    }

    public void loadMaze(File file) throws IOException, ClassNotFoundException {
        model.loadMaze(file);
    }

    public void saveMaze(File saveFile) throws IOException {
        model.saveMaze(saveFile);
    }

    public void movePlayer(KeyCode KeyCode) {
        MovementDirection direction;
        switch (KeyCode) {
            case UP,NUMPAD8 -> direction = MovementDirection.UP;
            case DOWN,NUMPAD2 -> direction = MovementDirection.DOWN;
            case LEFT,NUMPAD4 -> direction = MovementDirection.LEFT;
            case RIGHT,NUMPAD6 -> direction = MovementDirection.RIGHT;

            case NUMPAD1 -> direction = MovementDirection.DOWN_LEFT;
            case NUMPAD3 -> direction = MovementDirection.DOWN_RIGHT;
            case NUMPAD7 -> direction = MovementDirection.UP_LEFT;
            case NUMPAD9 -> direction = MovementDirection.UP_RIGHT;
            default -> {
                // no need to move the player...
                return;
            }
        }
        model.updatePlayerLocation(direction);
    }
    public int getPlayerRow(){
        return model.getPlayerRowPos();
    }

    public int getPlayerCol(){
        return model.getPlayerColPos();
    }

/*
    private void updatePlayerPosition()
    public boolean validateMazeGenerationParams(int row, int col)
        */
}
