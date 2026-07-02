package entity;

public class DashboardSummary {
    private final int totalEmployees;
    private final double totalSalary;

    public DashboardSummary(int totalEmployees, double totalSalary) {
        this.totalEmployees = totalEmployees;
        this.totalSalary = totalSalary;
    }

    public int getTotalEmployees() {
        return totalEmployees;
    }

    public double getTotalSalary() {
        return totalSalary;
    }
}
