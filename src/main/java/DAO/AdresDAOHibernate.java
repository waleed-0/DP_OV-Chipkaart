package main.java.DAO;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;

import main.java.POJO.Adres;
import main.java.POJO.Reiziger;

import java.util.List;

public class AdresDAOHibernate
        implements AdresDAO {

    private EntityManagerFactory emf;

    public AdresDAOHibernate(
            EntityManagerFactory emf) {

        this.emf = emf;
    }

    @Override
    public boolean save(
            Adres adres) {

        if (adres == null ||
                adres.getReiziger() == null) {

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
                    em.getReference(
                            Reiziger.class,
                            adres.getReiziger().getId()
                    );

            adres.setReiziger(
                    managedReiziger
            );

            managedReiziger.setAdres(
                    adres
            );

            em.persist(
                    adres
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
            Adres adres) {

        if (adres == null ||
                adres.getReiziger() == null) {

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
                    em.getReference(
                            Reiziger.class,
                            adres.getReiziger().getId()
                    );


            adres.setReiziger(
                    managedReiziger
            );

            Adres managedAdres =
                    em.merge(
                            adres
                    );

            managedReiziger.setAdres(
                    managedAdres
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
            Adres adres) {

        if (adres == null) {
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

            Adres managedAdres =
                    em.find(
                            Adres.class,
                            adres.getId()
                    );

            if (managedAdres != null) {

                Reiziger managedReiziger =
                        managedAdres.getReiziger();

                if (managedReiziger != null) {

                    managedReiziger.setAdres(
                            null
                    );
                }

                em.remove(
                        managedAdres
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
    public Adres findById(
            int id) {

        EntityManager em =
                emf.createEntityManager();

        Adres adres =
                null;

        try {

            List<Adres> resultaten =
                    em.createQuery(
                                    "SELECT a " +
                                            "FROM Adres a " +
                                            "LEFT JOIN FETCH a.reiziger " +
                                            "WHERE a.adres_id = :adresId",
                                    Adres.class
                            )
                            .setParameter(
                                    "adresId",
                                    id
                            )
                            .getResultList();

            if (!resultaten.isEmpty()) {

                adres =
                        resultaten.get(0);
            }

        } finally {

            em.close();
        }

        return adres;
    }

    @Override
    public Adres findByReiziger(
            Reiziger reiziger) {

        Adres adres =
                null;

        if (reiziger != null) {

            EntityManager em =
                    emf.createEntityManager();

            try {

                List<Adres> resultaten =
                        em.createQuery(
                                        "SELECT a " +
                                                "FROM Adres a " +
                                                "LEFT JOIN FETCH a.reiziger " +
                                                "WHERE a.reiziger.reiziger_id = :reizigerId",
                                        Adres.class
                                )
                                .setParameter(
                                        "reizigerId",
                                        reiziger.getId()
                                )
                                .getResultList();

                if (!resultaten.isEmpty()) {

                    adres =
                            resultaten.get(0);
                }

            } finally {

                em.close();
            }
        }

        return adres;
    }

    @Override
    public List<Adres> findAll() {

        EntityManager em =
                emf.createEntityManager();

        List<Adres> adressen;

        try {

            adressen =
                    em.createQuery(
                                    "SELECT a " +
                                            "FROM Adres a " +
                                            "LEFT JOIN FETCH a.reiziger",
                                    Adres.class
                            )
                            .getResultList();

        } finally {

            em.close();
        }

        return adressen;
    }
}