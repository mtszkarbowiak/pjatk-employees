package s23603.employees;

public enum Position
{
    Janitor(1_500, 3_500),
    Programmer(0, 10_000),
    Architect(5_000, 20_000),
    CEO(5_000, 50_000),
    Artist2D(2_000, 10_000),
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
