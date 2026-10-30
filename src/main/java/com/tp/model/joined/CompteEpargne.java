package com.tp.model.joined;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import java.time.LocalDate;

/**
 * Sous-classe CompteEpargne dans la Stratégie JOINED.
 * 
 * Sa table 'comptes_epargnes' contient 'compte_id', 'taux_interet' et 'plafond_depot'.
 */
@Entity
@Table(name = "comptes_epargnes")
@PrimaryKeyJoinColumn(name = "compte_id")
public class CompteEpargne extends Compte {

    @Column(name = "taux_interet", nullable = false)
    private double tauxInteret;

    @Column(name = "plafond_depot", nullable = false)
    private double plafondDepot;

    public CompteEpargne() {
        super();
    }

    public CompteEpargne(String code, double solde, LocalDate dateCreation,
                         double tauxInteret, double plafondDepot) {
        super(code, solde, dateCreation);
        this.tauxInteret = tauxInteret;
        this.plafondDepot = plafondDepot;
    }

    public CompteEpargne(Long id, String code, double solde, LocalDate dateCreation,
                         double tauxInteret, double plafondDepot) {
        super(id, code, solde, dateCreation);
        this.tauxInteret = tauxInteret;
        this.plafondDepot = plafondDepot;
    }

    public double getTauxInteret() {
        return tauxInteret;
    }

    public void setTauxInteret(double tauxInteret) {
        this.tauxInteret = tauxInteret;
    }

    public double getPlafondDepot() {
        return plafondDepot;
    }

    public void setPlafondDepot(double plafondDepot) {
        this.plafondDepot = plafondDepot;
    }

    @Override
    public String toString() {
        return String.format("CompteEpargne[ID=%d, Code='%s', Solde=%.2f DH, Taux=%.2f%%, Plafond=%.2f DH, Créé=%s]",
                getId(), getCode(), getSolde(), (tauxInteret * 100), plafondDepot, getDateCreation());
    }
}
