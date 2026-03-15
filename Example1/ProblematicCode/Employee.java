public class Employee {
    private int id;
    private String name;
    private String address;

    public Employee(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public void printPerformanceReport() {
        System.out.println("Performance report of employee: " + name);
    }

    public double computeSalary() {
        return 1000.0;
    }

    public void updateEmployeeData() {
        System.out.println("Employee data updated successfully");
    }

    public void fetchEmployeeData() {
        System.out.println("Employee data fetched successfully");
    }
}

// This class is trying to do too many things.
// If data storage requirements changes we might need to change updateEmployeeData function.
// If the report format changes we might need to update printPerformanceReport function.

// Because the above class is doing too many things, there are too many reasons to update the code present in the class.
// This violates Single responsibiliity principle.

// Single responsibility principle states that, there should be one and only one reason to change a piece of code.
