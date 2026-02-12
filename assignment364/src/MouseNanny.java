import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MouseNanny extends MouseAdapter {

    //timer for clicks. Click twice under one second will create blue start
    private static final int DOUBLE_CLICK_MS = 1000;

    private final Grid grid;
    private final Timer singleClickTimer;

    //the cell we just clicked
    private JLabel pendingCell;

    public MouseNanny(Grid grid) {
        this.grid = grid;

        singleClickTimer = new Timer(DOUBLE_CLICK_MS, e -> {
            if (pendingCell != null) {
                grid.setStartCell(pendingCell); // only one start allowed
                pendingCell = null;
            }
        });

        singleClickTimer.setRepeats(false);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

        JLabel cell = (JLabel) e.getSource();

        //right click creates obstacle
        if (SwingUtilities.isRightMouseButton(e)) {

            singleClickTimer.stop();
            pendingCell = null;

            Color bg = cell.getBackground();

            // Don't allow obstacle on start or end
            if (bg.equals(Color.YELLOW) || bg.equals(Color.BLUE)) {
                return;
            }

            // Toggle black/white
            if (bg.equals(Color.BLACK)) {
                cell.setBackground(Color.WHITE);
            } else {
                cell.setBackground(Color.BLACK);
            }

            return;
        }


        //the end cell
        if (e.getClickCount() == 2) {
            singleClickTimer.stop();
            pendingCell = null;

            if (cell.getBackground().equals(Color.BLUE)) {
                grid.clearEndCell(); // remove end
            } else {
                grid.setEndCell(cell); // only one end allowed
            }
            return;
        }

        //start cell
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
