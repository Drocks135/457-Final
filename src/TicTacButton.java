import javax.swing.*;

public class TicTacButton {
    public JButton jbutton;
    public int row;
    public int col;

    public TicTacButton(int row, int col){
        this.row = row;
        this.col = col;
        this.jbutton = new JButton();
    }
}
