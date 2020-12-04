

import java.io.*;
import java.net.*;
import java.util.*;
import java.lang.*;

public class ftpserver extends Thread {
        private Socket socket;
        private BufferedReader reader = null;
        private BufferedWriter writer = null;
        private static boolean DEBUG = true;


        public ftpserver(Socket socket) {
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
            String fromClient;
            String clientCommand;

            while (true) {

                System.out.println("User connected" + socket.getInetAddress());
                fromClient = readLine();
                StringTokenizer tokens = new StringTokenizer(fromClient);

                tokens.nextToken(); //Consume the first token which is the port
                clientCommand = tokens.nextToken();

                if(clientCommand.matches("(move:)\\s(x|o)\\s[1-9]"));
                    MakeMove(tokens);
            }
        }

        private void MakeMove(StringTokenizer command){
            try {
                System.out.println(command);
                sendLine("Success");
            } catch (Exception e){
                System.out.println("Fail");
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
	

