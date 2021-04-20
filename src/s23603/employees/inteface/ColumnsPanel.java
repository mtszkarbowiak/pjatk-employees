package s23603.employees.inteface;

import javax.swing.*;
import java.awt.*;

public abstract class ColumnsPanel extends JPanel
{
    private GridBagLayout layout;
    
    public ColumnsPanel(){
        layout = new GridBagLayout();
    
        buildRows();
        
        setLayout(layout);
    }
    
    protected abstract void buildRows();
    
    private int inspectionRow = 0;
    
    protected void buildRow(Component left, Component right, float lw){
        var leftConstraints = generateConstraints(0,inspectionRow, lw, 0f);
        var rightConstraints = generateConstraints(1,inspectionRow, 1.0f - lw, 0f);
        inspectionRow++;
        
        add(left,leftConstraints);
        layout.addLayoutComponent(left,leftConstraints);
        add(right,rightConstraints);
        layout.addLayoutComponent(right,rightConstraints);
    }
    
    protected void buildSectionTitle(String title){
        var constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = inspectionRow;
        constraints.gridwidth = 2;
        constraints.gridheight = 1;
        constraints.weightx = 1.0f;
        constraints.weighty = 0.0f;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.anchor = GridBagConstraints.CENTER;
        
        var label = new JLabel(title);
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setBorder(BorderFactory.createEtchedBorder());
        
        add(label,constraints);
        layout.addLayoutComponent(label,constraints);
        inspectionRow++;
    }
    
    protected static GridBagConstraints generateConstraints(int x, int y, float wx, float wy){
        var result = new GridBagConstraints();
        
        result.gridx = x;
        result.gridy = y;
        result.weightx = wx;
        result.weighty = wy;
        
        result.fill = GridBagConstraints.BOTH;
        
        return result;
    }
}
