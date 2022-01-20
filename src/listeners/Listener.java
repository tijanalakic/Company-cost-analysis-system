/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package listeners;

import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import service.RacunService;

/**
 *
 * @author Tijana Lakic
 */
public class Listener extends Thread {

    private static int counter;
    private int listenerID;
    private File folderPath;
    private int brojFajlova;
    private String poslovnica;
    public Listener(File folderPath) {

        listenerID = counter++;
        this.folderPath = folderPath;
        brojFajlova = folderPath.list().length;
        poslovnica=folderPath.getName();
    }

    @Override
    public void run() {

        try {
            Logger.getLogger("").addHandler(new FileHandler(utils.Utils.PROPERTIES.getProperty("LOG_FILE_PATH")));
        } catch (IOException ex) {
            Logger.getLogger(Listener.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(Listener.class.getName()).log(Level.SEVERE, null, ex);
        }

        while (!Thread.currentThread().isInterrupted()) {
            int trenutniBrojFajlova = folderPath.list().length;
            if (trenutniBrojFajlova != brojFajlova) {
                if (trenutniBrojFajlova < brojFajlova) {
                    Logger.getLogger("").log(Level.INFO, EnumEvent.OBRISAN_FAJL + " ID osluskivaca: " + getListenerID());
                } else if (trenutniBrojFajlova > brojFajlova) {
                    Logger.getLogger("").log(Level.INFO, EnumEvent.DODAN_FAJL + " ID osluskivaca: " + getListenerID());
                    importData(trenutniBrojFajlova, brojFajlova, getListenerID(), folderPath);
                }
                brojFajlova = trenutniBrojFajlova;

            }
        }

    }

    public static synchronized void importData(int trenutniBrojFajlova, int brojFajlova, int listenerID, File folderPath) {

        for (File file : folderPath.listFiles()) {
            if (!file.getName().startsWith("done_") && !file.getName().startsWith("error_")) {
                RacunService.modulZaUvozPodataka(file);
            }
        }
    }

    /**
     * @return the listenerID
     */
    public int getListenerID() {
        return listenerID;
    }

    /**
     * @return the poslovnica
     */
    public String getPoslovnica() {
        return poslovnica;
    }
}
