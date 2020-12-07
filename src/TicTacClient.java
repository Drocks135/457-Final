import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.StringTokenizer;

public class TicTacClient extends Thread {
    private Socket socket;
    private BufferedReader reader;
    private BufferedWriter writer;
    TicTacClientHandler clientHandler;

    private static boolean DEBUG = false; //Enable this to see incoming and outgoing data in terminal

    /*****************************************************************
     * Starts the client
     *****************************************************************/
    public void StartClient(int portNumber, String HostName, TicTacClientHandler clientHandler)throws Exception{
       this.clientHandler = clientHandler;
        EstablishConnection(HostName, portNumber);
        Thread t = new Thread(this);
        t.start();
    }

    public TicTacClient(){

    }

    /*****************************************************************
     * Constantly reads inputs from the server and decides which
     * methods to call based on inputs
     *****************************************************************/
    private void processRequest() throws Exception {
        String clientCommand;

        while (true) {
            clientCommand = readLine();

            if(clientCommand.matches("(move:)\\s((true)|(false))\\s[0-9]\\s[0-9]"))
                ReceiveMove(clientCommand);
            if(clientCommand.matches("SetPlayer:\\s((true)|(false))"))
                SetPlayer(clientCommand);
            if(clientCommand.matches("(Close)"))
                Disconnect();
            if(clientCommand.matches("(Reset)"))
                ResetGame();

        }
    }

    /*****************************************************************
     * Updates the clients game logic when receiving a SetPlayer
     * command from the server
     *****************************************************************/
    private void SetPlayer(String clientCommand){
        Boolean player = Boolean.parseBoolean(clientCommand.substring(clientCommand.indexOf(":") + 2));
        clientHandler.SetPlayer(player);
    }

    /*****************************************************************
     * Thread starting logic
     *****************************************************************/
    public void run() {
        System.out.println("User connected" + socket.getInetAddress());
        try {
            processRequest();
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    /******************************************************************
     * Connects to a specified server
     * @param serverName: The name of the server you want to connect to
     * @param  connectionPort: The port you want to connect at
     *****************************************************************/
    public void EstablishConnection(String serverName, int connectionPort) throws Exception{
            socket = new Socket(serverName, connectionPort);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new BufferedWriter(
                    new OutputStreamWriter(socket.getOutputStream()));

            System.out.println("You are connected to " + serverName);

    }

    /*****************************************************************
     * This method sends a designated move to process on the server
     * @param move: A TicTacMove that has the player information and
     *            move information
     *****************************************************************/
    public void SendMove(TicTacMove move){
        try {
            sendLine("move: " + move.GetPlayer() + " " + move.GetRow() + " " + move.GetCol());
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    /*****************************************************************
     * This method recieves a move from the server and returns a
     * TicTacMove that can be processed by the game logic
     *****************************************************************/
    public void ReceiveMove(String command){
        TicTacMove move = null;
        try {
            StringTokenizer tokenCommand = new StringTokenizer(command);
            tokenCommand.nextToken(); //Consume the move token
            move = new TicTacMove(tokenCommand.nextToken(), tokenCommand.nextToken(), tokenCommand.nextToken());
        } catch (Exception e){
            System.out.println("Fail");
        }
        clientHandler.ReceiveMove(move);
    }

    /*****************************************************************
     * Method to reset the client when received from the server
     *****************************************************************/
    public void ResetGame(){
        clientHandler.ResetGame();
    }

    /*****************************************************************
     * Sends a reset command to the server
     *****************************************************************/
    public void SendReset(){
        try{
            sendLine("Reset");
        } catch (Exception e){
            System.out.println("Failed to send reset");
        }
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

    /*****************************************************************
     * Sends a string to the server
     *****************************************************************/
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

    /*****************************************************************
     * Receives a string from the server
     *****************************************************************/
    private String readLine() throws IOException {
        String line = reader.readLine();
        while(line == null)
            line = reader.readLine();
        if (DEBUG) {
            System.out.println("< " + line);
        }
        return line;
    }
}
