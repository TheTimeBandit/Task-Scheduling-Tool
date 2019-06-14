package tomastakacs.taskschedulingtool;

import java.io.Serializable;

public class Task implements Serializable {

    private String title = null;
    private String notes = null;
    private String deadlineDate = null;
    private String deadlineTime = null;
    private boolean completed = false;

    public Task() {}

    public String getTitle() { return title;}
    public void setTitle(String title) {this.title = title;}

    public String getNotes() { return notes; }
    public void setNotes(String notes) {this.notes = notes;}

    public String getDeadlineDate() { return deadlineDate; }
    public void setDeadlineDate(String deadlineDate) {this.deadlineDate = deadlineDate; }

    public String getDeadlineTime(){return deadlineTime;}
    public void setDeadlineTime(String deadlineTime) {this.deadlineTime = deadlineTime; }

    public boolean isCompleted() { return completed; }
    public void setCompleted(boolean completed) { this.completed = completed; }


    // NewTask constructor
    public Task(String title, String notes, String deadlineDate, String deadlineTime){
        this.title = title;
        this.notes = notes;
        this.deadlineDate = deadlineDate;
        this.deadlineTime = deadlineTime;
    }

    public Task(String title, String notes, String deadlineDate, String deadlineTime, boolean completed){
        this.title = title;
        this.notes = notes;
        this.deadlineDate = deadlineDate;
        this.deadlineTime = deadlineTime;
        this.completed = completed;
    }


}
