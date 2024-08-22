package models;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

/**
 * Classe représentant un élève.
 * Elle hérite de la classe models.Personne et implémente l'interface dao.ICRUDEleve.
 */
public class Eleve extends Personne {
    private String classe;
    private String matricule;

    /**
     * Constructeur par défaut de la classe models.Eleve.
     */
    public Eleve() {
    }


    public Eleve(int id, Date dateNaissance, String ville, String nom, String prenom, String classe, String matricule) {
        super(id, dateNaissance, ville, nom, prenom);
        this.classe = classe;
        this.matricule = matricule;
    }

    public Eleve(int id, LocalDate dateNaissance, String ville, String prenom, String nom, String classe, String matricule) {
    }

    public Eleve(int id, LocalDate dateNaissance, String ville, String prenom, String nom, String telephone, String classe, String matricule) {
    }

    public String getClasse() {
        return classe;
    }

    public String getMatricule() {
        return matricule;
    }

    public void setClasse(String classe) {
        this.classe = classe;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    /**
     * Méthode pour ajouter un élève.
     *
     * @param eleve Élève à ajouter.
     * @return Élève ajouté.
     */


    /**
     * Méthode pour supprimer un élève.
     *
     * @param identifiant Identifiant de l'élève à supprimer.
     */
    @Override
    public void supprimer(int identifiant) {
        // Implémentation de la suppression d'un élève
    }

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return "Élève ID: " + getId() +
                ", Nom: " + getNom() +
                ", Prénom: " + getPrenom() +
                ", Ville: " + getVille() +
                ", Date de Naissance: " + sdf.format(getDateNaissance()) +
                ", Classe: " + getClasse() +
                ", Matricule: " + getMatricule();
    }


}
