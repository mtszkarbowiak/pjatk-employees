package s23603.employees.inteface;

import s23603.employees.Employee;
import s23603.employees.EmployeeListIO;

import javax.swing.table.AbstractTableModel;

class ExplorerTableModel extends AbstractTableModel
{
    EmployeeListIO employeeListIO;
    
    public ExplorerTableModel(EmployeeListIO employeeListIO)
    {
        this.employeeListIO = employeeListIO;
    }
    
    @Override
    public int getRowCount()
    {
        return employeeListIO.size();
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
            case 0 -> employeeListIO.get(rowIndex).getName();
            case 1 -> employeeListIO.get(rowIndex).getSurname();
            case 2 -> employeeListIO.get(rowIndex).getPosition();
            case 3 -> employeeListIO.get(rowIndex).getSalary();
            case 4 -> employeeListIO.get(rowIndex).getExperience();
            default -> throw new IllegalArgumentException();
        };
    }
}
