import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TicTacBoard extends JFrame {

    private Container gamePane;
    private String currPlayer;
    private JButton [][] board;
    private boolean isWinner;
    private JMenuBar menuBar;
    private JMenu menu;
    private JMenuItem quitGame;
    private JMenuItem newGame;
    private JMenuItem connectGame;

    public TicTacBoard() {
        super();
        gamePane =  this.getContentPane();
        gamePane.setLayout(new GridLayout(9,9));
        setTitle("Tic Tac Toe");
        setSize(800,800); //Resolution
        setResizable(false); //Set Resizability
        setDefaultCloseOperation(DISPOSE_ON_CLOSE); //Control closing of application
        setVisible(true);
        currPlayer = "x";
        board = new JButton[9][9];
        isWinner = false;
        initializeGame();
        initializeMenu();
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
                resetGame(); //Reset the game
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
                System.exit(0);
            }
        });

        //Add all menu items to the menu
        menu.add(connectGame);
        menu.add(newGame);
        menu.add(quitGame);

        //Add menu to the menubar
        menuBar.add(menu);
    }

    private void resetGame(){
        //insert code to reset the game
    }

    private void initializeGame(){
        //insert code to initialize the game
    }

    private void changePlayer(){
        //insert code to change the player
    }

    private void hasWinner(){
        //insert code for when a player has won
    }




}
