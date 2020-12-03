
import java.net.*;
import java.io.*;

public class ftpmulti {
    public static void main(String[] args){
        ftpmulti f = new ftpmulti();
        f.StartServer();
    }

    public void StartServer(){
        ServerSocket serverSocket = null;
        ftpserver server;

        try{
            serverSocket = new ServerSocket(1200);
        } catch (IOException e){
            System.err.println("Could not listen on port: 1200.");
            System.exit(-1);
        }

        while (true)
        {
            try {
                server = new ftpserver(serverSocket.accept());
                Thread t = new Thread(server);
                t.start();
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}

