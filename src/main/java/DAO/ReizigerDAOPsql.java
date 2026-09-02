package DAO;

import POJO.Reiziger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReizigerDAOPsql implements ReizigerDAO {

    private Connection conn;

    public ReizigerDAOPsql(Connection conn) {
        this.conn = conn;
    }

    @Override
    public boolean save(Reiziger reiziger) {
        String query = "INSERT INTO reiziger (id, voorletters, tussenvoegsel, achternaam, geboortedatum) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement statement = conn.prepareStatement(query)) {

            statement.setInt(1, reiziger.getId());
            statement.setString(2, reiziger.getVoorletters());
            statement.setString(3, reiziger.getTussenvoegsel());
            statement.setString(4, reiziger.getAchternaam());
            statement.setDate(5, reiziger.getGeboortedatum());

            int result = statement.executeUpdate();

            return result > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Reiziger reiziger) {
        String query = "UPDATE reiziger " +
                "SET voorletters = ?, tussenvoegsel = ?, achternaam = ?, geboortedatum = ? " +
                "WHERE id = ?";

        try (PreparedStatement statement = conn.prepareStatement(query)) {

            statement.setString(1, reiziger.getVoorletters());
            statement.setString(2, reiziger.getTussenvoegsel());
            statement.setString(3, reiziger.getAchternaam());
            statement.setDate(4, reiziger.getGeboortedatum());
            statement.setInt(5, reiziger.getId());

            int result = statement.executeUpdate();

            return result > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(Reiziger reiziger) {
        String query = "DELETE FROM reiziger WHERE id = ?";

        try (PreparedStatement statement = conn.prepareStatement(query)) {

            statement.setInt(1, reiziger.getId());

            int result = statement.executeUpdate();

            return result > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Reiziger findById(int id) {
        String query = "SELECT id, voorletters, tussenvoegsel, achternaam, geboortedatum " +
                "FROM reiziger WHERE id = ?";

        try (PreparedStatement statement = conn.prepareStatement(query)) {

            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {

                    int reizigerId = resultSet.getInt("id");
                    String voorletters = resultSet.getString("voorletters");
                    String tussenvoegsel = resultSet.getString("tussenvoegsel");
                    String achternaam = resultSet.getString("achternaam");
                    java.sql.Date geboortedatum = resultSet.getDate("geboortedatum");

                    return new Reiziger(
                            reizigerId,
                            voorletters,
                            tussenvoegsel,
                            achternaam,
                            geboortedatum.toLocalDate()
                    );
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<Reiziger> findByGbdatum(String datum) {

        List<Reiziger> reizigers = new ArrayList<>();

        String query = "SELECT id, voorletters, tussenvoegsel, achternaam, geboortedatum " +
                "FROM reiziger WHERE geboortedatum = ?";

        try (PreparedStatement statement = conn.prepareStatement(query)) {

            statement.setDate(1, java.sql.Date.valueOf(datum));

            try (ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {

                    int reizigerId = resultSet.getInt("id");
                    String voorletters = resultSet.getString("voorletters");
                    String tussenvoegsel = resultSet.getString("tussenvoegsel");
                    String achternaam = resultSet.getString("achternaam");
                    java.sql.Date geboortedatum = resultSet.getDate("geboortedatum");

                    reizigers.add(
                            new Reiziger(
                                    reizigerId,
                                    voorletters,
                                    tussenvoegsel,
                                    achternaam,
                                    geboortedatum.toLocalDate()
                            )
                    );
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return reizigers;
    }

    @Override
    public List<Reiziger> findAll() {

        List<Reiziger> reizigers = new ArrayList<>();

        String query = "SELECT id, voorletters, tussenvoegsel, achternaam, geboortedatum " +
                "FROM reiziger";

        try (PreparedStatement statement = conn.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {

                int reizigerId = resultSet.getInt("id");
                String voorletters = resultSet.getString("voorletters");
                String tussenvoegsel = resultSet.getString("tussenvoegsel");
                String achternaam = resultSet.getString("achternaam");
                java.sql.Date geboortedatum = resultSet.getDate("geboortedatum");

                reizigers.add(
                        new Reiziger(
                                reizigerId,
                                voorletters,
                                tussenvoegsel,
                                achternaam,
                                geboortedatum.toLocalDate()
                        )
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return reizigers;
    }
}