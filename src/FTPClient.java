import java.io.*;
import java.net.Socket;
import java.util.StringTokenizer;

public class FTPClient {
    private Socket socket;
    private BufferedReader reader;
    private BufferedWriter writer;

    private static boolean DEBUG = false; //Enable this to see incoming and outgoing data in terminal

    public FTPClient(){
        socket = new Socket();

    }

    public void ProcessCommand(String command){
        if(socket.isConnected()){
            String rawCommand = command.substring(0, command.indexOf(":"));

            switch (rawCommand) {
                case "move":
                    MakeMove(command);
                    break;
                case "close":
                    Disconnect();
                    break;
                default:
                    System.out.println("Invalid command");
            }
        }
        else if(command.startsWith("connect"))
            EstablishConnection(command);
    }

    private void EstablishConnection(String command){
        StringTokenizer tokenizedCommand = new StringTokenizer(command);
        tokenizedCommand.nextToken(); //Use the connect command token
        String serverName = tokenizedCommand.nextToken();
        int connectionPort = Integer.parseInt(tokenizedCommand.nextToken());

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

    private void MakeMove(String command){
        try {
            sendLine(command);
            System.out.println(readLine());
        } catch (Exception e){
            e.printStackTrace();
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

    private void Disconnect(){
        try {
            socket.close();
            System.out.println("Server connection closed");
        } catch (Exception e) {
            System.out.println("There was an error closing the connection to the server");
        }
    }

}
