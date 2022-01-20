/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import data.Racun;
import data.Stavka;

/**
 *
 * @author Tijana Lakic
 */
public class StavkaService {

    public static Stavka stavka;
    public static double ukupnaCijena;
    public static double vrijednostPDV;
    public static double ukupnoZaUplatu;

    public static Boolean dodajStavku(String nazivProizvoda, Integer kolicina, Double cijena) {

        //Double temp=kolicina.doubleValue();
        if (nazivProizvoda != null && kolicina != 0 && cijena != 0 && !nazivProizvoda.trim().isEmpty()&&
                !nazivProizvoda.equals("") && !cijena.isNaN() && !kolicina.toString().equals("") && !cijena.toString().equals("")) {
            stavka = new Stavka(nazivProizvoda, kolicina, cijena);
            ukupnaCijena += cijena * kolicina;
            vrijednostPDV = ukupnaCijena * 0.17;
            ukupnoZaUplatu = ukupnaCijena + vrijednostPDV;
            System.out.println("service.StavkaService.dodajStavku()");
            return true;
        }

        return false;

    }

    public static Boolean ukloniStavku(Stavka stavka) {
        double cijena = stavka.getCijena() * stavka.getKolicina();
        ukupnaCijena -= cijena;
        double vrijednostPDVStavke = cijena * 0.17;
        vrijednostPDV -= vrijednostPDVStavke;
        ukupnoZaUplatu -= cijena + vrijednostPDVStavke;
        return true;
    }

    public static Boolean ukloniSveStavke() {
        ukupnaCijena = 0;
        vrijednostPDV = 0;
        ukupnoZaUplatu = 0;
        return true;
    }
}
