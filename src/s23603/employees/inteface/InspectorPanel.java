package s23603.employees.inteface;

import s23603.employees.*;

import javax.swing.*;
import java.awt.*;

public class InspectorPanel extends JPanel
{
    JLabel label;
    
    public InspectorPanel(){
        label = new JLabel();
        add(label);
    }
    
    public void inspect(Employee employee){
        label.setText(employee.getName());
    }
}