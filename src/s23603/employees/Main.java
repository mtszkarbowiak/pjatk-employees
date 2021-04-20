package s23603.employees;

import s23603.employees.inteface.UserInterface;

import javax.swing.*;

public class Main
{
    private static UserInterface userInterface;
    private static EmployeeListIO employeeListIO;
    
    public static void main(String[] args)
    {
        SelfTests.RunAll();
    
        System.out.println("Hello World!");
    
        employeeListIO = new EmployeeListIO();
        
        SwingUtilities.invokeLater(Main::initUserInterface);
    }
    
    public static void initUserInterface(){
        userInterface = new UserInterface(employeeListIO);
    }
}