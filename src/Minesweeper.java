import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;
public class Minesweeper {
    int tileSize = 70;
    int numRows = 8;
    int numCols = numRows;
    int boardWidth = numCols * tileSize;
    int boardHeight = numRows * tileSize;

    JFrame frame = new JFrame("Minesweeper");

    //To display game name and number of mines present
    JLabel textLabel = new JLabel();
    JPanel textPanel = new JPanel();

    Minesweeper(){
        frame.setVisible(true);
        frame.setSize(boardWidth,boardHeight);
        frame.setLocationRelativeTo(null); //open new game window at center of your monitor screen
        frame.setResizable(false); //user not allowed to resize screen
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //click x to close window
        frame.setLayout(new BorderLayout());

    }
}
