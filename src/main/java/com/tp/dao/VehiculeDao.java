package com.tp.dao;

import com.tp.model.tableperclass.Moto;
import com.tp.model.tableperclass.Vehicule;
import com.tp.model.tableperclass.Voiture;
import com.tp.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.Collections;
import java.util.List;

/**
 * DAO pour la hiérarchie Vehicule (Stratégie TABLE_PER_CLASS).
 * Démontre les requêtes polymorphes résolues par SQL UNION ALL entre 'voitures_table_per_class' et 'motos_table_per_class'.
 */
public class VehiculeDao implements IDao<Vehicule> {

    @Override
    public boolean create(Vehicule o) {
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            session.persist(o);
            tx.commit();
            System.out.printf("  [VehiculeDao] Véhicule persisté : ID=%d, Immat='%s', Type=%s%n",
                    o.getId(), o.getImmatriculation(), o.getClass().getSimpleName());
            return true;
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                try { tx.rollback(); } catch (Exception ignored) {}
            }
            System.err.println("  [VehiculeDao Error] Échec de création : " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            if (session != null && session.isOpen()) session.close();
        }
    }

    @Override
    public boolean update(Vehicule o) {
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            session.merge(o);
            tx.commit();
            System.out.printf("  [VehiculeDao] Véhicule mis à jour : ID=%d, Prix=%.2f DH%n", o.getId(), o.getPrix());
            return true;
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                try { tx.rollback(); } catch (Exception ignored) {}
            }
            System.err.println("  [VehiculeDao Error] Échec de mise à jour : " + e.getMessage());
            return false;
        } finally {
            if (session != null && session.isOpen()) session.close();
        }
    }

    @Override
    public boolean delete(Vehicule o) {
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            Vehicule toDelete = session.get(Vehicule.class, o.getId());
            if (toDelete != null) {
                session.remove(toDelete);
                tx.commit();
                System.out.printf("  [VehiculeDao] Véhicule supprimé : ID=%d%n", o.getId());
                return true;
            }
            return false;
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                try { tx.rollback(); } catch (Exception ignored) {}
            }
            System.err.println("  [VehiculeDao Error] Échec de suppression : " + e.getMessage());
            return false;
        } finally {
            if (session != null && session.isOpen()) session.close();
        }
    }

    @Override
    public Vehicule findById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Vehicule.class, id);
        } catch (Exception e) {
            System.err.println("  [VehiculeDao Error] findById : " + e.getMessage());
            return null;
        }
    }

    @Override
    public List<Vehicule> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Requête polymorphe : Hibernate exécute un UNION ALL sur toutes les tables concrètes
            return session.createQuery("from Vehicule order by id", Vehicule.class).list();
        } catch (Exception e) {
            System.err.println("  [VehiculeDao Error] findAll : " + e.getMessage());
            return Collections.emptyList();
        }
    }

    public List<Voiture> findAllVoitures() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Requête directe sans union : SELECT sur 'voitures_table_per_class'
            return session.createQuery("from Voiture order by id", Voiture.class).list();
        } catch (Exception e) {
            System.err.println("  [VehiculeDao Error] findAllVoitures : " + e.getMessage());
            return Collections.emptyList();
        }
    }

    public List<Moto> findAllMotos() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Requête directe sans union : SELECT sur 'motos_table_per_class'
            return session.createQuery("from Moto order by id", Moto.class).list();
        } catch (Exception e) {
            System.err.println("  [VehiculeDao Error] findAllMotos : " + e.getMessage());
            return Collections.emptyList();
        }
    }

    public double calculerValeurTotaleParc() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Double total = session.createQuery("select sum(v.prix) from Vehicule v", Double.class).uniqueResult();
            return total != null ? total : 0.0;
        } catch (Exception e) {
            System.err.println("  [VehiculeDao Error] calculerValeurTotaleParc : " + e.getMessage());
            return 0.0;
        }
    }
}
