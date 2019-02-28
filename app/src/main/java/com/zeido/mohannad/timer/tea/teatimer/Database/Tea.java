package com.zeido.mohannad.timer.tea.teatimer.Database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.io.Serializable;

@Entity(tableName = "teas")
public class Tea implements Serializable {
//    @PrimaryKey(autoGenerate = true)
//    private String teaID;

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "name")
    private String teaName;

    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "time")
    private long brewingTime;

    @ColumnInfo(name = "temperature")
    private int brewingTemperature;

    @ColumnInfo(name = "image")
    private String image;


    public Tea(String teaName, String description, long brewingTime, int brewingTemperature, String image) {
        this.teaName = teaName;
        this.description = description;
        this.brewingTime = brewingTime;
        this.brewingTemperature = brewingTemperature;
        this.image = image;
    }

//    public String getTeaID() {
//        return teaID;
//    }

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

    public long getBrewingTime() {
        return brewingTime;
    }

    public void setBrewingTime(long brewingTime) {
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
