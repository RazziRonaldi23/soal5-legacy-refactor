package repository;

import database.DatabaseConnection;
import entity.ReportSummary;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ReportRepository {
    public ReportSummary getEmployeeReport() throws SQLException {
        String sql = "SELECT COUNT(*) AS total_employee, COALESCE(SUM(salary), 0) AS total_salary, COALESCE(AVG(salary), 0) AS average_salary, COALESCE(MAX(salary), 0) AS highest_salary FROM employees";

        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery()
        ) {
            if (resultSet.next()) {
                return new ReportSummary(
                        resultSet.getInt("total_employee"),
                        resultSet.getDouble("total_salary"),
                        resultSet.getDouble("average_salary"),
                        resultSet.getDouble("highest_salary")
                );
            }
        }

        return new ReportSummary(0, 0, 0, 0);
    }
}
