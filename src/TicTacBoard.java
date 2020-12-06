import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TicTacBoard extends JFrame {
    
    //Get the icon images for both the o and x peices of the board
    ImageIcon xIcon = new ImageIcon(new ImageIcon("src/icons/xplayer.png").getImage().getScaledInstance(40, 40, Image.SCALE_DEFAULT));
    ImageIcon oIcon = new ImageIcon(new ImageIcon("src/icons/oplayer.png").getImage().getScaledInstance(40, 40, Image.SCALE_DEFAULT));

    private Container gamePane;
    private int boardSize = 3;
    private ImageIcon currPlayer;
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
        gamePane.setLayout(new GridLayout(boardSize,boardSize));
        setTitle("Tic Tac Toe");
        setSize(800,800); //Resolution
        setResizable(false); //Set Resizability
        setDefaultCloseOperation(DISPOSE_ON_CLOSE); //Control closing of application
        setVisible(true);
        currPlayer = xIcon;
        board = new JButton[boardSize][boardSize];
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

        //Set the JMenuBar
        setJMenuBar(menuBar);
    }

    private void resetGame(){
        currPlayer = xIcon;
        isWinner = false;
        for(int i = 0; i < boardSize; i++){
            for(int j = 0; j < boardSize; j++){
                board[i][j].setText(""); //Change this to an actual icon later on when working
            }
        }
    }

    private void initializeGame(){
        for(int i = 0; i < boardSize; i++){
            for(int j = 0; j < boardSize; j++){
                JButton button = new JButton();
                button.setFont(new Font(Font.SERIF, Font.BOLD, 30)); //Text style for the text icons
                board[i][j] = button;
                button.addActionListener(new ActionListener(){
                    @Override
                    public void actionPerformed(ActionEvent e){
                        if(((JButton)e.getSource()).getIcon() == null && isWinner == false){
                            button.setIcon(currPlayer);
                            hasWinner(); //Determine if there is a winner
                            changePlayer();
                        }
                    }
                });
                gamePane.add(button);
            }
        }
    }

    //Toggle between players x and o 
    private void changePlayer(){
        if(currPlayer.equals(xIcon)){
            currPlayer = oIcon;
        }
        else{
            currPlayer = xIcon;
        }
    }


    //Function to determine if there is a winner or not
    private void hasWinner(){
        //Implement function that can check boards of all sizes for a winner
    }




}
