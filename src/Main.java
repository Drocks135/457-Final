import javax.swing.*;

public class Main {
    public static void main(String[] args){
        TicTacServerHandler serverHandler;


        Object[] buttons = {"Host", "Connect"};
        Object n = JOptionPane.showInputDialog(null, "How do you wish to proceed?", "P2P Start Menu",
                JOptionPane.INFORMATION_MESSAGE, null, buttons, buttons[0]);

        System.out.println("The user chose: " + n);

        String portString = "";
        int port = 0;



        //if the client chooses host then we need to take in the host port number (ip is already handled)
        if(n == "Host"){

            //first create a boolean to determine if input is valid
            boolean valid = false;

            while(!valid){

                valid = true;

                //ask for the port number
                portString = JOptionPane.showInputDialog("Enter your port number: ");
                System.out.println("Servers port: " + portString);

                //see if the port number provided is a valid port
                try {
                    port = Integer.parseInt(portString);
                    serverHandler = new TicTacServerHandler(port);
                } catch (Exception e) {
                    //if not throw an error joption and ensure that the loop continues
                    JOptionPane.showMessageDialog(null, "Invalid Port Number. Try again.", "WARNING",
                            JOptionPane.WARNING_MESSAGE);

                    valid = false;
                }


            }


        }
        else {
            Boolean connected = false;
            while (!connected) {
                //for client we also need to know the IP
                String IPString = "";

                boolean valid = false;

                //same logic as before
                while (!valid) {

                    valid = true;

                    IPString = JOptionPane.showInputDialog("Enter host IP: ");

                    System.out.println("Host IP: " + IPString);

                    //IP only needs to be not null
                    if (IPString.length() >= 0) {
                        valid = true;
                    } else {
                        JOptionPane.showMessageDialog(null, "Invalid IP. Please try again.", "WARNING",
                                JOptionPane.WARNING_MESSAGE);
                    }


                }

                //valid boolean is reset
                valid = false;

                //client port logic is handled the same as the host
                while (!valid) {

                    valid = true;

                    portString = JOptionPane.showInputDialog("Enter host Port: ");

                    System.out.println("Host port: " + portString);

                    try {
                        port = Integer.parseInt(portString);
                    } catch (NumberFormatException e) {

                        JOptionPane.showMessageDialog(null, "Invalid Port Number. Please try again.", "WARNING",
                                JOptionPane.WARNING_MESSAGE);
                        valid = false;
                    }


                }

                TicTacClientHandler clientHandler;

                try {
                    clientHandler = new TicTacClientHandler(IPString, port);
                    connected = true;
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Could not connect, please verify host name and port", "WARNING",
                            JOptionPane.WARNING_MESSAGE);
                }


            }
        }
    }

}
