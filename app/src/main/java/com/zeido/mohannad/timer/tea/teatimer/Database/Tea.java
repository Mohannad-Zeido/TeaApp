package com.zeido.mohannad.timer.tea.teatimer.Database;

import java.io.Serializable;

public class Tea implements Serializable {
    private String teaID;
    private String teaName;
    private String description;
    private int brewingTime;
    private int brewingTemperature;
    private String image;


    public Tea(String teaName, String description, int brewingTime, int brewingTemperature, String image) {
        this.teaName = teaName;
        this.description = description;
        this.brewingTime = brewingTime;
        this.brewingTemperature = brewingTemperature;
        this.image = image;
    }

    public String getTeaID() {
        return teaID;
    }

    public String getTeaName() {
        return teaName;
    }

    public void setTeaName(String teaName) {
        this.teaName = teaName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getBrewingTime() {
        return brewingTime;
    }

    public void setBrewingTime(int brewingTime) {
        this.brewingTime = brewingTime;
    }

    public int getBrewingTemperature() {
        return brewingTemperature;
    }

    public void setBrewingTemperature(int brewingTemperature) {
        this.brewingTemperature = brewingTemperature;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Tea{" +
                "teaName='" + teaName + '\'' +
                ", description='" + description + '\'' +
                ", brewingTime=" + brewingTime +
                ", brewingTemperature=" + brewingTemperature +
                ", image='" + image + '\'' +
                '}';
    }
}
