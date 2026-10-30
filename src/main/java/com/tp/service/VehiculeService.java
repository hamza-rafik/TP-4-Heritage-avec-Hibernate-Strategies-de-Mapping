package com.tp.service;

import com.tp.dao.VehiculeDao;
import com.tp.model.tableperclass.Moto;
import com.tp.model.tableperclass.Vehicule;
import com.tp.model.tableperclass.Voiture;

import java.util.List;

/**
 * Service métier pour la gestion du parc de véhicules (TABLE_PER_CLASS).
 */
public class VehiculeService {

    private final VehiculeDao vehiculeDao;

    public VehiculeService() {
        this.vehiculeDao = new VehiculeDao();
    }

    public boolean ajouterVehicule(Vehicule v) {
        if (v == null || v.getImmatriculation() == null) {
            return false;
        }
        return vehiculeDao.create(v);
    }

    public boolean modifierVehicule(Vehicule v) {
        return vehiculeDao.update(v);
    }

    public boolean retirerVehicule(Long id) {
        Vehicule v = vehiculeDao.findById(id);
        if (v != null) {
            return vehiculeDao.delete(v);
        }
        return false;
    }

    public Vehicule trouverParId(Long id) {
        return vehiculeDao.findById(id);
    }

    public List<Vehicule> listerToutLeParc() {
        return vehiculeDao.findAll();
    }

    public List<Voiture> listerVoitures() {
        return vehiculeDao.findAllVoitures();
    }

    public List<Moto> listerMotos() {
        return vehiculeDao.findAllMotos();
    }

    public double calculerValeurTotale() {
        return vehiculeDao.calculerValeurTotaleParc();
    }
}
