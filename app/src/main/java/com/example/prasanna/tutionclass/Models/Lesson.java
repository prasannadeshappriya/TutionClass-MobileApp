package com.example.prasanna.tutionclass.Models;

/**
 * Created by prasanna on 8/14/17.
 */

public class Lesson {
    private long id;
    private String date;
    private int student_count;
    private String grade;
    private String name;
    private String comments;
    private long user_id;

    public Lesson(long user_id, String name, String comments, String grade, int student_count, String date, long id) {
        this.id = id;
        this.user_id = user_id;
        this.name = name;
        this.comments = comments;
        this.grade = grade;
        this.student_count = student_count;
        this.date = date;
    }

    public Lesson(long user_id, String name, String comments, String grade, int student_count, String date) {
        this.user_id = user_id;
        this.name = name;
        this.comments = comments;
        this.grade = grade;
        this.student_count = student_count;
        this.date = date;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getStudent_count() {
        return student_count;
    }

    public void setStudent_count(int student_count) {
        this.student_count = student_count;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }
}
