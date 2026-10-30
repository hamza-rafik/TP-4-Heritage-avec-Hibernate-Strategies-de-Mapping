package com.tp.model.joined;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import java.time.LocalDate;

/**
 * Sous-classe CompteCourant dans la Stratégie JOINED.
 * 
 * Sa table 'comptes_courants' contient uniquement 'compte_id', 'decouvert_autorise'
 * et 'frais_tenue_compte'. La colonne 'compte_id' référence 'comptes_joined(id)'.
 */
@Entity
@Table(name = "comptes_courants")
@PrimaryKeyJoinColumn(name = "compte_id")
public class CompteCourant extends Compte {

    @Column(name = "decouvert_autorise", nullable = false)
    private double decouvertAutorise;

    @Column(name = "frais_tenue_compte", nullable = false)
    private double fraisTenueCompte;

    public CompteCourant() {
        super();
    }

    public CompteCourant(String code, double solde, LocalDate dateCreation,
                         double decouvertAutorise, double fraisTenueCompte) {
        super(code, solde, dateCreation);
        this.decouvertAutorise = decouvertAutorise;
        this.fraisTenueCompte = fraisTenueCompte;
    }

    public CompteCourant(Long id, String code, double solde, LocalDate dateCreation,
                         double decouvertAutorise, double fraisTenueCompte) {
        super(id, code, solde, dateCreation);
        this.decouvertAutorise = decouvertAutorise;
        this.fraisTenueCompte = fraisTenueCompte;
    }

    public double getDecouvertAutorise() {
        return decouvertAutorise;
    }

    public void setDecouvertAutorise(double decouvertAutorise) {
        this.decouvertAutorise = decouvertAutorise;
    }

    public double getFraisTenueCompte() {
        return fraisTenueCompte;
    }

    public void setFraisTenueCompte(double fraisTenueCompte) {
        this.fraisTenueCompte = fraisTenueCompte;
    }

    @Override
    public String toString() {
        return String.format("CompteCourant[ID=%d, Code='%s', Solde=%.2f DH, DécouvertMax=%.2f DH, Frais=%.2f DH, Créé=%s]",
                getId(), getCode(), getSolde(), decouvertAutorise, fraisTenueCompte, getDateCreation());
    }
}
