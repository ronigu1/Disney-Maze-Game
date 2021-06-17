package View;

import java.io.IOException;

public interface IView {
    void displayMaze(int[][] maze);
    void Save() throws IOException;
    void Load() throws IOException, ClassNotFoundException;
    void New();
}
