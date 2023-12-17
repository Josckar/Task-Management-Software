package logic;

import java.io.Serializable;

public class Manager extends Employee implements Serializable{

	private static final long serialVersionUID = 1L;
	private boolean isTeamMember;
	   public Manager(String username, String password) throws BlankFieldException {
		   super(username, password);
		   this.employeeID = "MN" + this.employeeID;
		   this.isTeamMember = false;
	    }
	    
	    public Manager(String username, String password, String name) throws BlankFieldException {
	    	super(username, password, name);
	    	this.employeeID = "MN" + this.employeeID;
	    	this.isTeamMember = false;
	    }
	    
	    Manager(String username, String password, String name, boolean isTeamMember) throws BlankFieldException {
	    	super(username, password, name);
	    	this.employeeID = "MN" + this.employeeID;
	    	this.isTeamMember = isTeamMember;
	    }
	    
	    @Override
	    public String toString(){
	    	String role = "Manager ";
	    	if(isTeamMember) {
	    		role = role + "and Team Member s";
	    	}
	    	return role + super.toString();
	    }
	    
	    public boolean isTeamMember() {
	    	return isTeamMember;
	    }
}
