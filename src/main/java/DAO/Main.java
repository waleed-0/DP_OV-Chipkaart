package DAO;

import DAO.ReizigerDAOPsql;
import POJO.Reiziger;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class Main {

    private static final String Url = "jdbc:postgresql://localhost:5432/ovchip";
    private static final String User = "postgres";
    private static final String Password = "135795";

    public static void main(String[] args) throws SQLException {

        Connection connection = getConnection();

        if (connection != null) {

            testReizigerDAO((ReizigerDAO) new ReizigerDAOPsql(connection));

            closeConnection(connection);

        } else {

            System.out.println("Er is een fout opgetreden tijdens het maken van de databaseverbinding.");
        }
    }

    private static Connection getConnection() {

        Connection connection = null;

        try {

            connection = DriverManager.getConnection(Url, User, Password);
            System.out.println("Databaseverbinding is ok");

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return connection;
    }

    private static void closeConnection(Connection connection) {

        if (connection != null) {

            try {

                connection.close();
                System.out.println("Databaseverbinding gesloten.");

            } catch (SQLException e) {

                e.printStackTrace();
            }
        }
    }

    private static void testReizigerDAO(ReizigerDAO rdao) throws SQLException {

        System.out.println("\n---------- Test ReizigerDAO -------------");

        // Haal alle reizigers op uit de database
        List<Reiziger> reizigers = rdao.findAll();

        System.out.println("[Test] ReizigerDAO.findAll() geeft de volgende reizigers:");

        for (Reiziger r : reizigers) {
            System.out.println(r);
        }

        System.out.println();

        // Maak een nieuwe reiziger aan en persisteer deze in de database
        String gbdatum = "1981-03-14";

        Reiziger sietske = new Reiziger(
                77,
                "S",
                "",
                "Boers",
                java.sql.Date.valueOf(gbdatum).toLocalDate()
        );

        System.out.print(
                "[Test] Eerst " + reizigers.size()
                        + " reizigers, na ReizigerDAO.save() "
        );

        rdao.save(sietske);

        reizigers = rdao.findAll();

        System.out.println(reizigers.size() + " reizigers\n");


        // Test ReizigerDAO.update()
        System.out.println("Update reiziger met ID 77");

        sietske.setAchternaam("Boers-Test");

        boolean updated = rdao.update(sietske);

        if (updated) {

            System.out.println(
                    "Reiziger met ID 77 is succesvol geüpdatet."
            );

        } else {

            System.out.println(
                    "Fout bij het updaten van reiziger met ID 77."
            );
        }


        // Test ReizigerDAO.findByGbdatum()
        List<Reiziger> rgd =
                rdao.findByGbdatum("1981-03-14");

        System.out.println(
                "Zoek reiziger die is geboren op 1981-03-14:"
        );

        for (Reiziger r : rgd) {
            System.out.println(r);
        }

        System.out.println();


        // Test ReizigerDAO.findById()
        System.out.println("Zoek reiziger met ID 77");

        Reiziger r = rdao.findById(77);

        if (r != null) {

            System.out.println(
                    "Gevonden reiziger met ID 77: " + r
            );

        } else {

            System.out.println(
                    "Reiziger met ID 77 is niet gevonden."
            );
        }


        // Test ReizigerDAO.delete()
        System.out.println("Verwijder reiziger met ID 77");

        boolean deleted = rdao.delete(sietske);

        if (deleted) {

            System.out.println(
                    "Reiziger met ID 77 is succesvol verwijderd."
            );

        } else {

            System.out.println(
                    "Fout bij het verwijderen van reiziger met ID 77."
            );
        }

        reizigers = rdao.findAll();

        System.out.println(
                "Na verwijdering zijn er "
                        + reizigers.size()
                        + " reizigers over."
        );
    }
}