package com.example.androidinsuranceproject;

public class Insurance {
    private String id;
    private String name;
    private String carModel;
    private String carYear;

    public Insurance(String name, String carModel, String carYear) {
        this.name = name;
        this.carModel = carModel;
        this.carYear = carYear;
    }

    public Insurance() {
        // Paraméter nélküli konstruktor
    }

    public String getName() {
        return name;
    }

    public String getCarModel() {
        return carModel;
    }

    public String getCarYear() {
        return carYear;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
