package models;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Personne {
    private int id;
    private Date dateNaissance;
    private String ville;
    private String nom;
    private String prenom;
    protected String telephone;

    public Personne() {
    }

    public Personne(int id, Date dateNaissance, String ville, String nom, String prenom) {
        this.id = id;
        this.dateNaissance = dateNaissance;
        this.ville = ville;
        this.nom = nom;
        this.prenom = prenom;
    }

    // Si vous avez besoin d'un constructeur avec LocalDate, vous pouvez le convertir en Date
    public Personne(int id, java.time.LocalDate dateNaissance, String ville, String nom, String prenom, String telephone) {
        this.id = id;
        this.dateNaissance = java.sql.Date.valueOf(dateNaissance); // Convert LocalDate to Date
        this.ville = ville;
        this.nom = nom;
        this.prenom = prenom;
        this.telephone = telephone;
    }

    public int getId() {
        return id;
    }

    public String getDateNaissance() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // Format for date
        return sdf.format(dateNaissance);
    }

    public String getVille() {
        return ville;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDateNaissance(Date dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    // Method to get the age of the person
    public int obtenirAge() {
        if (dateNaissance == null) {
            return 0;
        }
        java.util.Calendar today = java.util.Calendar.getInstance();
        java.util.Calendar birthDate = java.util.Calendar.getInstance();
        birthDate.setTime(dateNaissance);
        int age = today.get(java.util.Calendar.YEAR) - birthDate.get(java.util.Calendar.YEAR);
        if (today.get(java.util.Calendar.MONTH) + 1 < birthDate.get(java.util.Calendar.MONTH) + 1 ||
                (today.get(java.util.Calendar.MONTH) + 1 == birthDate.get(java.util.Calendar.MONTH) + 1 &&
                        today.get(java.util.Calendar.DAY_OF_MONTH) < birthDate.get(java.util.Calendar.DAY_OF_MONTH))) {
            age--;
        }
        return age;
    }

    public String getTelephone() {
        return telephone;
    }

    public void supprimer(int id) {
        // Implement the logic to delete a Personne record from the database
    }

    public void lister() {
        // Implement the logic to list Personne records from the database
    }
}
