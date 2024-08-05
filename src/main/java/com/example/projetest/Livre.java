package com.example.projetest;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Livre {
    private IntegerProperty id;
    private StringProperty titre;
    private StringProperty auteur;
    private StringProperty categorie;

    public Livre(int id, String titre, String auteur, String categorie) {
        this.id = new SimpleIntegerProperty(id);
        this.titre = new SimpleStringProperty(titre);
        this.auteur = new SimpleStringProperty(auteur);
        this.categorie = new SimpleStringProperty(categorie);
    }

    public int getId() {
        return id.get();
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public String getTitre() {
        return titre.get();
    }

    public void setTitre(String titre) {
        this.titre.set(titre);
    }

    public StringProperty titreProperty() {
        return titre;
    }

    public String getAuteur() {
        return auteur.get();
    }

    public void setAuteur(String auteur) {
        this.auteur.set(auteur);
    }

    public StringProperty auteurProperty() {
        return auteur;
    }

    public String getCategorie() {
        return categorie.get();
    }

    public void setCategorie(String categorie) {
        this.categorie.set(categorie);
    }

    public StringProperty categorieProperty() {
        return categorie;
    }

    @Override
    public String toString() {
        return "Livre{" +
                "id=" + id.get() +
                ", titre='" + titre.get() + '\'' +
                ", auteur='" + auteur.get() + '\'' +
                ", categorie='" + categorie.get() + '\'' +
                '}';
    }
}