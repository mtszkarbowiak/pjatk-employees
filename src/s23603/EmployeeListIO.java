package s23603;

import java.io.*;
import java.util.*;

public class EmployeeListIO extends ArrayList<Employee>
{
    public boolean tryOpen(File file)
    {
        clear();
    
        try(var scanner = new Scanner(file)){
        
            while(scanner.hasNextLine()){
                var employee = new Employee();
                var line = scanner.nextLine();
            
                if(!employee.tryDeserialize(line)) return false;
            
                add(employee);
            }
        
            return true;
        } catch(IOException e){
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean trySave(File file)
    {
        FileWriter fileWriter = null;
        
        try{
            if(!file.exists()){
                if(!file.createNewFile())
                    return false;
            }
            
            fileWriter = new FileWriter(file);
    
            for(Employee employee : this){
                fileWriter.write(employee.serialize());
                fileWriter.write('\n');
            }
            
            return true;
            
        } catch(IOException e){
            e.printStackTrace();
            return false;
            
        } finally{
            if(fileWriter != null){
                try{
                    fileWriter.close();
                } catch(IOException e){
                    e.printStackTrace();
                }
            }
        }
    }
}