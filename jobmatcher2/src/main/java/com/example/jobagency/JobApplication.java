package com.example.jobagency;

public class JobApplication {
    private String name;
    private String email;
    private String experience;
    private String education;

    public JobApplication(String name, String email, String experience, String education) {
        this.name = name;
        this.email = email;
        this.experience = experience;
        this.education = education;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }
}
