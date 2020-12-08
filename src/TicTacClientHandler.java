import javax.swing.*;
import java.io.IOException;
import java.net.Socket;

public class TicTacClientHandler implements TicTacHandler{
    private TicTacBoard board;
    private TicTacClient client;
    private TicTacLogic game;
    private boolean player = false;

    /*****************************************************************
     * Constructor
     *****************************************************************/
    public TicTacClientHandler(String hostName, int hostPort) throws Exception{
        this.client = new TicTacClient();
        client.StartClient(hostPort, hostName, this);
        this.game = new TicTacLogic(3, true);
        this.board = new TicTacBoard(this);
    }

    /*****************************************************************
     * Sets the client logic to the received player
     *****************************************************************/
    public void SetPlayer(Boolean setPlayer){
        game.SetCurrentPlayer(setPlayer);
    }

    /*****************************************************************
     * Method for when the client receives a move from the server
     *****************************************************************/
    public void ReceiveMove (TicTacMove move){
        board.MakeMove(move);
        game.MakeMove(move);
        HasWon();
    }

    /*****************************************************************
     * Method to send a move to the server whenever a valid move is
     * made
     *****************************************************************/
    public void SendMove(int row, int col){
        TicTacMove move = new TicTacMove(player, row, col);
        if(player == game.GetCurrentPlayer()) {
            client.SendMove(move);
            game.MakeMove(move);
            board.MakeMove(move);
            HasWon();
        } else {
            board.DisplayInvalidTurn();
        }
    }

    /*****************************************************************
     * Sends a reset command to the server
     *****************************************************************/
    public void Reset(){
        client.SendReset();
    }

    /*****************************************************************
     * Method for when the client receives a reset command
     *****************************************************************/
    public void ResetGame(){
        System.out.println("Have i received the servers request?");
        board.resetGame();
        game.ResetBoard();
    }

    /*****************************************************************
     * Method to check if a game has completed and calls the gui to
     * display the result
     *****************************************************************/
    private void HasWon(){
        int result = game.HasWon();
        if(game.HasWon() != -1) {
            if(result == 0) {
                board.hasWinner("Poggers my doggy", "You WON", 0);
            }
            if(result == 1){
                board.hasWinner("You lost, don't tell, but the host cheated (shhhh)", "You LOST", 1);
            }
            if(result == 2)
                board.hasWinner("ISSA DRAW, uWu!!!!!! :)", "WOWWZA NO WAY THATS CRAZY", -1);
        }
    }


}
