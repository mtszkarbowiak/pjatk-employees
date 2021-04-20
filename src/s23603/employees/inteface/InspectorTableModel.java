package s23603.employees.inteface;

import s23603.employees.Employee;

import javax.swing.table.AbstractTableModel;

public class InspectorTableModel extends AbstractTableModel
{
    private Employee inspectedElement;
    
    public Employee getInspectedElement()
    {
        return inspectedElement;
    }
    
    public void setInspectedElement(Employee inspectedElement)
    {
        this.inspectedElement = inspectedElement;
    }
    
    @Override
    public int getRowCount(){
        return 5;
    }
    
    @Override
    public int getColumnCount(){
        return 2;
    }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex)
    {
        if(columnIndex == 0){
            return UserInterface.ATTRIBUTE_NAMES[rowIndex];
        }
        else{
            if(inspectedElement == null) return "-";
            
            return switch(rowIndex){
                case 0 -> inspectedElement.getName();
                case 1 -> inspectedElement.getSurname();
                case 2 -> inspectedElement.getPosition();
                case 3 -> inspectedElement.getSalary();
                case 4 -> inspectedElement.getExperience();
                default -> throw new IllegalArgumentException();
            };
        }
    }
    
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex == 1 && inspectedElement != null;
    }
}
