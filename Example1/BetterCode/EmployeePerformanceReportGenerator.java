package Example1.BetterCode;

// SRP: This class has only one responsibility — generating and printing performance reports.
// Report formatting changes are contained here and do not ripple into Employee or salary logic.
public class EmployeePerformanceReportGenerator {
    public void printPerformanceReport(Employee e) {
        System.out.println("Performance report of employee " + e.getName());
    }
}
