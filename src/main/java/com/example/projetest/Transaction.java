package com.example.projetest;

import java.util.Date;

public class Transaction {
    private int id;
    private int utilisateurId;
    private int livreId;
    private Date dateEmprunt;
    private Date dateRetour;

    public Transaction(int id, int utilisateurId, int livreId, Date dateEmprunt, Date dateRetour) {
        this.id = id;
        this.utilisateurId = utilisateurId;
        this.livreId = livreId;
        this.dateEmprunt = dateEmprunt;
        this.dateRetour = dateRetour;
    }

    public Transaction(int id, int utilisateurId, int livreId, java.sql.Date dateEmprunt, java.sql.Date dateRetour, String utilisateurNom, String livreTitre) {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUtilisateurId() {
        return utilisateurId;
    }

    public void setUtilisateurId(int utilisateurId) {
        this.utilisateurId = utilisateurId;
    }

    public int getLivreId() {
        return livreId;
    }

    public void setLivreId(int livreId) {
        this.livreId = livreId;
    }

    public Date getDateEmprunt() {
        return dateEmprunt;
    }

    public void setDateEmprunt(Date dateEmprunt) {
        this.dateEmprunt = dateEmprunt;
    }

    public Date getDateRetour() {
        return dateRetour;
    }

    public void setDateRetour(Date dateRetour) {
        this.dateRetour = dateRetour;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", utilisateurId=" + utilisateurId +
                ", livreId=" + livreId +
                ", dateEmprunt=" + dateEmprunt +
                ", dateRetour=" + dateRetour +
                '}';
    }
}