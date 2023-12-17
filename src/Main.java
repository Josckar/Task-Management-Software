import javax.swing.*;
import javax.swing.border.LineBorder;

import logic.*;
import logic.Employee.BlankFieldException;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.concurrent.*;

public class Main {
	private JFrame frame;

	static ArrayList<Employee> employees;
	static ArrayList<Task> tasks;
	static ArrayList<Team> teams;
	static ArrayList<Group> groups;
	private Employee loggedInEmployee;

	private JTextField usernameField;
	private JPasswordField passwordField;

	public Main() {

	}

	private void openLoginGUI() throws IOException {
		frame = new JFrame("Login Form");

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

		JButton loginButton = new JButton("LOGIN") {
			public void updateUI() {
				super.updateUI();
				setOpaque(false);
				setBorder(new LineBorder(Color.BLACK, 1, true));
			}
		};

		JLabel usernameError = new JLabel();
		JLabel passwordError = new JLabel();

		frame.setContentPane(new JPanel() {
		});

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

		usernameField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if (usernameField.getText().equals("Enter your username")) {
					usernameField.setText("");
					usernameField.setForeground(Color.black);
				}
			}

			@Override
			public void focusLost(FocusEvent e) {
				if (usernameField.getText().isEmpty()) {
					usernameField.setText("Enter your username");
					usernameField.setForeground(Color.gray);
				}
			}
		});

		passwordField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if (new String(passwordField.getPassword()).equals("Enter your password")) {
					passwordField.setText("");
					passwordField.setForeground(Color.black);
					passwordField.setEchoChar('*');
				}
			}

			@Override
			public void focusLost(FocusEvent e) {
				if (new String(passwordField.getPassword()).isEmpty()) {
					passwordField.setText("Enter your password");
					passwordField.setForeground(Color.gray);
					passwordField.setEchoChar((char) 0);
				}
			}
		});

		usernameField.getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "submit");
		usernameField.getActionMap().put("submit", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				loginButton.doClick();
			}
		});

		// Add key bindings for passwordField
		passwordField.getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "submit");
		passwordField.getActionMap().put("submit", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				loginButton.doClick();
			}
		});

		// Add key bindings for loginButton
		loginButton.getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "submit");
		loginButton.getActionMap().put("submit", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				handleLogin();
			}
		});

		usernameError.setFont(new Font("SansSerif", Font.BOLD, 11));
		usernameError.setForeground(Color.RED);

		frame.setLayout(new GridBagLayout());

		Insets textInsets = new Insets(10, 10, 5, 10);
		Insets buttonInsets = new Insets(20, 10, 10, 10);
		Insets errorInsets = new Insets(0, 20, 0, 0);

		GridBagConstraints input = new GridBagConstraints();
		input.anchor = GridBagConstraints.CENTER;
		input.insets = textInsets;
		input.gridy = 1;
		frame.add(usernameField, input);

		input.insets = errorInsets;
		input.gridy = 2;
		input.anchor = GridBagConstraints.WEST;
		frame.add(usernameError, input);

		input.insets = textInsets;
		input.gridy = 3;
		frame.add(passwordField, input);

		input.insets = errorInsets;
		input.gridy = 4;
		input.anchor = GridBagConstraints.WEST;
		frame.add(passwordError, input);

		input.insets = buttonInsets;
		input.anchor = GridBagConstraints.WEST;
		input.gridx = 0;
		input.gridy = 5;
		frame.add(loginButton, input);

		frame.setSize(500, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);

		frame.requestFocus();
	}

	private void handleLogin() {
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
						frame.dispose();
						System.out.println("Main: Employee logged in: " + loggedInEmployee.getName());

						SwingUtilities.invokeLater(() -> {
							createAccountGUI();
						});
					} else {
						System.out.println("Main: Login unsuccessful");
					}
				} catch (InterruptedException | ExecutionException ex) {
					ex.printStackTrace();
				}
			}
		};
		worker.execute();
	}

	private Employee findEmployee(String username, String password, ArrayList<Employee> employees) {
		for (Employee employee : employees) {
			if (employee.login(username, password)) {
				System.out.println("Login successful for: " + employee.getUsername());
				return employee;
			}
		}
		return null;
	}

	public static void main(String[] args) throws Employee.BlankFieldException {
		loadArrays();

		SwingUtilities.invokeLater(() -> {
			Main mainGUI = new Main();
			try {
				mainGUI.openLoginGUI();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		});

		saveArrays();
	}

	public static void updateGroup(Employee emp, Group group) {
		emp.addGroup(group);
		group.addEmployee(emp);
		saveArrays();
	}

	public static void updateGroup(Team team, Group group) {
		team.addGroup(group);
		group.addTeam(team);
		saveArrays();
	}

	public static void updateTeam(Employee emp, Team team) {
		team.addMember(emp);
		emp.addTeam(team);
		saveArrays();
	}

	public static void updateTeam(Group group, Team team) {
		team.addGroup(group);
		group.addTeam(team);
		saveArrays();
	}

	public static void updateArrMember(Employee emp) {
		for (int i = 0; i < employees.size(); i++) {
			if (employees.get(i).getID().equals(emp.getID())) {
				employees.set(i, emp);
				saveArrays();
				return;
			}
		}

	}

	public static void updateTask(Task updatedTask) {
		for (int i = 0; i < tasks.size(); i++) {
			if (tasks.get(i).getID().equals(updatedTask.getID())) {
				tasks.set(i, updatedTask);
				saveArrays();
				return;
			}
		}
	}

	public static void updateGroup(ArrayList<Group> groups, Group updatedGroup) {
		for (int i = 0; i < groups.size(); i++) {
			if (groups.get(i).getID().equals(updatedGroup.getID())) {
				groups.set(i, updatedGroup);
				saveArrays();
				return;
			}
		}
	}

	public static void updateTeam(ArrayList<Team> teams, Team updatedTeam) {
		for (int i = 0; i < teams.size(); i++) {
			if (teams.get(i).getID().equals(updatedTeam.getID())) {
				teams.set(i, updatedTeam);
				saveArrays();
				return;
			}
		}
	}

	private void createAccountGUI() {
		JFrame accountFrame = new JFrame("Employee Information");
		accountFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JTextArea taskTextArea = new JTextArea();
		taskTextArea.setEditable(false);
		taskTextArea.setFont(new Font("Arial", Font.PLAIN, 14));

		StringBuilder tasksText = new StringBuilder("Employee Information:\n");
		tasksText.append(loggedInEmployee.toString()).append("\n\nTasks (Priority - Task Name):\n");

		if (loggedInEmployee != null) {
			ArrayList<Task> employeeTasks = loggedInEmployee.getTasks();

		
			Collections.sort(employeeTasks, new Comparator<Task>() {
				@Override
				public int compare(Task task1, Task task2) {

					return task2.getPriority().compareTo(task1.getPriority());
				}
			});

			for (Task task : employeeTasks) {
				tasksText.append(task.toString());
			}
		}

		taskTextArea.setText(tasksText.toString());
		JScrollPane scrollPane = new JScrollPane(taskTextArea);

		
		
		JButton changeNameButton = new JButton("Change Name");
		JButton changeUsernameButton = new JButton("Change Username");
		JButton changePasswordButton = new JButton("Change Password");
		JButton acceptableTasksButton = new JButton("Acceptable Tasks");

	    JButton createTeamMemberButton = new JButton("Create Team Member");

	    createTeamMemberButton.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	            String username = JOptionPane.showInputDialog(frame, "Enter username for the new Team Member:");
	            if (username != null && !username.isEmpty()) {
	                char[] password = promptForPassword();
	                if (password != null) {
	                    String name = JOptionPane.showInputDialog(frame, "Enter name for the new Team Member:");
	                    if (name != null && !name.isEmpty()) {

	                        TeamMember newTeamMember = null;
							try {
								newTeamMember = new TeamMember(username, new String(password), name);
							} catch (BlankFieldException e1) {
						
								e1.printStackTrace();
							}
	                        employees.add(newTeamMember);
	                        saveArrays();
	                        JOptionPane.showMessageDialog(frame, "Team Member " + username + " created!");
	                    } else {
	                        JOptionPane.showMessageDialog(frame, "Name cannot be empty.");
	                    }
	                }
	            }
	        }
	    });
		
		acceptableTasksButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFrame acceptableTasksFrame = new JFrame("Acceptable Tasks");
				JPanel taskPanel = new JPanel(new GridLayout(0, 1));

				for (Task task : tasks) {
					if (!task.isOpen()) {
						continue; 
					}

					for (String group : task.getGroups()) {
						for (Group grp : groups) {
							if (grp.getID().equals(group)) {
								JTextArea taskTextArea = new JTextArea(task.toString());
								JButton acceptTaskButton = createAcceptTaskButton(task);
								taskPanel.add(taskTextArea);
								taskPanel.add(acceptTaskButton);
							}
						}
					}
				}

				JScrollPane scrollPane = new JScrollPane(taskPanel);
				acceptableTasksFrame.add(scrollPane);

				acceptableTasksFrame.setSize(400, 300);
				acceptableTasksFrame.setLocationRelativeTo(null);
				acceptableTasksFrame.setVisible(true);
			}
		});
		changeNameButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String newName = JOptionPane.showInputDialog(accountFrame, "Enter new name:");
				try {
					loggedInEmployee.setName(newName);
					taskTextArea.setText(loggedInEmployee.toString()); // Update the displayed info
					for (int i = 0; i < employees.size(); i++) {
						if (employees.get(i).getID().equals(loggedInEmployee.getID())) {
							employees.set(i, loggedInEmployee);
						}
					}
					saveArrays();
				} catch (Employee.BlankFieldException ex) {
					JOptionPane.showMessageDialog(accountFrame, "Error: " + ex.getMessage());
				}
			}

		});
		changeUsernameButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String newUsername = JOptionPane.showInputDialog(accountFrame, "Enter new name:");
				try {
					loggedInEmployee.changeUsername(newUsername);
					taskTextArea.setText(loggedInEmployee.toString()); // Update the displayed info
					for (int i = 0; i < employees.size(); i++) {
						if (employees.get(i).getID().equals(loggedInEmployee.getID())) {
							employees.set(i, loggedInEmployee);
						}
					}
					saveArrays();
				} catch (Employee.BlankFieldException ex) {
					JOptionPane.showMessageDialog(accountFrame, "Error: " + ex.getMessage());
				}
			}

		});

		// Add action listeners for the buttons
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(changeNameButton);
		buttonPanel.add(changeUsernameButton);
		buttonPanel.add(changePasswordButton);
		buttonPanel.add(acceptableTasksButton);
		buttonPanel.add(createTeamMemberButton);
		accountFrame.getContentPane().add(BorderLayout.CENTER, scrollPane);
		accountFrame.getContentPane().add(BorderLayout.SOUTH, buttonPanel);
		accountFrame.setSize(600, 600);
		accountFrame.setLocationRelativeTo(null);
		accountFrame.setVisible(true);
	}
	
	private char[] promptForPassword() {
	    JPasswordField passwordField = new JPasswordField();
	    int option = JOptionPane.showConfirmDialog(frame, passwordField, "Enter password:", JOptionPane.OK_CANCEL_OPTION);
	    if (option == JOptionPane.OK_OPTION) {
	        return passwordField.getPassword();
	    }
	    return null;
	}

	private JButton createAcceptTaskButton(Task task) {
		JButton acceptButton = new JButton("Accept Task " + task.getID());
		acceptButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
		
				loggedInEmployee.addTask(task);

			
				task.setOpen(false);

				updateTask(task);

				JOptionPane.showMessageDialog(frame, "Task " + task.getID() + " accepted!");
			}
		});
		return acceptButton;
	}

	@SuppressWarnings("unchecked")
	private static void loadArrays() {
		try (ObjectInputStream employeesStream = new ObjectInputStream(
				new FileInputStream("savedinfo/management/employees.ser"));
				ObjectInputStream tasksStream = new ObjectInputStream(new FileInputStream("savedinfo/tasks/tasks.ser"));
				ObjectInputStream teamsStream = new ObjectInputStream(
						new FileInputStream("savedinfo/management/teams.ser"));
				ObjectInputStream groupsStream = new ObjectInputStream(
						new FileInputStream("savedinfo/management/groups.ser"))) {

			employees = (ArrayList<Employee>) employeesStream.readObject();
			tasks = (ArrayList<Task>) tasksStream.readObject();
			teams = (ArrayList<Team>) teamsStream.readObject();
			groups = (ArrayList<Group>) groupsStream.readObject();

		} catch (FileNotFoundException e) {
			// Files do not exist, create new arrays
			employees = new ArrayList<>();
			tasks = new ArrayList<>();
			teams = new ArrayList<>();
			groups = new ArrayList<>();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	private static void saveArrays() {
		try (ObjectOutputStream employeesStream = new ObjectOutputStream(
				new FileOutputStream("savedinfo/management/employees.ser"));
				ObjectOutputStream tasksStream = new ObjectOutputStream(
						new FileOutputStream("savedinfo/tasks/tasks.ser"));
				ObjectOutputStream teamsStream = new ObjectOutputStream(
						new FileOutputStream("savedinfo/management/teams.ser"));
				ObjectOutputStream groupsStream = new ObjectOutputStream(
						new FileOutputStream("savedinfo/management/groups.ser"))) {

			employeesStream.writeObject(employees);
			tasksStream.writeObject(tasks);
			teamsStream.writeObject(teams);
			groupsStream.writeObject(groups);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
