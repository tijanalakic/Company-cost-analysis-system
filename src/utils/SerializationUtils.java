/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

/**
 *
 * @author Tijana Lakic
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import data.Stavka;

public class SerializationUtils {

    public static <T> T readObject(String fullPath) {
        ObjectInputStream ois = null;
        T object = null;
        try {
            ois = new ObjectInputStream(new FileInputStream(new File(fullPath)));
            object = (T) ois.readObject();
            ois.close();

        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        } finally {
            try {
                ois.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return object;
    }

    public static <T> boolean save(T object, String path, String filename) {
        ObjectOutputStream oos = null;
        boolean result = false;
        try {
            oos = new ObjectOutputStream(new FileOutputStream(new File(path + File.separator + filename)));
            oos.writeObject(object);
            result = true;
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                oos.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }
    public static <T> boolean save(T object, String path) {
        ObjectOutputStream oos = null;
        boolean result = false;
        try {
            oos = new ObjectOutputStream(new FileOutputStream(new File(path)));
            oos.writeObject(object);
            result = true;
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                oos.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }

}
