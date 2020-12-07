import java.io.IOException;
import java.net.Socket;

public class TicTacClientHandler implements TicTacHandler{
    private TicTacBoard board;
    private TicTacClient client;
    private TicTacLogic game;
    private boolean player = false;

    public TicTacClientHandler(String hostName, int hostPort) throws Exception{
        this.client = new TicTacClient();
        this.game = new TicTacLogic(3, true);
        this.board = new TicTacBoard(this);
        client.StartClient(hostPort, hostName, this);

    }

    public void SetPlayer(Boolean setPlayer){
        game.SetCurrentPlayer(setPlayer);
    }

    public void ReceiveMove (TicTacMove move){
        board.MakeMove(move);
        game.MakeMove(move);
        HasWon();
    }

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

    public void Reset(){
        client.SendReset();
    }

    public void ResetGame(){
        game.ResetBoard();
        board.resetGame();
    }

    private void HasWon(){
        int result = game.HasWon();
        if(game.HasWon() != -1) {
            if(result == 0)
                board.hasWinner("You won, poggers my doggy");
            if(result == 1)
                board.hasWinner("You lost, don't tell, but the host cheated (shhhh)");
            if(result == 2)
                board.hasWinner("WOOOOOOWZA, ISSA DRAW, uWu!!!!!! :)");
        }
    }

}
