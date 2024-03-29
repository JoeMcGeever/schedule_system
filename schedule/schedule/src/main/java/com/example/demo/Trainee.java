package com.example.demo;

import javax.persistence.*;

@Entity
public class Trainee extends User {
    @Column(name="trainerbio") //case insensitive, but trainerBio does convert to trainer_bio
    private String trainerBio;

    @Override
    public String getDescription() {
        return trainerBio;
    }

    public void setBio(String trainerBio) {
        this.trainerBio = trainerBio;
    }
}

