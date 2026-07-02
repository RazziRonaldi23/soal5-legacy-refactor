package view;

import controller.ReportController;
import entity.ReportSummary;

import javax.swing.*;
import java.awt.*;

public class ReportFrame extends JFrame {
    private final ReportController reportController;

    private JLabel totalEmployeeLabel;
    private JLabel totalSalaryLabel;
    private JLabel averageSalaryLabel;
    private JLabel highestSalaryLabel;

    public ReportFrame() {
        this.reportController = new ReportController();

        setTitle("Laporan Karyawan");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        initComponents();
        loadReport();
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        JLabel titleLabel = new JLabel("Laporan Data Karyawan");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));

        JPanel reportPanel = new JPanel(new GridLayout(4, 1, 10, 10));
        reportPanel.setBorder(BorderFactory.createTitledBorder("Ringkasan Laporan"));

        totalEmployeeLabel = new JLabel("Total Karyawan: 0");
        totalSalaryLabel = new JLabel("Total Gaji: 0");
        averageSalaryLabel = new JLabel("Rata-rata Gaji: 0");
        highestSalaryLabel = new JLabel("Gaji Tertinggi: 0");

        totalEmployeeLabel.setFont(new Font("Arial", Font.BOLD, 16));
        totalSalaryLabel.setFont(new Font("Arial", Font.BOLD, 16));
        averageSalaryLabel.setFont(new Font("Arial", Font.BOLD, 16));
        highestSalaryLabel.setFont(new Font("Arial", Font.BOLD, 16));

        reportPanel.add(totalEmployeeLabel);
        reportPanel.add(totalSalaryLabel);
        reportPanel.add(averageSalaryLabel);
        reportPanel.add(highestSalaryLabel);

        JButton refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(event -> loadReport());

        mainPanel.add(titleLabel, BorderLayout.NORTH);
        mainPanel.add(reportPanel, BorderLayout.CENTER);
        mainPanel.add(refreshButton, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private void loadReport() {
        try {
            ReportSummary report = reportController.getEmployeeReport();

            totalEmployeeLabel.setText("Total Karyawan: " + report.getTotalEmployees());
            totalSalaryLabel.setText("Total Gaji: " + report.getTotalSalary());
            averageSalaryLabel.setText("Rata-rata Gaji: " + report.getAverageSalary());
            highestSalaryLabel.setText("Gaji Tertinggi: " + report.getHighestSalary());
        } catch (Exception exception) {
            JOptionPane.showMessageDialog(this, "Gagal memuat laporan: " + exception.getMessage());
        }
    }
}
