package view;

import controller.EmployeeController;
import entity.Employee;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.List;

public class EmployeeFrame extends JFrame {
    private final EmployeeController employeeController;
    private final DefaultTableModel tableModel;

    private JTable employeeTable;
    private JTextField nameField;
    private JTextField positionField;
    private JTextField departmentField;
    private JTextField salaryField;
    private JTextField searchField;

    public EmployeeFrame() {
        this.employeeController = new EmployeeController();
        this.tableModel = new DefaultTableModel(new String[]{"ID", "Nama", "Jabatan", "Departemen", "Gaji"}, 0);

        setTitle("Master Data Karyawan");
        setSize(900, 550);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        initComponents();
        loadEmployees("");
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));

        JPanel formPanel = new JPanel(new GridLayout(5, 2, 8, 8));
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

        JPanel buttonPanel = new JPanel(new GridLayout(1, 5, 8, 8));

        JButton addButton = new JButton("Tambah");
        JButton updateButton = new JButton("Ubah");
        JButton deleteButton = new JButton("Hapus");
        JButton searchButton = new JButton("Cari");
        JButton refreshButton = new JButton("Refresh");

        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(searchButton);
        buttonPanel.add(refreshButton);

        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.add(formPanel, BorderLayout.CENTER);
        leftPanel.add(buttonPanel, BorderLayout.SOUTH);

        employeeTable = new JTable(tableModel);

        mainPanel.add(leftPanel, BorderLayout.WEST);
        mainPanel.add(new JScrollPane(employeeTable), BorderLayout.CENTER);

        add(mainPanel);

        employeeTable.getSelectionModel().addListSelectionListener(event -> fillFormFromTable());

        addButton.addActionListener(event -> saveEmployee());
        updateButton.addActionListener(event -> updateEmployee());
        deleteButton.addActionListener(event -> deleteEmployee());
        searchButton.addActionListener(event -> loadEmployees(searchField.getText()));
        refreshButton.addActionListener(event -> {
            clearForm();
            searchField.setText("");
            loadEmployees("");
        });
    }

    private void loadEmployees(String keyword) {
        tableModel.setRowCount(0);

        try {
            List<Employee> employees = employeeController.findAll(keyword);

            for (Employee employee : employees) {
                tableModel.addRow(new Object[]{
                        employee.getId(),
                        employee.getName(),
                        employee.getPosition(),
                        employee.getDepartment(),
                        employee.getSalary()
                });
            }
        } catch (Exception exception) {
            JOptionPane.showMessageDialog(this, "Gagal memuat data: " + exception.getMessage());
        }
    }

    private void saveEmployee() {
        try {
            employeeController.save(
                    nameField.getText(),
                    positionField.getText(),
                    departmentField.getText(),
                    salaryField.getText()
            );

            JOptionPane.showMessageDialog(this, "Data berhasil ditambahkan.");
            clearForm();
            loadEmployees("");
        } catch (Exception exception) {
            JOptionPane.showMessageDialog(this, exception.getMessage());
        }
    }

    private void updateEmployee() {
        int selectedId = getSelectedId();

        try {
            employeeController.update(
                    selectedId,
                    nameField.getText(),
                    positionField.getText(),
                    departmentField.getText(),
                    salaryField.getText()
            );

            JOptionPane.showMessageDialog(this, "Data berhasil diubah.");
            clearForm();
            loadEmployees("");
        } catch (Exception exception) {
            JOptionPane.showMessageDialog(this, exception.getMessage());
        }
    }

    private void deleteEmployee() {
        int selectedId = getSelectedId();

        int confirm = JOptionPane.showConfirmDialog(this, "Yakin ingin menghapus data?");
        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        try {
            employeeController.delete(selectedId);
            JOptionPane.showMessageDialog(this, "Data berhasil dihapus.");
            clearForm();
            loadEmployees("");
        } catch (Exception exception) {
            JOptionPane.showMessageDialog(this, exception.getMessage());
        }
    }

    private int getSelectedId() {
        int row = employeeTable.getSelectedRow();

        if (row < 0) {
            return 0;
        }

        return Integer.parseInt(tableModel.getValueAt(row, 0).toString());
    }

    private void fillFormFromTable() {
        int row = employeeTable.getSelectedRow();

        if (row >= 0) {
            nameField.setText(tableModel.getValueAt(row, 1).toString());
            positionField.setText(tableModel.getValueAt(row, 2).toString());
            departmentField.setText(tableModel.getValueAt(row, 3).toString());
            salaryField.setText(tableModel.getValueAt(row, 4).toString());
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
