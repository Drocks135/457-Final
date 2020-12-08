import javax.swing.*;
import javax.xml.crypto.Data;
import java.awt.*;
import java.awt.event.*;
import java.io.FileInputStream;
import java.io.*;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.sound.sampled.LineUnavailableException;
import java.io.IOException;


public class TicTacBoard extends JFrame {

    private Container gamePane;
    private int boardSize = 3;
    private Icon currPlayer;
    private JButton [][] board;
    private boolean isWinner;
    private JMenuBar menuBar;
    private JMenu menu;
    private JMenuItem quitGame;
    private JMenuItem newGame;
    private JMenuItem muteSound;
    private TicTacHandler handler;
    private boolean playSound;
    private JOptionPane jop;
    private JFrame frame;


    //Get appropriately scaled versions of the x and o icon for game usage.
    private Icon xPlayerIcon = new ImageIcon(new ImageIcon("src/icons/xplayer.png")
    .getImage().getScaledInstance(60, 60, Image.SCALE_DEFAULT));
    private Icon oPlayerIcon = new ImageIcon(new ImageIcon("src/icons/oplayer.png")
    .getImage().getScaledInstance(60, 60, Image.SCALE_DEFAULT));

    //Get both wav sound files for background music and player movement
    File bgmSound = new File("src/sounds/bgm.wav");
    File moveSound = new File("src/sounds/move.wav");

    public TicTacBoard(TicTacHandler handler) {
        super();
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
        Icon player;
        if (move.GetPlayer())
            player = xPlayerIcon;
        else
            player = oPlayerIcon;
        board[move.GetRow()][move.GetCol()].setIcon(player);
        playMoveSound();
    }

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

    //This would play a bgm sound but I haven't figured out how to mute it immediately yet
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
