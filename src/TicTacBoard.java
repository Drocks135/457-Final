import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;


/***************************************************************************************
 * Desc: This is the main class for the Tic Tac Toe board which will draw the board for
 * both the client and the host server. 
 * 
 * Note: Some sounds were excluded due to issues running them, eveythign else works
 * 
 ***************************************************************************************/
public class TicTacBoard extends JFrame {

    //Container for the game
    private Container gamePane;
    //Integer for the boardSize
    private int boardSize = 3;
    //Icon for the current player
    private Icon currPlayer;
    //board which contains a 2D array of JButtons
    private JButton [][] board;
    //boolean which controls if there was a winner
    private boolean isWinner;
    //Create a menubar for the application
    private JMenuBar menuBar;
    //Create a menu for that menubar
    private JMenu menu;
    //One of the File menu options to quit the game
    private JMenuItem quitGame;
    //One of the File menu options to start a new game
    private JMenuItem newGame;
    //One of the File menu options to mute the sounds
    private JMenuItem muteSound;
    //handler for the tic-tac-toe game
    private TicTacHandler handler;
    //Boolean which is used to control playing of sounds
    private boolean playSound;
    private JOptionPane jop;
    private JFrame frame;


    //Get appropriately scaled versions of the x and o icon for game usage.
    private Icon xPlayerIcon = new ImageIcon(new ImageIcon("src/icons/xplayer.png")
    .getImage().getScaledInstance(60, 60, Image.SCALE_DEFAULT));
    private Icon oPlayerIcon = new ImageIcon(new ImageIcon("src/icons/oplayer.png")
    .getImage().getScaledInstance(60, 60, Image.SCALE_DEFAULT));

    //Get both wav sound files for background music and player movement
    //
    //Note: bgm.wav is not implemented currently due to issues
    File bgmSound = new File("src/sounds/bgm.wav");
    File moveSound = new File("src/sounds/move.wav");

    /***************************************************************************************
    * Desc: This class is used to create a new version of a tic-tac-board and uses the 
    * TicTacHandler in order to handle the game between both client and host 
    *
    * @param handler
    * 
    ***************************************************************************************/
    public TicTacBoard(TicTacHandler handler) {
        super();
        //Code below just sets up the GUI and initializes variables
        gamePane =  this.getContentPane();
        gamePane.setLayout(new GridLayout(boardSize,boardSize));
        setTitle("Tic Tac Toe");
        setSize(800,800); //Resolution
        setResizable(false); //Set Resizability
        setDefaultCloseOperation(DISPOSE_ON_CLOSE); //Control closing of application
        setVisible(true);
        currPlayer = xPlayerIcon;
        board = new JButton[boardSize][boardSize];
        isWinner = false;
        playSound = true;
        initializeGame();
        initializeMenu();
        this.handler = handler;
    }

    /*************************************************************************************
     * Desc: This function is used to display to the user if it is their turn or not when
     * attempting to press on the board
     * 
     * **********************************************************************************/
    public void DisplayInvalidTurn(){
        JOptionPane.showMessageDialog(null, 
        "It is not your turn yet. Wait just, like, one second.", "WARNING",
                JOptionPane.WARNING_MESSAGE);
    }

    
    /*************************************************************************************
     * Desc: This function is used to handle both the reset and quit abilities of both
     * the client and host so that things are not out of sync
     * 
     * **********************************************************************************/
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

    // This function is used to make a move on the board depending
    // on the player
    public void MakeMove(TicTacMove move){
        Icon player;
        if (move.GetPlayer())
            player = xPlayerIcon;
        else
            player = oPlayerIcon;
        board[move.GetRow()][move.GetCol()].setIcon(player);
        playMoveSound();
    }

    
    /*************************************************************************************
     * Desc: This function is used to control all of the menu items that are at the top
     * of the window. These include the new game button, mute sound button, and the 
     * quit button. It also adds everything to the menu itself
     * 
     * **********************************************************************************/
    private void initializeMenu(){
        menuBar = new JMenuBar();
        menu = new JMenu("File");

        newGame = new JMenuItem("New Game");
        muteSound = new JMenuItem("Mute Sound");
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

        muteSound.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                if(playSound == true){
                    playSound = false;
                    muteSound.setText("Unmute Sound");
                    
                }
                else{
                    playSound = true;
                    muteSound.setText("Mute Sound");
                   
                }
            }
        });

        quitGame.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                if(ConfirmChange("Are you sure you wish to quit?", "Quit Menu")){
                    playSound = false;
                    System.exit(0);
                }
            }
        });

        //Add all menu items to the menu
        menu.add(newGame);
        menu.add(muteSound);
        menu.add(quitGame);

        //Add menu to the menubar
        menuBar.add(menu);

        //Set the JMenuBar
        setJMenuBar(menuBar);
    }

    //Used to control the reset of the game
    public void resetGame(){
        System.out.println("Do we just hang?");
        for(int i = 0; i < boardSize; i++){
            for(int j = 0; j < boardSize; j++){
                board[i][j].setIcon(null); 
            }
        }
        //closes jop if it was made
        System.out.println("Does this run");
        frame.setVisible(false);

        playSound = true;
    }

    
    /*************************************************************************************
     * Desc: This function is used to intitalize the tic tac toe game and sets up the 
     * board for both the host and the client to use. It also controlls whos' turn it is
     * at any given time for the JButtons on the board.
     * 
     * **********************************************************************************/
    private void initializeGame(){
        for(int i = 0; i < boardSize; i++){
            for(int j = 0; j < boardSize; j++){
                TicTacButton button = new TicTacButton(i, j);
                button.jbutton.setFont(new Font(Font.SERIF, Font.BOLD, 30)); //Text style for the text icons
                board[i][j] = button.jbutton;
                button.jbutton.addActionListener(new ActionListener(){
                    @Override
                    public void actionPerformed(ActionEvent e){
                        if(((JButton)e.getSource()).getIcon() == null && isWinner == false){
                            Boolean player;
                            if (currPlayer.equals(xPlayerIcon))
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

    //This is used to play the background music sound but I did not find a way to 
    //continually check if it should be playing or not so it is currently left out.
    //It will run if it is called anywhere.
    private void playBgmSound(){
        try {
            AudioInputStream bgmStream = AudioSystem.getAudioInputStream(bgmSound);
            AudioFormat bgmFormat = bgmStream.getFormat();
            DataLine.Info bgmInfo = new DataLine.Info(Clip.class, bgmFormat);
            Clip bgmClip = (Clip) AudioSystem.getLine(bgmInfo);   
            bgmClip.open(bgmStream);
            if(playSound == true){
                bgmClip.start();
            }
            else{
                bgmClip.stop();
            }
        } catch (Exception e) {

            //Add actual exceptions
            e.printStackTrace();
        }
    }

    //This is used to play the movement sound of the board. This does work with the mute
    //button because it is called everytime a move is made.
    private void playMoveSound(){
        try {
            AudioInputStream moveStream = AudioSystem.getAudioInputStream(moveSound);
            AudioFormat moveFormat = moveStream.getFormat();
            DataLine.Info moveInfo = new DataLine.Info(Clip.class, moveFormat);  
            Clip moveClip = (Clip) AudioSystem.getLine(moveInfo);
            moveClip.open(moveStream);  
            if(playSound == true){
                moveClip.start();
            }
            else{
                moveClip.stop();
            }
         } catch (Exception e) {
              //Add actual exceptions
             e.printStackTrace();
         }
   
    }

    //Function to determine if there is a winner or not
    public void hasWinner(String message, String title, int loser){

        frame = new JFrame("Frame");
        frame.setVisible(true);

        if(loser == 0) {
            Object[] buttons = {"Play Again", "Quit"};



            jop = new JOptionPane();

            int choice = jop.showOptionDialog(frame, message, title,
                    JOptionPane.INFORMATION_MESSAGE, 1, null, buttons, buttons[0]);

            if (choice == 0) {
                handler.Reset();
            } else if (choice == 1) {
                System.exit(0);
            }
        }

        else if(loser == 1){
            jop = new JOptionPane();

            String a = title + ". Waiting for other player";

            jop.showMessageDialog(frame, a);
        }
        else{
            jop.showMessageDialog(frame, message);
            handler.Reset();
        }
    }
}
