import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ActionNanny implements ActionListener {

    //important so that ActionNanny knows what grid we are referencing
    private final Grid grid;
    private final GridModel model;

    public ActionNanny(Grid grid, GridModel model) {
        this.grid = grid;
        this.model = model;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();

        if (src instanceof JComboBox<?>) {
            @SuppressWarnings("unchecked")
            JComboBox<Integer> combo = (JComboBox<Integer>) src;

            Integer selected = (Integer) combo.getSelectedItem();
            if (selected != null) {
                model.reset(selected);
                grid.SetGridSize(selected);   // ✅ resize immediately
            }
        }
    }
}
