# Task Scheduling System

## Overview

The project aims to develop a GUI-based Task Scheduling System in Java that allows employees and management to efficiently manage their tasks based on priority, thereby enhancing productivity and organization. There will be two types of employees: Managers and Team Members.
NOTE: This software contains no password encryption and authnticates by simple string comparison.
## System Functionality

The software will operate on two levels:

### Management Level

- **Open Tasks:** Management can create "open" tasks that are viewable by employees based on associated groups. Team members or teams can voluntarily accept these tasks.
  
- **Assigned Tasks:** Management can assign tasks to teams or individual team members.

### Member Level

- Team members can accept open tasks.
  
- Team members can optionally add subtasks underneath their accepted tasks to track progress.
  
- Some managers may also be team members.

## Teams and Tasks

- **Teams:** Teams will be created by management, and contain an ArrayList of members and tasks. (Note that this differs from the original description, wherein "groups" took this role in addition to their current role regarding visibility). 

- **Tasks:** Tasks will include the following information:
  - Priority level (Low, Medium, High)
  - Description
  - Expected completion time
  - Date uploaded
  - Associated groups that can view and accept the tasks

## Groups

- **Groups**: Groups will contain an ArrayList of associated Teams and Team Members.

## Subtasks

- Subtasks will be identical to tasks, with additional optional fields.
  
- They may contain an associated percentage, representing the percentage of the overall task that the subtask makes up.
  
- Subtasks may also contain associated team members to whom the team may designate subtasks.

## Project Structure

- The project consists of GUI components for efficient interaction.

## Getting Started

1. Clone the repository.
2. Open the project in your preferred Java development environment.
3. Build and run the application.

## Contributing

Feel free to contribute by submitting bug reports, feature requests, or pull requests.

