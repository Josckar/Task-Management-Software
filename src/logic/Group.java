package logic;
import java.util.ArrayList;

public class Group {
    static int groupCount;
    String id;
    String name, description;
    ArrayList<Team> teams;
    ArrayList<Employee> employees;

    Group() {
        this.teams = new ArrayList<>();
        this.employees = new ArrayList<>();
        this.id = "GR" + String.valueOf(groupCount++);
        System.out.println("New group created with id: " + id);
    }

    Group(String name, ArrayList<Team> teams, ArrayList<Employee> employees) {
        this.name = name;
        this.teams = teams;
        this.employees = employees;
        this.id = "GR" + String.valueOf(groupCount++);
    }

    // Setters
    public void setTeams(ArrayList<Team> teams) {
        this.teams = teams;
    }

    public void setEmployees(ArrayList<Employee> employees) {
        this.employees = employees;
    }

    public boolean addTeam(Team team) {
    	if(!this.checkMember(team)) {
    		this.teams.add(team);
    		return true;
    	}
    	return false;
    }

    public boolean addEmployee(Employee employee) {
    	if(!this.checkMember(employee)) {
    		this.employees.add(employee);
    		return true;
    	}
    	return false;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Getters
    public ArrayList<Team> getTeams() {
        return teams;
    }

    public ArrayList<Employee> getEmployees() {
        return employees;
    }

    public String getID() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }
    
    public boolean checkMember(Employee employee) {
        String employeeId = employee.getID();
        for (Employee member : employees) {
            if (member.getID().equals(employeeId)) {
                return true; // Employee is a member
            }
        }
        return false; // Employee is not a member
    }

    // Method to check if a team is a member based on team ID
    public boolean checkMember(Team team) {
        String teamId = team.getID();
        for (Team member : teams) {
            if (member.getID().equals(teamId)) {
                return true; // Team is a member
            }
        }
        return false; // Team is not a member
    }


    // toString method
    @Override
    public String toString() {
        return String.format("Group ID: %s%nName: %s%nDescription: %s%nTeams: %s%nEmployees: %s",
                             id, name, description, teams, employees);
    }
}
