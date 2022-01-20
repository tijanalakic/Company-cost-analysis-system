package utils;

import data.Stavka;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CsvFileWriter {

    public static boolean writeCsvFile(String fileName, List<Stavka> stavke) {

        String newLineSeparator = utils.Utils.PROPERTIES.getProperty("NEW_LINE_SEPARATOR");
        String comaDelimiter = utils.Utils.PROPERTIES.getProperty("COMMA_DELIMITER");
        String fileHeader=utils.Utils.PROPERTIES.getProperty("CSV_FILE_HEADER");
        
        FileWriter fileWriter = null;

        try {

            fileWriter = new FileWriter(utils.Utils.PROPERTIES.get("CSV_FOLDER_PATH")+
                   File.separator+fileName+".csv");
            fileWriter.append(fileHeader);
            fileWriter.append(newLineSeparator);

            // Write a new student object list to the CSV file
            for (Stavka stavka : stavke) {

                fileWriter.append(stavka.getNazivProizvoda());
                fileWriter.append(comaDelimiter);
                fileWriter.append(String.valueOf(stavka.getKolicina()));
                fileWriter.append(comaDelimiter);
                fileWriter.append(String.valueOf(stavka.getCijena()));
                fileWriter.append(comaDelimiter);
                fileWriter.append(String.valueOf(stavka.getUkupno()));
                fileWriter.append(newLineSeparator);

            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;

        } finally {

            try {
                fileWriter.flush();
                fileWriter.close();
                return true;

            } catch (IOException e) {

                System.out
                        .println("Error while flushing/closing fileWriter !!!");
                e.printStackTrace();
                return false;
            }

        }

    }

}
