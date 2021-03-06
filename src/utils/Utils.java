/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import analizatroskova.FXMLAnaliticarController;
import static analizatroskova.FXMLAnaliticarController.ROOT_RESOURCE;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Tijana Lakic
 */
public class Utils {

    public static Properties PROPERTIES;
    public static SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("dd-MM-yy-hh-mm-ss");
    public static DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    static {
        PROPERTIES = new Properties();

        try {
            InputStream input = new FileInputStream(ROOT_RESOURCE + "config.properties");
            PROPERTIES.load(input);
            input.close();
        } catch (IOException ex) {
            Logger.getLogger(FXMLAnaliticarController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void setValuta(String valuta) {
        try {
            FileOutputStream out = new FileOutputStream(ROOT_RESOURCE + "config.properties");
            PROPERTIES.setProperty("VALUTA_NAZIV", valuta);
            PROPERTIES.store(out, null);
            out.close();

        } catch (IOException ex) {
            Logger.getLogger(FXMLAnaliticarController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
