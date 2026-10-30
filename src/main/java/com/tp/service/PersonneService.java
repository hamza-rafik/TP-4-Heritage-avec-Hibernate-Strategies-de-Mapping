package com.tp.service;

import com.tp.dao.PersonneDao;
import com.tp.model.singletable.Etudiant;
import com.tp.model.singletable.Personne;
import com.tp.model.singletable.Professeur;

import java.util.List;

/**
 * Service métier pour la gestion de la hiérarchie Personne (SINGLE_TABLE).
 */
public class PersonneService {

    private final PersonneDao personneDao;

    public PersonneService() {
        this.personneDao = new PersonneDao();
    }

    public boolean enregistrer(Personne p) {
        if (p == null || p.getEmail() == null || p.getEmail().trim().isEmpty()) {
            System.err.println("  [Service Validation] Email obligatoire.");
            return false;
        }
        return personneDao.create(p);
    }

    public boolean modifier(Personne p) {
        if (p == null || p.getId() == null) {
            return false;
        }
        return personneDao.update(p);
    }

    public boolean supprimer(Long id) {
        Personne p = personneDao.findById(id);
        if (p != null) {
            return personneDao.delete(p);
        }
        return false;
    }

    public Personne rechercherParId(Long id) {
        return personneDao.findById(id);
    }

    public List<Personne> listerTous() {
        return personneDao.findAll();
    }

    public List<Etudiant> listerEtudiants() {
        return personneDao.findAllEtudiants();
    }

    public List<Professeur> listerProfesseurs() {
        return personneDao.findAllProfesseurs();
    }
}
