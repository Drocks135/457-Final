import java.io.IOException;
import java.net.Socket;
import java.util.Random;

public class TicTacServerHandler implements TicTacHandler{
    private TicTacBoard board;
    private TicTacServer server;
    private TicTacLogic game;
    private boolean player = true;
    //private Socket socket;

    public TicTacServerHandler(int port) throws Exception {
        this.server = new TicTacServer();
        this.game = new TicTacLogic(3, true);
        this.board = new TicTacBoard(this);
        StartServer(port);
    }

    private void StartServer(int port) throws Exception{
        server.StartServer(port, this, server);
        SetPlayer();
    }

    private void SetPlayer(){
        Random rnJesus = new Random();
        Boolean SetPlayer;
        if (0 == rnJesus.nextInt(2))
            SetPlayer = true;
        else
            SetPlayer = false;
        System.out.println(player);
        server.SendPlayer(SetPlayer);
        game.SetCurrentPlayer(SetPlayer);
    }

    public void ReceiveMove(TicTacMove move){
        board.MakeMove(move);
        game.MakeMove(move);
        HasWon();
    }

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

    private void HasWon(){
        int result = game.HasWon();
        if(game.HasWon() != -1) {
            if(result == 0)
                board.hasWinner("You lost, get rekt nerd");
            if(result == 1)
                board.hasWinner("You won, poggers my doggy");
            if(result == 2)
                board.hasWinner("WOOOOOOWZA, ISSA DRAW, uWu!!!!!! :)");
        }
    }

    public void Reset(){
        board.resetGame();
        game.ResetBoard();
        server.SendReset();
        SetPlayer();
    }
}
