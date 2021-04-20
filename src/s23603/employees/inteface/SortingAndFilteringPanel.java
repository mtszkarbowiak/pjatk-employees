package s23603.employees.inteface;

import s23603.employees.Employee;
import s23603.employees.EmployeeListLogic;

import javax.swing.*;
import java.util.Comparator;

public class SortingAndFilteringPanel extends ColumnsPanel
{
    private EmployeeListLogic employeeListLogic;
    private EmployeeListChangeListener employeeListChangeListener;
    
    public SortingAndFilteringPanel(EmployeeListLogic employeeListLogic, EmployeeListChangeListener employeeListChangeListener){
        this.employeeListLogic = employeeListLogic;
        this.employeeListChangeListener = employeeListChangeListener;
    }
    
    @Override
    protected void buildRows()
    {
        buildSectionTitle("Sorting");
        buildSortingTrigger("Name", Comparator.comparing(Employee::getName));
        buildSortingTrigger("Surname", Comparator.comparing(Employee::getSurname));
        buildSortingTrigger("Salary", Comparator.comparingInt(Employee::getSalary));
    }
    
    private void buildSortingTrigger(String label, Comparator<Employee> employeeComparator){
        var positiveTrigger = new JButton(label + " (asc.)");
        var negativeTrigger = new JButton(label + " (desc.)");
        
        positiveTrigger.addActionListener(e -> {
            employeeListLogic.sort(employeeComparator);
            employeeListChangeListener.OnEmployeeListChanged();
        });
        negativeTrigger.addActionListener(e -> {
            employeeListLogic.sort(generateNegationComparator(employeeComparator));
            employeeListChangeListener.OnEmployeeListChanged();
        });
        
        buildRow(positiveTrigger,negativeTrigger, 0.5f);
    }
    
    private Comparator<Employee> generateNegationComparator(Comparator<Employee> original){
        return (o1, o2) -> -original.compare(o1,o2);
    }
}
