package s23603.employees.inteface;

import javax.swing.*;
import java.awt.*;

public abstract class ColumnsPanel extends JPanel
{
    private final GridBagLayout layout;
    
    public ColumnsPanel(){
        layout = new GridBagLayout();
    
        buildRows();
        
        setLayout(layout);
    }
    
    protected abstract void buildRows();
    
    private int inspectionRow = 0;
    
    protected void buildRow(Component left, Component right){
        var leftConstraints = generateConstraints(0,inspectionRow);
        var rightConstraints = generateConstraints(1,inspectionRow);
        inspectionRow++;
        
        add(left,leftConstraints);
        layout.addLayoutComponent(left,leftConstraints);
        add(right,rightConstraints);
        layout.addLayoutComponent(right,rightConstraints);
    }
    
    protected void buildSectionTitle(String title, String tooltip){
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
        label.setToolTipText(tooltip);
        
        add(label,constraints);
        layout.addLayoutComponent(label,constraints);
        inspectionRow++;
    }
    
    protected static GridBagConstraints generateConstraints(int x, int y){
        var result = new GridBagConstraints();
        
        result.gridx = x;
        result.gridy = y;
        result.weightx = (float) 0.5;
        result.weighty = (float) 0.0;
        
        result.fill = GridBagConstraints.BOTH;
        
        return result;
    }
}
