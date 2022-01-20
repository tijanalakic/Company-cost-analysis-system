/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analizatroskova;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import service.NalogService;

/**
 * FXML Controller class
 *
 * @author Tijana Lakic
 */
public class FXMLLoginFormaController implements Initializable {

    @FXML
    private TextField txtKorisnickoIme;
    @FXML
    private PasswordField txtLozinka;
    @FXML
    private Button btnPrijaviSe;
    private Alert alert;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
       
    }

    @FXML
    private void handleButtonAction(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("FXMLAdmin.fxml"));
        Scene sceneAdmin = new Scene(root);
        String korisnickoIme = txtKorisnickoIme.getText();
        String lozinka = txtLozinka.getText();

        if (service.NalogService.login(korisnickoIme, lozinka)) {

            switch (service.NalogService.getKorisnickaGrupa()) {
                case "administrator":
                    Stage stageAdmin = new Stage();
                    stageAdmin.setScene(sceneAdmin);
                    
                    stageAdmin.setOnCloseRequest(new EventHandler<WindowEvent>() {
                        public void handle(WindowEvent we) {
                            NalogService.sacuvajNaloge();
                        }
                    });
                    Main.stage.close();
                    stageAdmin.setTitle("Administratorski dio");
                    stageAdmin.getIcons().add(new Image("admin.jpg"));
                    stageAdmin.show();

                    break;
                case "analiticar":
                    Stage stageAnaliticar = new Stage();
                    Scene sceneAnaliticar = new Scene(FXMLLoader.load(getClass().getResource("FXMLAnaliticar.fxml")));
                    stageAnaliticar.setScene(sceneAnaliticar);
                    Main.stage.close();
                    stageAnaliticar.setTitle("Analiticarski dio");
                    stageAnaliticar.getIcons().add(new Image("admin.jpg"));
                    stageAnaliticar.show();
                    break;
            }
        }

    }

    @FXML
    private void loginEnter(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
            try {
                handleButtonAction(new ActionEvent());
            } catch (IOException ex) {
                Logger.getLogger(FXMLLoginFormaController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
