import java.util.Scanner;
public class TicTacLogic {
    public static final int BLANK = 0;
    public static final int XPLAYER = 1;
    public static final int OPLAYER = 2;

    public static final int PLAYING = 0;
    public static final int DRAW = 1;
    public static final int X_WON = 2;
    public static final int O_WON = 3;


    public static final int ROWS = 3, COLS = 3;
    public static int[][] board = new int[ROWS][COLS];

    public static int currentState;
    public static int currentPlayer;
    public static int currntRow, currentCol;

    public static Scanner in = new Scanner(System.in);

    public static void main(String[] args) {
        initGame();
        do {
            playerMove(currentPlayer);
            updateGame(currentPlayer, currntRow, currentCol);
            if (currentState == X_WON) {
                System.out.println("X won!");
            } else if (currentState == O_WON) {
                System.out.println("O won!");
            } else if (currentState == DRAW) {
                System.out.println("Cats Game!");
            }
            // Switch player
            currentPlayer = (currentPlayer == XPLAYER) ? OPLAYER : XPLAYER;
        } while (currentState == PLAYING);
    }


    public static void initGame() {
        for (int row = 0; row < ROWS; ++row) {
            for (int col = 0; col < COLS; ++col) {
                board[row][col] = BLANK;
            }
        }
        currentState = PLAYING;
        currentPlayer = XPLAYER;
    }

    public static void playerMove(int currentPlay) {
        //TODO: Click JButtons to move player
    }

    public static void updateGame(int currentPlay, int currentRow, int currentCol) {
        if (hasWon(currentPlay, currentRow, currentCol)) {
            currentState = (currentPlay == XPLAYER) ? X_WON : O_WON;
        } else if (isDraw()) {
            currentState = DRAW;
        }
    }

    public static boolean isDraw() {
        for (int row = 0; row < ROWS; ++row) {
            for (int col = 0; col < COLS; ++col) {
                if (board[row][col] == BLANK) {
                    return false;
                }
            }
        }
        return true;  // no empty cell, it's a draw
    }

    /**We can use currentRow and currentCol because they can only win after they place a in
     * that row or column **/
    public static boolean hasWon(int currentPlay, int currentRow, int currentCol) {
        return (board[currentRow][0] == currentPlay         // 3-in-the-row
                && board[currentRow][1] == currentPlay
                && board[currentRow][2] == currentPlay

                || board[0][currentCol] == currentPlay      // 3-in-the-column
                && board[1][currentCol] == currentPlay
                && board[2][currentCol] == currentPlay

                || currentRow == currentCol            // 3-in-the-diagonal
                && board[0][0] == currentPlay
                && board[1][1] == currentPlay
                && board[2][2] == currentPlay

                || currentRow + currentCol == 2  // 3-in-the-opposite-diagonal
                && board[0][2] == currentPlay
                && board[1][1] == currentPlay
                && board[2][0] == currentPlay);
    }


}