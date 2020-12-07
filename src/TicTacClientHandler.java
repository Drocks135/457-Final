import java.io.IOException;
import java.net.Socket;

public class TicTacClientHandler implements TicTacHandler{
    private TicTacBoard board;
    private TicTacClient client;
    private TicTacLogic game;
    private boolean player = true;

    public TicTacClientHandler(){
        this.client = new TicTacClient();
        this.game = new TicTacLogic(3, true);
        this.board = new TicTacBoard(this);

    }

    public void ReceiveMove (TicTacMove move){
        board.MakeMove(move);
        client.SendMove(move);
        game.MakeMove(move);
    }

    public void SendMove(int row, int col){
        TicTacMove move = new TicTacMove(player, row, col);
        if(player = game.GetCurrentPlayer())
            client.SendMove(move);
        else
            board.DisplayInvalidTurn();
    }

    public void Reset(){
        client.ResetGame();
        game.ResetBoard();
        board.resetGame();
    }

    public void SendReset(){
        client.SendReset();
    }

    public void ConnectToServer(String serverName, int connectionPort) throws Exception{
        client.StartClient(connectionPort, serverName, this);
    }

}
