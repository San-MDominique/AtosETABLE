package dao;

import models.Professeur;

import java.util.List;

public interface IProfesseurDao {

    Professeur save(Professeur professeur);

    Professeur update(Professeur professeur);

    void delete(int id);

    List<Professeur> getAll();

    Professeur getOne(int id);

    Professeur ajouter(Professeur professeur);

    public Professeur Ajouter(Professeur professeur);
    public  Professeur modifier(Professeur professeur);
    public void supprimer(int identifiant);
    public List obtenirProfesseur();
    public Professeur Obtenir(int identifiant);

    List<Professeur> obtenirProfesseurs();

    Professeur obtenir(int id);
}
