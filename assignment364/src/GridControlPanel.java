import javax.swing.*;
import java.awt.*;

public class GridControlPanel extends JPanel
{
    private Thread worker;
    public GridControlPanel(Grid grid, GridModel model)
    {
        //actionnanny that will check when the size is changed

        //scroll down menu to control grid size
        JComboBox<Integer> gridSize = new JComboBox<>();

        for (int i = 10; i <= 100; i++) {
            gridSize.addItem(i);
        }
        //important that ActionNanny accepts grid as inputs so it knows which grid to change
        ActionNanny actionNanny = new ActionNanny(grid, model);
        gridSize.addActionListener(actionNanny);

        JButton startButton = new JButton("Start");
        JButton pauseButton = new JButton("Pause/Resume");
        JButton resetButton = new JButton("Reset");

        JLabel label = new JLabel("Grid Size:");

        startButton.addActionListener(e -> {
            if (model.isRunning())
                return;
            if (!model.hasStart() || !model.hasEnd())
                return;

            model.requestStop();
            model.clearSearchMarks();
            worker = new Thread(new Pathfinder(model));
            worker.start();
        });

        pauseButton.addActionListener(e -> model.togglePause());

        resetButton.addActionListener(e -> {
            model.requestStop();
            model.reset(model.getSize());
            grid.SetGridSize(model.getSize());
        });
        

        //set layout of control buttons
        setLayout(new GridLayout(3, 10));
        add(label);
        add(gridSize);
        add(startButton);
        add(pauseButton);
        add(resetButton);


    }
}
