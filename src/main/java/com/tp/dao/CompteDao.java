package com.tp.dao;

import com.tp.model.joined.Compte;
import com.tp.model.joined.CompteCourant;
import com.tp.model.joined.CompteEpargne;
import com.tp.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.Collections;
import java.util.List;

/**
 * DAO pour la hiérarchie Compte (Stratégie JOINED).
 * Démontre les requêtes polymorphes avec jointures SQL automatiques (LEFT OUTER JOIN).
 */
public class CompteDao implements IDao<Compte> {

    @Override
    public boolean create(Compte o) {
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            session.persist(o);
            tx.commit();
            System.out.printf("  [CompteDao] Compte persisté : ID=%d, Code='%s', Type=%s%n",
                    o.getId(), o.getCode(), o.getClass().getSimpleName());
            return true;
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                try { tx.rollback(); } catch (Exception ignored) {}
            }
            System.err.println("  [CompteDao Error] Échec de création : " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            if (session != null && session.isOpen()) session.close();
        }
    }

    @Override
    public boolean update(Compte o) {
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            session.merge(o);
            tx.commit();
            System.out.printf("  [CompteDao] Compte mis à jour : ID=%d, Solde=%.2f DH%n", o.getId(), o.getSolde());
            return true;
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                try { tx.rollback(); } catch (Exception ignored) {}
            }
            System.err.println("  [CompteDao Error] Échec de mise à jour : " + e.getMessage());
            return false;
        } finally {
            if (session != null && session.isOpen()) session.close();
        }
    }

    @Override
    public boolean delete(Compte o) {
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            Compte toDelete = session.get(Compte.class, o.getId());
            if (toDelete != null) {
                session.remove(toDelete);
                tx.commit();
                System.out.printf("  [CompteDao] Compte supprimé : ID=%d%n", o.getId());
                return true;
            }
            return false;
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                try { tx.rollback(); } catch (Exception ignored) {}
            }
            System.err.println("  [CompteDao Error] Échec de suppression : " + e.getMessage());
            return false;
        } finally {
            if (session != null && session.isOpen()) session.close();
        }
    }

    @Override
    public Compte findById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Compte.class, id);
        } catch (Exception e) {
            System.err.println("  [CompteDao Error] findById : " + e.getMessage());
            return null;
        }
    }

    @Override
    public List<Compte> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Requête polymorphe : Hibernate exécute un LEFT OUTER JOIN avec 'comptes_courants' et 'comptes_epargnes'
            return session.createQuery("from Compte order by id", Compte.class).list();
        } catch (Exception e) {
            System.err.println("  [CompteDao Error] findAll : " + e.getMessage());
            return Collections.emptyList();
        }
    }

    public List<CompteCourant> findAllComptesCourants() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Requête ciblée : INNER JOIN entre 'comptes_joined' et 'comptes_courants'
            return session.createQuery("from CompteCourant order by id", CompteCourant.class).list();
        } catch (Exception e) {
            System.err.println("  [CompteDao Error] findAllComptesCourants : " + e.getMessage());
            return Collections.emptyList();
        }
    }

    public List<CompteEpargne> findAllComptesEpargnes() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Requête ciblée : INNER JOIN entre 'comptes_joined' et 'comptes_epargnes'
            return session.createQuery("from CompteEpargne order by id", CompteEpargne.class).list();
        } catch (Exception e) {
            System.err.println("  [CompteDao Error] findAllComptesEpargnes : " + e.getMessage());
            return Collections.emptyList();
        }
    }

    public double calculerTotalSoldes() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Double total = session.createQuery("select sum(c.solde) from Compte c", Double.class).uniqueResult();
            return total != null ? total : 0.0;
        } catch (Exception e) {
            System.err.println("  [CompteDao Error] calculerTotalSoldes : " + e.getMessage());
            return 0.0;
        }
    }
}
