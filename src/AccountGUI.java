import javax.swing.*;

import logic.Employee;

import java.awt.*;
import java.awt.event.*;

public class AccountGUI extends JFrame {
    private JFrame frame;
    private JTextArea infoTextArea;
    private Employee loggedInEmployee;

    public AccountGUI(Employee loggedInEmployee) {
        this.loggedInEmployee = loggedInEmployee;
    }

    public Employee handleGUI() {
        SwingUtilities.invokeLater(this::displayGUI);
        return loggedInEmployee;
    }

    private void displayGUI() {
        frame = new JFrame("Employee Information");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        infoTextArea = new JTextArea();
        infoTextArea.setEditable(false);
        infoTextArea.setFont(new Font("Arial", Font.PLAIN, 14));

        // Set the text area content with the string representation of the logged-in employee
        updateInfoTextArea(loggedInEmployee);

        JScrollPane scrollPane = new JScrollPane(infoTextArea);

        JButton changeNameButton = new JButton("Change Name");
        JButton changeUsernameButton = new JButton("Change Username");
        JButton changePasswordButton = new JButton("Change Password");

        // Add action listeners for the buttons
        changeNameButton.addActionListener(e -> {
            String newName = JOptionPane.showInputDialog(frame, "Enter new name:");
            try {
                loggedInEmployee.setName(newName);
                updateInfoTextArea(loggedInEmployee);
            } catch (Employee.BlankFieldException ex) {
                JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage());
            }
        });

        changeUsernameButton.addActionListener(e -> {
            String newUsername = JOptionPane.showInputDialog(frame, "Enter new username:");
            try {
                loggedInEmployee.changeUsername(newUsername);
                updateInfoTextArea(loggedInEmployee);
            } catch (Employee.BlankFieldException ex) {
                JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage());
            }
        });

        changePasswordButton.addActionListener(e -> {
            String currentPassword = JOptionPane.showInputDialog(frame, "Enter current password:");
            String newPassword = JOptionPane.showInputDialog(frame, "Enter new password:");
            try {
                loggedInEmployee.changePassword(currentPassword, newPassword);
                updateInfoTextArea(loggedInEmployee);
            } catch (Employee.MismatchException | Employee.BlankFieldException ex) {
                JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage());
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(changeNameButton);
        buttonPanel.add(changeUsernameButton);
        buttonPanel.add(changePasswordButton);

        frame.getContentPane().add(BorderLayout.CENTER, scrollPane);
        frame.getContentPane().add(BorderLayout.SOUTH, buttonPanel);
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    // Update the text area content with the new string representation of the employee
    private void updateInfoTextArea(Employee loggedInEmployee) {
        infoTextArea.setText(loggedInEmployee.toString());
    }
}
