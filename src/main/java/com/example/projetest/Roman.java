package com.example.projetest;

public class Roman extends Livre {
    public Roman(int id, String titre, String auteur) {
        super(id, titre, auteur, "Roman");
    }

    @Override
    public String toString() {
        return "Roman{" +
                "id=" + getId() +
                ", titre='" + getTitre() + '\'' +
                ", auteur='" + getAuteur() + '\'' +
                ", categorie='" + getCategorie() + '\'' +
                '}';
    }
}