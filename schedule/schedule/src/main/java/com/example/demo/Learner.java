package com.example.demo;

import javax.persistence.*;

@Entity
public class Learner extends User {
    @Column(name="companyName")
    private String companyName;

    @Column(name="skillLevel")
    private String skillLevel;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getskillLevel() {
        return skillLevel;
    }

    public void setskillLevel(String skillLevel) {
        this.skillLevel = skillLevel;
    }
}
