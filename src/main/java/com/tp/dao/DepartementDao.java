package com.tp.dao;

import com.tp.model.mapped.Departement;
import com.tp.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.Collections;
import java.util.List;

/**
 * DAO pour l'entité Departement (héritant de @MappedSuperclass EntiteAudit).
 */
public class DepartementDao implements IDao<Departement> {

    @Override
    public boolean create(Departement o) {
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            session.persist(o);
            tx.commit();
            System.out.printf("  [DepartementDao] Département créé : ID=%d, Code='%s'%n", o.getId(), o.getCode());
            return true;
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                try { tx.rollback(); } catch (Exception ignored) {}
            }
            System.err.println("  [DepartementDao Error] Échec de création : " + e.getMessage());
            return false;
        } finally {
            if (session != null && session.isOpen()) session.close();
        }
    }

    @Override
    public boolean update(Departement o) {
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            session.merge(o);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                try { tx.rollback(); } catch (Exception ignored) {}
            }
            return false;
        } finally {
            if (session != null && session.isOpen()) session.close();
        }
    }

    @Override
    public boolean delete(Departement o) {
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            Departement toDelete = session.get(Departement.class, o.getId());
            if (toDelete != null) {
                session.remove(toDelete);
                tx.commit();
                return true;
            }
            return false;
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                try { tx.rollback(); } catch (Exception ignored) {}
            }
            return false;
        } finally {
            if (session != null && session.isOpen()) session.close();
        }
    }

    @Override
    public Departement findById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Departement.class, id);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<Departement> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Departement order by id", Departement.class).list();
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }
}
