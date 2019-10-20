package com.elkhamitech.studentmanagerr.data.model;

/**
 * Created by ElkhamiTech on 2/12/2018.
 */

public class TaskModel {

    private long row_id;
    private String taskName;
    private String created;
    private long student_task;

    public TaskModel() {
    }

    public long getRow_id() {
        return row_id;
    }

    public void setRow_id(long row_id) {
        this.row_id = row_id;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public long getStudent_task() {
        return student_task;
    }

    public void setStudent_task(long student_task) {
        this.student_task = student_task;
    }
}
