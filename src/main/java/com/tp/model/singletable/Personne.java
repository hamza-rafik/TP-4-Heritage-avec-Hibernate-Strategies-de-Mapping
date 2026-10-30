package com.tp.model.singletable;

import jakarta.persistence.*;

/**
 * Super-classe de la Stratégie 1 : SINGLE_TABLE (Une seule table par hiérarchie).
 * 
 * Dans cette stratégie, toutes les entités de la hiérarchie (Personne, Etudiant, Professeur)
 * sont stockées dans une seule table 'personnes_single_table'.
 * Une colonne discriminante 'type_personne' est utilisée pour identifier le sous-type concret.
 */
@Entity
@Table(name = "personnes_single_table")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type_personne", discriminatorType = DiscriminatorType.STRING, length = 30)
public class Personne {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nom", nullable = false, length = 60)
    private String nom;

    @Column(name = "prenom", nullable = false, length = 60)
    private String prenom;

    @Column(name = "email", nullable = false, unique = true, length = 100)
    private String email;

    @Column(name = "telephone", length = 30)
    private String telephone;

    public Personne() {
    }

    public Personne(String nom, String prenom, String email, String telephone) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.telephone = telephone;
    }

    public Personne(Long id, String nom, String prenom, String email, String telephone) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.telephone = telephone;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    @Override
    public String toString() {
        return String.format("Personne[ID=%d, Nom='%s %s', Email='%s', Tél='%s']",
                id, prenom, nom, email, telephone);
    }
}
