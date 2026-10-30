package com.tp.model.mapped;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import java.time.LocalDateTime;

/**
 * Super-classe non-entité annotée avec @MappedSuperclass.
 * 
 * Les attributs (id, dateCreation, creePar) sont factorisés et hérités
 * directement dans la table de l'entité descendante 'departements'.
 * Aucune table propre n'est créée pour 'EntiteAudit', et elle ne peut pas
 * faire l'objet de requêtes polymorphes polymorphes EntityManager.
 */
@MappedSuperclass
public abstract class EntiteAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date_creation", nullable = false)
    private LocalDateTime dateCreation;

    @Column(name = "cree_par", length = 60)
    private String creePar;

    public EntiteAudit() {
        this.dateCreation = LocalDateTime.now();
        this.creePar = "SYSTEM";
    }

    public EntiteAudit(String creePar) {
        this.dateCreation = LocalDateTime.now();
        this.creePar = creePar;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }

    public String getCreePar() {
        return creePar;
    }

    public void setCreePar(String creePar) {
        this.creePar = creePar;
    }
}
