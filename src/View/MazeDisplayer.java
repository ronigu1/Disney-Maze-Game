package View;

import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.media.MediaPlayer;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;

public class MazeDisplayer extends Canvas {

    // player position:
    private int playerRowPos;
    private int playerColPos;
    private Solution solution;
    private Maze maze;
    private int[][] grid;
    public static MediaPlayer mediaPlayer;

    private StringProperty ImageFileNameWall = new SimpleStringProperty();
    private Image wallImage;
    private Image solutionPathImage;
    private Image playerImage;
    private Image GoalImage;
    private Image playerGoalImage;
    private Image GoalGifImage;


   public void setPlayer(URL Url) throws Exception {
       playerImage = new Image(new FileInputStream(Url.getPath()));
   }
   public void setGoal(URL Url) throws Exception {
       GoalImage = new Image(new FileInputStream(Url.getPath()));
   }
   public void setPlayerGoal(URL Url) throws Exception {
       playerGoalImage = new Image(new FileInputStream(Url.getPath()));
   }
   public void setGoalGif(URL Url) throws Exception {
       GoalGifImage = new Image(new FileInputStream(Url.getPath()));
   }

    /** draws the maze scene including the player(this method should be invoked after each movement of the player) */
    public void redraw() {
        if (grid != null) {
            double canvasHeight = getHeight();
            double canvasWidth = getWidth();
            double cellHeight = canvasHeight / grid.length;
            double cellWidth = canvasWidth / grid[0].length;
            try {
                /*get gc for this maze Canvas */
                GraphicsContext graphicsContext2D = getGraphicsContext2D();
                /*clear the entire canvas */
                graphicsContext2D.clearRect(0, 0, getWidth(), getHeight());
                /* get paths for images and create an Image instance for each of them */
                wallImage = new Image(new FileInputStream(ImageFileNameWall.get()));
                /* draw the maze itself (walls) */
                for (int i = 0; i < grid.length; i++) {
                    for (int j = 0; j < grid[i].length; j++) {
                        if (grid[i][j] == 1) {
                            graphicsContext2D.drawImage(wallImage, j * cellWidth, i * cellHeight, cellWidth, cellHeight);
                        }
                    }
                }
                /*draw the goal Image in the Maze's Goal Position */
                Position goalPosition = maze.getGoalPosition();
                int goalPosRow = goalPosition.getRowIndex();
                int goalPosCol = goalPosition.getColumnIndex();
                graphicsContext2D.drawImage(GoalImage, goalPosCol * cellWidth, goalPosRow * cellHeight, cellWidth, cellHeight);
                /*draw the player's Image in player's current position */
                graphicsContext2D.drawImage(playerImage, goalPosCol * cellWidth, goalPosRow * cellHeight, cellWidth, cellHeight);
                if (playerRowPos == goalPosRow && playerColPos == goalPosCol) {
                    showStageForUserWinningTheGame();
                }
            } catch (FileNotFoundException e) {
            }
        }
    }

    private void showStageForUserWinningTheGame() {

    }


    public static void MuteORUnmuteMusic() {
    }

    public void setPlayerPositionAndRedraw(int startPosRow, int startPosCol){
        this.playerRowPos =  startPosRow;
        this.playerColPos = startPosCol;
        redraw();
    }

    public void setPlayerStartPositionWithoutRedraw(int startPosRow, int startPosCol) {
        this.playerRowPos =  startPosRow;
        this.playerColPos = startPosCol;
    }

    public void setMazeGridAndRedraw(int[][] maze) {
    }

    public void setMazeGridWithoutRedraw(int[][] data) {
    }

    public void setMaze(Maze maze) {
    }

    public void setSolution(Solution solution) {
    }

    public void drawSolution() {
    }
}

