package com.tp.model.tableperclass;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/**
 * Sous-classe concrète Moto dans la Stratégie TABLE_PER_CLASS.
 * 
 * La table 'motos_table_per_class' contient TOUTES les colonnes :
 * id, immatriculation, marque, modele, prix (héritées) ainsi que
 * cylindree et type_transmission (propres à la moto).
 */
@Entity
@Table(name = "motos_table_per_class")
public class Moto extends Vehicule {

    @Column(name = "cylindree", nullable = false)
    private int cylindree;

    @Column(name = "type_transmission", length = 30)
    private String typeTransmission;

    public Moto() {
        super();
    }

    public Moto(String immatriculation, String marque, String modele, double prix,
                int cylindree, String typeTransmission) {
        super(immatriculation, marque, modele, prix);
        this.cylindree = cylindree;
        this.typeTransmission = typeTransmission;
    }

    public Moto(Long id, String immatriculation, String marque, String modele, double prix,
                int cylindree, String typeTransmission) {
        super(id, immatriculation, marque, modele, prix);
        this.cylindree = cylindree;
        this.typeTransmission = typeTransmission;
    }

    public int getCylindree() {
        return cylindree;
    }

    public void setCylindree(int cylindree) {
        this.cylindree = cylindree;
    }

    public String getTypeTransmission() {
        return typeTransmission;
    }

    public void setTypeTransmission(String typeTransmission) {
        this.typeTransmission = typeTransmission;
    }

    @Override
    public String toString() {
        return String.format("Moto[ID=%d, Immat='%s', Marque='%s %s', Prix=%.2f DH, Cylindrée=%d cc, Transmission='%s']",
                getId(), getImmatriculation(), getMarque(), getModele(), getPrix(),
                cylindree, typeTransmission);
    }
}
