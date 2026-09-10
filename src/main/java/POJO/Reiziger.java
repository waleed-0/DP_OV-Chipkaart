package main.java.POJO;

import java.sql.Date;

public class Reiziger {

    private int reiziger_id;
    private String voorletters;
    private String tussenvoegsel;
    private String achternaam;
    private Date geboortedatum;
    private Adres adres;

    public Reiziger() {
    }

    public Reiziger(
            int reiziger_id,
            String voorletters,
            String tussenvoegsel,
            String achternaam,
            Date geboortedatum) {

        this.reiziger_id =
                reiziger_id;

        this.voorletters =
                voorletters;

        this.tussenvoegsel =
                tussenvoegsel;

        this.achternaam =
                achternaam;

        this.geboortedatum =
                geboortedatum;
    }

    public Reiziger(
            int reiziger_id,
            String voorletters,
            String tussenvoegsel,
            String achternaam,
            Date geboortedatum,
            Adres adres) {

        this.reiziger_id =
                reiziger_id;

        this.voorletters =
                voorletters;

        this.tussenvoegsel =
                tussenvoegsel;

        this.achternaam =
                achternaam;

        this.geboortedatum =
                geboortedatum;

        this.adres =
                adres;
    }

    public int getId() {
        return reiziger_id;
    }

    public void setId(int id) {
        this.reiziger_id =
                id;
    }

    public String getVoorletters() {
        return voorletters;
    }

    public void setVoorletters(
            String voorletters) {

        this.voorletters =
                voorletters;
    }

    public String getTussenvoegsel() {
        return tussenvoegsel;
    }

    public void setTussenvoegsel(
            String tussenvoegsel) {

        this.tussenvoegsel =
                tussenvoegsel;
    }

    public String getAchternaam() {
        return achternaam;
    }

    public void setAchternaam(
            String achternaam) {

        this.achternaam =
                achternaam;
    }

    public Date getGeboortedatum() {
        return geboortedatum;
    }

    public void setGeboortedatum(
            Date geboortedatum) {

        this.geboortedatum =
                geboortedatum;
    }

    public Adres getAdres() {
        return adres;
    }

    public void setAdres(
            Adres adres) {

        this.adres =
                adres;
    }

    @Override
    public String toString() {

        String naam =
                voorletters;

        if (tussenvoegsel != null &&
                !tussenvoegsel.isEmpty()) {

            naam +=
                    " " +
                            tussenvoegsel;
        }

        naam +=
                " " +
                        achternaam;

        return "Reiziger {" +
                "#" + reiziger_id +
                " " + naam +
                ", geb. " +
                geboortedatum +
                (adres != null
                        ? ", " + adres
                        : "") +
                "}";
    }
}