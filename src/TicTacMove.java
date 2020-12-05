import java.util.StringTokenizer;

public class TicTacMove {
    private boolean player;
    private int move;
    /******************************************************************
     * Creates a tictac move
     * @param player: Boolean that represents which player the move is
     *              for, 0 represents O, 1 represents X
     * @param move: This integer represents which spot on the board to
     *            make the move on
     ******************************************************************/
    public TicTacMove(boolean player, int move){
        this.player = player;
        this.move = move;
    }

    /******************************************************************
     * Creates a tictac move that parses two strings into a boolean
     * and an integer
     * @param player: A string that represents a boolean of which player the
     *              move is for, 0 represents O, 1 represents X
     * @param move: This string represents an integer of which spot on
     *            the board to make the move on
     ******************************************************************/
    public TicTacMove(String player, String move){
        this.player = Boolean.parseBoolean(player);
        this.move = Integer.parseInt(move);
    }
    /******************************************************************
     * Returns and integer representing which spot on the board a move
     * is played on
     ******************************************************************/
    public int GetMove(){
        return this.move;
    }

    /******************************************************************
     * Returns a boolean representing which player is making the move
     * 0 represents O, 1 represents X
     ******************************************************************/
    public boolean GetPlayer(){
        return this.player;
    }
}
