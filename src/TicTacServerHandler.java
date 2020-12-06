public class TicTacServerHandler implements TicTacHandler{
    private TicTacBoard board;
    private TicTacServer server;
    private TicTacLogic game;

    public TicTacServerHandler(TicTacBoard board, TicTacServer server, TicTacLogic game, int port){
        this.board = board;
        this.server = server;
        this.game = game;
        StartServer(port);
    }

    private void StartServer(int port){
        server.StartServer(port, this);
    }

    public void ReceiveMove(TicTacMove move){
        board.MakeMove(move);
        game.MakeMove(move);
    }

    public void Reset(){
        board.resetGame();
        game.ResetBoard();
        ///server.reset todo: add server reset
    }
}
