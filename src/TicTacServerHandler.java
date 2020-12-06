public class TicTacServerHandler {
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
        server.StartServer(port);
    }

    private void MakeMove(TicTacMove move){

    }
}
