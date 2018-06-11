package com.example.knoxpo.myexamples;

public class ToDoModel  {

    private String title;
    private boolean isCheck;

    public ToDoModel() {

    }

    public ToDoModel(String title, boolean isCheck) {
        this.title = title;
        this.isCheck = isCheck;
    }

    public String getTitle() {
        return title;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }
}
