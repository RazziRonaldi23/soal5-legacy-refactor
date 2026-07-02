package view;

import controller.DashboardController;
import entity.DashboardSummary;

import javax.swing.*;
import java.awt.*;

public class DashboardFrame extends JFrame {
    private final DashboardController dashboardController;
    private final JLabel totalEmployeeLabel;
    private final JLabel totalSalaryLabel;

    public DashboardFrame() {
        this.dashboardController = new DashboardController();

        setTitle("Dashboard - Employee Management");
        setSize(700, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        JLabel titleLabel = new JLabel("Dashboard Employee Management");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));

        JPanel statisticPanel = new JPanel(new GridLayout(2, 1, 10, 10));
        statisticPanel.setBorder(BorderFactory.createTitledBorder("Statistik"));

        totalEmployeeLabel = new JLabel("Total Karyawan: 0");
        totalSalaryLabel = new JLabel("Total Gaji: 0");

        totalEmployeeLabel.setFont(new Font("Arial", Font.BOLD, 18));
        totalSalaryLabel.setFont(new Font("Arial", Font.BOLD, 18));

        statisticPanel.add(totalEmployeeLabel);
        statisticPanel.add(totalSalaryLabel);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 3, 10, 10));

        JButton refreshButton = new JButton("Refresh");
        JButton masterButton = new JButton("Master Data");
        JButton logoutButton = new JButton("Logout");

        buttonPanel.add(refreshButton);
        buttonPanel.add(masterButton);
        buttonPanel.add(logoutButton);

        mainPanel.add(titleLabel, BorderLayout.NORTH);
        mainPanel.add(statisticPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);

        refreshButton.addActionListener(event -> loadDashboard());
        masterButton.addActionListener(event -> openMasterData());
        logoutButton.addActionListener(event -> logout());

        loadDashboard();
    }

    private void loadDashboard() {
        try {
            DashboardSummary summary = dashboardController.getSummary();

            totalEmployeeLabel.setText("Total Karyawan: " + summary.getTotalEmployees());
            totalSalaryLabel.setText("Total Gaji: " + summary.getTotalSalary());
        } catch (Exception exception) {
            JOptionPane.showMessageDialog(this, "Gagal memuat dashboard: " + exception.getMessage());
        }
    }

    private void openMasterData() {
        new LegacyEmployeeFrame(true).setVisible(true);
    }

    private void logout() {
        dispose();
        new LoginFrame().setVisible(true);
    }
}
