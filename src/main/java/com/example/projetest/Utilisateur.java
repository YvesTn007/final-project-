package com.example.projetest;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Utilisateur {
    private IntegerProperty id;
    private StringProperty nom;
    private StringProperty email;

    public Utilisateur() {
        this.id = new SimpleIntegerProperty();
        this.nom = new SimpleStringProperty();
        this.email = new SimpleStringProperty();
    }

    public Utilisateur(int id, String nom, String email) {
        this.id = new SimpleIntegerProperty(id);
        this.nom = new SimpleStringProperty(nom);
        this.email = new SimpleStringProperty(email);
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

    public String getNom() {
        return nom.get();
    }

    public void setNom(String nom) {
        this.nom.set(nom);
    }

    public StringProperty nomProperty() {
        return nom;
    }

    public String getEmail() {
        return email.get();
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public StringProperty emailProperty() {
        return email;
    }

    @Override
    public String toString() {
        return "Utilisateur{" +
                "id=" + id.get() +
                ", nom='" + nom.get() + '\'' +
                ", email='" + email.get() + '\'' +
                '}';
    }
}