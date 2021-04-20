package s23603.employees.inteface;

import s23603.employees.*;

import javax.swing.*;

public class Inspector extends JTable
{
    private InspectorTableModel tableModel;
    private JTable table;
    
    public Inspector(){
        table = this;
        tableModel = new InspectorTableModel();
    
        table.setModel(tableModel);
        table.updateUI();
    }
    
    public void inspect(Employee employee){
        tableModel.setInspectedElement(employee);
        table.updateUI();
    }
}