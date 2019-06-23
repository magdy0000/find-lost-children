package com.example.findlostchildren.Models;

import java.util.ArrayList;

public class VictimModel {

    private ArrayList<String> imagesURL;
    private String name, city, age, number, description;


    public VictimModel(ArrayList<String> imagesURL, String name, String city, String age, String number, String description) {
        this.imagesURL = imagesURL;
        this.name = name;
        this.city = city;
        this.age = age;
        this.number = number;
        this.description = description;
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
}
