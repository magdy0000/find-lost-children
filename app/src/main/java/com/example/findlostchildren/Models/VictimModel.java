package com.example.findlostchildren.Models;

import java.util.ArrayList;

public class VictimModel {

    private ArrayList<String> imagesURL;
    private String userId, sourceName, postTime, name, city, age, number, description, deviceToken;

    public VictimModel() {

    }

    public VictimModel(String userId, String sourceName, String postTime, ArrayList<String> imagesURL, String name, String city, String age, String number, String description, String deviceToken) {
        this.userId = userId;
        this.sourceName = sourceName;
        this.postTime = postTime;
        this.imagesURL = imagesURL;
        this.name = name;
        this.city = city;
        this.age = age;
        this.number = number;
        this.description = description;
        this.deviceToken = deviceToken;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public String getPostTime() {
        return postTime;
    }

    public void setPostTime(String postTime) {
        this.postTime = postTime;
    }

    public ArrayList<String> getImagesURL() {
        return imagesURL;
    }

    public void setImagesURL(ArrayList<String> imagesURL) {
        this.imagesURL = imagesURL;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }
}
