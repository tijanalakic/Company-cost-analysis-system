/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import data.Racun;
import data.Stavka;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Tijana Lakic
 */
public class RacunService {

    public static Racun racun;
    public static int counter;

    public static Boolean sacuvajRacun(String nazivKupca, String poslovnica, LocalDate datumKupovine, ArrayList<Stavka> stavke) {

        if (nazivKupca != null && !nazivKupca.trim().isEmpty()&& poslovnica != null &&
                !poslovnica.trim().isEmpty() && datumKupovine != null && !datumKupovine.toString().equals("")) {
            racun = new Racun(nazivKupca, poslovnica, datumKupovine, stavke);
            utils.SerializationUtils.save(racun, utils.Utils.PROPERTIES.
                    getProperty("SERIJALIZOVANI_RACUNI_FOLDER_PATH") + File.separator
                    + utils.Utils.SIMPLE_DATE_FORMAT.format(new Date()) + ".ser");
            return true;
        }
        return false;

    }

    public static Boolean modulZaUvozPodataka(File path) {
        BufferedReader in = null;
        try {
            ArrayList<Stavka> stavkeRacun = new ArrayList<>();
            in = new BufferedReader(new FileReader(path));
            Racun uvezenRacun = new Racun();
            double uvozniUkupno = 0, uvozniTotal = 0, uvozniPDV = 0;
            HashMap<String, Double> stavkaUkupno = new HashMap<>();
            String red = "";
            boolean ispravanRacun = true;

            while ((red = in.readLine()) != null) {

                if (red.contains("Poslovnica")) {
                    uvezenRacun.setPoslovnica((red.split(":")[1]).trim());
                } else if (red.contains("Kupac")) {
                    uvezenRacun.setNazivKupca((red.split(":")[1]).trim());
                } else if (red.contains("Datum")) {
                    uvezenRacun.setDatumKupovine(LocalDate.parse((red.split(":")[1]).trim(), utils.Utils.DATE_TIME_FORMATTER));
                } else if (red.endsWith("kupno")) {
                    String noviRed = in.readLine();

                    if (noviRed.endsWith("---")) {
                        noviRed = in.readLine();
                    }

                    while (!noviRed.endsWith("---")) {
                        String stavkeProizvod[] = null;
                        String separator1 = utils.Utils.PROPERTIES.getProperty("FORMAT1_SEPARATOR");
                        String separator3 = utils.Utils.PROPERTIES.getProperty("FORMAT3_SEPARATOR");

                        if (noviRed.contains(separator1)) {
                            stavkeProizvod = noviRed.split(separator1);
                        } else if (noviRed.contains(separator3)) {
                            stavkeProizvod = noviRed.split(separator3);
                        }
                        stavkeRacun.add(new Stavka(stavkeProizvod[0].trim(), Integer.parseInt(stavkeProizvod[1].trim()),
                                Double.parseDouble(stavkeProizvod[2].trim())));
                        stavkaUkupno.put(stavkeProizvod[0].trim(), Double.parseDouble(stavkeProizvod[3].trim()));
                        noviRed = in.readLine();
                    }
                    uvezenRacun.setStavke(stavkeRacun);

                } else if (red.startsWith("Ukupno:")) {
                    uvozniUkupno = Double.parseDouble(((red.split(":")[1]).trim().split("\\t+")[0]));
                    if (red.contains("PDV")) {
                        red = red.substring(red.indexOf("PDV"), red.length());
                    }
                } else if (red.startsWith("Ukupno za placanje:")) {
                    uvozniTotal = Double.parseDouble((red.split(":")[1]).trim());
                }
                if (red.contains("PDV")) {

                    uvozniPDV = Double.parseDouble((red.split(":")[1]).trim());
                }
            }
            if (uvezenRacun.getPoslovnica() == null) {
                uvezenRacun.setPoslovnica(path.getAbsoluteFile().getParentFile().getName());
            }
            if (uvozniUkupno == uvezenRacun.getUkupnoRacun() && uvozniPDV == uvezenRacun.getVrijednostPDV()
                    && uvozniTotal == uvezenRacun.getUkupnoZaUplatu()) {

                for (Stavka stavka : uvezenRacun.getStavke()) {
                    if (!stavkaUkupno.get(stavka.getNazivProizvoda()).equals(stavka.getUkupno())) {
                        ispravanRacun = false;
                        break;
                    }
                }
            } else {
                ispravanRacun = false;
            }
            in.close();

            if (ispravanRacun) {
                File file = new File(path.getAbsoluteFile().getParent() + File.separator + "done_" + path.getName());
                utils.SerializationUtils.save(uvezenRacun, utils.Utils.PROPERTIES.
                        getProperty("SERIJALIZOVANI_RACUNI_FOLDER_PATH") + File.separator
                        + path.getAbsoluteFile().getParentFile().getName() + "-" + path.getName().substring(0, path.getName().length() - 4) + ".ser");
                path.renameTo(file);

            } else {
                File file = new File(path.getAbsoluteFile().getParent() + File.separator + "error_" + path.getName());
                path.renameTo(file);

            }

        } catch (IOException ex) {
            return false;
        }
        return true;
    }
}
