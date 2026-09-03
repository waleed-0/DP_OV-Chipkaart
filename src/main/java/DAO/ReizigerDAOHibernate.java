package main.java.DAO;

import POJO.Reiziger;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.ArrayList;
import java.util.List;

public class ReizigerDAOHibernate implements DAO.ReizigerDAO {

    private EntityManagerFactory entityManagerFactory;

    public ReizigerDAOHibernate() {
        entityManagerFactory =
                Persistence.createEntityManagerFactory("ovchip");
    }

    @Override
    public boolean save(Reiziger reiziger) {

        EntityManager entityManager =
                entityManagerFactory.createEntityManager();

        try {

            entityManager.getTransaction().begin();

            entityManager.persist(reiziger);

            entityManager.getTransaction().commit();

            return true;

        } catch (Exception e) {

            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }

            e.printStackTrace();

            return false;

        } finally {

            entityManager.close();
        }
    }

    @Override
    public boolean update(Reiziger reiziger) {

        EntityManager entityManager =
                entityManagerFactory.createEntityManager();

        try {

            entityManager.getTransaction().begin();

            entityManager.merge(reiziger);

            entityManager.getTransaction().commit();

            return true;

        } catch (Exception e) {

            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }

            e.printStackTrace();

            return false;

        } finally {

            entityManager.close();
        }
    }

    @Override
    public boolean delete(Reiziger reiziger) {

        EntityManager entityManager =
                entityManagerFactory.createEntityManager();

        try {

            entityManager.getTransaction().begin();

            Reiziger bestaandeReiziger =
                    entityManager.find(Reiziger.class, reiziger.getId());

            if (bestaandeReiziger != null) {

                entityManager.remove(bestaandeReiziger);

                entityManager.getTransaction().commit();

                return true;
            }

            entityManager.getTransaction().rollback();

            return false;

        } catch (Exception e) {

            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }

            e.printStackTrace();

            return false;

        } finally {

            entityManager.close();
        }
    }

    @Override
    public Reiziger findById(int id) {

        EntityManager entityManager =
                entityManagerFactory.createEntityManager();

        try {

            return entityManager.find(Reiziger.class, id);

        } catch (Exception e) {

            e.printStackTrace();

            return null;

        } finally {

            entityManager.close();
        }
    }

    @Override
    public List<Reiziger> findByGbdatum(String datum) {

        EntityManager entityManager =
                entityManagerFactory.createEntityManager();

        try {

            return entityManager.createQuery(
                            "SELECT r FROM Reiziger r " +
                                    "WHERE r.geboortedatum = :datum",
                            Reiziger.class)
                    .setParameter(
                            "datum",
                            java.sql.Date.valueOf(datum)
                    )
                    .getResultList();

        } catch (Exception e) {

            e.printStackTrace();

            return new ArrayList<>();

        } finally {

            entityManager.close();
        }
    }

    @Override
    public List<Reiziger> findAll() {

        EntityManager entityManager =
                entityManagerFactory.createEntityManager();

        try {

            return entityManager.createQuery(
                            "SELECT r FROM Reiziger r",
                            Reiziger.class)
                    .getResultList();

        } catch (Exception e) {

            e.printStackTrace();

            return new ArrayList<>();

        } finally {

            entityManager.close();
        }
    }

    public void close() {

        if (entityManagerFactory != null
                && entityManagerFactory.isOpen()) {

            entityManagerFactory.close();
        }
    }
}