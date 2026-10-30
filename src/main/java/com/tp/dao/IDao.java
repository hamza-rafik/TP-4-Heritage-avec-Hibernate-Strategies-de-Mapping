package com.tp.dao;

import java.util.List;

/**
 * Interface générique DAO définissant les opérations CRUD fondamentales.
 * @param <T> Le type de l'entité manipulée.
 */
public interface IDao<T> {

    /**
     * Enregistre une nouvelle entité en base de données.
     * @param o L'objet à persister.
     * @return true si l'opération a réussi, false sinon.
     */
    boolean create(T o);

    /**
     * Met à jour les informations d'une entité existante.
     * @param o L'objet à modifier.
     * @return true si l'opération a réussi, false sinon.
     */
    boolean update(T o);

    /**
     * Supprime une entité de la base de données.
     * @param o L'objet à supprimer.
     * @return true si l'opération a réussi, false sinon.
     */
    boolean delete(T o);

    /**
     * Recherche une entité par son identifiant unique.
     * @param id L'identifiant Long de l'entité.
     * @return L'entité trouvée ou null.
     */
    T findById(Long id);

    /**
     * Récupère l'ensemble des entités de ce type.
     * @return La liste complète des entités.
     */
    List<T> findAll();
}
