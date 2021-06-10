package Model;

import algorithms.mazeGenerators.Maze;
import algorithms.search.Solution;

import java.io.File;
import java.util.Observer;

public interface IModel {
    public void generateMaze(int rowHeight,int ColWidth);
    public void solveMaze();
    public Maze getMaze();
    public Solution getSolution();
    void updatePlayerLocation(MovementDirection direction);
    int getPlayerRowPos();
    int getPlayerColPos();
    void assignObserver(Observer o);

    public void startServers();
    public void stopServers();
    public void saveMaze(File saveFile);
    public void loadMaze(File file);


}
