

import java.io.*;
import java.net.*;
import java.util.*;
import java.lang.*;

public class TicTacServer extends Thread {
        private Socket socket;
        private BufferedReader reader = null;
        private BufferedWriter writer = null;
        private TicTacServerHandler serverHandler;
        private static boolean DEBUG = true;

        public void StartServer(int portNumber, TicTacServerHandler serverHandler, TicTacServer server) throws Exception{
            ServerSocket serverSocket = null;
            this.serverHandler = serverHandler;

            try{
                serverSocket = new ServerSocket(portNumber);
                this.socket = serverSocket.accept();
                reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                writer = new BufferedWriter(
                        new OutputStreamWriter(socket.getOutputStream()));
            } catch (IOException e){
                System.err.println("Could not listen on port: 1200.");
                System.exit(-1);
            }

            Thread t = new Thread(this);
            t.start();
        }

        /*************************************************************
         * Returns if the client socket was instantiated
         *************************************************************/
        public boolean ClientConnected(){
            if (socket != null)
                return true;
            else
                return false;
        }


        public TicTacServer(){

        }


        public void run() {
            System.out.println("User connected" + socket.getInetAddress());
            try {
                processRequest();
            } catch (Exception e) {
                System.out.println(e);
            }

        }


        private void processRequest() throws Exception {
            String clientCommand;

            while (true) {
                clientCommand = readLine();

                if(clientCommand.matches("(move:)\\s((true)|(false))\\s[0-9]\\s[0-9]"))
                    ReceiveMove(clientCommand);
                if(clientCommand.matches("(Close)"))
                    Disconnect();
                if(clientCommand.matches("(Reset)"))
                    ResetGame();
            }
        }

        public void ResetGame(){
            serverHandler.Reset();
        }

        private void Disconnect(){
            try {
                socket.close();
                writer.close();
                reader.close();
            } catch (Exception e) {
                System.out.println("There was a problem disconnecting from client");
            }
        }

        public void ReceiveMove(String command){
            TicTacMove move = null;
            try {
                StringTokenizer tokenCommand = new StringTokenizer(command);
                tokenCommand.nextToken(); //Consume the move token
                move = new TicTacMove(tokenCommand.nextToken(), tokenCommand.nextToken(), tokenCommand.nextToken());
            } catch (Exception e){
                System.out.println("Fail");
            }
            serverHandler.ReceiveMove(move);
        }

        public void SendMove(TicTacMove move){
            try {
                sendLine("move: " + move.GetPlayer() + " " + move.GetRow() + " " + move.GetCol());
            } catch (Exception e){
                System.out.println("Failed to send move to client");
            }
        }

        public void SendPlayer(Boolean player){
            try {
                sendLine("SetPlayer: " + player);
            } catch (Exception e){
                System.out.println("Failed to send player to client");
            }
        }

        public void SendReset(){
            try {
                sendLine("Reset");
            } catch (Exception e){
                System.out.println("Failed to communicate reset to client");
            }
        }

    private void sendLine(String line) throws IOException {
        if (socket == null) {
            throw new IOException("SimpleFTP is not connected.");
        }
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
        while(line == null)
            line = reader.readLine();
        if (DEBUG) {
            System.out.println("< " + line);
        }
        return line;
    }
    }
	

