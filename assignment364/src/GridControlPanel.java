import javax.swing.*;
import java.awt.*;

public class GridControlPanel extends JPanel
{
    public GridControlPanel(Grid grid)
    {
        //actionnanny that will check when the size is changed

        //scroll down menu to control grid size
        JComboBox<Integer> gridSize = new JComboBox<>();

        for (int i = 10; i <= 100; i++) {
            gridSize.addItem(i);
        }
        //important that ActionNanny accepts grid as inputs so it knows which grid to change
        ActionNanny actionNanny = new ActionNanny(grid);

        JButton startButton = new JButton("Start");

        JLabel label = new JLabel("Grid Size:");

        //attach ActionNanny to gridSize
        gridSize.addActionListener(actionNanny);

        //set layout of control buttons
        setLayout(new GridLayout(3, 10));
        add(label);
        add(gridSize);
        add(startButton);


    }
}
