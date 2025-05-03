package md.streams.model;

public class Employee {
    private String fullName;
    private int department;
    private double employeeSalary;
    private int id;
    private static int idCounter = 0;

   private static int id() {
        return idCounter++;

    }

    public Employee(String fullName, int department, double employeeSalary) {
        this.id = id();
        this.fullName = fullName;
        this.department = department;
        this.employeeSalary = employeeSalary;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {

        return this.fullName;
    }

    public int getId() {

        return id;
    }


    public int getDepartment() {

        return this.department;
    }

    public double getEmployeeSalary() {

        return this.employeeSalary;
    }

    public void setDepartment(int department) {

        this.department = department;
    }

    public void setEmployeeSalary(double employeeSalary) {

        this.employeeSalary = employeeSalary;
    }


    @Override
    public String toString() {
        return "Employee{" +
                "fullName='" + fullName + '\'' +
                ", department=" + department +
                ", employeeSalary=" + employeeSalary +
                ", id=" + id +
                '}';
    }
}
