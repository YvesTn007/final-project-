package com.example.projetest;

import java.sql.*;
import java.util.Date;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Bibliotheque {
    private static final Logger LOGGER = Logger.getLogger(Bibliotheque.class.getName());
    private final HashMap<Integer, Livre> livres = new HashMap<>();
    private final ObservableList<Livre> observableLivres = FXCollections.observableArrayList();
    private final HashMap<Integer, Utilisateur> utilisateurs = new HashMap<>();
    private final ObservableList<Utilisateur> observableUtilisateurs = FXCollections.observableArrayList();
    private final HashMap<Integer, Transaction> transactions = new HashMap<>();
    private final ObservableList<Transaction> observableTransactions = FXCollections.observableArrayList();
    private final String URL = "jdbc:mysql://localhost:3306/bibliotheque";
    private final String USER = "root";
    private final String PASSWORD = "";

    public Bibliotheque() {
        chargerLivres();
        chargerUtilisateurs();
        chargerTransactions();
    }

    private void chargerLivres() {
        String query = "SELECT * FROM livres";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                Livre livre = new Livre(
                        resultSet.getInt("id"),
                        resultSet.getString("titre"),
                        resultSet.getString("auteur"),
                        resultSet.getString("categorie")
                );
                livres.put(livre.getId(), livre);
                observableLivres.add(livre);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Erreur lors du chargement des livres: {0}", e.getMessage());
        }
    }

    public void ajouterLivre(Livre livre) {
        if (livre == null || livre.getTitre() == null || livre.getAuteur() == null || livre.getCategorie() == null) {
            LOGGER.log(Level.WARNING, "Livre invalide: {0}", livre);
            return;
        }

        String query = "INSERT INTO livres (titre, auteur, categorie) VALUES (?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, livre.getTitre());
            statement.setString(2, livre.getAuteur());
            statement.setString(3, livre.getCategorie());
            statement.executeUpdate();
            livre.setId(getGeneratedId(statement));
            livres.put(livre.getId(), livre);
            observableLivres.add(livre);
            LOGGER.log(Level.INFO, "Livre ajouté: {0}", livre);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Erreur lors de l'ajout du livre: {0}", e.getMessage());
        }
    }

    public void ajouterUtilisateur(Utilisateur utilisateur) {
        if (utilisateur == null || utilisateur.getNom() == null || utilisateur.getEmail() == null) {
            LOGGER.log(Level.WARNING, "Utilisateur invalide: {0}", utilisateur);
            return;
        }

        String query = "INSERT INTO utilisateurs (nom, email) VALUES (?, ?)";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, utilisateur.getNom());
            statement.setString(2, utilisateur.getEmail());
            statement.executeUpdate();
            utilisateur.setId(getGeneratedId(statement));
            utilisateurs.put(utilisateur.getId(), utilisateur);
            observableUtilisateurs.add(utilisateur);
            LOGGER.log(Level.INFO, "Utilisateur ajouté: {0}", utilisateur);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Erreur lors de l'ajout de l'utilisateur: {0}", e.getMessage());
        }
    }

    public boolean emprunterLivre(String utilisateurNom, String livreTitre) {
        Utilisateur utilisateur = getUtilisateurParNom(utilisateurNom);
        Livre livre = getLivreParTitre(livreTitre);

        if (utilisateur == null || livre == null) {
            LOGGER.log(Level.WARNING, "Utilisateur ou livre non trouvé");
            return false;
        }

        String query = "INSERT INTO transactions (utilisateur_id, livre_id, date_emprunt) VALUES (?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, utilisateur.getId());
            statement.setInt(2, livre.getId());
            statement.setDate(3, new java.sql.Date(new Date().getTime()));
            statement.executeUpdate();
            Transaction transaction = new Transaction(
                    getGeneratedId(statement),
                    utilisateur.getId(),
                    livre.getId(),
                    new Date(),
                    null
            );
            transactions.put(transaction.getId(), transaction);
            observableTransactions.add(transaction);
            LOGGER.log(Level.INFO, "Livre emprunté: {0}", transaction);
            return true;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Erreur lors de l'emprunt du livre: {0}", e.getMessage());
            return false;
        }
    }

    public boolean retournerLivre(String utilisateurNom, String livreTitre) {
        Utilisateur utilisateur = getUtilisateurParNom(utilisateurNom);
        Livre livre = getLivreParTitre(livreTitre);

        if (utilisateur == null || livre == null) {
            LOGGER.log(Level.WARNING, "Utilisateur ou livre non trouvé");
            return false;
        }

        String query = "UPDATE transactions SET date_retour = ? WHERE utilisateur_id = ? AND livre_id = ? AND date_retour IS NULL";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setDate(1, new java.sql.Date(new Date().getTime()));
            statement.setInt(2, utilisateur.getId());
            statement.setInt(3, livre.getId());
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated == 0) {
                LOGGER.log(Level.WARNING, "Aucune transaction trouvée pour le retour");
                return false;
            }
            chargerTransactions(); // Recharger les transactions pour mettre à jour la liste observable
            LOGGER.log(Level.INFO, "Livre retourné: {0}", livreTitre);
            return true;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Erreur lors du retour du livre: {0}", e.getMessage());
            return false;
        }
    }

    private Utilisateur getUtilisateurParNom(String nom) {
        for (Utilisateur utilisateur : utilisateurs.values()) {
            if (utilisateur.getNom().equals(nom)) {
                return utilisateur;
            }
        }
        return null;
    }

    private Livre getLivreParTitre(String titre) {
        for (Livre livre : livres.values()) {
            if (livre.getTitre().equals(titre)) {
                return livre;
            }
        }
        return null;
    }

    private int getGeneratedId(PreparedStatement statement) throws SQLException {
        try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                return generatedKeys.getInt(1);
            } else {
                throw new SQLException("Aucun ID généré");
            }
        }
    }

    public ObservableList<Livre> getObservableLivres() {
        return observableLivres;
    }

    public ObservableList<Utilisateur> getObservableUtilisateurs() {
        return observableUtilisateurs;
    }

    public ObservableList<Transaction> getObservableTransactions() {
        return observableTransactions;
    }

    private void chargerUtilisateurs() {
        String query = "SELECT * FROM utilisateurs";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                Utilisateur utilisateur = new Utilisateur(
                        resultSet.getInt("id"),
                        resultSet.getString("nom"),
                        resultSet.getString("email")
                );
                utilisateurs.put(utilisateur.getId(), utilisateur);
                observableUtilisateurs.add(utilisateur);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Erreur lors du chargement des utilisateurs: {0}", e.getMessage());
        }
    }

    private void chargerTransactions() {
        String query = "SELECT transactions.id, utilisateur_id, livre_id, date_emprunt, date_retour, utilisateurs.nom AS utilisateurNom, livres.titre AS livreTitre " +
                "FROM transactions " +
                "JOIN utilisateurs ON transactions.utilisateur_id = utilisateurs.id " +
                "JOIN livres ON transactions.livre_id = livres.id";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                Transaction transaction = new Transaction(
                        resultSet.getInt("id"),
                        resultSet.getInt("utilisateur_id"),
                        resultSet.getInt("livre_id"),
                        resultSet.getDate("date_emprunt"),
                        resultSet.getDate("date_retour")
                );
                transactions.put(transaction.getId(), transaction);
                observableTransactions.add(transaction);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Erreur lors du chargement des transactions: {0}", e.getMessage());
        }
    }
}