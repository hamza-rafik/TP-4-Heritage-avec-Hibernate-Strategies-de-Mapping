package com.tp.service;

import com.tp.dao.CompteDao;
import com.tp.model.joined.Compte;
import com.tp.model.joined.CompteCourant;
import com.tp.model.joined.CompteEpargne;

import java.util.List;

/**
 * Service métier pour la gestion des comptes bancaires (JOINED).
 */
public class CompteService {

    private final CompteDao compteDao;

    public CompteService() {
        this.compteDao = new CompteDao();
    }

    public boolean ouvrirCompte(Compte c) {
        if (c == null || c.getCode() == null) {
            return false;
        }
        return compteDao.create(c);
    }

    public boolean modifierCompte(Compte c) {
        return compteDao.update(c);
    }

    public boolean fermerCompte(Long id) {
        Compte c = compteDao.findById(id);
        if (c != null) {
            return compteDao.delete(c);
        }
        return false;
    }

    public Compte trouverParId(Long id) {
        return compteDao.findById(id);
    }

    public List<Compte> listerTousComptes() {
        return compteDao.findAll();
    }

    public List<CompteCourant> listerComptesCourants() {
        return compteDao.findAllComptesCourants();
    }

    public List<CompteEpargne> listerComptesEpargnes() {
        return compteDao.findAllComptesEpargnes();
    }

    public double totalEncoursBancaire() {
        return compteDao.calculerTotalSoldes();
    }
}
