package services.impl;

import dao.Impl.ProfesseurDaoImpl;
import models.Professeur;
import services.IProfesseurService;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class ProfesseurServiceImpl implements IProfesseurService {
    Instant debutSession = Instant.now();
    private static final Scanner scanner = new Scanner(System.in);
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private final ProfesseurDaoImpl professeurDaoImpl;

    public ProfesseurServiceImpl(ProfesseurDaoImpl professeurDaoImpl) {
        this.professeurDaoImpl = professeurDaoImpl;
    }

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


    public void finSession() {
        Instant finSession = Instant.now();
        Duration duree = Duration.between(debutSession, finSession);

        long heures = duree.toHours();
        long minutes = duree.toMinutes() % 60;
        long secondes = duree.getSeconds() % 60;

        System.out.println("Merci d'avoir utilisé l'application ETAB. Au revoir !");
        System.out.println("Durée de la session : " + heures + " heures, " + minutes + " minutes, " + secondes + " secondes.");
    }
}
