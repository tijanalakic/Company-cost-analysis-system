/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import javafx.collections.ObservableList;

/**
 *
 * @author Tijana Lakic
 */
public class Racun implements Serializable {

    private String nazivKupca;
    private LocalDate datumKupovine;
    private ArrayList<Stavka> stavke = new ArrayList<Stavka>();
    private String poslovnica;
    private double ukupnoRacun = 0;
    private double vrijednostPDV = 0;
    private double ukupnoZaUplatu = 0;

    public Racun() {
    }

    public Racun(String nazivKupca, String poslovnica, LocalDate datumKupovine, ArrayList<Stavka> stavke) {

        this.nazivKupca = nazivKupca;
        this.datumKupovine = datumKupovine;
        this.poslovnica = poslovnica;
        this.stavke = stavke;

        racunajUkupno(stavke);
    }

    /**
     *
     * @param nazivProizvoda
     * @param kolicina
     * @param cijena
     */
   
    public void racunajUkupno(ArrayList<Stavka> stavke) {
        for (Stavka s : stavke) {

            ukupnoRacun += s.getUkupno();
        }
        vrijednostPDV = ukupnoRacun * 0.17;
        ukupnoZaUplatu = vrijednostPDV + ukupnoRacun;
    }

    /**
     * @return the nazivKupca
     */
    public String getNazivKupca() {
        return nazivKupca;
    }

    /**
     * @param nazivKupca the nazivKupca to set
     */
    public void setNazivKupca(String nazivKupca) {
        this.nazivKupca = nazivKupca;
    }

    /**
     * @return the datumKupovine
     */
    public LocalDate getDatumKupovine() {
        return datumKupovine;
    }

    /**
     * @param datumKupovine the datumKupovine to set
     */
    public void setDatumKupovine(LocalDate datumKupovine) {
        this.datumKupovine = datumKupovine;
    }

    /**
     * @return the stavke
     */
    public ArrayList<Stavka> getStavke() {
        return stavke;
    }

    /**
     * @return the poslovnica
     */
    public String getPoslovnica() {
        return poslovnica;
    }

    /**
     * @param poslovnica the poslovnica to set
     */
    public void setPoslovnica(String poslovnica) {
        this.poslovnica = poslovnica;
    }

    @Override
    public String toString() {

        return getNazivKupca() + " " + getPoslovnica() + " " + getDatumKupovine() + 
                " " + getUkupnoRacun() + " " + getVrijednostPDV() + " " + getUkupnoZaUplatu();
    }

    /**
     * @param stavke the stavke to set
     */
    public void setStavke(ArrayList<Stavka> stavke) {
        this.stavke = stavke;
        racunajUkupno(stavke);
    }

    /**
     * @return the ukupnoRacun
     */
    public double getUkupnoRacun() {

        return ukupnoRacun;
    }

    /**
     * @param ukupnoRacun the ukupnoRacun to set
     */
    public void setUkupnoRacun(double ukupnoRacun) {
        this.ukupnoRacun = ukupnoRacun;
    }

    /**
     * @return the vrijednostPDV
     */
    public double getVrijednostPDV() {
        return vrijednostPDV;
    }

    /**
     * @param vrijednostPDV the vrijednostPDV to set
     */
    public void setVrijednostPDV(double vrijednostPDV) {
        this.vrijednostPDV = vrijednostPDV;
    }

    /**
     * @return the ukupnoZaUplatu
     */
    public double getUkupnoZaUplatu() {
        return ukupnoZaUplatu;
    }

    /**
     * @param ukupnoZaUplatu the ukupnoZaUplatu to set
     */
    public void setUkupnoZaUplatu(double ukupnoZaUplatu) {
        this.ukupnoZaUplatu = ukupnoZaUplatu;
    }

}
