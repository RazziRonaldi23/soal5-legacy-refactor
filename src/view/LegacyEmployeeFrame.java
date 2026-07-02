package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class LegacyEmployeeFrame extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;

    private JTextField nameField;
    private JTextField positionField;
    private JTextField departmentField;
    private JTextField salaryField;
    private JTextField searchField;

    private JTable employeeTable;
    private DefaultTableModel tableModel;
    private JLabel totalEmployeeLabel;

    public LegacyEmployeeFrame() {
        setTitle("Legacy Employee Management");
        setSize(900, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        showLoginView();
    }

    private Connection connect() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver MySQL belum ditemukan.", e);
        }

        return DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/db_legacy_refactor",
                "root",
                ""
        );
    }

    private void showLoginView() {
        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(120, 250, 120, 250));

        usernameField = new JTextField();
        passwordField = new JPasswordField();
        JButton loginButton = new JButton("Login");

        panel.add(new JLabel("Username"));
        panel.add(usernameField);
        panel.add(new JLabel("Password"));
        panel.add(passwordField);
        panel.add(new JLabel(""));
        panel.add(loginButton);

        add(panel);

        loginButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Username dan password wajib diisi.");
                return;
            }

            try {
                Connection connection = connect();
                PreparedStatement statement = connection.prepareStatement(
                        "SELECT * FROM users WHERE username = ? AND password = ?"
                );

                statement.setString(1, username);
                statement.setString(2, password);

                ResultSet resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    JOptionPane.showMessageDialog(this, "Login berhasil.");
                    getContentPane().removeAll();
                    showMainView();
                    revalidate();
                    repaint();
                } else {
                    JOptionPane.showMessageDialog(this, "Username atau password salah.");
                }

                resultSet.close();
                statement.close();
                connection.close();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error login: " + ex.getMessage());
            }
        });
    }

    private void showMainView() {
        setTitle("Legacy Employee Management - Dashboard");

        JPanel mainPanel = new JPanel(new BorderLayout());

        JPanel topPanel = new JPanel(new GridLayout(1, 3, 10, 10));
        totalEmployeeLabel = new JLabel("Total Karyawan: 0");
        JButton refreshButton = new JButton("Refresh");
        JButton reportButton = new JButton("Laporan");

        topPanel.add(totalEmployeeLabel);
        topPanel.add(refreshButton);
        topPanel.add(reportButton);

        JPanel formPanel = new JPanel(new GridLayout(6, 2, 8, 8));
        formPanel.setBorder(BorderFactory.createTitledBorder("Form Karyawan"));

        nameField = new JTextField();
        positionField = new JTextField();
        departmentField = new JTextField();
        salaryField = new JTextField();
        searchField = new JTextField();

        formPanel.add(new JLabel("Nama"));
        formPanel.add(nameField);
        formPanel.add(new JLabel("Jabatan"));
        formPanel.add(positionField);
        formPanel.add(new JLabel("Departemen"));
        formPanel.add(departmentField);
        formPanel.add(new JLabel("Gaji"));
        formPanel.add(salaryField);
        formPanel.add(new JLabel("Cari Nama"));
        formPanel.add(searchField);

        JButton addButton = new JButton("Tambah");
        JButton updateButton = new JButton("Ubah");
        JButton deleteButton = new JButton("Hapus");
        JButton searchButton = new JButton("Cari");

        JPanel buttonPanel = new JPanel(new GridLayout(1, 4, 8, 8));
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(searchButton);

        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.add(formPanel, BorderLayout.CENTER);
        leftPanel.add(buttonPanel, BorderLayout.SOUTH);

        tableModel = new DefaultTableModel(new String[]{"ID", "Nama", "Jabatan", "Departemen", "Gaji"}, 0);
        employeeTable = new JTable(tableModel);

        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(leftPanel, BorderLayout.WEST);
        mainPanel.add(new JScrollPane(employeeTable), BorderLayout.CENTER);

        add(mainPanel);

        loadEmployees("");
        loadDashboard();

        employeeTable.getSelectionModel().addListSelectionListener(e -> {
            int row = employeeTable.getSelectedRow();

            if (row >= 0) {
                nameField.setText(tableModel.getValueAt(row, 1).toString());
                positionField.setText(tableModel.getValueAt(row, 2).toString());
                departmentField.setText(tableModel.getValueAt(row, 3).toString());
                salaryField.setText(tableModel.getValueAt(row, 4).toString());
            }
        });

        addButton.addActionListener(e -> {
            if (nameField.getText().isEmpty() || positionField.getText().isEmpty()
                    || departmentField.getText().isEmpty() || salaryField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Semua input wajib diisi.");
                return;
            }

            try {
                double salary = Double.parseDouble(salaryField.getText());

                Connection connection = connect();
                PreparedStatement statement = connection.prepareStatement(
                        "INSERT INTO employees (name, position, department, salary) VALUES (?, ?, ?, ?)"
                );

                statement.setString(1, nameField.getText());
                statement.setString(2, positionField.getText());
                statement.setString(3, departmentField.getText());
                statement.setDouble(4, salary);

                statement.executeUpdate();

                statement.close();
                connection.close();

                JOptionPane.showMessageDialog(this, "Data berhasil ditambahkan.");
                clearForm();
                loadEmployees("");
                loadDashboard();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Gaji harus berupa angka.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error tambah data: " + ex.getMessage());
            }
        });

        updateButton.addActionListener(e -> {
            int row = employeeTable.getSelectedRow();

            if (row < 0) {
                JOptionPane.showMessageDialog(this, "Pilih data yang ingin diubah.");
                return;
            }

            if (nameField.getText().isEmpty() || positionField.getText().isEmpty()
                    || departmentField.getText().isEmpty() || salaryField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Semua input wajib diisi.");
                return;
            }

            try {
                int id = Integer.parseInt(tableModel.getValueAt(row, 0).toString());
                double salary = Double.parseDouble(salaryField.getText());

                Connection connection = connect();
                PreparedStatement statement = connection.prepareStatement(
                        "UPDATE employees SET name = ?, position = ?, department = ?, salary = ? WHERE id = ?"
                );

                statement.setString(1, nameField.getText());
                statement.setString(2, positionField.getText());
                statement.setString(3, departmentField.getText());
                statement.setDouble(4, salary);
                statement.setInt(5, id);

                statement.executeUpdate();

                statement.close();
                connection.close();

                JOptionPane.showMessageDialog(this, "Data berhasil diubah.");
                clearForm();
                loadEmployees("");
                loadDashboard();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Gaji harus berupa angka.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error ubah data: " + ex.getMessage());
            }
        });

        deleteButton.addActionListener(e -> {
            int row = employeeTable.getSelectedRow();

            if (row < 0) {
                JOptionPane.showMessageDialog(this, "Pilih data yang ingin dihapus.");
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(this, "Yakin ingin menghapus data?");
            if (confirm != JOptionPane.YES_OPTION) {
                return;
            }

            try {
                int id = Integer.parseInt(tableModel.getValueAt(row, 0).toString());

                Connection connection = connect();
                PreparedStatement statement = connection.prepareStatement(
                        "DELETE FROM employees WHERE id = ?"
                );

                statement.setInt(1, id);
                statement.executeUpdate();

                statement.close();
                connection.close();

                JOptionPane.showMessageDialog(this, "Data berhasil dihapus.");
                clearForm();
                loadEmployees("");
                loadDashboard();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error hapus data: " + ex.getMessage());
            }
        });

        searchButton.addActionListener(e -> loadEmployees(searchField.getText()));

        refreshButton.addActionListener(e -> {
            clearForm();
            searchField.setText("");
            loadEmployees("");
            loadDashboard();
        });

        reportButton.addActionListener(e -> {
            try {
                Connection connection = connect();

                Statement totalStatement = connection.createStatement();
                ResultSet totalResult = totalStatement.executeQuery("SELECT COUNT(*) AS total FROM employees");

                int total = 0;
                if (totalResult.next()) {
                    total = totalResult.getInt("total");
                }

                Statement salaryStatement = connection.createStatement();
                ResultSet salaryResult = salaryStatement.executeQuery("SELECT SUM(salary) AS total_salary FROM employees");

                double totalSalary = 0;
                if (salaryResult.next()) {
                    totalSalary = salaryResult.getDouble("total_salary");
                }

                JOptionPane.showMessageDialog(this,
                        "Laporan Karyawan\n"
                                + "Total Karyawan: " + total + "\n"
                                + "Total Gaji: " + totalSalary
                );

                totalResult.close();
                totalStatement.close();
                salaryResult.close();
                salaryStatement.close();
                connection.close();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error laporan: " + ex.getMessage());
            }
        });
    }

    private void loadEmployees(String keyword) {
        tableModel.setRowCount(0);

        try {
            Connection connection = connect();
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM employees WHERE name LIKE ? ORDER BY id DESC"
            );

            statement.setString(1, "%" + keyword + "%");

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                tableModel.addRow(new Object[]{
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("position"),
                        resultSet.getString("department"),
                        resultSet.getDouble("salary")
                });
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error tampil data: " + ex.getMessage());
        }
    }

    private void loadDashboard() {
        try {
            Connection connection = connect();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) AS total FROM employees");

            if (resultSet.next()) {
                totalEmployeeLabel.setText("Total Karyawan: " + resultSet.getInt("total"));
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error dashboard: " + ex.getMessage());
        }
    }

    private void clearForm() {
        nameField.setText("");
        positionField.setText("");
        departmentField.setText("");
        salaryField.setText("");
        employeeTable.clearSelection();
    }
}
