import java.awt.Color;

import javax.swing.*;
import javax.swing.border.Border;

public class TicTacButton {
    public JButton jbutton;
    public int row;
    public int col;

    public TicTacButton(int row, int col){
        this.row = row;
        this.col = col;
        this.jbutton = new JButton();
        this.jbutton.setBorder(BorderFactory.createLineBorder(Color.black));
        this.jbutton.setBackground(Color.lightGray);
    }
}
