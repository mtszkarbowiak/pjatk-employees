package s23603.employees.inteface;

import s23603.employees.*;
import javax.swing.*;
import java.awt.*;

public class InspectorPanel extends ColumnsPanel
{
    JTextField nameField, surnameField;
    JComboBox<Position> positionComboBox;
    JSpinner salarySpinner, experienceSpinner;
    
    @Override
    protected void buildRows()
    {
        nameField = new JTextField();
        surnameField = new JTextField();
        positionComboBox = new JComboBox<>(Position.values());
        salarySpinner = new JSpinner();
        experienceSpinner = new JSpinner();
    
        buildSectionTitle("Attributes");
        buildLabeledEditorRow(UserInterface.ATTRIBUTE_NAMES[0],nameField);
        buildLabeledEditorRow(UserInterface.ATTRIBUTE_NAMES[1],surnameField);
        buildLabeledEditorRow(UserInterface.ATTRIBUTE_NAMES[2],positionComboBox);
        buildLabeledEditorRow(UserInterface.ATTRIBUTE_NAMES[3],salarySpinner);
        buildLabeledEditorRow(UserInterface.ATTRIBUTE_NAMES[4],experienceSpinner);
    }
    
    protected void buildLabeledEditorRow(String label, Component right){
        buildRow(new JLabel(label), right, 0.3f);
    }
    
    public void inspect(Employee employee)
    {
        boolean isEmpty = employee == null;
    
        nameField.setEnabled(!isEmpty);
        surnameField.setEnabled(!isEmpty);
        positionComboBox.setEnabled(!isEmpty);
        salarySpinner.setEnabled(!isEmpty);
        experienceSpinner.setEnabled(!isEmpty);
        
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