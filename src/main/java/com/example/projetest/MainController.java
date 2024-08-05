package com.example.projetest;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import java.util.Date;

public class MainController {

    @FXML
    private TableView<Livre> booksTable;
    @FXML
    private TableColumn<Livre, Integer> idColumn;
    @FXML
    private TableColumn<Livre, String> titleColumn;
    @FXML
    private TableColumn<Livre, String> authorColumn;
    @FXML
    private TableColumn<Livre, String> categoryColumn;

    @FXML
    private TableView<Utilisateur> usersTable;
    @FXML
    private TableColumn<Utilisateur, String> nameColumn;
    @FXML
    private TableColumn<Utilisateur, String> emailColumn;

    @FXML
    private TableView<Transaction> transactionsTable;
    @FXML
    private TableColumn<Transaction, String> userColumn;
    @FXML
    private TableColumn<Transaction, String> bookColumn;
    @FXML
    private TableColumn<Transaction, Date> dateBorrowedColumn;
    @FXML
    private TableColumn<Transaction, Date> dateReturnedColumn;

    @FXML
    private TextField categorieField;
    @FXML
    private TextField titleField;
    @FXML
    private TextField authorField;
    @FXML
    private TextField nameField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField transactionUserField;
    @FXML
    private TextField transactionBookField;

    private Bibliotheque bibliotheque;

    @FXML
    public void initialize() {
        bibliotheque = new Bibliotheque();

        // Configurer les colonnes des livres
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("titre"));
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("auteur"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("categorie"));

        // Configurer les colonnes des utilisateurs
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));

        // Configurer les colonnes des transactions
        userColumn.setCellValueFactory(new PropertyValueFactory<>("utilisateurNom"));
        bookColumn.setCellValueFactory(new PropertyValueFactory<>("livreTitre"));
        dateBorrowedColumn.setCellValueFactory(new PropertyValueFactory<>("dateEmprunt"));
        dateReturnedColumn.setCellValueFactory(new PropertyValueFactory<>("dateRetour"));

        // Lier les listes observables aux tables
        booksTable.setItems(bibliotheque.getObservableLivres());
        usersTable.setItems(bibliotheque.getObservableUtilisateurs());
        transactionsTable.setItems(bibliotheque.getObservableTransactions());
    }

    @FXML
    private void handleAddBook() {
        String categorie = categorieField.getText();
        String titre = titleField.getText();
        String auteur = authorField.getText();

        if (categorie.isEmpty() || titre.isEmpty() || auteur.isEmpty()) {
            showAlert("Erreur", "Tous les champs doivent être remplis.");
            return;
        }

        Livre nouveauLivre = new Livre(0, titre, auteur, categorie); // Utiliser le constructeur correct
        bibliotheque.ajouterLivre(nouveauLivre);
        booksTable.setItems(bibliotheque.getObservableLivres());
        categorieField.clear();
        titleField.clear();
        authorField.clear();
    }

    @FXML
    private void handleAddUser() {
        String nom = nameField.getText();
        String email = emailField.getText();

        if (nom.isEmpty() || email.isEmpty()) {
            showAlert("Erreur", "Tous les champs doivent être remplis.");
            return;
        }

        Utilisateur nouvelUtilisateur = new Utilisateur(0, nom, email); // Remplacer resultSet.getInt("id") par 0
        bibliotheque.ajouterUtilisateur(nouvelUtilisateur);
        usersTable.setItems(bibliotheque.getObservableUtilisateurs());
        nameField.clear();
        emailField.clear();
    }

    @FXML
    private void handleBorrowBook() {
        String utilisateurNom = transactionUserField.getText();
        String livreTitre = transactionBookField.getText();

        if (utilisateurNom.isEmpty() || livreTitre.isEmpty()) {
            showAlert("Erreur", "Tous les champs doivent être remplis.");
            return;
        }

        boolean success = bibliotheque.emprunterLivre(utilisateurNom, livreTitre);
        if (!success) {
            showAlert("Erreur", "L'emprunt n'a pas pu être réalisé. Veuillez vérifier les informations.");
        }
        transactionsTable.setItems(bibliotheque.getObservableTransactions());
        transactionUserField.clear();
        transactionBookField.clear();
    }

    @FXML
    private void handleReturnBook() {
        String utilisateurNom = transactionUserField.getText();
        String livreTitre = transactionBookField.getText();

        if (utilisateurNom.isEmpty() || livreTitre.isEmpty()) {
            showAlert("Erreur", "Tous les champs doivent être remplis.");
            return;
        }

        boolean success = bibliotheque.retournerLivre(utilisateurNom, livreTitre);
        if (!success) {
            showAlert("Erreur", "Le retour n'a pas pu être réalisé. Veuillez vérifier les informations.");
        }
        transactionsTable.setItems(bibliotheque.getObservableTransactions());
        transactionUserField.clear();
        transactionBookField.clear();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}