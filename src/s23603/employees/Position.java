package s23603.employees;

public enum Position
{
    Architect(5_000, 20_000),
    Artist2D(2_000, 10_000),
    CEO(5_500, 50_000),
    Driver(1_000,3_500),
    Janitor(1_500, 3_200),
    Policeman(1_800,8_500),
    Politician(0,50_000),
    President(0,30_000),
    Programmer(0, 10_000),
    Researcher(1_000,8_000),
    SystemAdministrator(0,7_500),
    Teacher(2_000,5_600),
    Witcher(1_000, 10_000);
    
    
    private final int minSalary, maxSalary;
    
    Position(int minSalary, int maxSalary)
    {
        this.minSalary = minSalary;
        this.maxSalary = maxSalary;
    }
    
    public int getMinSalary()
    {
        return minSalary;
    }
    
    public int getMaxSalary()
    {
        return maxSalary;
    }
    
    public boolean isNotValidSalary(int salary)
    {
        return salary < getMinSalary() || salary > getMaxSalary();
    }
}
