package s23603.employees;

import java.io.*;
import java.util.*;
import java.util.function.Predicate;

// Main logic class of the project. Controls all employees

public class EmployeeListLogic
{
    private final ArrayList<Employee> items;
    private final ArrayList<Employee> filteredItems;
    private Predicate<Employee> filter;
    
    public EmployeeListLogic()
    {
        items = new ArrayList<>();
        filteredItems = new ArrayList<>();
        filter = e -> true;
    }
    
    
    
    public void add(Employee employee)
    {
        items.add(employee);
        
        if(filter.test(employee))
            filteredItems.add(employee);
    }
    
    public void remove(Employee cachedSelection)
    {
        items.remove(cachedSelection);
        filteredItems.remove(cachedSelection);
    }
    
    public void clear()
    {
        items.clear();
        filteredItems.clear();
    }
    
    public void sort(Comparator<Employee> employeeComparator)
    {
        items.sort(employeeComparator);
        filteredItems.sort(employeeComparator);
    }
    
    
    
    public int sizeOfAll()
    {
        return items.size();
    }
    
    public int size()
    {
        return filteredItems.size();
    }
    
    
    
    public Employee getFromAll(int index)
    {
        return items.get(index);
    }
    
    public Employee get(int index)
    {
        return filteredItems.get(index);
    }
    
    
    
    public void setFilterAndRefresh(Predicate<Employee> filter)
    {
        this.filter = filter;
        
        refreshFiltering();
    }
    
    public void refreshFiltering()
    {
        filteredItems.clear();
        
        for(var item : items){
            if(filter.test(item)) filteredItems.add(item);
        }
    }
    
    
    
    public FileOperationResult tryOpen(File file)
    {
        clear();
        
        try(var scanner = new Scanner(file)){
            var skippedLines = new LinkedList<Integer>();
            int lineCount = 0;
            
            while(scanner.hasNextLine()){
                var employee = new Employee();
                var line = scanner.nextLine();
                
                if(employee.tryDeserialize(line)){
                    add(employee);
    
                    refreshFiltering();
                }
                else{
                    skippedLines.add(lineCount);
                }
                
    
                lineCount++;
            }
            
            return FileOperationResult.doneWithSkippedLines(skippedLines);
            
        } catch(IOException e){
            e.printStackTrace();
            
            return FileOperationResult.terminatedByException(e);
        }
    }
    
    public FileOperationResult trySave(File file)
    {
        FileWriter fileWriter = null;
        
        try{
            if(!file.exists()){
                if(!file.createNewFile())
                    return FileOperationResult.terminatedByException(new FileNotFoundException());
            }
            
            fileWriter = new FileWriter(file);
            
            for(Employee employee : items){
                fileWriter.write(employee.serialize());
                fileWriter.write('\n');
            }
            
            return FileOperationResult.done();
            
        } catch(IOException e){
            e.printStackTrace();
            return FileOperationResult.terminatedByException(e);
            
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