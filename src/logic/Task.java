package logic;
import java.util.Date;
import java.util.List;

public class Task {
    private PriorityLevel priority;
    private String description;
    private int expectedCompletionTime; // in hours
    private Date dateUploaded;
    private List<String> groups; // Associated groups that can view and accept the task
    static int taskCount;
    private String id;
    
    boolean isOpen; //true if task is open to be taken, false if must be assigned by management
    boolean isComplete;
    
    /**
     * Throws error if constructor attempted without fields
     */
    Task() {
        throw new IllegalStateException("Task must be initialized");
    }
    
    /**
     * Sets all fields of task
     * @param priority
     * @param description
     * @param expectedCompletionTime
     * @param dateUploaded
     * @param groups
     */
    public Task(PriorityLevel priority, String description, int expectedCompletionTime, Date dateUploaded, List<String> groups, boolean isOpen) {
        this.priority = priority;
        this.description = description;
        this.expectedCompletionTime = expectedCompletionTime;
        this.dateUploaded = dateUploaded;
        this.groups = groups;
        this.id = "TK" + String.valueOf(taskCount++);
        this.isComplete = false;
        this.isOpen = isOpen;
    }
    
    public String getId() {
    	return this.id;
    }
    
    public PriorityLevel getPriority() {
        return priority;
    }

    public String getDescription() {
        return description;
    }

    public int getExpectedCompletionTime() {
        return expectedCompletionTime;
    }

    public Date getDateUploaded() {
        return dateUploaded;
    }

    public List<String> getGroups() {
        return groups;
    }

    // Setters
    public void setPriority(PriorityLevel priority) {
        this.priority = priority;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setExpectedCompletionTime(int expectedCompletionTime) {
        this.expectedCompletionTime = expectedCompletionTime;
    }

    public void setDateUploaded(Date dateUploaded) {
        this.dateUploaded = dateUploaded;
    }

    public void setGroups(List<String> groups) {
        this.groups = groups;
    }
    
    public void setComplete() { 
    	this.isComplete = true;
    }
    
    @Override
    public String toString() {
        return String.format("Task ID: %s%nPriority: %s%nDescription: %s%nExpected Completion Time: %d hours%nDate Uploaded: %s%nGroups: %s%nIs Open: %b%nIs Complete: %b",
                             getId(), getPriority(), getDescription(), getExpectedCompletionTime(),
                             getDateUploaded(), getGroups(), isOpen, isComplete);
    }
}

class Subtask extends Task {
    private double percentage; // Percentage of the overall task
    private List<String> teamMembers; // Team members assigned to the subtask

    // Constructor
    public Subtask(PriorityLevel priority, String description, int expectedCompletionTime, Date dateUploaded, List<String> groups,
                   double percentage, List<String> teamMembers, boolean isOpen) {
        super(priority, description, expectedCompletionTime, dateUploaded, groups, isOpen);
        this.percentage = percentage;
        this.teamMembers = teamMembers;
    }

    // Getters
    public double getPercentage() {
        return percentage;
    }

    public List<String> getTeamMembers() {
        return teamMembers;
    }

    // Setters
    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }

    public void setTeamMembers(List<String> teamMembers) {
        this.teamMembers = teamMembers;
    }

    public String toString() {
        return String.format("%s%nPercentage: %.2f%%%nTeam Members: %s",
                             super.toString(), getPercentage(), getTeamMembers());
    }
}

// Enum for priority levels
enum PriorityLevel {
    LOW,
    MEDIUM,
    HIGH
}
