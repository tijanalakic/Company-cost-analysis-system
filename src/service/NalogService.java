/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import static analizatroskova.FXMLAnaliticarController.ROOT_RESOURCE;
import data.Nalog;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import utils.SerializationUtils;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.MyAlert;
import static utils.SerializationUtils.readObject;

/**
 *
 * @author Tijana Lakic
 */
public class NalogService {

    public static Properties properties = new Properties();

    public static Nalog nalog;
    public static ArrayList<Nalog> nalozi = new ArrayList<Nalog>();
    public static Nalog prijavljeniNalog;
    private static String naloziPath;

    static {
        naloziPath = utils.Utils.PROPERTIES.getProperty("NALOZI_FILE_PATH");
        nalozi = SerializationUtils.readObject(naloziPath);

    }

    public static Boolean login(String korisnickoIme, String lozinka) {

        for (Nalog nalog : nalozi) {
            if (nalog.getKorisnickoIme().equals(korisnickoIme)) {
                if (nalog.checkPassword(lozinka)) {
                    prijavljeniNalog = nalog;
                    return Boolean.TRUE;

                } else {
                    MyAlert.display("Greska", "Pogresno unesena lozinka", "error");
                    return Boolean.FALSE;
                }
            }

        }
        MyAlert.display("Greska", "Ne postoji korisnicki nalog", "error");
        return Boolean.FALSE;

    }

    public static Boolean dodajNalog(String ime, String prezime, String korisnickoIme, String lozinka, String korisnickaGrupa) {

        if (ime != null && prezime != null && korisnickoIme != null && lozinka != null && korisnickaGrupa != null &&
                !ime.equals("") && !prezime.equals("") && !korisnickoIme.equals("") && !lozinka.equals("") && !korisnickaGrupa.equals("")) {
            nalog = new Nalog(ime, prezime, korisnickoIme, lozinka, korisnickaGrupa);
            nalozi.add(nalog);
            return true;
        }

        return false;
    }

    public static String getKorisnickaGrupa() {

        return prijavljeniNalog.getKorisnickaGrupa();

    }

    public static void ukloniNalog(Nalog nalog) {
        nalozi.remove(nalog);
    }

    public static void sacuvajNaloge() {
        SerializationUtils.save(nalozi, naloziPath);
    }
}
