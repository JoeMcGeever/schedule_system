package com.example.demo;

import javax.persistence.*;

@Entity
public class Learner extends User {
    @Column(name="companyname")
    private String companyName;

    @Column(name="skilllevel")
    private String skillLevel;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getSkillLevel() {
        return skillLevel;
    }

    public void setSkillLevel(String skillLevel) {
        this.skillLevel = skillLevel;
    }
}
