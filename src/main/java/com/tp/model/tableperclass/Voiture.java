package com.tp.model.tableperclass;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/**
 * Sous-classe concrète Voiture dans la Stratégie TABLE_PER_CLASS.
 * 
 * La table 'voitures_table_per_class' contient TOUTES les colonnes :
 * id, immatriculation, marque, modele, prix (héritées) ainsi que
 * nombre_portes, puissance_fiscale et climatisee (propres à la voiture).
 */
@Entity
@Table(name = "voitures_table_per_class")
public class Voiture extends Vehicule {

    @Column(name = "nombre_portes", nullable = false)
    private int nombrePortes;

    @Column(name = "puissance_fiscale", nullable = false)
    private int puissanceFiscale;

    @Column(name = "climatisee", nullable = false)
    private boolean climatisee;

    public Voiture() {
        super();
    }

    public Voiture(String immatriculation, String marque, String modele, double prix,
                   int nombrePortes, int puissanceFiscale, boolean climatisee) {
        super(immatriculation, marque, modele, prix);
        this.nombrePortes = nombrePortes;
        this.puissanceFiscale = puissanceFiscale;
        this.climatisee = climatisee;
    }

    public Voiture(Long id, String immatriculation, String marque, String modele, double prix,
                   int nombrePortes, int puissanceFiscale, boolean climatisee) {
        super(id, immatriculation, marque, modele, prix);
        this.nombrePortes = nombrePortes;
        this.puissanceFiscale = puissanceFiscale;
        this.climatisee = climatisee;
    }

    public int getNombrePortes() {
        return nombrePortes;
    }

    public void setNombrePortes(int nombrePortes) {
        this.nombrePortes = nombrePortes;
    }

    public int getPuissanceFiscale() {
        return puissanceFiscale;
    }

    public void setPuissanceFiscale(int puissanceFiscale) {
        this.puissanceFiscale = puissanceFiscale;
    }

    public boolean isClimatisee() {
        return climatisee;
    }

    public void setClimatisee(boolean climatisee) {
        this.climatisee = climatisee;
    }

    @Override
    public String toString() {
        return String.format("Voiture[ID=%d, Immat='%s', Marque='%s %s', Prix=%.2f DH, Portes=%d, Puissance=%d CV, Clim=%s]",
                getId(), getImmatriculation(), getMarque(), getModele(), getPrix(),
                nombrePortes, puissanceFiscale, (climatisee ? "OUI" : "NON"));
    }
}
