package com.example.sortirametz.modeles;

public class Categorie {
    private int id;
    private String name;

    public Categorie(String name) {
        this.name = name;
    }

    public Categorie(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Categorie() {

    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
