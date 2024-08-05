﻿# final-project-

# Description de l'application

L'application est un Système de Gestion de Bibliothèque développé en Java. Elle permet de gérer les livres, les utilisateurs et les transactions de prêt et de retour de livres. Les données sont stockées dans une base de données MySQL, et l'interface utilisateur est développée avec JavaFX.

# Présentation détaillée des différentes fonctionnalités

Gestion des livres
Ajout de livres :
Permet d'ajouter de nouveaux livres à la bibliothèque en fournissant le titre, l'auteur et la catégorie.
Les livres peuvent être de différentes catégories telles que ScienceFiction, Roman ou Biographie.

Chargement des livres :
Charge les livres existants depuis la base de données.
Les livres sont stockés dans une collection observable pour une mise à jour automatique de l'interface utilisateur.
Gestion des utilisateurs

Ajout d'utilisateurs :
Permet d'ajouter de nouveaux utilisateurs en fournissant leur nom et leur email.
Chargement des utilisateurs :
Charge les utilisateurs existants depuis la base de données.
Les utilisateurs sont également stockés dans une collection observable.
Gestion des transactions

Emprunter un livre :
Permet à un utilisateur d'emprunter un livre en spécifiant le nom de l'utilisateur et le titre du livre.
Enregistre la transaction dans la base de données avec la date d'emprunt.

Retourner un livre :
Permet à un utilisateur de retourner un livre emprunté.
Met à jour la transaction dans la base de données avec la date de retour.
Chargement des transactions :
Charge les transactions existantes depuis la base de données.
Les transactions sont également stockées dans une collection observable.


# Instructions pour compiler et exécuter le projet

importation du projet comme un projet Maven pour la compilation
exécution de la classe Main pour l'execution 
