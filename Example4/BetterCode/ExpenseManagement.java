package Example4.BetterCode;

// ISP: Only business-tier cards implement this interface.
// Travel or basic cards are never polluted with corporate expense methods.
public interface ExpenseManagement {
    void generateExpenseReport();
    void addEmployeeCard(String employeeName);
}
