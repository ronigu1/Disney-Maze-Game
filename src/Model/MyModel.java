package Model;

import Client.*;
import IO.MyDecompressorInputStream;
import Server.*;
import algorithms.mazeGenerators.Maze;
import algorithms.search.Solution;

import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Observable;
import java.util.Observer;

public class MyModel extends Observable implements IModel {
    private Server generatorServer;
    private Server solverServer;
    private Maze maze;
    private Solution solution;
    private int playerRowPos;
    private int playerColPos;
    /*private int[][] mazeGrid;*/
    private boolean isMazeExist = false;
    private boolean serversStarted = false;

    public MyModel() {
        generatorServer = new Server(5400, 1000, new ServerStrategyGenerateMaze());
        solverServer = new Server(5401, 1000, new ServerStrategySolveSearchProblem());
    }

    /**
     * public method for generating a Maze
     *
     * @param rows the number of rows in the maze
     * @param cols the number of cols in the maze
     */
    public void generateMaze(int rows, int cols) {
        CommunicateWithServer_MazeGenerating(rows, cols);
        isMazeExist = true;
        setChanged();
        notifyObservers("mazeGenerated");
        movePlayer(maze.getStartPosition().getRowIndex(),maze.getStartPosition().getColumnIndex());
    }

    /**
     * generates a Maze instance with given dimensions using CommunicateWithServer_MazeGenerating
     * Updates this.maze with the new Maze we created (doesn't notify ViewModel)
     *
     * @param rows the number of rows in the maze
     * @param cols the number of cols in the maze
     */
    public void CommunicateWithServer_MazeGenerating(int rows, int cols) {
        try {
            Client client = new Client(InetAddress.getLocalHost(), 5400, new IClientStrategy() {
                public void clientStrategy(InputStream inFromServer, OutputStream outToServer) {
                    try {
                        ObjectOutputStream toServer = new ObjectOutputStream(outToServer);
                        ObjectInputStream fromServer = new ObjectInputStream(inFromServer);
                        toServer.flush();
                        int[] mazeDimensions = new int[]{rows, cols};
                        toServer.writeObject(mazeDimensions);
                        toServer.flush();
                        byte[] compressedMaze = (byte[]) fromServer.readObject();
                        InputStream is = new MyDecompressorInputStream(new ByteArrayInputStream(compressedMaze));
                        int decompressedMazeSize = rows * cols;
                        if (compressedMaze[0] == 0)
                            decompressedMazeSize += 7;
                        else
                            decompressedMazeSize += 25;
                        byte[] decompressedMaze = new byte[decompressedMazeSize];
                        is.read(decompressedMaze);
                        maze = new Maze(decompressedMaze);
                    } catch (Exception var10) {
                        var10.printStackTrace();
                    }
                }
            });
            client.communicateWithServer();
        } catch (UnknownHostException var1) {
            var1.printStackTrace();
        }
    }

    public void solveMaze() {
        if (isMazeExist) {
            CommunicateWithServer_SolveSearchProblem();
            setChanged();
            notifyObservers("mazeSolved");
        }
    }

    private void CommunicateWithServer_SolveSearchProblem() {
        try {
            Client client = new Client(InetAddress.getLocalHost(), 5401, new IClientStrategy() {
                public void clientStrategy(InputStream inFromServer, OutputStream outToServer) {
                    try {
                        ObjectOutputStream toServer = new ObjectOutputStream(outToServer);
                        ObjectInputStream fromServer = new ObjectInputStream(inFromServer);
                        toServer.flush();
                        toServer.writeObject(maze);
                        toServer.flush();
                        Solution mazeSolution = (Solution) fromServer.readObject();
                        solution = mazeSolution;
                    } catch (Exception var10) {
                        var10.printStackTrace();
                    }
                }
            });
            client.communicateWithServer();
        } catch (UnknownHostException var1) {
            var1.printStackTrace();
        }

    }

    private void movePlayer(int row, int col){
        this.playerRowPos = row;
        this.playerColPos = col;
        setChanged();
        notifyObservers("playerMoved");
    }

    public Maze getMaze() {
        return maze;
    }

    public Solution getSolution() {
        return solution;
    }

    public int getPlayerRowPos() {
        return playerRowPos;
    }

    public int getPlayerColPos() {
        return playerColPos;
    }

    /**
     * calls Server.start() for both servers
     */
    public void startServers() {
        if (!(serversStarted)) {
            solverServer.start();
            generatorServer.start();
            serversStarted = true;
        }
    }

    /**
     * calls Server.stop() for both servers
     */
    public void stopServers() {
        if (serversStarted) {
            generatorServer.stop();
            solverServer.stop();
        }
    }

    @Override
    public void assignObserver(Observer o) {
        this.addObserver(o);
    }

    public void saveMaze(File fileToSave) {
        File mazeFile = new File(fileToSave.getPath());
        try {
            mazeFile.createNewFile();
            maze.setStart(playerRowPos, playerColPos);
            FileOutputStream fileOut = new FileOutputStream(fileToSave.getPath());
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(maze);
            objectOut.close();
        } catch (Exception var7) {
            var7.printStackTrace();
        }
    }

    public void loadMaze(File file) {
        try {
            FileInputStream fileIn = new FileInputStream(file.getPath());
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            this.maze = (Maze) objectIn.readObject();
            objectIn.close();
            isMazeExist = true;
            setChanged();
            notifyObservers("mazeLoaded");
            movePlayer(maze.getStartPosition().getRowIndex(),maze.getStartPosition().getColumnIndex());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean up(){return (playerRowPos > 0 && (maze.getData()[playerRowPos-1][playerColPos] != 1)); }

    public boolean down(){return (playerRowPos < this.maze.getRows() - 1 && (maze.getData()[playerRowPos+1][playerColPos] != 1)); }

    public boolean left(){return (playerColPos > 0 && (maze.getData()[playerRowPos][playerColPos-1] != 1)); }

    public boolean right(){return (playerColPos < this.maze.getColumns() - 1 && (maze.getData()[playerRowPos][playerColPos+1] != 1)); }

    public void updatePlayerLocation(MovementDirection direction) {
        int [][] mazeData = maze.getData();
        switch (direction) {
            case UP -> {
                if (up())
                    movePlayer(playerRowPos - 1, playerColPos);
            }
            case DOWN -> {
                if (down())
                    movePlayer(playerRowPos + 1, playerColPos);
            }
            case LEFT -> {
                if (left())
                    movePlayer(playerRowPos, playerColPos - 1);
            }
            case RIGHT -> {
                if (right())
                    movePlayer(playerRowPos, playerColPos + 1);
            }

            case DOWN_LEFT -> {
                if(playerColPos > 0 && playerRowPos < this.maze.getRows()-1 && mazeData[playerRowPos+1][playerColPos-1]!=1 && (down()||left()))
                    movePlayer(playerRowPos+1,playerColPos-1);
            }

            case UP_LEFT -> {
                if(playerColPos > 0 && playerRowPos > 0 && mazeData[playerRowPos-1][playerColPos-1]!=1 && (up()||left()))
                    movePlayer(playerRowPos-1,playerColPos-1);
            }

            case UP_RIGHT -> {
                if(playerRowPos > 0 && playerColPos < this.maze.getColumns() - 1 && mazeData[playerRowPos-1][playerColPos+1]!=1 && (up()||right()))
                    movePlayer(playerRowPos-1,playerColPos+1);
            }

            case DOWN_RIGHT -> {
                if(playerRowPos < this.maze.getRows() - 1 && playerColPos < this.maze.getColumns() - 1 && mazeData[playerRowPos+1][playerColPos+1]!=1 && (down()||right()))
                    movePlayer(playerRowPos+1,playerColPos+1);
            }
        }
    }


}