package models;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

public class Professeur extends Personne {

    private boolean vacant;
    private String matiereEnseigne;

    private String prochainCours;

    private String sujetProchaineReunion;


    public Professeur(int id, LocalDate dateNaissance, String ville, String prenom, String nom, String telephone,
                      boolean vacant, String matiereEnseigne, String prochainCours, String sujetProchaineReunion) {
        super(id, dateNaissance, ville, prenom, nom, telephone);
        this.vacant = vacant;
        this.matiereEnseigne = matiereEnseigne;
        this.prochainCours = prochainCours;
        this.sujetProchaineReunion = sujetProchaineReunion;
    }

    public Professeur(int id, Date dateNaissance, String ville, String nom, String prenom, String vacant, String matiere, String cours, String reunion) {
    }


    public boolean isVacant() {
        return vacant;
    }

    public void setVacant(boolean vacant) {
        this.vacant = vacant;
    }

    public String getMatiereEnseigne() {
        return matiereEnseigne;
    }

    public void setMatiereEnseigne(String matiereEnseigne) {
        this.matiereEnseigne = matiereEnseigne;
    }

    public String getProchainCours() {
        return prochainCours;
    }

    public void setProchainCours(String prochainCours) {
        this.prochainCours = prochainCours;
    }

    public String getSujetProchaineReunion() {
        return sujetProchaineReunion;
    }

    public void setSujetProchaineReunion(String sujetProchaineReunion) {
        this.sujetProchaineReunion = sujetProchaineReunion;
    }

}
