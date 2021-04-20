package s23603.employees.inteface;

import s23603.employees.EmployeeListLogic;

import javax.swing.table.AbstractTableModel;

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
        return employeeListLogic.sizeFiltered();
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
            case 0 -> employeeListLogic.getFromFiltered(rowIndex).getName();
            case 1 -> employeeListLogic.getFromFiltered(rowIndex).getSurname();
            case 2 -> employeeListLogic.getFromFiltered(rowIndex).getPosition();
            case 3 -> employeeListLogic.getFromFiltered(rowIndex).getSalary();
            case 4 -> employeeListLogic.getFromFiltered(rowIndex).getExperience();
            default -> throw new IllegalArgumentException();
        };
    }
}
