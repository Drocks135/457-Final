import java.util.StringTokenizer;

public class TicTacMove {
    private boolean player;
    private int row;
    private int col;

    /******************************************************************
     * Creates a tictac move
     * @param player: Boolean that represents which player the move is
     *              for, 0 represents O, 1 represents X
     * @param row: This integer represents which spot on the board to
     *            make the move on
     ******************************************************************/
    public TicTacMove(boolean player, int row, int col){
        this.player = player;
        this.row = row;
        this.col = col;
    }

    /******************************************************************
     * Creates a tictac move that parses two strings into a boolean
     * and an integer
     * @param player: A string that represents a boolean of which player the
     *              move is for, 0 represents O, 1 represents X
     * @param col: This string represents an integer of which spot on
     *            the board to make the move on
     ******************************************************************/
    public TicTacMove(String player, String row, String col){
        this.player = Boolean.parseBoolean(player);
        this.row = Integer.parseInt(row);
        this.col = Integer.parseInt(col);
    }

    /******************************************************************
     * Returns and integer representing which spot on the board a move
     * is played on
     ******************************************************************/
    public int GetRow(){
        return this.row;
    }

    /******************************************************************
     * Returns and integer representing which spot on the board a move
     * is played on
     ******************************************************************/
    public int GetCol(){
        return this.col;
    }

    /******************************************************************
     * Returns a boolean representing which player is making the move
     * 0 represents O, 1 represents X
     ******************************************************************/
    public boolean GetPlayer(){
        return this.player;
    }
}
