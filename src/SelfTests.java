import s23603.*;

import java.io.File;

public class SelfTests
{
    public static void RunAll()
    {
        System.out.println("--- Self Tests ---");
        System.out.println("employeeSerializationPositive: "+employeeSerializationPositive());
        System.out.println("employeeSerializationNegative: "+employeeSerializationNegative());
        System.out.println("employeeMutation: "+employeeMutation());
        System.out.println("employeeListIO: "+employeeListIO());
        System.out.println("---  ---");
    }
    
    public static boolean employeeSerializationPositive(){
        var employee = new Employee();
        var serialized = "Jan;Kowalski;CEO;10000;5";
        
        boolean pass = employee.tryDeserialize(serialized);
        pass &= employee.serialize().equals(serialized);
        
        return pass;
    }
    
    public static boolean employeeSerializationNegative(){
        var employee = new Employee();
        
        String[] serializedEmployees = {
                "Jan;Kowalski;CEO;10000",
                "Jan;Kowalski+;CEO;10000;1",
                ";;CEO;10000;1",
        };
    
        for(String serializedEmployee : serializedEmployees){
            if(employee.tryDeserialize(serializedEmployee)) return false;
        }
        
        return true;
    }
    
    public static boolean employeeMutation(){
        var employee = new Employee();
        
        try{
            employee.setName("Jan;");
            
            return false;
        }catch(IllegalArgumentException ignored){}
        
        try{
            employee.setName(" ");
        
            return false;
        }catch(IllegalArgumentException ignored){}
    
        try{
            employee.setPositionAndSalary(Position.CEO,1);
        
            return false;
        }catch(IllegalArgumentException ignored){}
        
        employee.setName("Jan");
        employee.setSurname("Jan Kowalski");
        employee.setPositionAndSalary(Position.CEO,9_000);
        
        return true;
    }

    public static boolean employeeListIO(){
        var employeeListIO = new EmployeeListIO();
        var file = new File("out\\TestFile.txt");
    
        employeeListIO.add(new Employee("Jan","Kowalski",Position.Janitor, 1_000, 20));
        employeeListIO.add(new Employee("Jan","Kowalski 2",Position.Janitor, 1_000, 20));
        int initSize = employeeListIO.size();
    
        boolean pass =  employeeListIO.trySave(file);
        employeeListIO.clear();
    
        pass &= employeeListIO.tryOpen(file);
        pass &= employeeListIO.size() == initSize;
        
        return pass;
    }
}