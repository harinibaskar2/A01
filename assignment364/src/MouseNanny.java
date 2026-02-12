import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.*;
public class MouseNanny implements MouseListener
{
    //1 second. Two clicks under 1 second will make yellow cell.
    private static final int DOUBLE_CLICK_MS = 1000;
    private final Timer singleClickTimer;

    private final Grid grid;

    //the cell in the grid that we clicked
    private JLabel pendingCell;

    //constructor for building the click events. Timer starts as soon as click
    public MouseNanny(Grid grid) {
        this.grid = grid;
        singleClickTimer = new Timer(DOUBLE_CLICK_MS, e -> {
            if (pendingCell != null) {
                grid.setStartCell(pendingCell);
                pendingCell = null;
            }
        });
        singleClickTimer.setRepeats(false);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        JLabel cell = (JLabel) e.getSource(); // the clicked cell
       if (e.getClickCount() == 2)
       {
           singleClickTimer.stop();
           pendingCell = null;

           //if blue, make it white and remove it as the end cell
           if (cell.getBackground().equals(Color.BLUE)) {
               grid.clearEndCell();
           } else {
               grid.setEndCell(cell);
           }
           return;
       }
       else if (e.getClickCount() > 2)
       {
           if (cell.getBackground().equals(Color.BLACK))
           {
               cell.setBackground(Color.WHITE);
           }
           else
           {
               cell.setBackground(Color.BLACK);
           }
       }

       else
       {
           cell.setBackground(Color.YELLOW);
       }

        pendingCell = cell;
        singleClickTimer.restart();
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
