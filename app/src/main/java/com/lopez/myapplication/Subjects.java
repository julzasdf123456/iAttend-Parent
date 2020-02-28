package com.lopez.myapplication;

public class Subjects {

    private String subject, description, schoolyear;

    public Subjects() {};

    public Subjects(String subject, String description, String year) {
        this.subject = subject;
        this.description = description;
        this.schoolyear = year;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSchoolyear() {
        return schoolyear;
    }

    public void setSchoolyear(String schoolyear) {
        this.schoolyear = schoolyear;
    }
}
