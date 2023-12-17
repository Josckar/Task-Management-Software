import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;
import logic.Employee; // Import your Employee class or the appropriate package
import logic.Employee.BlankFieldException;
import logic.*;

public class SwingSample {
	JFrame jframe;
	JButton loginButton;
	JTextField usernameField; // Rename 'email' to 'usernameField'
	JPasswordField passwordField;
	JLabel usernameError;
	JLabel passwordError;

	public SwingSample() throws IOException {
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

		jframe.setContentPane(new JPanel() {
		});
		init();
	}

	public void init() {
		usernameField.setPreferredSize(new Dimension(250, 35));
		passwordField.setPreferredSize(new Dimension(250, 35));
		loginButton.setPreferredSize(new Dimension(250, 35));
		loginButton.setBackground(new Color(66, 245, 114));
		loginButton.setFocusPainted(false);

		usernameField.setText("Enter your username"); // Adjusted the placeholder text
		usernameField.setForeground(Color.gray);
		passwordField.setText("Enter your password");
		passwordField.setForeground(Color.gray);
		passwordField.setEchoChar((char) 0);

		usernameError.setFont(new Font("SansSerif", Font.BOLD, 11));
		usernameError.setForeground(Color.RED);

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
		addEventListeners(null);
	}

	public void addEventListeners(ArrayList<Employee> employees) {
		loginButton.addActionListener(e -> {
			String username = usernameField.getText();
			char[] password = passwordField.getPassword();

			Employee loggedInEmployee = findEmployee(username, new String(password), employees);

			if (loggedInEmployee != null) {
				// If login is successful, display a message or perform actions
				JOptionPane.showMessageDialog(null, "Login Successful! Welcome, " + loggedInEmployee.getName());
			} else {
				// Display an error message or handle unsuccessful login
				JOptionPane.showMessageDialog(null, "Login failed. Please check your credentials.");
			}
		});

		// Other focus listeners and validations can remain unchanged
	}

	private Employee findEmployee(String username, String password, ArrayList<Employee> employees) {
		// Implement your logic to find the employee based on the provided credentials
		// You can use the 'employees' list for this purpose
		for (Employee employee : employees) {
			if (employee.login(username, password)) {
				return employee;
			}
		}
		return null; // Return null if no matching employee is found
	}

	// Other methods remain unchanged

	public static void main(String args[]) throws BlankFieldException {
		try {
			ArrayList<Employee> employees = new ArrayList<Employee>();
			/* Populate the list with your Employee objects */;
			employees.add(new Manager("bob", "bob"));
			SwingSample swingSample = new SwingSample();
			swingSample.addEventListeners(employees);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
