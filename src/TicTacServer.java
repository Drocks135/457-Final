

import java.io.*;
import java.net.*;
import java.util.*;
import java.lang.*;

public class TicTacServer extends Thread {
        private Socket socket;
        private BufferedReader reader = null;
        private BufferedWriter writer = null;
        private static boolean DEBUG = true;

        public void StartServer(){
            ServerSocket serverSocket = null;
            TicTacServer server;

            try{
                serverSocket = new ServerSocket(1200);
            } catch (IOException e){
                System.err.println("Could not listen on port: 1200.");
                System.exit(-1);
            }

            while (true)
            {
                try {
                    server = new TicTacServer(serverSocket.accept());
                    Thread t = new Thread(server);
                    t.start();
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        }


        public TicTacServer(Socket socket) {
            this.socket = socket;
            try {
                reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                writer = new BufferedWriter(
                        new OutputStreamWriter(socket.getOutputStream()));
            } catch (Exception e){
                System.out.println("fail");
            }
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

                if(clientCommand.matches("(move:)\\s(x|o)\\s[1-9]"))
                    MakeMove(clientCommand);
                if(clientCommand.matches("(Close)"))
                    Disconnect();
            }
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

        public TicTacMove MakeMove(String command){
            TicTacMove move = null;
            try {
                StringTokenizer tokenCommand = new StringTokenizer(command);
                tokenCommand.nextToken(); //Consume the move token
                move = new TicTacMove(tokenCommand.nextToken(), tokenCommand.nextToken());
            } catch (Exception e){
                System.out.println("Fail");
            }
            return move;
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
	

