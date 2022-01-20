/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template path, choose Tools | Templates
 * and open the template in the editor.
 */
package analizatroskova;

import data.Racun;
import data.Stavka;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import service.RacunService;
import static service.RacunService.racun;
import service.StavkaService;
import static service.StavkaService.stavka;
import utils.MyAlert;
import utils.Utils;

/**
 * FXML Controller class
 *
 * @author Tijana Lakic
 */
public class FXMLAnaliticarController implements Initializable {

    @FXML
    private ComboBox comboBoxIzborTipaPodataka;
    @FXML
    private DatePicker datePickerDatumRacuna;

    @FXML
    private TextField txtNazivProizvoda;
    @FXML
    private TextField txtKolicina;
    @FXML
    private TextField txtCijena;
    @FXML
    private Button btnDodajStavku;
    @FXML
    private Button btnUkloniStavku;
    @FXML
    private TableView<Stavka> tableViewStavke;
    @FXML
    private TableColumn<Stavka, String> tableColumnNazivProizvoda;
    @FXML
    private TableColumn<Stavka, Integer> tableColumnKolicina;
    @FXML
    private TableColumn<Stavka, Integer> tableColumnCijena;
    @FXML
    private TableColumn<Stavka, Integer> tableColumnUkupno;
    @FXML
    private Button btnSacuvajRacun;

    ObservableList<Stavka> stavke;
    @FXML
    private TextField txtPoslovnica;
    @FXML
    private Label lblUkupnoZaUplatu;
    @FXML
    private Label lblVrijednostPDV;
    @FXML
    private Label lblUkupnoRacun;

    public static NumberFormat formatter = new DecimalFormat("#0.00");
    @FXML
    private ComboBox<String> comboBoxFormat;
    @FXML
    private TextField txtKupac;

    public static String ROOT_RESOURCE = "./src/resources/";
    @FXML
    private Button btnIzradiCsv;
    @FXML
    private TextField txtProizvod;
    @FXML
    private ComboBox<String> comboBoxIzborMjeseca;
    @FXML
    private TextField txtIzborKupca;
    @FXML
    private ComboBox<Integer> comboBoxGodina;

    public FXMLAnaliticarController() {
        this.stavke = FXCollections.observableArrayList();
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        comboBoxIzborTipaPodataka.getItems().addAll("Pregled kupaca", "Pregled proizvoda",
                "Pregled prodaje");
        comboBoxFormat.getItems().addAll("Format 1", "Format 2", "Format 3", "Format 4");
        comboBoxIzborMjeseca.getItems().addAll("Januar","Februar","Mart","April","Maj","Jun","Jul",
                "Avgust","Septembar","Oktobar","Novembar","Decembar");
        comboBoxGodina.getItems().addAll(2016,2017,2018);
        txtProizvod.setVisible(false);
        comboBoxIzborMjeseca.setVisible(false);
        txtIzborKupca.setVisible(false);
        comboBoxGodina.setVisible(false);
        
    }

    @FXML
    private void handleBtnDodajStavkuOnAction(ActionEvent event) throws IOException {

        if (StavkaService.dodajStavku(txtNazivProizvoda.getText(), Integer.parseInt(txtKolicina.getText()),
                Double.parseDouble(txtCijena.getText()))) {

            stavke.add(stavka);
            System.out.println(stavka);
            setLabels();
            tableColumnNazivProizvoda.setCellValueFactory(new PropertyValueFactory("nazivProizvoda"));
            tableColumnKolicina.setCellValueFactory(new PropertyValueFactory("kolicina"));
            tableColumnCijena.setCellValueFactory(new PropertyValueFactory("cijena"));
            tableColumnUkupno.setCellValueFactory(new PropertyValueFactory("ukupno"));
            tableViewStavke.setItems(stavke);
            txtNazivProizvoda.clear();
            txtKolicina.clear();
            txtCijena.clear();
            tableViewStavke.refresh();
            System.out.println(stavke);

        }
        else  MyAlert.display("Greska", "Niste dobro unijeli podatke o stavci.", "error");

    }

    @FXML
    private void handleBtnSacuvajRacunOnAction(ActionEvent event) {

        ArrayList<Stavka> stavkeLista = new ArrayList<Stavka>(stavke);
        if (RacunService.sacuvajRacun(txtKupac.getText(), txtPoslovnica.getText(), datePickerDatumRacuna.getValue(), stavkeLista)) {
             
            StavkaService.ukloniSveStavke();
            stavke.clear();
            txtKupac.clear();
            comboBoxFormat.getEditor().clear();
            txtPoslovnica.clear();
            datePickerDatumRacuna.getEditor().clear();
            tableViewStavke.getItems().clear();
            tableViewStavke.refresh();
            setLabels();
            System.out.println(racun);
            printResults(RacunService.racun);
            MyAlert.display("Obavijest", "Uspjesno ste dodali novi racun.", "info");

        }
        else     MyAlert.display("Greska", "Niste dobro unijeli podatke o racunu.", "error");


    }

    @FXML
    private void handleBtnUkloniStavkuOnAction(ActionEvent event) {

        Stavka stavka = tableViewStavke.getSelectionModel().getSelectedItem();
        if (StavkaService.ukloniStavku(stavka)) {
            tableViewStavke.getItems().removeAll(tableViewStavke.getSelectionModel().getSelectedItem());
            tableViewStavke.refresh();
            stavke.remove(stavka);
            setLabels();

        }
    }

    private void printResults(Racun racun) {

        String formatName = comboBoxFormat.getSelectionModel().getSelectedItem();
        String formatTemplatePath = "";
        String separator = "";
        switch (formatName) {
            case "Format 1":
                formatTemplatePath = Utils.PROPERTIES.getProperty("FORMAT1_FILE_PATH");
                separator = Utils.PROPERTIES.getProperty("FORMAT1_SEPARATOR");
                break;
            case "Format 2":
                formatTemplatePath = Utils.PROPERTIES.getProperty("FORMAT2_FILE_PATH");
                separator = Utils.PROPERTIES.getProperty("FORMAT2_SEPARATOR");
                break;
            case "Format 3":
                formatTemplatePath = Utils.PROPERTIES.getProperty("FORMAT3_FILE_PATH");
                separator = Utils.PROPERTIES.getProperty("FORMAT3_SEPARATOR");
                break;
            case "Format 4":
                formatTemplatePath = Utils.PROPERTIES.getProperty("FORMAT4_FILE_PATH");
                separator = Utils.PROPERTIES.getProperty("FORMAT4_SEPARATOR");

                break;
        }
        File formatTemplate = new File(formatTemplatePath);
        try {
            File file = new File(Utils.PROPERTIES.getProperty("RACUNI_FOLDER_PATH") + File.separator + "racun"
                    + utils.Utils.SIMPLE_DATE_FORMAT.format(new Date()) + ".txt");
            System.out.println("a:"+formatTemplatePath);
            System.out.println(file);
            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(file)));
            BufferedReader in = new BufferedReader(new FileReader(formatTemplate));
            String format = "";
            String red = "";
            while ((red = in.readLine()) != null) {
                format += red + "\n";
            }
            format = format.replace("$poslovnica", racun.getPoslovnica());
            format = format.replace("$kupac", racun.getNazivKupca());
            format = format.replace("$datum", racun.getDatumKupovine().toString());

            String proizvodi = "";
            ArrayList<Stavka> stavkeRacun = racun.getStavke();

            System.out.println(stavkeRacun.size());
            for (int k = 0; k < stavkeRacun.size(); k++) {
                proizvodi += stavkeRacun.get(k).toString(separator) + "\n";

            }
            format = format.replace("$proizvodi", proizvodi);
            format = format.replace("$ukupno", "" + racun.getUkupnoRacun());
            format = format.replace("$pdv", "" + racun.getVrijednostPDV());
            format = format.replace("$total", "" + racun.getUkupnoZaUplatu());
            out.print(format);
            in.close();
            out.close();

        } catch (IOException ex) {
            Logger.getLogger(FXMLAnaliticarController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setLabels() {
        lblUkupnoRacun.setText("" + formatter.format(StavkaService.ukupnaCijena));
        lblVrijednostPDV.setText("" + formatter.format(StavkaService.vrijednostPDV));
        lblUkupnoZaUplatu.setText("" + formatter.format(StavkaService.ukupnoZaUplatu));
    }

    @FXML
    private void btnIzradiCsvOnAction(ActionEvent event) {
        int izborPodataka = comboBoxIzborTipaPodataka.getSelectionModel().getSelectedIndex() + 1;
        if(comboBoxIzborTipaPodataka.getSelectionModel().isEmpty())
        {
                MyAlert.display("Greska", "Niste izabrali tip podatka.", "error");

        }else{
        switch (izborPodataka) {
            case 1:
                String kupac = txtIzborKupca.getText();
                ArrayList<Stavka> stavkeArray = new ArrayList<Stavka>();
                String folderPathString = Utils.PROPERTIES.getProperty("SERIJALIZOVANI_RACUNI_FOLDER_PATH");
                File folderPath = new File(folderPathString);
                for (File path : folderPath.listFiles()) {
                    Racun racunTrenutni = utils.SerializationUtils.readObject(path.getAbsolutePath());
                    System.out.println(racunTrenutni.getNazivKupca());
                    if (racunTrenutni.getNazivKupca().equals(kupac)) {
                        ArrayList<Stavka> stavkeTrenutne = racunTrenutni.getStavke();
                        for (Stavka stavkaTrenutna : stavkeTrenutne) {
                            if (stavkeArray.contains(stavkaTrenutna)) {
                                for (Stavka temp : stavkeArray) {
                                    if (temp.equals(stavkaTrenutna)) {
                                        temp.setKolicina(temp.getKolicina() + stavkaTrenutna.getKolicina());
                                        temp.setUkupno(temp.getKolicina(), temp.getCijena());
                                    }
                                }
                            } else {
                                stavkeArray.add(stavkaTrenutna);

                            }
                        }
                    }
                }
                utils.CsvFileWriter.writeCsvFile(kupac, stavkeArray);

                break;

            case 2:
                String proizvod = txtProizvod.getText();
                stavkeArray = new ArrayList<Stavka>();
                folderPathString = Utils.PROPERTIES.getProperty("SERIJALIZOVANI_RACUNI_FOLDER_PATH");
                folderPath = new File(folderPathString);
                for (File path : folderPath.listFiles()) {
                    Racun racunTrenutni = utils.SerializationUtils.readObject(path.getAbsolutePath());
                    ArrayList<Stavka> stavkeTrenutne = racunTrenutni.getStavke();
                    for (Stavka stavkaTrenutna : stavkeTrenutne) {
                        if (stavkaTrenutna.getNazivProizvoda().equals(proizvod)) {
                            if (stavkeArray.contains(stavkaTrenutna)) {
                                for (Stavka temp : stavkeArray) {
                                    if (temp.equals(stavkaTrenutna)) {
                                        temp.setKolicina(temp.getKolicina() + stavkaTrenutna.getKolicina());
                                        temp.setUkupno(temp.getKolicina(), temp.getCijena());
                                    }
                                }
                            } else {
                                stavkeArray.add(stavkaTrenutna);

                            }
                        }
                    }
                }
                utils.CsvFileWriter.writeCsvFile(proizvod, stavkeArray);

                break;
            case 3:
                int mjesec = comboBoxIzborMjeseca.getSelectionModel().getSelectedIndex()+1;
                int godina=(int)comboBoxGodina.getSelectionModel().getSelectedItem();
                stavkeArray = new ArrayList<Stavka>();
                folderPathString = Utils.PROPERTIES.getProperty("SERIJALIZOVANI_RACUNI_FOLDER_PATH");
                folderPath = new File(folderPathString);
                for (File path : folderPath.listFiles()) {
                    Racun racunTrenutni = utils.SerializationUtils.readObject(path.getAbsolutePath());
                    
                    if ((racunTrenutni.getDatumKupovine().getMonthValue()==mjesec) &&
                            (racunTrenutni.getDatumKupovine().getYear()==godina)) {
                        ArrayList<Stavka> stavkeTrenutne = racunTrenutni.getStavke();
                        for (Stavka stavkaTrenutna : stavkeTrenutne) {
                            if (stavkeArray.contains(stavkaTrenutna)) {
                                for (Stavka temp : stavkeArray) {
                                    if (temp.equals(stavkaTrenutna)) {
                                        temp.setKolicina(temp.getKolicina() + stavkaTrenutna.getKolicina());
                                        temp.setUkupno(temp.getKolicina(), temp.getCijena());
                                    }
                                }
                            } else {
                                stavkeArray.add(stavkaTrenutna);

                            }
                        }
                    }
                }
                utils.CsvFileWriter.writeCsvFile(comboBoxIzborMjeseca.getSelectionModel().getSelectedItem()+"-"
                        +comboBoxGodina.getSelectionModel().getSelectedItem(), stavkeArray);

                break;
        }
                MyAlert.display("Obavijest", "Uspjesno ste izvezli racun u CSV format.", "info");


    }
    }

    @FXML
    private void handleIzborPodatakaOnAction(ActionEvent event) {
        int izborPodataka = comboBoxIzborTipaPodataka.getSelectionModel().getSelectedIndex();
        if (izborPodataka == 0) {
            txtIzborKupca.setVisible(true);
            txtProizvod.setVisible(false);
            comboBoxGodina.setVisible(false);
            comboBoxIzborMjeseca.setVisible(false);
        } else if (izborPodataka == 1) {
            txtProizvod.setVisible(true);
            txtIzborKupca.setVisible(false);
                        comboBoxGodina.setVisible(false);
            comboBoxIzborMjeseca.setVisible(false);
        } else if (izborPodataka == 2) {
            txtProizvod.setVisible(false);
            txtIzborKupca.setVisible(false);
            comboBoxGodina.setVisible(true);
            comboBoxIzborMjeseca.setVisible(true);
        }
        
    }
}
