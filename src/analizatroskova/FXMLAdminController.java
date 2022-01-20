/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analizatroskova;

import data.Nalog;
import data.Racun;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
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
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import service.NalogService;
import static service.NalogService.nalog;
import utils.Utils;
import listeners.Listener;
import utils.MyAlert;

/**
 * FXML Controller class
 *
 * @author Tijana Lakic
 */
public class FXMLAdminController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private ComboBox comboBoxIzborPoslovnice;
    @FXML
    private TextField txtImeKorisnika;
    @FXML
    private TextField txtKorisnickoIme;
    @FXML
    private TextField txtKorisnickaGrupa;
    @FXML
    private PasswordField txtLozinka;
    @FXML
    private Button btnDodajNalog;
    @FXML
    private TextField txtPrezimeKorisnika;
    @FXML
    private TableView<Nalog> tableViewNalozi;
    @FXML
    private TableColumn<Nalog, String> tableColumnKorisnickoIme;
    @FXML
    private TableColumn<Nalog, String> tableColumnKorisnickaGrupa;

    ObservableList<Nalog> nalozi;
    @FXML
    private TableColumn<?, ?> tableColumnIme;
    @FXML
    private TableColumn<?, ?> tableColumnPrezime;
    @FXML
    private Button btnUkloniNalog;
    @FXML
    private Button btnDodajOsluskivac;
    @FXML
    private Button btnUkloniOsluskivac;

    ObservableList<Listener> listeneri = FXCollections.observableArrayList();
    @FXML
    private TableView<Listener> tableViewListeners;
    @FXML
    private TableColumn<Listener, Integer> tableColumnOsluskivaciId;
    @FXML
    private TableColumn<Listener, String> tableColumnPoslovnica;
    @FXML
    private TextField txtValuta;
    @FXML
    private Label lblValuta;

    public FXMLAdminController() {
        this.nalozi = FXCollections.observableArrayList(NalogService.nalozi);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO        
        comboBoxIzborPoslovnice.getItems().addAll(Utils.PROPERTIES.getProperty("POSLOVNICA1_NAZIV"),
                Utils.PROPERTIES.getProperty("POSLOVNICA2_NAZIV"), Utils.PROPERTIES.getProperty("POSLOVNICA3_NAZIV"),
                Utils.PROPERTIES.getProperty("POSLOVNICA4_NAZIV"));
        tableColumnKorisnickoIme.setCellValueFactory(new PropertyValueFactory("korisnickoIme"));
        tableColumnIme.setCellValueFactory(new PropertyValueFactory("ime"));
        tableColumnPrezime.setCellValueFactory(new PropertyValueFactory("prezime"));
        tableColumnKorisnickaGrupa.setCellValueFactory(new PropertyValueFactory("korisnickaGrupa"));
        tableViewNalozi.setItems(nalozi);
        lblValuta.setText(utils.Utils.PROPERTIES.getProperty("VALUTA_NAZIV"));
                          //     System.out.println(utils.Utils.PROPERTIES.getProperty("VALUTA_NAZIV"));


    }

    @FXML
    private void handleBtnDodajNalogOnAction(ActionEvent event) throws IOException {

        if (NalogService.dodajNalog(txtImeKorisnika.getText(), txtPrezimeKorisnika.getText(),
                txtKorisnickoIme.getText(), txtLozinka.getText(), txtKorisnickaGrupa.getText())) {

            nalozi.add(nalog);
            tableViewNalozi.setItems(nalozi);
            txtImeKorisnika.clear();
            txtPrezimeKorisnika.clear();
            txtKorisnickoIme.clear();
            txtKorisnickaGrupa.clear();
            txtLozinka.clear();
            tableViewNalozi.refresh();
            MyAlert.display("Obavijest", "Uspjesno ste dodali novi korisnicki nalog.", "info");

        } else {
            MyAlert.display("Greska", "Niste dobro unijeli podatke o korisniku", "error");
        }

    }

    @FXML
    private void handleBtnUkloniNalogOnAction(ActionEvent event) {

        if(!tableViewNalozi.getSelectionModel().isEmpty()){
        Nalog nalog = tableViewNalozi.getSelectionModel().getSelectedItem();
        NalogService.ukloniNalog(nalog);
        tableViewNalozi.getItems().removeAll(tableViewNalozi.getSelectionModel().getSelectedItem());
        tableViewNalozi.refresh();
        nalozi.remove(nalog);
        MyAlert.display("Obavijest", "Uspjesno ste uklonili korisnicki nalog.", "info");
    }else   MyAlert.display("Greska", "Niste izabrali korisnika kog zelite ukloniti", "error");
    }

    @FXML
    private void btnDodajOsluskivacOnAction(ActionEvent event) {
        if (!comboBoxIzborPoslovnice.getSelectionModel().isEmpty()) {
            int poslovnica = comboBoxIzborPoslovnice.getSelectionModel().getSelectedIndex() + 1;
            String folderString = Utils.PROPERTIES.getProperty("POSLOVNICA" + poslovnica + "_FOLDER_PATH");
            Listener listener = new Listener(new File(folderString));
            listener.setDaemon(true);
            listeneri.add(listener);
            listener.start();
            tableColumnOsluskivaciId.setCellValueFactory(new PropertyValueFactory("listenerID"));
            tableColumnPoslovnica.setCellValueFactory(new PropertyValueFactory("poslovnica"));
            tableViewListeners.setItems(listeneri);
            tableViewListeners.refresh();
            MyAlert.display("Obavijest", "Uspjesno ste dodali novi osluskivac.", "info");

        } else {
            MyAlert.display("Greska", "Niste izabrali poslovnicu na koju zelite da postavite osluskivac.", "error");
        }
    }

    @FXML
    private void handleBtnUkloniOsluskivacOnAction(ActionEvent event) {

        if (!tableViewListeners.getSelectionModel().isEmpty()) {
            Listener listener = tableViewListeners.getSelectionModel().getSelectedItem();
            listeneri.get(tableViewListeners.getSelectionModel().getSelectedIndex()).interrupt();
            System.out.println(tableViewListeners.getSelectionModel().getSelectedIndex());
            tableViewListeners.getItems().removeAll(tableViewListeners.getSelectionModel().getSelectedItem());

            listeneri.remove(listener);
            tableViewListeners.refresh();
            MyAlert.display("Obavijest", "Uspjesno ste uklonili osluskivac.", "info");
        } else {
            MyAlert.display("Greska", "Niste izabrali osluskivac koji zelite ukloniti.", "error");
        }

    }

    @FXML
    private void handleBtnPromjeniValutuOnAction(ActionEvent event) {

        if (txtValuta.getText() != null && !txtValuta.getText().trim().isEmpty()) {

                String novaValuta=txtValuta.getText();
               utils.Utils.setValuta(novaValuta);
                lblValuta.setText(utils.Utils.PROPERTIES.getProperty("VALUTA_NAZIV"));         
                txtValuta.clear();
               // System.out.println(utils.Utils.PROPERTIES.getProperty("VALUTA_NAZIV"));
            
        } else {
            MyAlert.display("Greska", "Niste unijeli novu valutu.", "error");
        }

    }

}
