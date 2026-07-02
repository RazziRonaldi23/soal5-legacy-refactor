package entity;

public class ReportSummary {
    private final int totalEmployees;
    private final double totalSalary;
    private final double averageSalary;
    private final double highestSalary;

    public ReportSummary(int totalEmployees, double totalSalary, double averageSalary, double highestSalary) {
        this.totalEmployees = totalEmployees;
        this.totalSalary = totalSalary;
        this.averageSalary = averageSalary;
        this.highestSalary = highestSalary;
    }

    public int getTotalEmployees() {
        return totalEmployees;
    }

    public double getTotalSalary() {
        return totalSalary;
    }

    public double getAverageSalary() {
        return averageSalary;
    }

    public double getHighestSalary() {
        return highestSalary;
    }
}
