package view;

import controller.AuthController;
import entity.User;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {
    private final JTextField usernameField;
    private final JPasswordField passwordField;
    private final AuthController authController;

    public LoginFrame() {
        this.authController = new AuthController();

        setTitle("Login - Employee Management");
        setSize(600, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(80, 100, 80, 100));

        usernameField = new JTextField();
        passwordField = new JPasswordField();
        JButton loginButton = new JButton("Login");

        mainPanel.add(new JLabel("Username"));
        mainPanel.add(usernameField);
        mainPanel.add(new JLabel("Password"));
        mainPanel.add(passwordField);
        mainPanel.add(new JLabel(""));
        mainPanel.add(loginButton);

        add(mainPanel);

        loginButton.addActionListener(event -> login());
    }

    private void login() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        try {
            User user = authController.login(username, password);

            if (user != null) {
                JOptionPane.showMessageDialog(this, "Login berhasil.");
                dispose();
                new DashboardFrame().setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Username atau password salah.");
            }
        } catch (IllegalArgumentException exception) {
            JOptionPane.showMessageDialog(this, exception.getMessage());
        } catch (Exception exception) {
            JOptionPane.showMessageDialog(this, "Terjadi kesalahan login: " + exception.getMessage());
        }
    }
}
