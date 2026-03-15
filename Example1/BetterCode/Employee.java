package Example1.BetterCode;

// SRP: This class has only one responsibility — holding employee data (id, name, address).
// It does NOT calculate salary or generate reports; those concerns are delegated to separate classes.
public class Employee {
    private int id;
    private String name;
    private String address;

    public Employee(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
