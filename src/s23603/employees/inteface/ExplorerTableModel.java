package s23603.employees.inteface;

import s23603.employees.EmployeeListLogic;
import javax.swing.table.AbstractTableModel;

// Table model of main employee record selection table.

class ExplorerTableModel extends AbstractTableModel
{
    EmployeeListLogic employeeListLogic;
    
    public ExplorerTableModel(EmployeeListLogic employeeListLogic)
    {
        this.employeeListLogic = employeeListLogic;
    }
    
    @Override
    public int getRowCount()
    {
        return employeeListLogic.size();
    }
    
    @Override
    public int getColumnCount()
    {
        return 5;
    }
    
    @Override
    public String getColumnName(int index)
    {
        return UserInterface.ATTRIBUTE_NAMES[index];
    }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex)
    {
        return switch(columnIndex){
            case 0 -> employeeListLogic.get(rowIndex).getName();
            case 1 -> employeeListLogic.get(rowIndex).getSurname();
            case 2 -> employeeListLogic.get(rowIndex).getPosition();
            case 3 -> employeeListLogic.get(rowIndex).getSalary();
            case 4 -> employeeListLogic.get(rowIndex).getExperience();
            default -> throw new IllegalArgumentException();
        };
    }
}
