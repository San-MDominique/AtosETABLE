package services;

import models.Eleve;

import java.util.List;

public interface  IEleveService {

    Eleve save(Eleve eleve);
    Eleve update(Eleve eleve);
    void delete(int identifiant);
    List<Eleve> getAll();
    Eleve getOne(int identifiant);
}
