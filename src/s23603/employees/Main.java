package s23603.employees;

import s23603.employees.inteface.UserInterface;
import javax.swing.*;

// Main start point of the application.

public class Main
{
    private static UserInterface userInterface;
    private static EmployeeListLogic employeeListLogic;
    
    public static void main(String[] args)
    {
        // SelfTests.RunAll();
        
        employeeListLogic = new EmployeeListLogic();
        
        SwingUtilities.invokeLater(Main::initUserInterface);
    }
    
    public static void initUserInterface()
    {
        userInterface = new UserInterface(employeeListLogic);
    }
}