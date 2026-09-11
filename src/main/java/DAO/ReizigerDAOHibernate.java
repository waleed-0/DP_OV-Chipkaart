package main.java.DAO;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;

import main.java.POJO.Reiziger;

import java.sql.Date;
import java.util.List;

public class ReizigerDAOHibernate
        implements ReizigerDAO {

    private EntityManagerFactory emf;

    public ReizigerDAOHibernate(
            EntityManagerFactory emf) {

        this.emf = emf;
    }

    @Override
    public boolean save(
            Reiziger reiziger) {

        if (reiziger == null) {
            return false;
        }

        EntityManager em =
                emf.createEntityManager();

        EntityTransaction transaction =
                em.getTransaction();

        boolean gelukt =
                false;

        try {

            transaction.begin();

            em.persist(
                    reiziger
            );

            transaction.commit();

            gelukt =
                    true;

        } catch (Exception e) {

            if (transaction.isActive()) {
                transaction.rollback();
            }

            e.printStackTrace();

        } finally {

            em.close();
        }

        return gelukt;
    }

    @Override
    public boolean update(
            Reiziger reiziger) {

        if (reiziger == null) {
            return false;
        }

        EntityManager em =
                emf.createEntityManager();

        EntityTransaction transaction =
                em.getTransaction();

        boolean gelukt =
                false;

        try {

            transaction.begin();

            em.merge(
                    reiziger
            );

            transaction.commit();

            gelukt =
                    true;

        } catch (Exception e) {

            if (transaction.isActive()) {
                transaction.rollback();
            }

            e.printStackTrace();

        } finally {

            em.close();
        }

        return gelukt;
    }

    @Override
    public boolean delete(
            Reiziger reiziger) {

        if (reiziger == null) {
            return false;
        }

        EntityManager em =
                emf.createEntityManager();

        EntityTransaction transaction =
                em.getTransaction();

        boolean gelukt =
                false;

        try {

            transaction.begin();

            Reiziger managedReiziger =
                    em.find(
                            Reiziger.class,
                            reiziger.getId()
                    );

            if (managedReiziger != null) {


                if (managedReiziger.getAdres() != null) {

                    transaction.rollback();

                    return false;
                }

                em.remove(
                        managedReiziger
                );

                transaction.commit();

                gelukt =
                        true;

            } else {

                transaction.rollback();
            }

        } catch (Exception e) {

            if (transaction.isActive()) {
                transaction.rollback();
            }

            e.printStackTrace();

        } finally {

            em.close();
        }

        return gelukt;
    }

    @Override
    public Reiziger findById(
            int id) {

        EntityManager em =
                emf.createEntityManager();

        Reiziger reiziger =
                null;

        try {

            List<Reiziger> resultaten =
                    em.createQuery(
                                    "SELECT r " +
                                            "FROM Reiziger r " +
                                            "LEFT JOIN FETCH r.adres " +
                                            "WHERE r.reiziger_id = :reizigerId",
                                    Reiziger.class
                            )
                            .setParameter(
                                    "reizigerId",
                                    id
                            )
                            .getResultList();

            if (!resultaten.isEmpty()) {

                reiziger =
                        resultaten.get(0);
            }

        } finally {

            em.close();
        }

        return reiziger;
    }

    @Override
    public List<Reiziger> findByGbdatum(
            String datum) {

        EntityManager em =
                emf.createEntityManager();

        List<Reiziger> reizigers;

        try {

            Date geboortedatum =
                    Date.valueOf(
                            datum
                    );

            reizigers =
                    em.createQuery(
                                    "SELECT r " +
                                            "FROM Reiziger r " +
                                            "LEFT JOIN FETCH r.adres " +
                                            "WHERE r.geboortedatum = :datum",
                                    Reiziger.class
                            )
                            .setParameter(
                                    "datum",
                                    geboortedatum
                            )
                            .getResultList();

        } finally {

            em.close();
        }

        return reizigers;
    }

    @Override
    public List<Reiziger> findAll() {

        EntityManager em =
                emf.createEntityManager();

        List<Reiziger> reizigers;

        try {

            reizigers =
                    em.createQuery(
                                    "SELECT r " +
                                            "FROM Reiziger r " +
                                            "LEFT JOIN FETCH r.adres",
                                    Reiziger.class
                            )
                            .getResultList();

        } finally {

            em.close();
        }

        return reizigers;
    }
}