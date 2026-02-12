import javax.swing.*;
import java.awt.*;


public class Grid extends JPanel {
    //default size
    private Integer size = 10;
    //array that will be the grid
    private JLabel cells[][] = new JLabel[size][size];

    //start and end cell will start as null until something is clicked
    private JLabel startCell = null;
    private JLabel endCell = null;


    public Grid() {
        setLayout(new GridLayout(size, size));

        //variable "this" refers to this grid
        MouseNanny mouseNanny = new MouseNanny(this);
        for (int row = 0; row < size; row++)
        {
            for (int col = 0; col < size; col++)
            {
                //creates a new label for this specific cell
                cells[row][col] = new JLabel();
                cells[row][col].setOpaque(true);
                cells[row][col].setBackground(Color.WHITE);
                //creates a black border around the cell
                cells[row][col].setBorder(BorderFactory.createLineBorder(Color.BLACK));
                //make the cell clickable
                cells[row][col].addMouseListener(mouseNanny);
                add(cells[row][col]);

            }
        }
    }

    //method that will reset the grid size when called
    public void SetGridSize(int newSize)
    {
        this.size = newSize;
        removeAll();
        MouseNanny mouseNanny = new MouseNanny(this);
        //create new cells

        cells = new JLabel[size][size];
        setLayout(new GridLayout(size, size));

        //begin adding cells to new grid
        for (int row = 0; row < size; row++)
        {
            for (int col = 0; col < size; col++)
            {
                cells[row][col] = new JLabel();
                cells[row][col].setOpaque(true);
                cells[row][col].setBackground(Color.WHITE);
                cells[row][col].setBorder(BorderFactory.createLineBorder(Color.BLACK));
                cells[row][col].addMouseListener(mouseNanny);
                add(cells[row][col]);

                //make the start and end cells null for the new grid
                startCell = null;
                endCell = null;

            }
        }

        //forces swig to redo the grid
        revalidate();
        repaint();

    }
    //method to create the start cell
    public void setStartCell(JLabel cell) {
        if (cell == null) return;
        if (cell == endCell) return;              //  don't allow start = end
        if (cell.getBackground().equals(Color.BLACK)) return; // don't allow on obstacle

        // clear old start
        if (startCell != null) {
            startCell.setBackground(Color.WHITE);
        }

        startCell = cell;
        startCell.setBackground(Color.YELLOW);
    }

    //method to create the end cell
    public void setEndCell(JLabel cell) {
        if (cell == null) return;
        if (cell == startCell) return;            //don't allow end = start
        if (cell.getBackground().equals(Color.BLACK)) return; // don't allow on obstacle

        // clear old end
        if (endCell != null) {
            endCell.setBackground(Color.WHITE);
        }

        endCell = cell;
        endCell.setBackground(Color.BLUE);
    }

    //removes the end cell
    public void clearEndCell() {
        if (endCell != null) endCell.setBackground(Color.WHITE);
        endCell = null;
    }


    @Override
    //method that controls the size of each cell in the grid. Cells become smaller as the grid gets bigger so it fits in panel
    public Dimension getPreferredSize() {
        int cellSize;

        if (size <= 20) {
            cellSize = 32;
        } else if (size <= 40) {
            cellSize = 19;
        } else if (size <= 70) {
            cellSize = 8;
        } else {
            cellSize = 4;
        }
        return new Dimension(size * cellSize, size * cellSize);
    }

}
