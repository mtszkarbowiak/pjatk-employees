import s23603.Employee;

public class SelfTests
{
    public static void RunAll()
    {
        System.out.println("--- Self Tests ---");
        System.out.println("EmployeesSerialization: "+employeeSerialization());
        System.out.println("---  ---");
    }
    
    public static boolean employeeSerialization(){
        var employee = new Employee();
        var serialized = "Jan;Kowalski;CEO;10000;5";
        
        boolean pass = employee.tryDeserialize(serialized);
        pass &= employee.serialize().equals(serialized);
        
        return pass;
    }
}