package com.example.jobagency;

public class Job {
    //set up the variables
    private String title;
    private String type;
    private String location;
    private String education;
    private String experience;
    private String Salary;

// Getters and setters omitted for brevity
    public Job(String title, String type, String location, String education, String experience, String Salary) {
        this.title = title;
        this.type = type;
        this.location = location;
        this.education = education;
        this.experience = experience;
        this.Salary = Salary;

    }


    //setters and getters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }


    public void setType(String type) {
        this.type = type;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public void setSalary(String type) {
        this.Salary = Salary;
    }

    public String getSalary() {
        return Salary;
    }
}