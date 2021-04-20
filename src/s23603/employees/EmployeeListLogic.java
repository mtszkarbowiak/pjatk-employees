package s23603.employees;

import java.io.*;
import java.util.*;
import java.util.function.Predicate;

public class EmployeeListLogic
{
    private ArrayList<Employee> items, filteredItems;
    private Predicate<Employee> filter;
    
    public EmployeeListLogic(){
        items = new ArrayList<>();
        filteredItems = new ArrayList<>();
        filter = e -> true;
    }
    
    public boolean tryOpen(File file)
    {
        clear();
    
        try(var scanner = new Scanner(file)){
        
            while(scanner.hasNextLine()){
                var employee = new Employee();
                var line = scanner.nextLine();
            
                if(!employee.tryDeserialize(line)) return false;
    
                add(employee);
                
                refreshFiltering();
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
    
            for(Employee employee : items){
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
    
    public void sort(Comparator<Employee> employeeComparator){
        items.sort(employeeComparator);
    }
    
    public void add(Employee employee){
        items.add(employee);
    }
    
    public int sizeAll(){
        return items.size();
    }
    
    public int sizeFiltered(){
        return filteredItems.size();
    }
    
    public Employee getFromAll(int index){
        return items.get(index);
    }
    
    public Employee getFromFiltered(int index){
        return filteredItems.get(index);
    }
    
    public void clear(){
        items.clear();
        filteredItems.clear();
    }
    
    public void setFilter(Predicate<Employee> filter){
        this.filter = filter;
        
        refreshFiltering();
    }
    
    public void refreshFiltering(){
        filteredItems.clear();
    
        for(var item : items){
            if(filter.test(item)) filteredItems.add(item);
        }
    }
}