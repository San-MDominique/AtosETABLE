package services.impl;

import dao.Impl.EleveDaoImpl;
import models.Eleve;
import services.IEleveService;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class EleveServiceImpl implements IEleveService {

    Instant debutSession = Instant.now();
    private static final Scanner scanner = new Scanner(System.in);
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private final EleveDaoImpl eleveDaoImpl;

    public EleveServiceImpl(EleveDaoImpl eleveDaoImpl) {
        this.eleveDaoImpl = eleveDaoImpl;
    }

    @Override
    public Eleve save(Eleve eleve) {
        return eleveDaoImpl.ajouter(eleve);
    }

    @Override
    public Eleve update(Eleve eleve) {
        return eleveDaoImpl.modifier(eleve);
    }

    @Override
    public void delete(int identifiant) {
        eleveDaoImpl.supprimer(identifiant);
    }

    @Override
    public List<Eleve> getAll() {
        return eleveDaoImpl.obtenirEleves();
    }

    @Override
    public Eleve getOne(int identifiant) {
        return eleveDaoImpl.obtenir(identifiant);
    }
}

