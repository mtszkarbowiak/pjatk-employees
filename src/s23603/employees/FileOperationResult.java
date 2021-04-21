package s23603.employees;

import java.util.List;

// Holds read-only information saying if file reading or writing was performed with any issues.

public class FileOperationResult
{
    private final List<Integer> skippedLines;
    private Exception exception;
    
    private FileOperationResult(List<Integer> skippedLines, Exception exception)
    {
        this.skippedLines = skippedLines;
        this.exception = exception;
    }
    
    
    public static FileOperationResult done()
    {
        return new FileOperationResult(null, null);
    }
    
    public static FileOperationResult doneWithSkippedLines(List<Integer> skippedLines)
    {
        return new FileOperationResult(skippedLines, null);
    }
    
    public static FileOperationResult terminatedByException(Exception exception)
    {
        return new FileOperationResult(null, exception);
    }
    
    
    
    public boolean isNotTerminatedByException()
    {
        return exception == null;
    }
    
    public List<Integer> getSkippedLines()
    {
        return skippedLines;
    }
    
    public boolean hasSkippedLines()
    {
        return skippedLines != null && skippedLines.size() > 0;
    }
    
    public Exception getTerminationException()
    {
        return exception;
    }
}