package com.example.projetest;

public class ScienceFiction extends Livre {
    public ScienceFiction(int id, String titre, String auteur) {
        super(id, titre, auteur, "ScienceFiction");
    }

    @Override
    public String toString() {
        return "ScienceFiction{" +
                "id=" + getId() +
                ", titre='" + getTitre() + '\'' +
                ", auteur='" + getAuteur() + '\'' +
                ", categorie='" + getCategorie() + '\'' +
                '}';
    }
}