package com.tp.dao;

import com.tp.model.singletable.Etudiant;
import com.tp.model.singletable.Personne;
import com.tp.model.singletable.Professeur;
import com.tp.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.Collections;
import java.util.List;

/**
 * DAO pour la hiérarchie Personne (Stratégie SINGLE_TABLE).
 * Démontre les requêtes polymorphes et spécifiques sur la table unique 'personnes_single_table'.
 */
public class PersonneDao implements IDao<Personne> {

    @Override
    public boolean create(Personne o) {
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            session.persist(o);
            tx.commit();
            System.out.printf("  [PersonneDao] Entité persistée : ID=%d, Classe=%s%n",
                    o.getId(), o.getClass().getSimpleName());
            return true;
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                try { tx.rollback(); } catch (Exception ignored) {}
            }
            System.err.println("  [PersonneDao Error] Échec de création : " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            if (session != null && session.isOpen()) session.close();
        }
    }

    @Override
    public boolean update(Personne o) {
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            session.merge(o);
            tx.commit();
            System.out.printf("  [PersonneDao] Entité mise à jour : ID=%d%n", o.getId());
            return true;
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                try { tx.rollback(); } catch (Exception ignored) {}
            }
            System.err.println("  [PersonneDao Error] Échec de mise à jour : " + e.getMessage());
            return false;
        } finally {
            if (session != null && session.isOpen()) session.close();
        }
    }

    @Override
    public boolean delete(Personne o) {
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            Personne toDelete = session.get(Personne.class, o.getId());
            if (toDelete != null) {
                session.remove(toDelete);
                tx.commit();
                System.out.printf("  [PersonneDao] Entité supprimée : ID=%d%n", o.getId());
                return true;
            }
            return false;
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                try { tx.rollback(); } catch (Exception ignored) {}
            }
            System.err.println("  [PersonneDao Error] Échec de suppression : " + e.getMessage());
            return false;
        } finally {
            if (session != null && session.isOpen()) session.close();
        }
    }

    @Override
    public Personne findById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Personne.class, id);
        } catch (Exception e) {
            System.err.println("  [PersonneDao Error] findById : " + e.getMessage());
            return null;
        }
    }

    @Override
    public List<Personne> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Requête polymorphe : ramène à la fois les Étudiants et les Professeurs
            return session.createQuery("from Personne order by id", Personne.class).list();
        } catch (Exception e) {
            System.err.println("  [PersonneDao Error] findAll : " + e.getMessage());
            return Collections.emptyList();
        }
    }

    /**
     * Requête ciblée sur la sous-classe Etudiant.
     * Hibernate filtre automatiquement sur le discriminateur : WHERE type_personne = 'ETUDIANT'.
     */
    public List<Etudiant> findAllEtudiants() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Etudiant order by id", Etudiant.class).list();
        } catch (Exception e) {
            System.err.println("  [PersonneDao Error] findAllEtudiants : " + e.getMessage());
            return Collections.emptyList();
        }
    }

    /**
     * Requête ciblée sur la sous-classe Professeur.
     * Hibernate filtre automatiquement sur le discriminateur : WHERE type_personne = 'PROFESSEUR'.
     */
    public List<Professeur> findAllProfesseurs() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Professeur order by id", Professeur.class).list();
        } catch (Exception e) {
            System.err.println("  [PersonneDao Error] findAllProfesseurs : " + e.getMessage());
            return Collections.emptyList();
        }
    }

    public Personne findByEmail(String email) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Personne> query = session.createQuery("from Personne where email = :email", Personne.class);
            query.setParameter("email", email);
            return query.uniqueResult();
        } catch (Exception e) {
            System.err.println("  [PersonneDao Error] findByEmail : " + e.getMessage());
            return null;
        }
    }
}
