import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;
public class Minesweeper {
    private class MineTile extends JButton{
        int r;
        int c;

        public MineTile(int r, int c){
            this.r = r;
            this.c = c;
        }
    }

    int tileSize = 70;
    int numRows = 8;
    int numCols = numRows;
    int boardWidth = numCols * tileSize;
    int boardHeight = numRows * tileSize;

    JFrame frame = new JFrame("Minesweeper");

    //To display game name and number of mines present
    JLabel textLabel = new JLabel();
    JPanel textPanel = new JPanel();

    JPanel boardPanel = new JPanel();

    int mineCount = 10;

    MineTile[][] board = new MineTile[numRows][numCols];
    ArrayList<MineTile> mineList;
    Random random = new Random();

    int tilesClicked = 0; //count if all tiles were clicked other than ones with mines
    boolean gameOver = false; //true if click on mine

    Minesweeper(){
        frame.setSize(boardWidth,boardHeight);
        frame.setLocationRelativeTo(null); //open new game window at center of your monitor screen
        frame.setResizable(false); //user not allowed to resize screen
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //click x to close window
        frame.setLayout(new BorderLayout());

        //Adding text to frame
        textLabel.setFont(new Font("Arial",Font.BOLD, 25));
        textLabel.setHorizontalAlignment(JLabel.CENTER);
        textLabel.setText("Minesweeper: "+Integer.toString(mineCount)+" Mines");
        textLabel.setOpaque(true);
        textPanel.setLayout(new BorderLayout());
        textPanel.add(textLabel);
        frame.add(textPanel, BorderLayout.NORTH);

        //add board panel to frame
        boardPanel.setLayout(new GridLayout(numRows,numCols)); //8x8
        frame.add(boardPanel);


        for(int r = 0; r < numRows; r++){
            for(int c = 0; c < numCols; c++){
                MineTile tile = new MineTile(r,c); //Create new tile objects to populate board
                board[r][c] = tile; //add objects to 2D array so we can keep track

                //defining characteristics of buttons/tiles
                tile.setFocusable(false);
                tile.setMargin(new Insets(0,0,0,0));
                tile.setFont(new Font("Arial Unicode MS", Font.PLAIN, 45));

                //checking for mouse clicks on tiles
                tile.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        if (gameOver){
                            return;
                        }

                        MineTile tile = (MineTile) e.getSource();

                        //left click reveal beneath tile
                        if(e.getButton() == MouseEvent.BUTTON1){

                            /* if we haven't opened tile yet (default tiles have no text on them, reveal
                            beneath is what adds the text to it, but before a tile is opened, there
                            is technically no text on it) */
                            if(tile.getText() == ""){
                                if(mineList.contains(tile)){ //if the tile is contained in mineList, you hit a mine
                                    revealMines(); //game over, reveal all mines
                                }
                                else{
                                    checkMine(tile.r,tile.c); //checks how many mines are nearby tile
                                }
                            }
                        }
                        //right click set flag on tile
                        else if(e.getButton() == MouseEvent.BUTTON3){
                            if(tile.getText() == "" && tile.isEnabled()){ //can only add flag if button hasn't been clicked on
                                tile.setText("🚩");
                            }
                            else if(tile.getText() == "🚩"){
                                tile.setText("");
                            }
                        }



                    }
                });
                boardPanel.add(tile); //add tile to board panel so that it can appear on JFrame
            }
        }

        /*set frame to be visible only AFTER all components have loaded in, as to avoid issues
        with buttons not loading in time before window opens*/
        frame.setVisible(true);

        setMines();
    }

    void setMines(){
        mineList = new ArrayList<MineTile>();

        int mineLeft = mineCount;
        while(mineLeft > 0){
            int r = random.nextInt(numRows);
            int c = random.nextInt(numCols);

            MineTile tile =  board[r][c];
            if(!mineList.contains(tile)){ //check if tile already has mine on it
                mineList.add(tile);
                mineLeft -= 1;
            }
        }
    }

    void revealMines(){
        /* iterate through array list that holds coordinates of all mines, since game is over,
         we will override and change default text (no text) to bomb emoji
         instead, revealing all mines */
        for(int i = 0; i < mineList.size(); i++){
            MineTile tile = mineList.get(i);
            tile.setText("💣");
        }

        gameOver = true;
        textLabel.setText("Game Over");
    }

    //checks for mines in a 1 tile radius around the tile clicked
    void checkMine(int r, int c){
        //recursive base case
        if(r < 0 || r >= numRows || c < 0 || c >= numCols){
            return;
        }

        MineTile tile = board[r][c];

        //another base case
        if(!tile.isEnabled()){ //tile already clicked on
            return;
        }

        tile.setEnabled(false); //make sure button clicked cannot be clicked twice
        tilesClicked+=1;

        int minesFound = 0;

        //check the three tiles above tile clicked
        minesFound += countMine(r-1,c-1); //top left
        minesFound += countMine(r-1,c); //top
        minesFound += countMine(r-1,c+1); //top right

        //check tiles left and right to the clicked tile
        minesFound += countMine(r,c-1); //left
        minesFound += countMine(r,c+1); //right

        //bottom three tiles
        minesFound += countMine(r+1,c-1); //bottom left
        minesFound += countMine(r+1,c); //bottom
        minesFound += countMine(r+1,c+1); //bottom right

        if(minesFound > 0){
            tile.setText(Integer.toString(minesFound));
        }
        else{
            tile.setText("");

            checkMine(r-1,c-1); //top left
            checkMine(r-1,c); //top
            checkMine(r-1,c+1); //top right

            checkMine(r,c-1); //left
            checkMine(r,c+1); //right

            checkMine(r+1,c-1); //bottom left
            checkMine(r+1,c); //bottom
            checkMine(r+1,c+1); //bottom right
        }

        if(tilesClicked == numRows*numCols-mineList.size()){
            gameOver = true;
            textLabel.setText("Mines Cleared!");
        }
    }

    //checks if tile next to tile clicked is a bomb
    int countMine(int r, int c){
        //check if tile next to the tile clicked is within bounds
        if(r < 0 || r >= numRows || c < 0 || c >= numCols){
            return 0; //return a count of 0 bombs touching the clicked tile
        }

        //assuming tile is within bounds, if tile is in the mineList
        if(mineList.contains(board[r][c])){
            return 1; //return a count of 1 bomb touching the clicked tile
        }

        //within bounds but no mine
        return 0;
    }
}
