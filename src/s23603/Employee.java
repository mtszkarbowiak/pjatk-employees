package s23603;

public class Employee
{
    private String name, surname;
    private Position position;
    private int salary, experience;
    
    private static final char SEPARATOR = ';';
    
    
    
    public Employee(){}
    
    public Employee(String name, String surname, Position position, int salary, int experience){
        this.name = name;
        this.surname = surname;
        this.position = position;
        this.salary = salary;
        this.experience = experience;
    }
    
    public String getName(){
        return name;
    }
    
    public String getSurname(){
        return surname;
    }
    
    public Position getPosition(){
        return position;
    }
    
    public int getSalary(){
        return salary;
    }
    
    public int getExperience(){
        return experience;
    }
    
    
    
    
    public String serialize(){
        return name
                + SEPARATOR + surname
                + SEPARATOR + position
                + SEPARATOR + salary
                + SEPARATOR + experience;
    }
    
    public boolean tryDeserialize(String src){
        try{
            var args = src.split(String.valueOf(SEPARATOR));
            
            var nameCached = args[0];
            var surnameCached = args[1];
            var positionCached = Position.valueOf(args[2]);
            var salaryCached = Integer.parseInt(args[3]);
            var experienceCached = Integer.parseInt(args[4]);
    
            this.name = nameCached;
            this.surname = surnameCached;
            this.position = positionCached;
            this.salary = salaryCached;
            this.experience = experienceCached;
    
            return true;
            
        }catch(Exception ex){
            return false;
        }
    }
}