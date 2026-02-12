import javax.swing.*;
import java.awt.*;

public class MainGrid extends JFrame
{
    public static void main(String[] args)
    {
        MainGrid app = new MainGrid();

        //sets title of app
        app.setTitle("Concurrent Grid Pathfinder");

        //set size of window
        app.setSize(1200, 800);
        app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        app.setResizable(true);
        app.setVisible(true);
    }
    public MainGrid()
    {
        Grid grid = new Grid();
        JPanel controlGrid = new GridControlPanel(grid);

        JPanel northPanel = new JPanel();
        northPanel.setLayout(new BoxLayout(northPanel, BoxLayout.Y_AXIS)); // stack vertically

        //Text above grid
        JLabel topText = new JLabel("Click 1 = Start, Click 2 = End, Right Click = Toggle Obstacle");
        topText.setAlignmentX(Component.CENTER_ALIGNMENT);


        //add topText and controlgrid to top panel
        northPanel.add(controlGrid);
        northPanel.add(topText);

        // Wrapper prevents BorderLayout.CENTER from stretching the grid
        JPanel gridWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
        gridWrapper.add(grid);

        // --- Text BELOW the grid ---
        JLabel bottomText = new JLabel("Grid fills most of the window; squares colored by state");
        bottomText.setHorizontalAlignment(SwingConstants.CENTER);
        bottomText.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        add(northPanel, BorderLayout.NORTH);
        add(gridWrapper, BorderLayout.CENTER);
        add(bottomText, BorderLayout.SOUTH);
    }
}
