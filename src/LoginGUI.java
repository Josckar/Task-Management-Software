import javax.swing.*;
import javax.swing.border.LineBorder;

import logic.Employee;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;

public class LoginGUI {
    private JFrame jframe;
    private JButton loginButton;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JLabel usernameError;
    private JLabel passwordError;
    private ArrayList<Employee> employees;
    private Employee loggedInEmployee;
    private CountDownLatch loginLatch;

    public LoginGUI(ArrayList<Employee> employees) throws IOException {
        this.employees = employees;
        jframe = new JFrame("Login Form");
        usernameField = new JTextField() {
            public void updateUI() {
                super.updateUI();
                setOpaque(false);
                setBorder(new LineBorder(Color.BLACK, 1, true));
            }
        };
        passwordField = new JPasswordField() {
            public void updateUI() {
                super.updateUI();
                setOpaque(false);
                setBorder(new LineBorder(Color.BLACK, 1, true));
            }
        };

        loginButton = new JButton("LOGIN") {
            public void updateUI() {
                super.updateUI();
                setOpaque(false);
                setBorder(new LineBorder(Color.BLACK, 1, true));
            }
        };
        usernameError = new JLabel();
        passwordError = new JLabel();

        jframe.setContentPane(new JPanel() {});
        loginLatch = new CountDownLatch(1);
        init();
    }

    public void init() {
        System.out.println("initialized");

        usernameField.setPreferredSize(new Dimension(250, 35));
        passwordField.setPreferredSize(new Dimension(250, 35));
        loginButton.setPreferredSize(new Dimension(250, 35));
        loginButton.setBackground(new Color(66, 245, 114));
        loginButton.setFocusPainted(false);

        usernameField.setText("Enter your username");
        usernameField.setForeground(Color.gray);
        passwordField.setText("Enter your password");
        passwordField.setForeground(Color.gray);
        passwordField.setEchoChar((char) 0);

        usernameError.setFont(new Font("SansSerif", Font.BOLD, 11));
        usernameError.setForeground(Color.RED);

        SwingUtilities.invokeLater(() -> {
            jframe.setLayout(new GridBagLayout());

            Insets textInsets = new Insets(10, 10, 5, 10);
            Insets buttonInsets = new Insets(20, 10, 10, 10);
            Insets errorInsets = new Insets(0, 20, 0, 0);

            GridBagConstraints input = new GridBagConstraints();
            input.anchor = GridBagConstraints.CENTER;
            input.insets = textInsets;
            input.gridy = 1;
            jframe.add(usernameField, input);

            input.insets = errorInsets;
            input.gridy = 2;
            input.anchor = GridBagConstraints.WEST;
            jframe.add(usernameError, input);

            input.insets = textInsets;
            input.gridy = 3;
            jframe.add(passwordField, input);

            input.insets = errorInsets;
            input.gridy = 4;
            input.anchor = GridBagConstraints.WEST;
            jframe.add(passwordError, input);

            input.insets = buttonInsets;
            input.anchor = GridBagConstraints.WEST;
            input.gridx = 0;
            input.gridy = 5;
            jframe.add(loginButton, input);

            jframe.setSize(950, 650);
            jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            jframe.setVisible(true);
            jframe.setResizable(false);
            jframe.setLocationRelativeTo(null);

            jframe.requestFocus();
            addEventListeners();
        });
        System.out.println(this.loggedInEmployee == null);
    }

    public void addEventListeners() {
        loginButton.addActionListener(e -> {
            SwingWorker<Employee, Void> worker = new SwingWorker<Employee, Void>() {
                @Override
                protected Employee doInBackground() throws Exception {
                    String username = usernameField.getText();
                    char[] password = passwordField.getPassword();
                    return findEmployee(username, new String(password), employees);
                }

                @Override
                protected void done() {
                    try {
                        loggedInEmployee = get();
                        if (loggedInEmployee != null) {
                            JOptionPane.showMessageDialog(null, "Login Successful! Welcome, " + loggedInEmployee.getName());
                            loginLatch.countDown();
                        } else {
                            JOptionPane.showMessageDialog(null, "Login failed. Please check your credentials.");
                        }
                    } catch (InterruptedException | ExecutionException ex) {
                        ex.printStackTrace();
                    }
                }
            };

            worker.execute();
        });
    }

    private Employee findEmployee(String username, String password, ArrayList<Employee> employees) {
        for (Employee employee : employees) {
            System.out.println("Login successful for: " + employee.getUsername());
            if (employee.login(username, password)) {
                return employee;
            }
        }
        return null;
    }

    public Employee awaitLogin() {
        try {
            loginLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return loggedInEmployee;
    }

    public Employee getLoggedInEmployee() {
        System.out.println("returning " + this.loggedInEmployee);
        return loggedInEmployee;
    }
}
