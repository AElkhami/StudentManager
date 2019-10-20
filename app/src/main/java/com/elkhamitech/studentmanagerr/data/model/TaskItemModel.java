package com.elkhamitech.studentmanagerr.data.model;

/**
 * Created by ElkhamiTech on 2/12/2018.
 */

public class TaskItemModel {

    private long row_id;
    private String itemName;
    private String isDone;
    private long task_item;

    public TaskItemModel() {
    }

    public long getRow_id() {
        return row_id;
    }

    public void setRow_id(long row_id) {
        this.row_id = row_id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getIsDone() {
        return isDone;
    }

    public void setIsDone(String done) {
        isDone = done;
    }

    public long getTask_item() {
        return task_item;
    }

    public void setTask_item(long task_item) {
        this.task_item = task_item;
    }
}
