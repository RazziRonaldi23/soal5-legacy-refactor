package repository;

import database.DatabaseConnection;
import entity.DashboardSummary;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DashboardRepository {
    public DashboardSummary getSummary() throws SQLException {
        String sql = "SELECT COUNT(*) AS total_employee, COALESCE(SUM(salary), 0) AS total_salary FROM employees";

        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery()
        ) {
            if (resultSet.next()) {
                return new DashboardSummary(
                        resultSet.getInt("total_employee"),
                        resultSet.getDouble("total_salary")
                );
            }
        }

        return new DashboardSummary(0, 0);
    }
}
