package s23603.employees.inteface;

import s23603.employees.*;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.util.Comparator;
import java.util.function.Predicate;
import java.util.regex.PatternSyntaxException;

public class SortingAndFilteringPanel extends ColumnsPanel
{
    private final EmployeeListLogic employeeListLogic;
    private final EmployeeListChangeListener employeeListChangeListener;
    private final EmployeeListFilter employeeListFilter;
    
    private JToggleButton numericalFilteringToggle;
    private JSpinner minSalaryField, maxSalaryField;
    private JSpinner minExperienceField, maxExperienceField;
    
    private JToggleButton regexFilteringToggle;
    private JTextField regexField;
    
    public SortingAndFilteringPanel(EmployeeListLogic employeeListLogic, EmployeeListChangeListener employeeListChangeListener){
        this.employeeListLogic = employeeListLogic;
        this.employeeListChangeListener = employeeListChangeListener;
        this.employeeListFilter = new EmployeeListFilter();
        
        refreshFiltering();
    }
    
    @Override
    protected void buildRows(){
        
        numericalFilteringToggle = new JToggleButton("OFF", false);
        numericalFilteringToggle.addItemListener(e -> refreshFiltering());
        
        
        minSalaryField = new JSpinner();
        minSalaryField.setValue(1000);
        minSalaryField.addChangeListener(e -> refreshFiltering());
        
        maxSalaryField = new JSpinner();
        maxSalaryField.setValue(10_000);
        maxSalaryField.addChangeListener(e -> refreshFiltering());
        
        minExperienceField = new JSpinner();
        minExperienceField.setValue(0);
        minExperienceField.addChangeListener(e -> refreshFiltering());
        
        maxExperienceField = new JSpinner();
        maxExperienceField.setValue(10);
        maxExperienceField.addChangeListener(e -> refreshFiltering());
    
        
        regexFilteringToggle = new JToggleButton("OFF", false);
        regexFilteringToggle.addItemListener(e -> refreshFiltering());
    
        regexField = new JTextField("John");
        regexField.getDocument().addDocumentListener(new DocumentListener(){
            @Override
            public void insertUpdate(DocumentEvent e){
                refreshFiltering();}
    
            @Override
            public void removeUpdate(DocumentEvent e){
                refreshFiltering();}
    
            @Override
            public void changedUpdate(DocumentEvent e){
                refreshFiltering();
            }
        });
        
        
        buildSectionTitle("Sorting", "");
        buildSortingTrigger("Name", Comparator.comparing(Employee::getName));
        buildSortingTrigger("Surname", Comparator.comparing(Employee::getSurname));
        buildSortingTrigger("Salary", Comparator.comparingInt(Employee::getSalary));
    
        buildSectionTitle("Numerical filtering", "Filter employees using numerical values.");
        buildRow(new JLabel("Enable"), numericalFilteringToggle);
        buildRow(new JLabel("Min Salary"), minSalaryField);
        buildRow(new JLabel("Max salary"), maxSalaryField);
        buildRow(new JLabel("Min Experience"), minExperienceField);
        buildRow(new JLabel("Max Experience"), maxExperienceField);
    
        buildSectionTitle("REGEX matching", "Filter employees matching their names OR surnames with a REGEX.");
        buildRow(new JLabel("Enable"), regexFilteringToggle);
        buildRow(new JLabel("Expression"), regexField);
    }
    
    private void buildSortingTrigger(String label, Comparator<Employee> employeeComparator){
        var positiveTrigger = new JButton(label + " (asc.)");
        var negativeTrigger = new JButton(label + " (desc.)");
        
        positiveTrigger.addActionListener(e -> {
            employeeListLogic.sort(employeeComparator);
            employeeListChangeListener.onEmployeeListChanged();
        });
        negativeTrigger.addActionListener(e -> {
            employeeListLogic.sort(generateNegationComparator(employeeComparator));
            employeeListChangeListener.onEmployeeListChanged();
        });
        
        buildRow(positiveTrigger,negativeTrigger);
    }
    
    private Comparator<Employee> generateNegationComparator(Comparator<Employee> original){
        return (o1, o2) -> -original.compare(o1,o2);
    }

    private void refreshFiltering(){
        {
            boolean enableNumericalFiltering = numericalFilteringToggle.isSelected();
            updateToggleLabel(numericalFilteringToggle);
        
            minSalaryField.setEnabled(enableNumericalFiltering);
            maxSalaryField.setEnabled(enableNumericalFiltering);
            minExperienceField.setEnabled(enableNumericalFiltering);
            maxExperienceField.setEnabled(enableNumericalFiltering);
        
            employeeListFilter.setNumericalFiltering(
                    enableNumericalFiltering,
                    (int) minSalaryField.getValue(),
                    (int) maxSalaryField.getValue(),
                    (int) minExperienceField.getValue(),
                    (int) maxExperienceField.getValue()
            );
        }
    
        {
            boolean enableRegexFiltering = regexFilteringToggle.isSelected();
            updateToggleLabel(regexFilteringToggle);
        
            regexField.setEnabled(enableRegexFiltering);
            
            employeeListFilter.setRegexFiltering(
                    enableRegexFiltering,
                    regexField.getText()
            );
        }
        
        employeeListLogic.setFilter(employeeListFilter);
        employeeListChangeListener.onEmployeeListChanged();
    }
    
    private static void updateToggleLabel(JToggleButton toggleButton){
        toggleButton.setText(toggleButton.isSelected() ? "ON" : "OFF");
    }
}

class EmployeeListFilter implements Predicate<Employee>{
    
    private boolean isNumericalFilteringEnabled;
    private int minSalary, maxSalary, minExperience, maxExperience;
    private boolean isRegexFilteringEnabled;
    private String regex;
    
    public void setNumericalFiltering(boolean enableNumericalFiltering, int minSalary, int maxSalary, int minExperience, int maxExperience)
    {
        isNumericalFilteringEnabled = enableNumericalFiltering;
        
        this.minSalary = minSalary;
        this.maxSalary = maxSalary;
        this.minExperience = minExperience;
        this.maxExperience = maxExperience;
    }
    
    public void setRegexFiltering(boolean enableRegexFiltering, String regex)
    {
        isRegexFilteringEnabled = enableRegexFiltering;
        
        this.regex = regex;
    }
    
    
    
    @Override
    public boolean test(Employee employee)
    {
        if(isNumericalFilteringEnabled)
        {
            var salary = employee.getSalary();
            var experience = employee.getExperience();
            
            if(salary < minSalary) return false;
            if(salary > maxSalary) return false;
            if(experience < minExperience) return false;
            if(experience > maxExperience) return false;
        }
        
        if(isRegexFilteringEnabled)
        {
            try{
                var nameMatch = employee.getName().matches(regex);
                var surnameMatch = employee.getSurname().matches(regex);
    
                if(!nameMatch && !surnameMatch) return false;
            }catch(PatternSyntaxException ex){
                return false;
            }
        }
        
        return true;
    }
}