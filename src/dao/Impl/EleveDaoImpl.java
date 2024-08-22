package dao.Impl;

import dao.IEleveDao;
import dao.SingletonDataBase;
import models.Eleve;
import models.Professeur;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EleveDaoImpl implements IEleveDao { private static final String INSERT_PERSONNE_SQL = "INSERT INTO personne (nom, prenom, date_naissance, ville, telephone) VALUES (?, ?, ?, ?, ?)";
    private final Connection connection;

    public EleveDaoImpl() {
        this.connection = SingletonDataBase.getInstance();
    }

    @Override
    public Eleve ajouter(Eleve eleve) {

        try {
            // Insertion dans la table personne
            String insertPersonneSql = "INSERT INTO personne (nom, prenom, date_naissance, ville, telephone) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement personneStatement = connection.prepareStatement(insertPersonneSql, PreparedStatement.RETURN_GENERATED_KEYS)) {
                personneStatement.setString(1, eleve.getNom());
                personneStatement.setString(2, eleve.getPrenom());
                personneStatement.setDate(3, Date.valueOf(eleve.getDateNaissance()));
                personneStatement.setString(4, eleve.getVille());
                personneStatement.setString(5, eleve.getTelephone());

                int affectedRowsPersonne = personneStatement.executeUpdate();

                if (affectedRowsPersonne == 0) {
                    throw new SQLException("Échec de l'insertion dans la table personne.");
                }

                // Récupération de l'ID généré pour la personne
                ResultSet generatedKeys = personneStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int personneId = generatedKeys.getInt(1);

                    // Insertion dans la table eleve
                    String insertEleveSql = "INSERT INTO eleve (id, classe, matricule) VALUES (?, ?, ?)";
                    try (PreparedStatement eleveStatement = connection.prepareStatement(insertEleveSql)) {
                        eleveStatement.setInt(1, personneId);
                        eleveStatement.setString(2, eleve.getClasse());
                        eleveStatement.setString(3, eleve.getMatricule());

                        int affectedRowsEleve = eleveStatement.executeUpdate();

                        if (affectedRowsEleve == 0) {
                            throw new SQLException("Échec de l'insertion dans la table eleve.");
                        }
                    }
                } else {
                    throw new SQLException("Aucun ID généré pour la table personne.");
                }
            }
            System.out.println("Élève ajouté avec succès !");
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout de l'élève : " + e.getMessage());
        }
        return eleve;

    }

    @Override
    public Eleve Ajouter(Eleve eleve) {
        return null;
    }

    @Override
    public Eleve modifier(Eleve eleve) {
        String query = "UPDATE personne SET nom=?, prenom=?, date_naissance=?, ville=?, telephone=? WHERE id=?";
        String queryEleve = "UPDATE eleve SET classe=?, matricule=? WHERE id=?";

        try {
            connection.setAutoCommit(false); // Commencer la transaction

            // Mettre à jour la table personne
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, eleve.getNom());
                statement.setString(2, eleve.getPrenom());
                statement.setDate(3, Date.valueOf(eleve.getDateNaissance()));
                statement.setString(4, eleve.getVille());
                statement.setString(5, eleve.getTelephone());
                statement.setInt(6, eleve.getId());
                int rowsUpdated = statement.executeUpdate();

                if (rowsUpdated > 0) {
                    // Mettre à jour la table eleve
                    try (PreparedStatement statementEleve = connection.prepareStatement(queryEleve)) {
                        statementEleve.setString(1, eleve.getClasse());
                        statementEleve.setString(2, eleve.getMatricule());
                        statementEleve.setInt(3, eleve.getId());
                        statementEleve.executeUpdate();

                        connection.commit(); // Valider la transaction
                        System.out.println("Élève modifié avec succès !");
                    }
                } else {
                    connection.rollback(); // Annuler la transaction si l'ID n'existe pas
                    System.out.println("Aucun élève trouvé avec cet ID.");
                }
            }

        } catch (SQLException e) {
            try {
                connection.rollback(); // Annuler la transaction en cas d'erreur
            } catch (SQLException rollbackEx) {
                System.out.println("Erreur lors de l'annulation de la transaction : " + rollbackEx.getMessage());
            }
            System.out.println("Erreur lors de la modification de l'élève : " + e.getMessage());
        } finally {
            try {
                connection.setAutoCommit(true); // Remettre l'auto-commit à true
            } catch (SQLException e) {
                System.out.println("Erreur lors du rétablissement de l'auto-commit : " + e.getMessage());
            }
        }
        return eleve;
    }

    @Override
    public Eleve supprimer(int id) {
        String deleteEleveSQL = "DELETE FROM eleve WHERE id = ?";
        String deletePersonneSQL = "DELETE FROM personne WHERE id = ?";

        try {
            connection.setAutoCommit(false); // Commencer la transaction

            try (PreparedStatement deleteEleveStmt = connection.prepareStatement(deleteEleveSQL)) {
                deleteEleveStmt.setInt(1, id);
                int rowsDeletedEleve = deleteEleveStmt.executeUpdate();

                if (rowsDeletedEleve > 0) {
                    try (PreparedStatement deletePersonneStmt = connection.prepareStatement(deletePersonneSQL)) {
                        deletePersonneStmt.setInt(1, id);
                        int rowsDeletedPersonne = deletePersonneStmt.executeUpdate();

                        if (rowsDeletedPersonne > 0) {
                            connection.commit(); // Valider la transaction
                            System.out.println("Élève supprimé avec succès !");
                        } else {
                            connection.rollback(); // Annuler la transaction si l'ID n'existe pas dans la table personne
                            System.out.println("Erreur lors de la suppression de l'élève dans la table personne.");
                        }
                    }
                } else {
                    connection.rollback(); // Annuler la transaction si l'ID n'existe pas dans la table eleve
                    System.out.println("Aucun élève trouvé avec cet ID dans la table eleve.");
                }
            } catch (SQLException e) {
                connection.rollback(); // Annuler la transaction en cas d'erreur
                System.out.println("Erreur lors de la suppression de l'élève : " + e.getMessage());
            } finally {
                connection.setAutoCommit(true); // Remettre l'auto-commit à true
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la gestion de la transaction : " + e.getMessage());
        }
        return null;
    }

    @Override
    public Eleve Obtenir(int identifiant) {
        String query = "SELECT p.id, p.nom, p.prenom, p.date_naissance, p.ville, p.telephone, e.classe, e.matricule FROM personne p INNER JOIN eleve e ON p.id = e.id WHERE p.id=?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, identifiant);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new Eleve(
                        resultSet.getInt("id"),
                        resultSet.getDate("date_naissance").toLocalDate(),
                        resultSet.getString("ville"),
                        resultSet.getString("prenom"),
                        resultSet.getString("nom"),
                        resultSet.getString("telephone"),
                        resultSet.getString("classe"),
                        resultSet.getString("matricule")
                );
            } else {
                System.out.println("Aucun élève trouvé avec cet ID.");
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération de l'élève : " + e.getMessage());
        }
        return null;
    }


    @Override
    public List<Professeur> obtenirProfesseur() {
        List<Professeur> professeurs = new ArrayList<>();
        String query = "SELECT p.id, p.nom, p.prenom, p.date_naissance, p.ville, p.telephone, pr.vacant, pr.matiere_enseigne, pr.prochain_cours, pr.sujet_prochaine_reunion FROM personne p INNER JOIN professeur pr ON p.id = pr.id";

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                Professeur professeur = new Professeur(
                        resultSet.getInt("id"),
                        resultSet.getDate("date_naissance").toLocalDate(),
                        resultSet.getString("ville"),
                        resultSet.getString("prenom"),
                        resultSet.getString("nom"),
                        resultSet.getString("telephone"),
                        resultSet.getBoolean("vacant"),
                        resultSet.getString("matiere_enseigne"),
                        resultSet.getString("prochain_cours"),
                        resultSet.getString("sujet_prochaine_reunion")
                );
                professeurs.add(professeur);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des professeurs : " + e.getMessage());
        }
        return professeurs;
    }

    @Override
    public List<Eleve> obtenirEleves() {
        List<Eleve> eleves = new ArrayList<>();
        String query = "SELECT p.id, p.nom, p.prenom, p.date_naissance, p.ville, p.telephone, e.classe, e.matricule FROM personne p INNER JOIN eleve e ON p.id = e.id";

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                Eleve eleve = new Eleve(
                        resultSet.getInt("id"),
                        resultSet.getDate("date_naissance").toLocalDate(),
                        resultSet.getString("ville"),
                        resultSet.getString("prenom"),
                        resultSet.getString("nom"),
                        resultSet.getString("telephone"),
                        resultSet.getString("classe"),
                        resultSet.getString("matricule")
                );
                eleves.add(eleve);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des élèves : " + e.getMessage());
        }
        return eleves;
    }

    @Override
    public Eleve obtenir(int id) {
        String query = "SELECT p.id, p.nom, p.prenom, p.date_naissance, p.ville, p.telephone, e.classe, e.matricule FROM personne p INNER JOIN eleve e ON p.id = e.id WHERE p.id=?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new Eleve(
                        resultSet.getInt("id"),
                        resultSet.getDate("date_naissance").toLocalDate(),
                        resultSet.getString("ville"),
                        resultSet.getString("prenom"),
                        resultSet.getString("nom"),
                        resultSet.getString("telephone"),
                        resultSet.getString("classe"),
                        resultSet.getString("matricule")
                );
            } else {
                System.out.println("Aucun élève trouvé avec cet ID.");
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération de l'élève : " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Professeur> obtenirProfesseurs() {
        List<Professeur> professeurs = new ArrayList<>();
        String query = "SELECT p.id, p.nom, p.prenom, p.date_naissance, p.ville, p.telephone, pr.vacant, pr.matiere_enseigne, pr.prochain_cours, pr.sujet_prochaine_reunion FROM personne p INNER JOIN professeur pr ON p.id = pr.id";

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                Professeur professeur = new Professeur(
                        resultSet.getInt("id"),
                        resultSet.getDate("date_naissance").toLocalDate(),
                        resultSet.getString("ville"),
                        resultSet.getString("prenom"),
                        resultSet.getString("nom"),
                        resultSet.getString("telephone"),
                        resultSet.getBoolean("vacant"),
                        resultSet.getString("matiere_enseigne"),
                        resultSet.getString("prochain_cours"),
                        resultSet.getString("sujet_prochaine_reunion")
                );
                professeurs.add(professeur);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des professeurs : " + e.getMessage());
        }
        return professeurs;
    }

    @Override
    public List<Eleve> obtenirTous() {
        return null;
    }

}