package com.tp.model.singletable;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

/**
 * Sous-classe Professeur dans la Stratégie SINGLE_TABLE.
 * 
 * Les attributs (specialite, departement, salaire) sont stockés dans la même table
 * 'personnes_single_table' avec la valeur discriminante 'PROFESSEUR'.
 */
@Entity
@DiscriminatorValue("PROFESSEUR")
public class Professeur extends Personne {

    @Column(name = "specialite", length = 100)
    private String specialite;

    @Column(name = "departement", length = 80)
    private String departement;

    @Column(name = "salaire")
    private Double salaire;

    public Professeur() {
        super();
    }

    public Professeur(String nom, String prenom, String email, String telephone,
                      String specialite, String departement, Double salaire) {
        super(nom, prenom, email, telephone);
        this.specialite = specialite;
        this.departement = departement;
        this.salaire = salaire;
    }

    public Professeur(Long id, String nom, String prenom, String email, String telephone,
                      String specialite, String departement, Double salaire) {
        super(id, nom, prenom, email, telephone);
        this.specialite = specialite;
        this.departement = departement;
        this.salaire = salaire;
    }

    public String getSpecialite() {
        return specialite;
    }

    public void setSpecialite(String specialite) {
        this.specialite = specialite;
    }

    public String getDepartement() {
        return departement;
    }

    public void setDepartement(String departement) {
        this.departement = departement;
    }

    public Double getSalaire() {
        return salaire;
    }

    public void setSalaire(Double salaire) {
        this.salaire = salaire;
    }

    @Override
    public String toString() {
        return String.format("Professeur[ID=%d, Nom='%s %s', Spécialité='%s', Département='%s', Salaire=%.2f DH]",
                getId(), getPrenom(), getNom(), specialite, departement, (salaire != null ? salaire : 0.0));
    }
}
