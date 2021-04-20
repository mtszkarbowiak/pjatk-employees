package s23603.employees.inteface;

import s23603.employees.*;
import javax.swing.*;
import java.awt.*;

public class InspectorPanel extends ColumnsPanel
{
    private final EmployeeListLogic employeeListLogic;
    private final EmployeeListChangeListener employeeListChangeListener;
    private final EmployeeListSelectionChanger employeeListSelectionChanger;
    
    private JTextField nameField, surnameField;
    private JComboBox<Position> positionComboBox;
    private JSpinner salarySpinner, experienceSpinner;
    
    private JButton modifyTrigger, cancelChangeTrigger;
    private JButton addTrigger, deleteTrigger;
    
    private Employee cachedSelection = null;
    
    
    
    public InspectorPanel(EmployeeListLogic employeeListLogic, EmployeeListChangeListener employeeListChangeListener, EmployeeListSelectionChanger employeeListSelectionChanger){
        this.employeeListLogic = employeeListLogic;
        this.employeeListChangeListener = employeeListChangeListener;
        this.employeeListSelectionChanger = employeeListSelectionChanger;
    }
    
    @Override
    protected void buildRows()
    {
        nameField = new JTextField();
        surnameField = new JTextField();
        positionComboBox = new JComboBox<>(Position.values());
        salarySpinner = new JSpinner();
        experienceSpinner = new JSpinner();
        
        
        modifyTrigger = new JButton("Modify");
        modifyTrigger.addActionListener(e -> onModifyTriggered());
        
        cancelChangeTrigger = new JButton("Cancel changes");
        cancelChangeTrigger.addActionListener(e -> onCancelChangesTriggered());
        
        addTrigger = new JButton("Create new");
        addTrigger.addActionListener(e -> onCreateNewTriggered());
        
        deleteTrigger = new JButton("Delete");
        deleteTrigger.addActionListener(e -> onDeleteTriggered());
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
        cachedSelection = employee;
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
    
    private void onCreateNewTriggered(){
        cachedSelection = new Employee("Name","Surname",Position.CEO,1_000,0);
    
        inspect(cachedSelection);
        
        employeeListLogic.add(cachedSelection);
        
        employeeListLogic.refreshFiltering();
        employeeListChangeListener.onEmployeeListChanged();
    
        employeeListSelectionChanger.onEmployeeListNoneSelected();
    }
    
    private void onDeleteTriggered(){
        if(cachedSelection == null) return;
        
        employeeListLogic.remove(cachedSelection);
        
        employeeListLogic.refreshFiltering();
        employeeListChangeListener.onEmployeeListChanged();
        
        inspect(null);
    }
    
    private void onModifyTriggered(){
        
        var name = nameField.getText();
        if(!Employee.isValidName(name)) {
            JOptionPane.showMessageDialog(this,
                    name + " is not a valid name.\n\n" +
                            "The record has not been modified.",
                    "Invalid name",
                    JOptionPane.ERROR_MESSAGE);
            
            return;
        }
    
        var surname = surnameField.getText();
        if(!Employee.isValidSurname(surname)) {
            JOptionPane.showMessageDialog(this,
                    name + " is not a valid surname.\n\n" +
                            "The record has not been modified.",
                    "Invalid surname",
                    JOptionPane.ERROR_MESSAGE);
        
            return;
        }
        
        var position = (Position) positionComboBox.getSelectedItem();
        var salary = (int) salarySpinner.getValue();
        assert position != null;
        if(!position.isValidSalary(salary)) {
            JOptionPane.showMessageDialog(this,
                    salary + " is not a valid salary.\n\n" + position + " must have a salary between " +
                            position.getMinSalary() + " and " + position.getMaxSalary() + ".\n\n" +
                            "The record has not been modified.",
                    "Invalid salary",
                    JOptionPane.ERROR_MESSAGE);
        
            return;
        }
        
        var experience = (int) experienceSpinner.getValue();
    
        System.out.println("Modifying the record...");
        
        cachedSelection.setName(name);
        cachedSelection.setSurname(surname);
        cachedSelection.setPositionAndSalary(position,salary);
        cachedSelection.setExperience(experience);
    
        employeeListLogic.refreshFiltering();
        employeeListChangeListener.onEmployeeListChanged();
    }
    
    private void onCancelChangesTriggered(){
        inspect(cachedSelection);
    }
}