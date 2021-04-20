package s23603.employees.inteface;

import s23603.employees.*;
import javax.swing.*;
import java.awt.*;

public class InspectorPanel extends ColumnsPanel
{
    JTextField nameField, surnameField;
    JComboBox<Position> positionComboBox;
    JSpinner salarySpinner, experienceSpinner;
    
    JButton modifyTrigger, cancelChangeTrigger;
    JButton addTrigger, deleteTrigger;
    
    @Override
    protected void buildRows()
    {
        nameField = new JTextField();
        surnameField = new JTextField();
        positionComboBox = new JComboBox<>(Position.values());
        salarySpinner = new JSpinner();
        experienceSpinner = new JSpinner();
        
        
        modifyTrigger = new JButton("Modify");
        cancelChangeTrigger = new JButton("Cancel changes");
        addTrigger = new JButton("Create new");
        deleteTrigger = new JButton("Delete");
        deleteTrigger.setForeground(Color.red);
        
    
        buildSectionTitle("Attributes","Editable attributes of selected employee. Confirm to validate and apply changes.");
        buildLabeledEditorRow(UserInterface.ATTRIBUTE_NAMES[0],nameField);
        buildLabeledEditorRow(UserInterface.ATTRIBUTE_NAMES[1],surnameField);
        buildLabeledEditorRow(UserInterface.ATTRIBUTE_NAMES[2],positionComboBox);
        buildLabeledEditorRow(UserInterface.ATTRIBUTE_NAMES[3],salarySpinner);
        buildLabeledEditorRow(UserInterface.ATTRIBUTE_NAMES[4],experienceSpinner);
        
        buildRow(addTrigger,modifyTrigger);
        buildRow(deleteTrigger,cancelChangeTrigger);
    }
    
    protected void buildLabeledEditorRow(String label, Component right){
        buildRow(new JLabel(label), right);
    }
    
    public void inspect(Employee employee)
    {
        boolean isEmpty = employee == null;
    
        nameField.setEnabled(!isEmpty);
        surnameField.setEnabled(!isEmpty);
        positionComboBox.setEnabled(!isEmpty);
        salarySpinner.setEnabled(!isEmpty);
        experienceSpinner.setEnabled(!isEmpty);
    
        modifyTrigger.setEnabled(!isEmpty);
        cancelChangeTrigger.setEnabled(!isEmpty);
        deleteTrigger.setEnabled(!isEmpty);
        addTrigger.setEnabled(true);
        
        if(isEmpty){
            nameField.setText("-");
            surnameField.setText("-");
            salarySpinner.setValue(0);
            experienceSpinner.setValue(0);
        }
        else{
            nameField.setText(employee.getName());
            surnameField.setText(employee.getName());
            positionComboBox.setSelectedItem(employee.getPosition());
            salarySpinner.setValue(employee.getSalary());
            experienceSpinner.setValue(employee.getExperience());
        }
    }
}