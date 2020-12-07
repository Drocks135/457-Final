import java.io.IOException;
import java.net.Socket;
import java.util.Random;

public class TicTacServerHandler implements TicTacHandler{
    private TicTacBoard board;
    private TicTacServer server;
    private TicTacLogic game;
    private boolean player;
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
        if(0 == rnJesus.nextInt(2))
            player = false;
        else
            player = true;
    }

    public void ReceiveMove(TicTacMove move){
        board.MakeMove(move);
        game.MakeMove(move);
    }

    public void SendMove(TicTacMove move){
        server.SendMove(move);
    }

    public void Reset(){
        board.resetGame();
        game.ResetBoard();
        ///server.reset todo: add server reset
    }
}
