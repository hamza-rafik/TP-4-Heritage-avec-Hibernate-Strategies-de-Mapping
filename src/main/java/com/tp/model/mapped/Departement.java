package com.tp.model.mapped;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/**
 * Entité concrète héritant des champs d'audit via @MappedSuperclass.
 */
@Entity
@Table(name = "departements")
public class Departement extends EntiteAudit {

    @Column(name = "code", nullable = false, unique = true, length = 30)
    private String code;

    @Column(name = "nom", nullable = false, length = 100)
    private String nom;

    @Column(name = "localisation", length = 80)
    private String localisation;

    public Departement() {
        super();
    }

    public Departement(String code, String nom, String localisation, String creePar) {
        super(creePar);
        this.code = code;
        this.nom = nom;
        this.localisation = localisation;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getLocalisation() {
        return localisation;
    }

    public void setLocalisation(String localisation) {
        this.localisation = localisation;
    }

    @Override
    public String toString() {
        return String.format("Departement[ID=%d, Code='%s', Nom='%s', Localisation='%s', Créé le=%s, Par='%s']",
                getId(), code, nom, localisation, getDateCreation(), getCreePar());
    }
}
