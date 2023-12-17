package logic;

import java.io.Serializable;
import java.util.ArrayList;

public class Team implements Serializable {

	private static final long serialVersionUID = 1L;
	static int teamCount;
	String id;
	ArrayList<Employee> members;
	Employee teamLeader;
	String name, description;
	ArrayList<Task> tasks;
	int maxSize;
	public ArrayList<String> groups;
	
	public Team() {
        throw new IllegalStateException("Team must be initialized");
	}
	
	public Team(String name) {
		this.name = name;
		this.id = "TM" + String.valueOf(teamCount++);
		this.groups = new ArrayList<String>();
	}
	
	Team(String name, Employee teamLeader, ArrayList<Employee> members) {
		this.name = name;
		this.teamLeader = teamLeader;
		this.members = members;
		this.id = "TM" + String.valueOf(teamCount++);
	}
	Team(String name, String description, Employee teamLeader, ArrayList<Employee> members) {
		this.name = name;
		this.teamLeader = teamLeader;
		this.members = members;
		this.description = description;
		this.id = "TM" + String.valueOf(teamCount++);
	}
	
	public void addGroup(Group group) {
		groups.add(group.getID());
	}
	
	void setMembers(ArrayList<Employee> members) {
		if (this.maxSize < members.size()) {
			throw new IllegalStateException("Amount of members exceeds max size");
		}
		this.members = members;
	}
	
	public boolean addMember(Employee employee) {
		if (this.maxSize == members.size()) {
			throw new IllegalStateException("Maximum team size reached");
		}
		for (Employee member: members) {
			if(member.getID() == employee.getID()) {
				return false;
			}
		}
		this.members.add(employee);
		return true;
	}
	
	void addMembers(ArrayList<Employee> newMembers) {
		if (this.maxSize < this.members.size() + newMembers.size()) {
			throw new IllegalStateException("Amount of members exceeds max size");
		}
		this.members.addAll(newMembers);
	}
	
	public ArrayList<Employee> getMembers(){
		return members;
	}
	
	void setLeader(Employee newLeader) {
		this.teamLeader = newLeader;
	}
	
	public Employee getLeader() {
		return this.teamLeader;
	}
	
	void setDesc(String description) {
		this.description = description;
	}
	
	public String getDesc() {
		return this.description;
	}
	
	void setMaxSize(int size) {
		this.maxSize = size;
	}
	
	int getMaxSize() {
		return this.maxSize;
	}
	
	public String getID() {
		return this.id;
	}
	
	@Override
	public String toString(){
	    StringBuilder result = new StringBuilder();
	    
	    result.append(String.format("Team Name: %s%n", name));
	    
	    if (description != null) {
	        result.append(String.format("Description: %s%n", description));
	    }
	    
	    if (teamLeader != null) {
	        result.append(String.format("Team Leader: %s%n", teamLeader.getName()));
	    }
	    
	    result.append("Members:\n");
	    for (Employee member : members) {
	        result.append(String.format("- %s%n", member.getName()));
	    }

	    return result.toString();
	}
	
	
}
