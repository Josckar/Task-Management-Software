import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.CountDownLatch;

import logic.*;

public class CreateAccountGUI extends TemplateGUI {
	
    public CreateAccountGUI() {
    	super("Create Account");
        addBackButton();
        latch = new CountDownLatch(1);
	}

	
    private JPanel panel;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    private JCheckBox managerCheckBox;
    private JLabel titleLabel;
    private CountDownLatch latch;

    private Employee createdEmployee; // Variable to store the created employee

    public Employee createAndShowGUI() {
    	
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(10, 0, 10, 0);

        titleLabel = new JLabel("Create Account Mode");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(titleLabel, gbc);

        gbc.gridwidth = 1;
        gbc.gridy++;
        gbc.anchor = GridBagConstraints.CENTER;

        usernameField = new JTextField(20);
        passwordField = new JPasswordField(20);
        confirmPasswordField = new JPasswordField(20);
        managerCheckBox = new JCheckBox("Manager");

        JButton confirmButton = new JButton("Confirm");
        panel.add(new JLabel("Username:"), gbc);
        gbc.gridx++;
        panel.add(usernameField, gbc);
        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Password:"), gbc);
        gbc.gridx++;
        panel.add(passwordField, gbc);
        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Confirm Password:"), gbc);
        gbc.gridx++;
        panel.add(confirmPasswordField, gbc);
        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(managerCheckBox, gbc);
        gbc.gridx++;
        panel.add(new JPanel(), gbc); // Empty cell for spacing
        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(confirmButton, gbc);

        confirmButton.addActionListener(e -> {
            handleConfirmButton();
            latch.countDown(); // Release the latch when the confirm button is pressed
        });

        frame.getContentPane().add(BorderLayout.CENTER, panel);
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        // Wait for the frame to close before returning the created employee
        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                System.out.println("closed without confirm");
                createdEmployee = null; // Set to null if the window is closed without confirmation
                latch.countDown(); // Release the latch when the window is closed
            }
        });

        try {
            latch.await(); // Wait for the latch to be released (confirm button pressed or window closed)
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return createdEmployee;
    }
    private Employee handleConfirmButton() {
        String username = usernameField.getText();
        char[] password = passwordField.getPassword();
        char[] confirmPassword = confirmPasswordField.getPassword();

        boolean isManager = managerCheckBox.isSelected();

        try {
            if (isManager) {
                createdEmployee = new Manager(username, new String(password));
            } else {
                createdEmployee = new TeamMember(username, new String(password));
            }

            // Additional logic for setting employee properties based on the GUI
            System.out.println("Account created successfully. " + createdEmployee);
            frame.dispose(); // Close the window after account creation
        } catch (Employee.BlankFieldException ex) {
            System.out.println("Error: " + ex.getMessage());
            createdEmployee = null; // Set to null if creation fails
        }
        return createdEmployee;
    }
}
