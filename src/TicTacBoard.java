import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TicTacBoard extends JFrame {

    private Container gamePane;
    private int boardSize = 3;
    private String currPlayer;
    private JButton [][] board;
    private boolean isWinner;
    private JMenuBar menuBar;
    private JMenu menu;
    private JMenuItem quitGame;
    private JMenuItem newGame;
    private JMenuItem connectGame;
    private TicTacHandler handler;

    public TicTacBoard(TicTacHandler handler) {
        super();
        gamePane =  this.getContentPane();
        gamePane.setLayout(new GridLayout(boardSize,boardSize));
        setTitle("Tic Tac Toe");
        setSize(800,800); //Resolution
        setResizable(false); //Set Resizability
        setDefaultCloseOperation(DISPOSE_ON_CLOSE); //Control closing of application
        setVisible(true);
        currPlayer = "x";
        board = new JButton[boardSize][boardSize];
        isWinner = false;
        initializeGame();
        initializeMenu();
        this.handler = handler;
    }

    public void DisplayInvalidTurn(){
        JOptionPane.showMessageDialog(null, "It is not your turn yet. Wait just, like, one second.", "WARNING",
                JOptionPane.WARNING_MESSAGE);
    }

    //this handles both reset and quit
    public Boolean ConfirmChange(String message, String title){
        //todo: make a confirmation for a board reset

        Object[] buttons = { "Yes", "No"};
        int choice = JOptionPane.showOptionDialog(null, message, title,
                JOptionPane.INFORMATION_MESSAGE, 1,null, buttons, buttons[0]);
        System.out.println(choice);

        if(choice == 0){
            return true;
        }

        return false;
    }

    public void MakeMove(TicTacMove move){
        String player;
        if (move.GetPlayer())
            player = "x";
        else
            player = "o";
        board[move.GetRow()][move.GetCol()].setText(player);
    }

    private void initializeMenu(){
        menuBar = new JMenuBar();
        menu = new JMenu("File");

        newGame = new JMenuItem("New Game");
        connectGame = new JMenuItem("Connect"); //Will handle connecting to opponent
        quitGame = new JMenuItem("Quit");

        newGame.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){

                String message = "Are you sure you wish to reset?";
                String title = "Reset Menu";

                if(ConfirmChange(message, title)){
                    handler.Reset(); //Reset the game
                }

            }
        });

        connectGame.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                //Add code for connection
            }
        });

        quitGame.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){

                if(ConfirmChange("Are you sure you wish to quit?", "Quit Menu")){
                    System.exit(0);
                }

            }
        });

        //Add all menu items to the menu
        menu.add(connectGame);
        menu.add(newGame);
        menu.add(quitGame);

        //Add menu to the menubar
        menuBar.add(menu);

        //Set the JMenuBar
        setJMenuBar(menuBar);
    }

    public void manualQuit(){
        System.exit(0);
    }

    public void resetGame(){
        for(int i = 0; i < boardSize; i++){
            for(int j = 0; j < boardSize; j++){
                board[i][j].setText(""); //Change this to an actual icon later on when working
            }
        }
    }

    private void initializeGame(){
        for(int i = 0; i < boardSize; i++){
            for(int j = 0; j < boardSize; j++){
                TicTacButton button = new TicTacButton(i, j);
                button.jbutton.setFont(new Font(Font.SERIF, Font.BOLD, 30)); //Text style for the text icons
                board[i][j] = button.jbutton;
                button.jbutton.addActionListener(new ActionListener(){
                    @Override
                    public void actionPerformed(ActionEvent e){
                        if(((JButton)e.getSource()).getText().equals("") && isWinner == false){
                            Boolean player;
                            if (currPlayer.equals("x"))
                                player = true;
                            else
                                player = false;
                            handler.SendMove(button.row, button.col);
                        }
                    }
                });
                gamePane.add(button.jbutton);
            }
        }
    }




}
