package com.tp.model.singletable;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

/**
 * Sous-classe Étudiant dans la Stratégie SINGLE_TABLE.
 * 
 * Les attributs spécifiques (cne, filiere, niveau) sont ajoutés comme colonnes
 * dans la table unique 'personnes_single_table'. Ces colonnes doivent autoriser
 * la valeur NULL pour les autres sous-classes.
 */
@Entity
@DiscriminatorValue("ETUDIANT")
public class Etudiant extends Personne {

    @Column(name = "cne", length = 30)
    private String cne;

    @Column(name = "filiere", length = 60)
    private String filiere;

    @Column(name = "niveau", length = 30)
    private String niveau;

    public Etudiant() {
        super();
    }

    public Etudiant(String nom, String prenom, String email, String telephone,
                    String cne, String filiere, String niveau) {
        super(nom, prenom, email, telephone);
        this.cne = cne;
        this.filiere = filiere;
        this.niveau = niveau;
    }

    public Etudiant(Long id, String nom, String prenom, String email, String telephone,
                    String cne, String filiere, String niveau) {
        super(id, nom, prenom, email, telephone);
        this.cne = cne;
        this.filiere = filiere;
        this.niveau = niveau;
    }

    public String getCne() {
        return cne;
    }

    public void setCne(String cne) {
        this.cne = cne;
    }

    public String getFiliere() {
        return filiere;
    }

    public void setFiliere(String filiere) {
        this.filiere = filiere;
    }

    public String getNiveau() {
        return niveau;
    }

    public void setNiveau(String niveau) {
        this.niveau = niveau;
    }

    @Override
    public String toString() {
        return String.format("Etudiant[ID=%d, Nom='%s %s', Email='%s', CNE='%s', Filière='%s', Niveau='%s']",
                getId(), getPrenom(), getNom(), getEmail(), cne, filiere, niveau);
    }
}
