import s23603.Employee;

public class SelfTests
{
    public static void RunAll()
    {
        System.out.println("--- Self Tests ---");
        System.out.println("employeeSerializationPositive: "+employeeSerializationPositive());
        System.out.println("employeeSerializationNegative: "+employeeSerializationNegative());
        System.out.println("employeeMutation: "+employeeMutation());
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
                "Jan;Kowalski0;CEO;10000;1",
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
        
        employee.setName("Jan");
        employee.setSurname("Jan Kowalski");
        
        return true;
    }
}