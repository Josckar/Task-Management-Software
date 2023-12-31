package logic;

import java.io.Serializable;

public class TeamMember extends Employee implements Serializable {

	private static final long serialVersionUID = 1L;

	public TeamMember(String username, String password) throws BlankFieldException {
		   super(username, password);
	    }
	    
	    public TeamMember(String username, String password, String name) throws BlankFieldException {
	    	super(username, password, name);
	    	this.employeeID = "TM" + this.employeeID;
	    }
	    
	    @Override
	    public String toString(){
	    	return "Employee" + super.toString();
	    }
	    
}
