package Example1.BetterCode;

// SRP: This class has only one responsibility — computing an employee's salary.
// Salary logic is isolated here so changes to pay rules never affect Employee data or reporting code.
public class EmployeeSalaryCalculator {
    public double computeSalary(Employee e) {
        return 1000.0;
    }
}
