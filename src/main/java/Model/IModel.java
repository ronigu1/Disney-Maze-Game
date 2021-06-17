package Model;

import algorithms.mazeGenerators.Maze;
import algorithms.search.Solution;

import java.io.File;
import java.util.Observer;

public interface IModel {
    void generateMaze(int rowHeight,int ColWidth);
    void solveMaze();
    Maze getMaze();
    Solution getSolution();
    void updatePlayerLocation(MovementDirection direction);
    int getPlayerRowPos();
    int getPlayerColPos();
    void assignObserver(Observer o);

    void startServers();
    void stopServers();
    void saveMaze(File saveFile);
    void loadMaze(File file);


}
