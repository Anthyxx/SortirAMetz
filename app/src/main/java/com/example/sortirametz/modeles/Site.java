package com.example.sortirametz.modeles;

public class Site {
    private int id;
    private String name;
    private double latitude;
    private double longitude;
    private String adresse;
    private String categorie;
    private String resume;

    public Site(String name, double latitude, double longitude, String adresse, String categorie, String resume) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.adresse = adresse;
        this.categorie = categorie;
        this.resume = resume;
    }

    public Site(int id, String name, double latitude, double longitude, String adresse, String categorie, String resume) {
        this.id = id;
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

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getAdresse() {
        return adresse;
    }

    public String getCategorie() {
        return categorie;
    }

    public String getResume() {
        return resume;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }
}
