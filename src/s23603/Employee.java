package s23603;

public class Employee
{
    private String name, surname;
    private Position position;
    private int salary, experience;
    
    private static final char SEPARATOR = ';';
    private static final String NAME_SURNAME_REGEX = "^[A-Za-z][A-Za-z0-9 .]{1,48}[A-Za-z0-9]$";
    
    
    
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
    
    
    public void setName(String name){
        validateName(name);
        
        this.name = name;
    }
    
    public void setSurname(String surname){
        validateSurname(surname);
        
        this.surname = surname;
    }
    
    public void setPositionAndSalary(Position position, int salary){
        validatePositionAndSalary(position,salary);
        
        this.position = position;
        this.salary = salary;
    }
    
    public void setExperience(int experience){
        this.experience = experience;
    }
    
    
    
    public static boolean isValidName(String name){
        return name.matches(NAME_SURNAME_REGEX);
    }
    
    public static boolean isValidSurname(String surname){
        return surname.matches(NAME_SURNAME_REGEX);
    }
    
    public static void validateName(String name){
        if(!isValidName(name))
            throw new IllegalArgumentException(name + " is not a valid name.");
    }
    
    public static void validateSurname(String surname){
        if(!isValidSurname(surname))
            throw new IllegalArgumentException(surname + " is not a valid surname.");
    }
    
    public static void validatePositionAndSalary(Position position, int salary){
        if(!position.isValidSalary(salary))
            throw new IllegalArgumentException(salary + " is not a valid salary for position of " + position + ".");
    }
    
    
    
    public String serialize(){
        return name
                + SEPARATOR + surname
                + SEPARATOR + position
                + SEPARATOR + salary
                + SEPARATOR + experience;
    }
    
    public void deserialize(String src){
        var args = src.split(String.valueOf(SEPARATOR));
        
        if(args.length != 5)
            throw new IllegalArgumentException(src + " does not consist of 5 arguments.");
    
        var nameCached = args[0];
        var surnameCached = args[1];
        var positionCached = Position.valueOf(args[2]);
        var salaryCached = Integer.parseInt(args[3]);
        var experienceCached = Integer.parseInt(args[4]);
    
        validateName(nameCached);
        validateSurname(surnameCached);
    
        this.name = nameCached;
        this.surname = surnameCached;
        this.position = positionCached;
        this.salary = salaryCached;
        this.experience = experienceCached;
    }
    
    public boolean tryDeserialize(String src){
        try{
            deserialize(src);
            return true;
        }catch(Exception ex){
            return false;
        }
    }
}