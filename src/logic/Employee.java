package logic;

import java.util.ArrayList;

public abstract class Employee {
	String employeeID, name;
	static int employeeCount = 0;
	String username, password;
	String employeeType;
	ArrayList<Task> tasks;
	
    Employee() {
        throw new IllegalStateException("Employee must be initialized");
    }
    
    Employee(String username, String password) throws BlankFieldException {
    	
    	checkBlanks(username, "New Username cannot be blank");
    	checkBlanks(password, "New Password cannot be blank");
    	
    	this.username = username;
    	this.password = password;
    	this.employeeID = String.valueOf(employeeCount++);
    	this.tasks = new ArrayList<Task>();
    }
    
    Employee(String username, String password, String name) throws BlankFieldException {
    	checkBlanks(username, "New Username cannot be blank");
    	checkBlanks(password, "New Password cannot be blank");
    	checkBlanks(name, "Employee name cannot be blank");
    	
    	this.username = username;
    	this.password = password;
    	this.name = name;
    	this.employeeID = String.valueOf(employeeCount++);
    	this.tasks = new ArrayList<Task>();
    }
    
    /**
     * Checks if task is in employees tasks then adds
     * @param newTask task to be added 
     * @return false if task is already in employee tasks, true otherwise
     */
    public boolean addTask(Task newTask) {
   
    	for(Task task : this.tasks) {
    		if(task.getId().equals( newTask.getId() ) ) {
    			return false;
    		}
    	}
    	
    	tasks.add(newTask);
    	return true;
    }
    
    public void changeUsername(String newUsername) throws BlankFieldException {
    	checkBlanks(newUsername, "New Username cannot be blank");
    	
    	this.username = newUsername;
    }
    
    public void changePassword(String attemptPassword, String newPassword) throws MismatchException, BlankFieldException {
    	checkBlanks(newPassword, "New Password cannot be blank");
    	
    	if (!attemptPassword.equals(newPassword)) {
    		throw new MismatchException("Password mismatch");
    	}
    	
    	this.password = newPassword;
    }
    
    public void setName(String newName) throws BlankFieldException {
    	checkBlanks(newName, "Employee name cannot be blank");
    	this.name = newName;
    }
    
    public String getUsername() {
    	return username;
    }
    
    public String getName() {
    	return name;
    }
    
    public ArrayList<Task> getTasks() {
    	return this.tasks;
    }
    
    public String getID() {
    	return this.employeeID;
    }
    
    @Override
    public String toString(){
        return String.format("%s Name: %s, Username: %s with %d tasks", this.employeeID, this.name, this.username, this.tasks.size());
    }

    
    public boolean login(String username, String password) {
        return this.username.equals(username) && this.password.equals(password);
    }
	
    public class MismatchException extends Exception {
        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public MismatchException(String message) {
            super(message);
        }
    }
    
    
    public class BlankFieldException extends Exception {
        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public BlankFieldException(String message) {
            super(message);
        }
    }
    private void checkBlanks(String field, String errorMessage) throws BlankFieldException {
        if (field.trim().isEmpty()) {
            throw new BlankFieldException(errorMessage);
        }
        return;
    }


}

