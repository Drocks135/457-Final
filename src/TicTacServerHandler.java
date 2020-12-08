import javax.swing.*;
import java.io.IOException;
import java.net.Socket;
import java.util.Random;

public class TicTacServerHandler implements TicTacHandler{
    private TicTacBoard board;
    private TicTacServer server;
    private TicTacLogic game;
    private boolean player = true;
    //private Socket socket;

    /*****************************************************************
     * Constructor
     *****************************************************************/
    public TicTacServerHandler(int port) throws Exception {
        this.server = new TicTacServer();
        this.game = new TicTacLogic(3, true);
        this.board = new TicTacBoard(this, "X");
        StartServer(port);
    }

    /*****************************************************************
     * Starts the server and calls to randomly generate which player
     * gets to play first
     *****************************************************************/
    private void StartServer(int port) throws Exception{
        server.StartServer(port, this, server);
        SetPlayer();
    }

    /*****************************************************************
     * This method randomly decides which player gets to play, it's
     * used on reset calls to determine who plays first
     *****************************************************************/
    private void SetPlayer(){
        Random rnJesus = new Random();
        Boolean SetPlayer;
        if (0 == rnJesus.nextInt(2))
            SetPlayer = true;
        else
            SetPlayer = false;
        if (SetPlayer)
            System.out.println("Host Goes First");
        else
            System.out.println("Guest Goes First");


        server.SendPlayer(SetPlayer);
        game.SetCurrentPlayer(SetPlayer);
    }

    /*****************************************************************
     *  This method occurs when the server receives a move, it will
     *  update the gui and the logic
     *****************************************************************/
    public void ReceiveMove(TicTacMove move){
        board.MakeMove(move);
        game.MakeMove(move);
        HasWon();
    }

    /*****************************************************************
     * Checks if the input move is valid and then makes the move on
     * the server side and sends the move to the client.
     * It will have a gui popup if the move is not valid
     *****************************************************************/
    public void SendMove(int row, int col){
        TicTacMove move = new TicTacMove(player, row, col);
        if(game.GetCurrentPlayer() == player) {
            server.SendMove(move);
            board.MakeMove(move);
            game.MakeMove(move);
            HasWon();
        }  else {
        board.DisplayInvalidTurn();
        }
    }

    /*****************************************************************
     * Determines if a win occurred and calls the gui to output a
     * message
     *****************************************************************/
    private void HasWon(){
        int result = game.HasWon();
        if(game.HasWon() != -1) {
            if(result == 0) {
                board.hasWinner("Get rekt nerd", "You LOST", 1);
            }
            if(result == 1){
                board.hasWinner("Poggers my doggy", "You WON", 0);
            }

            if(result == 2){
                board.hasWinner("ISSA DRAW, uWu!!!!!! :)", "WOWWZA NO WAY THATS CRAZY", -1);
            }
        }
    }




    /*****************************************************************
     * Resets the server board and issues a reset command to the
     * client
     *****************************************************************/
    public void Reset(){
        board.resetGame();
        game.ResetBoard();
        server.SendReset();
        SetPlayer();
    }
}
