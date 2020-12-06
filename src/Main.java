import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args){
        TicTacServer server = new TicTacServer();
        TicTacServerHandler serverHandler;
        TicTacLogic logic = new TicTacLogic(3, true);
        TicTacBoard board = new TicTacBoard();

        serverHandler = new TicTacServerHandler(board, server, logic, 1233);
        SwingUtilities.invokeLater(new Runnable(){
            @Override
            public void run() {
                new TicTacBoard();
            }   
        });
    }
}
