import java.util.Scanner;
public class TicTacLogic {
    private int[][] GameBoard;
    private boolean CurrentPlayer;

    public TicTacLogic(int boardSize, Boolean startingPlayer){
        GameBoard = new int[boardSize][boardSize];
        CurrentPlayer = startingPlayer;
    }

    public void MakeMove(TicTacMove move){

    }

    public void ResetBoard(){

    }

    //0 for O win, 1 for X win, 2 for draw
    public int HasWon(){
        return 0;
    }

    private void SwitchPlayer(){

    }

    public Boolean GetCurrentPlayer(){
        return CurrentPlayer;
    }

    public Boolean CanPlay(Boolean player){
        return true;
    }
}