package logic;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public abstract class Employee implements Serializable {

	private static final long serialVersionUID = 1L;
	String employeeID, name;
	static int employeeCount = 0;
	String username, password;
	String employeeType;
	ArrayList<Task> tasks;
	public ArrayList<String> groups, teams; //stores ids of groups employee is a member of
	
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
    	this.groups = new ArrayList<String>();
    	this.teams = new ArrayList<String>();
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
    	this.groups = new ArrayList<String>();
    	this.teams = new ArrayList<String>();
    }
    
    /**
     * Checks if task is in employees tasks then adds
     * @param newTask task to be added 
     * @return false if task is already in employee tasks, true otherwise
     */
    public boolean addTask(Task newTask) {
   
    	for(Task task : this.tasks) {
    		if(task.getID().equals( newTask.getID() ) ) {
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
    
    public void addGroup(Group group) {
    	this.groups.add(group.getID());
    }
    public void addTeam(Team team) {
    	this.teams.add(team.getID());
    }
    
    public ArrayList<String> getGroups() {
    	return this.groups;
    }
    
    public ArrayList<String> getTeams() {
    	return this.teams;
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

    public void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
    }

    public void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
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

