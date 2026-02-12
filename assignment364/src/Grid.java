import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;


public class Grid extends JPanel implements PropertyChangeListener {
    
    //default size
    private Integer size = 10;
    //array that will be the grid
    private JLabel cells[][] = new JLabel[size][size];

    //start and end cell will start as null until something is clicked
   // private JLabel startCell = null;
    //private JLabel endCell = null;

    private final GridModel model;


    public Grid(GridModel model) {
        this.model = model;
        this.size = model.getSize();
        setLayout(new GridLayout(size, size));

        //variable "this" refers to this grid
        MouseNanny mouseNanny = new MouseNanny(this, model);
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
                cells[row][col].putClientProperty("row", row);
                cells[row][col].putClientProperty("col", col);
                
                //make the cell clickable
                cells[row][col].addMouseListener(mouseNanny);
                add(cells[row][col]);

            }
        }
        repaintFromModel();
    }

    //method that will reset the grid size when called
    public void SetGridSize(int newSize)
    {
        model.reset(newSize);
        this.size = newSize;
        removeAll();
        MouseNanny mouseNanny = new MouseNanny(this, model);
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
                
                cells[row][col].putClientProperty("row", row);
                cells[row][col].putClientProperty("col", col);
                cells[row][col].addMouseListener(mouseNanny);
                add(cells[row][col]);
            }
        }

        //forces swig to redo the grid
        revalidate();
        repaint();
        repaintFromModel();

    }

    private void repaintFromModel(){
        if (model.getSize() != size) {
            SetGridSize(model.getSize());
            return;
        }

        for (int row = 0; row < size; row ++) {
            for (int col = 0; col < size; col++) {
                cells[row][col].setBackground(colorFor(model.getCell(row, col)));
            }
        }
        repaint();
    }

    private Color colorFor(CellType t) {
        if (t == CellType.EMPTY) 
            return Color.WHITE;
        if (t == CellType.START) 
            return Color.YELLOW;
        if (t == CellType.END) 
            return Color.BLUE;
        if (t == CellType.OBSTACLE) 
            return Color.BLACK;
        if (t == CellType.FRONTIER) 
            return Color.ORANGE;
        if (t == CellType.VISITED) 
            return Color.LIGHT_GRAY;
        if (t == CellType.PATH) 
            return Color.GREEN;
        return Color.WHITE;
    }
    //method to create the start cell
    /* 
    public void setStartCell(JLabel cell) {
        if (cell == null) return;
        if (cell == endCell) return;              //  don't allow start = end
        if (cell.getBackground().equals(Color.BLACK)) return; // don't allow on obstacle

        // Clear previous start
        if (startCell != null && startCell != cell) {
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

    public void clearStartCell()
    {
        if (startCell != null) startCell.setBackground(Color.WHITE);
        startCell = null;
    }

*/


    @Override
    public void propertyChange(PropertyChangeEvent evt){
        SwingUtilities.invokeLater(this::repaintFromModel);
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
