package com.tp.model.tableperclass;

import jakarta.persistence.*;

/**
 * Super-classe de la Stratégie 3 : TABLE_PER_CLASS (Une table par classe concrète).
 * 
 * Dans cette stratégie, aucune table n'est créée pour la super-classe si elle est abstraite,
 * ou chaque table concrète hérite de toutes les colonnes de la super-classe.
 * Les requêtes polymorphes sont résolues par Hibernate via des requêtes SQL avec 'UNION ALL'.
 * 
 * Note JPA : GenerationType.IDENTITY n'est pas autorisé avec TABLE_PER_CLASS.
 * On utilise GenerationType.SEQUENCE ou TABLE (ici TABLE generator pour compatibilité H2/MySQL).
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Vehicule {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "vehicule_gen")
    @TableGenerator(name = "vehicule_gen", table = "id_gen_vehicule", pkColumnName = "gen_name",
                    valueColumnName = "gen_val", allocationSize = 1)
    private Long id;

    @Column(name = "immatriculation", nullable = false, length = 30)
    private String immatriculation;

    @Column(name = "marque", nullable = false, length = 50)
    private String marque;

    @Column(name = "modele", nullable = false, length = 50)
    private String modele;

    @Column(name = "prix", nullable = false)
    private double prix;

    public Vehicule() {
    }

    public Vehicule(String immatriculation, String marque, String modele, double prix) {
        this.immatriculation = immatriculation;
        this.marque = marque;
        this.modele = modele;
        this.prix = prix;
    }

    public Vehicule(Long id, String immatriculation, String marque, String modele, double prix) {
        this.id = id;
        this.immatriculation = immatriculation;
        this.marque = marque;
        this.modele = modele;
        this.prix = prix;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImmatriculation() {
        return immatriculation;
    }

    public void setImmatriculation(String immatriculation) {
        this.immatriculation = immatriculation;
    }

    public String getMarque() {
        return marque;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }

    public String getModele() {
        return modele;
    }

    public void setModele(String modele) {
        this.modele = modele;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    @Override
    public String toString() {
        return String.format("Vehicule[ID=%d, Immat='%s', Marque='%s', Modèle='%s', Prix=%.2f DH]",
                id, immatriculation, marque, modele, prix);
    }
}
