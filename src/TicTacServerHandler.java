import java.io.IOException;
import java.net.Socket;

public class TicTacServerHandler implements TicTacHandler{
    private TicTacBoard board;
    private TicTacServer server;
    private TicTacLogic game;
    private Socket socket;

    public TicTacServerHandler(int port) throws Exception {
        this.server = new TicTacServer();
        this.game = new TicTacLogic(3, true);
        this.board = new TicTacBoard(this);
        StartServer(port);
    }

    private void StartServer(int port) throws Exception{
        server.StartServer(port, this, server);
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
