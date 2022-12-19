package com.example.sortirametz.modeles;

public class Site {
    private int id;
    private String name;
    private float latitude;
    private float longitude;
    private String adresse;
    private int categorie;
    private String resume;

    public Site(String name, float latitude, float longitude, String adresse, int categorie, String resume) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.adresse = adresse;
        this.categorie = categorie;
        this.resume = resume;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public float getLatitude() {
        return latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public String getAdresse() {
        return adresse;
    }

    public int getCategorie() {
        return categorie;
    }

    public String getResume() {
        return resume;
    }
}
