
import java.awt.event.*;
import javax.swing.*;

public class MouseNanny extends MouseAdapter {

    private final GridModel model;

    public MouseNanny(Grid grid, GridModel model) {
        this.model = model;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

        JLabel cell = (JLabel) e.getSource();
        int r = (int) cell.getClientProperty("row");
        int c = (int) cell.getClientProperty("col");
        if (model.isRunning())
            return;

        //right click creates obstacle
        if (SwingUtilities.isRightMouseButton(e)) {

            CellType t = model.getCell(r, c);
            if (t == CellType.START || t == CellType.END) {
                return;
            }

            
            model.toggleObstacle(r, c);
            return;
        }
        if (e.getClickCount() == 2) {
                model.setEnd(r, c);
                return;
            }
        model.setStart(r, c);


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
