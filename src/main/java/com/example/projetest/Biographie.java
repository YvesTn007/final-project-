package com.example.projetest;

public class Biographie extends Livre {
    public Biographie(int id, String titre, String auteur, String categorie) {
        super(id, titre, auteur, categorie);
    }

    @Override
    public String toString() {
        return "Biographie{" +
                "id=" + getId() +
                ", titre='" + getTitre() + '\'' +
                ", auteur='" + getAuteur() + '\'' +
                ", categorie='" + getCategorie() + '\'' +
                '}';
    }
}