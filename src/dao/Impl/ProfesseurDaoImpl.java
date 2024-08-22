package dao.Impl;

import dao.IProfesseurDao;
import dao.SingletonDataBase;
import models.Professeur;

import java.sql.Connection;
import java.time.Duration;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;


public class ProfesseurDaoImpl implements IProfesseurDao {

    Instant debutSession = Instant.now();
    private static final Scanner scanner = new Scanner(System.in);
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private final Connection connection;

    public ProfesseurDaoImpl(ProfesseurDaoImpl professeurDaoImpl) {
        this.professeurDaoImpl = professeurDaoImpl;
        this.connection = SingletonDataBase.getInstance();
    }

    private final ProfesseurDaoImpl professeurDaoImpl;


    @Override
    public Professeur save(Professeur professeur) {
        return professeurDaoImpl.ajouter(professeur);
    }

    @Override
    public Professeur update(Professeur professeur) {
        return professeurDaoImpl.modifier(professeur);
    }

    @Override
    public void delete(int id) {
        professeurDaoImpl.supprimer(id);
    }

    @Override
    public List<Professeur> getAll() {
        return professeurDaoImpl.obtenirProfesseurs();
    }

    @Override
    public Professeur getOne(int id) {
        return professeurDaoImpl.obtenir(id);
    }



    private void listerProfesseurs() {
        List<Professeur> professeurs = getAll();
        if (professeurs.isEmpty()) {
            System.out.println("Aucun professeur n'est enregistré.");
        } else {
            for (Professeur professeur : professeurs) {
                System.out.println("ID: " + professeur.getId() + "\n" +
                        "Nom: " + professeur.getNom() + "\n" +
                        "Prénom: " + professeur.getPrenom() + "\n" +
                        "Matière enseignée: " + professeur.getMatiereEnseigne() + "\n" +
                        "Prochain cours: " + professeur.getProchainCours() + "\n" +
                        "Sujet de la prochaine réunion: " + professeur.getSujetProchaineReunion() + "\n");
            }
        }
    }

    private void obtenirDernierProfesseurAjoute() {
        List<Professeur> professeurs = getAll();
        if (professeurs.isEmpty()) {
            System.out.println("Aucun professeur n'est enregistré.");
        } else {
            Professeur dernierProfesseur = professeurs.get(professeurs.size() - 1);
            System.out.println("Dernier professeur ajouté : \n" +
                    "        ID: " + dernierProfesseur.getId() + "\n" +
                    "        Nom: " + dernierProfesseur.getNom() + "\n" +
                    "        Prénom: " + dernierProfesseur.getPrenom() + "\n" +
                    "        Matière enseignée: " + dernierProfesseur.getMatiereEnseigne() + "\n" +
                    "        Prochain cours: " + dernierProfesseur.getProchainCours() + "\n" +
                    "        Sujet de la prochaine réunion: " + dernierProfesseur.getSujetProchaineReunion() + "\n");
        }
    }

    public void finSession() {
        Instant finSession = Instant.now();
        Duration duree = Duration.between(debutSession, finSession);

        long heures = duree.toHours();
        long minutes = duree.toMinutes() % 60;
        long secondes = duree.getSeconds() % 60;

        System.out.println("Merci d'avoir utilisé l'application ETAB. Au revoir !");
        System.out.println("Durée de la session : " + heures + " heures, " + minutes + " minutes, " + secondes + " secondes.");
    }

    @Override
    public Professeur ajouter(Professeur professeur) {
        return null;
    }

    @Override
    public Professeur Ajouter(Professeur professeur) {
        return null;
    }

    @Override
    public Professeur modifier(Professeur professeur) {
        return null;
    }

    @Override
    public void supprimer(int identifiant) {

    }

    @Override
    public List obtenirProfesseur() {
        return null;
    }

    @Override
    public Professeur Obtenir(int identifiant) {
        return null;
    }

    @Override
    public List<Professeur> obtenirProfesseurs() {
        return null;
    }

    @Override
    public Professeur obtenir(int id) {
        return null;
    }
}
