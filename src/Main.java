import logic.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    static ArrayList<Employee> employees = new ArrayList<>();
    static ArrayList<Task> tasks = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the Employee Task Manager CLI!");

        while (true) {
            System.out.println("\nSelect an option:");
            System.out.println("1. Create an account");
            System.out.println("2. Login");
            System.out.println("3. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    createAccount(scanner);
                    break;
                case 2:
                    login(scanner);
                    break;
                case 3:
                    System.out.println("Exiting the Employee Task Manager CLI. Goodbye!");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        }
    }

    private static void createAccount(Scanner scanner) {
        System.out.println("\nCreating an account:");

        System.out.print("Enter username: ");
        String username = scanner.nextLine();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        System.out.print("Enter name: ");
        String name = scanner.nextLine();

        try {
            // Choose whether to create a Manager or TeamMember account
            System.out.println("Select account type:");
            System.out.println("1. Manager");
            System.out.println("2. Team Member");
            int accountTypeChoice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            Employee employee;
            if (accountTypeChoice == 1) {
                employee = new Manager(username, password, name);
            } else if (accountTypeChoice == 2) {
                employee = new TeamMember(username, password, name);
            } else {
                System.out.println("Invalid account type choice.");
                return;
            }

            employees.add(employee);
            System.out.println("Account created successfully: " + employee);
        } catch (Employee.BlankFieldException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void login(Scanner scanner) {
        System.out.println("\nLogging in:");

        System.out.print("Enter username: ");
        String username = scanner.nextLine();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        // Check if the user exists in the employees array
        Employee employee = null;
        for (Employee emp : employees) {
            if (emp.login(username, password)) {
                employee = emp;
                break;
            }
        }

        if (employee != null) {
            System.out.println("Login successful. Welcome, " + employee.getName() + "!");
            editSettings(scanner, employee);
        } else {
            System.out.println("Invalid username or password. Login failed.");
        }
    }

    private static void editSettings(Scanner scanner, Employee employee) {
        System.out.println("\nEditing settings:");

        while (true) {
            System.out.println("Select setting to edit:");
            System.out.println("1. Change Username");
            System.out.println("2. Change Password");
            System.out.println("3. Change Name");
            System.out.println("4. Back to Main Menu");

            int settingChoice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (settingChoice) {
                case 1:
                    System.out.print("Enter new username: ");
                    String newUsername = scanner.nextLine();
                    try {
                        employee.changeUsername(newUsername);
                        System.out.println("Username changed successfully. New details: " + employee);
                    } catch (Employee.BlankFieldException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;
                case 2:
                    System.out.print("Enter new password: ");
                    String newPassword = scanner.nextLine();
                    try {
                        employee.changePassword(newPassword);
                        System.out.println("Password changed successfully.");
                    } catch (Employee.BlankFieldException | Employee.MismatchException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;
                case 3:
                    System.out.print("Enter new name: ");
                    String newName = scanner.nextLine();
                    try {
                        employee.setName(newName);
                        System.out.println("Name changed successfully. New details: " + employee);
                    } catch (Employee.BlankFieldException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;
                case 4:
                    return; // Back to main menu
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        }
    }
}
