import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        TicTacServerHandler serverHandler;

        String startOption = ShowStartDialog();

        System.out.println("The user chose: " + startOption);

        //if the client chooses host then we need to take in the host port number (ip is already handled)
        if (startOption == "Host") {
            HostServer();
        }
        if(startOption == "Connect")
            ConnectClientToHost();

    }

    private static void HostServer() {
        String portString;
        int port;

        while (true) {
            //ask for the port number
            portString = JOptionPane.showInputDialog("Enter your port number: ");
            System.out.println("Servers port: " + portString);

            //see if the port number provided is a valid port
            try {
                port = Integer.parseInt(portString);
                TicTacServerHandler s = new TicTacServerHandler(port);
                break;
            } catch (Exception e) {
                //if not throw an error joption and ensure that the loop continues
                JOptionPane.showMessageDialog(null, "Invalid Port Number. Try again.", "WARNING",
                        JOptionPane.WARNING_MESSAGE);
            }


        }
    }

    private static String ShowStartDialog() {
        Object[] buttons = {"Host", "Connect"};
        Object SelectOption = JOptionPane.showInputDialog(null, "How do you wish to proceed?", "P2P Start Menu",
                JOptionPane.INFORMATION_MESSAGE, null, buttons, buttons[0]);
        return SelectOption.toString();
    }

    private static void ConnectClientToHost() {
        String portString;
        int port;
        while (true) {
            //for client we also need to know the IP
            String IPString = "";

            while (true) {
                IPString = JOptionPane.showInputDialog("Enter host IP: ");

                System.out.println("Host IP: " + IPString);

                //IP only needs to be not null
                if (IPString.length() >= 0) {
                    break;
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid IP. Please try again.", "WARNING",
                            JOptionPane.WARNING_MESSAGE);
                }
            }

            //client port logic is handled the same as the host
            while (true) {
                portString = JOptionPane.showInputDialog("Enter host Port: ");

                System.out.println("Host port: " + portString);

                try {
                    port = Integer.parseInt(portString);
                    break;
                } catch (NumberFormatException e) {

                    JOptionPane.showMessageDialog(null, "Invalid Port Number. Please try again.", "WARNING",
                            JOptionPane.WARNING_MESSAGE);
                }
            }

            try {
                TicTacClientHandler c = new TicTacClientHandler(IPString, port);
                break;
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Could not connect, please verify host name and port", "WARNING",
                        JOptionPane.WARNING_MESSAGE);
            }
        }
    }
}


