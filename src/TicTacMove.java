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
