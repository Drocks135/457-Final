import java.util.Random;
public class TicTacLogic {
    private int[][] GameBoard;
    private boolean CurrentPlayer;
    private int boardSize;

    public TicTacLogic(int boardSize, Boolean startingPlayer){
        this.boardSize = boardSize;
        GameBoard = new int[boardSize][boardSize];
        CurrentPlayer = startingPlayer;
        ResetBoard();
    }

    public void MakeMove(TicTacMove move){
        if (isValidMove(move.GetRow(), move.GetCol())) {
            GameBoard[move.GetRow()][move.GetCol()] = move.GetPlayer() ? 1 : 0;
            SwitchPlayer();
        }
    }

    /** This Method Resets the board. We loop through our size and
     *  Initialize all the values to -1 telling us that no one has
     *  moved on this location. */
    public void ResetBoard(){
        for(int i = 0; i < boardSize; i ++){
            for(int j = 0; j < boardSize; j++){
                GameBoard[i][j] = -1;
            }
        }
    }

    /** This Method Determines whether the game has been won. **/
    public int HasWon(){
        int countX = 0;
        int countO = 0;
        boolean canContinue = false;

        // Rows
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++){
                if (GameBoard[i][j] == 1){
                    countX ++;
                }
                else if (GameBoard[i][j] == 0){
                    countO ++;
                }
            }
            if (countX == boardSize) return 1;
            if (countO == boardSize) return 0;
            countO = 0;
            countX = 0;
        }
        // Cols
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++){
                if (GameBoard[j][i] == 1){
                    countX ++;
                }
                else if (GameBoard[j][i] == 0){
                    countO ++;
                }
            }
            if (countX == boardSize) return 1;
            if (countO == boardSize) return 0;
            countO = 0;
            countX = 0;
        }
        // Diagonals top top left to bottom right
        for (int i = 0; i < boardSize; i++){
            if (GameBoard[i][i] == 1){
                countX ++;
            }
            if (GameBoard[i][i] == 0){
                countO ++;
            }
            if (countX == boardSize) return 1;
            if (countO == boardSize) return 0;
        }
        countO = 0;
        countX = 0;
        // Diagonals bottom left to top right

        if (GameBoard[2][0] == 1)
            countX ++;
        if (GameBoard[1][1] == 1)
            countX ++;
        if (GameBoard[0][2] == 1)
            countX ++;

        if (GameBoard[2][0] == 0)
            countO ++;
        if (GameBoard[1][1] == 0)
            countO ++;
        if (GameBoard[0][2] == 0)
            countO ++;


        if (countX == boardSize) return 1;
        if (countO == boardSize) return 0;

        // Draw
        int i = 0;
        int j = 0;
        while(!canContinue){
            // If empty space we can continue.
            if (GameBoard[i][j] == -1){
                canContinue = true;
            }
            // If we checked the whole board
            if (i == boardSize -1 && j == boardSize -1){
                // See if you still can't continue. If so return false.
                if (!canContinue){
                    return 2;
                }
            }
            // If J is end of row increment i reset j to 0;
            if (j == boardSize - 1){
                i++;
                j = -1;
            }
            // Increment j;
            j++;
        }

        // Game is not won.
        return -1;
    }

    private void SwitchPlayer(){
        if (CurrentPlayer) { CurrentPlayer = false; }
        else { CurrentPlayer = true; }
        //System.out.println("Player switched, cur player: " + CurrentPlayer);
    }

    public Boolean GetCurrentPlayer(){
        return CurrentPlayer;
    }

    public void SetCurrentPlayer(Boolean player){
        CurrentPlayer = player;
    }

    public Boolean isValidMove(int row, int col){
            return (GameBoard[row][col] == -1);
    }

}