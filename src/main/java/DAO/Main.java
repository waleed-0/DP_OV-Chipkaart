package main.java.DAO;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import main.java.POJO.Adres;
import main.java.POJO.Reiziger;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public class Main {

    public static void main(
            String[] args)
            throws SQLException {

        EntityManagerFactory emf =
                Persistence.createEntityManagerFactory(
                        "ovchip"
                );

        try {

            ReizigerDAO reizigerDAO =
                    new ReizigerDAOHibernate(
                            emf
                    );

            AdresDAO adresDAO =
                    new AdresDAOHibernate(
                            emf
                    );

            testAdresDAO(
                    adresDAO,
                    reizigerDAO
            );

        } finally {

            emf.close();
        }
    }

    public static void testAdresDAO(
            AdresDAO adresDAO,
            ReizigerDAO reizigerDAO)
            throws SQLException {

        System.out.println(
                "\n========== P3H TEST =========="
        );


        Adres bestaandAdres =
                adresDAO.findById(
                        100
                );

        if (bestaandAdres != null) {

            adresDAO.delete(
                    bestaandAdres
            );
        }

        Reiziger bestaandeReiziger =
                reizigerDAO.findById(
                        100
                );

        if (bestaandeReiziger != null) {

            reizigerDAO.delete(
                    bestaandeReiziger
            );
        }


        Reiziger reiziger =
                new Reiziger(
                        100,
                        "W.",
                        null,
                        "Aldehni",
                        Date.valueOf(
                                "2002-09-17"
                        )
                );


        System.out.println(
                "\n--- Reiziger opslaan ---"
        );

        boolean reizigerOpgeslagen =
                reizigerDAO.save(
                        reiziger
                );

        System.out.println(
                "Reiziger opgeslagen: " +
                        reizigerOpgeslagen
        );



        Adres adres =
                new Adres(
                        100,
                        "3511 LX",
                        "37",
                        "Straatnaam 1",
                        "Utrecht"
                );


        reiziger.setAdres(
                adres
        );

        adres.setReiziger(
                reiziger
        );


        System.out.println(
                "\n--- Adres opslaan ---"
        );

        boolean adresOpgeslagen =
                adresDAO.save(
                        adres
                );

        System.out.println(
                "Adres opgeslagen: " +
                        adresOpgeslagen
        );


        System.out.println(
                "\n--- Adres zoeken op ID ---"
        );

        System.out.println(
                adresDAO.findById(
                        100
                )
        );


        System.out.println(
                "\n--- Adres zoeken bij Reiziger ---"
        );

        System.out.println(
                adresDAO.findByReiziger(
                        reiziger
                )
        );



        System.out.println(
                "\n--- Adres wijzigen ---"
        );

        adres.setPostcode(
                "3521 AL"
        );

        adres.setHuisnummer(
                "6A"
        );

        adres.setStraat(
                "Nieuwe Straat"
        );

        boolean adresGewijzigd =
                adresDAO.update(
                        adres
                );

        System.out.println(
                "Adres gewijzigd: " +
                        adresGewijzigd
        );

        System.out.println(
                adresDAO.findById(
                        100
                )
        );


        System.out.println(
                "\n--- Reiziger wijzigen ---"
        );

        reiziger.setVoorletters(
                "W.A."
        );

        boolean reizigerGewijzigd =
                reizigerDAO.update(
                        reiziger
                );

        System.out.println(
                "Reiziger gewijzigd: " +
                        reizigerGewijzigd
        );

        System.out.println(
                reizigerDAO.findById(
                        100
                )
        );


        System.out.println(
                "\n--- Alle adressen ---"
        );

        List<Adres> adressen =
                adresDAO.findAll();

        for (Adres a : adressen) {

            System.out.println(
                    a
            );
        }



        System.out.println(
                "\n--- Alle reizigers ---"
        );

        List<Reiziger> reizigers =
                reizigerDAO.findAll();

        for (Reiziger r : reizigers) {

            System.out.println(
                    r
            );
        }


        System.out.println(
                "\n--- Reizigers zoeken op geboortedatum ---"
        );

        List<Reiziger> gevondenReizigers =
                reizigerDAO.findByGbdatum(
                        "2002-09-17"
                );

        for (Reiziger r : gevondenReizigers) {

            System.out.println(
                    r
            );
        }

        System.out.println(
                "\n--- Adres verwijderen ---"
        );

        Adres teVerwijderenAdres =
                adresDAO.findById(
                        100
                );

        boolean adresVerwijderd =
                adresDAO.delete(
                        teVerwijderenAdres
                );

        System.out.println(
                "Adres verwijderd: " +
                        adresVerwijderd
        );



        System.out.println(
                "\n--- Reiziger verwijderen ---"
        );

        Reiziger teVerwijderenReiziger =
                reizigerDAO.findById(
                        100
                );

        boolean reizigerVerwijderd =
                reizigerDAO.delete(
                        teVerwijderenReiziger
                );

        System.out.println(
                "Reiziger verwijderd: " +
                        reizigerVerwijderd
        );


        System.out.println(
                "\n--- Controle na verwijderen ---"
        );

        System.out.println(
                "Adres 100: " +
                        adresDAO.findById(
                                100
                        )
        );

        System.out.println(
                "Reiziger 100: " +
                        reizigerDAO.findById(
                                100
                        )
        );

        System.out.println(
                "\n========== EINDE P3H TEST =========="
        );
    }
}