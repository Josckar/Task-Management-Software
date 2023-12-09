package logic;

public class Manager extends Employee {
	private boolean isTeamMember;
	   public Manager(String username, String password) throws BlankFieldException {
		   super(username, password);
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
	    	this.isTeamMember = true;
	    }
	    
	    @Override
	    public String toString(){
	    	String role = "Manager";
	    	if(isTeamMember) {
	    		role = role + "and Team Member";
	    	}
	    	return role + super.toString();
	    }
	    
	    public boolean isTeamMember() {
	    	return isTeamMember;
	    }
}
