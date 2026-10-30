package com.tp.model.joined;

import jakarta.persistence.*;
import java.time.LocalDate;

/**
 * Super-classe de la Stratégie 2 : JOINED (Une table par sous-classe avec jointure).
 * 
 * La table 'comptes_joined' héberge les attributs communs à tous les comptes bancaires.
 * Chaque sous-classe possède sa propre table, reliée à 'comptes_joined' par une clé étrangère
 * servant simultanément de clé primaire.
 */
@Entity
@Table(name = "comptes_joined")
@Inheritance(strategy = InheritanceType.JOINED)
public class Compte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code", nullable = false, unique = true, length = 30)
    private String code;

    @Column(name = "solde", nullable = false)
    private double solde;

    @Column(name = "date_creation", nullable = false)
    private LocalDate dateCreation;

    public Compte() {
    }

    public Compte(String code, double solde, LocalDate dateCreation) {
        this.code = code;
        this.solde = solde;
        this.dateCreation = dateCreation;
    }

    public Compte(Long id, String code, double solde, LocalDate dateCreation) {
        this.id = id;
        this.code = code;
        this.solde = solde;
        this.dateCreation = dateCreation;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public double getSolde() {
        return solde;
    }

    public void setSolde(double solde) {
        this.solde = solde;
    }

    public LocalDate getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDate dateCreation) {
        this.dateCreation = dateCreation;
    }

    @Override
    public String toString() {
        return String.format("Compte[ID=%d, Code='%s', Solde=%.2f DH, Créé le=%s]",
                id, code, solde, dateCreation);
    }
}
