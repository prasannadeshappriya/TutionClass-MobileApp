package com.example.prasanna.tutionclass.Models;

/**
 * Created by prasanna on 8/14/17.
 */

public class Feedback {
    private long id;
    private long event_id;
    private String flag;
    private long user_id;

    public Feedback(long id, long event_id, String flag, long user_id) {
        this.id = id;
        this.event_id = event_id;
        this.flag = flag;
        this.user_id = user_id;
    }

    public Feedback(long event_id, String flag, long user_id) {
        this.event_id = event_id;
        this.flag = flag;
        this.user_id = user_id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getEvent_id() {
        return event_id;
    }

    public void setEvent_id(long event_id) {
        this.event_id = event_id;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }
}
