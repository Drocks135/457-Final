import java.io.*;
import java.net.Socket;
import java.util.StringTokenizer;

public class TicTacClient {
    private Socket socket;
    private BufferedReader reader;
    private BufferedWriter writer;

    private static boolean DEBUG = false; //Enable this to see incoming and outgoing data in terminal

    public void StartClient(){
        socket = new Socket();
    }

    /******************************************************************
     * Connects to a specified server
     * @param serverName: The name of the server you want to connect to
     * @param  connectionPort: The port you want to connect at
     *****************************************************************/
    public void EstablishConnection(String serverName, int connectionPort){
        try {
            socket = new Socket(serverName, connectionPort);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new BufferedWriter(
                    new OutputStreamWriter(socket.getOutputStream()));

            System.out.println("You are connected to " + serverName);
        } catch (Exception e){
            System.out.println("There was a problem connecting to the server");
        }
    }

    /*****************************************************************
     * This method sends a designated move to process on the server
     * @param move: A TicTacMove that has the player information and
     *            move information
     *****************************************************************/
    public void MakeMove(TicTacMove move){
        try {
            sendLine("move: " + move.GetPlayer() + " " + move.GetMove());
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    /*****************************************************************
     * This method recieves a move from the server and returns a
     * TicTacMove that can be processed by the game logic
     *****************************************************************/
    public TicTacMove ReceiveMove(){
        TicTacMove move = null;
        try {
            String StringMove = readLine();
            StringTokenizer TokenizedMove = new StringTokenizer(StringMove);
            move = new TicTacMove(TokenizedMove.nextToken(), TokenizedMove.nextToken());
        } catch (Exception e){
            e.printStackTrace();
        }
        return move;
    }

    /*****************************************************************
     * This method sends a disconnect to the server and closes the
     * client connection
     *****************************************************************/
    public void Disconnect(){
        try {
            sendLine("Close");
            socket.close();
            writer.close();
            reader.close();
        } catch (Exception e) {
            System.out.println("There was an error closing the connection to the server");
        }
    }

    private void sendLine(String line) throws IOException {
        if (socket == null) throw new IOException("SimpleFTP is not connected.");
        try {
            writer.write(line + "\r\n");
            writer.flush();
            if (DEBUG) {
                System.out.println("> " + line);
            }
        } catch (IOException e) {
            socket = null;
            throw e;
        }
    }

    private String readLine() throws IOException {
        String line = reader.readLine();
        if (DEBUG) {
            System.out.println("< " + line);
        }
        return line;
    }
}
