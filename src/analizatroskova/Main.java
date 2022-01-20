/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analizatroskova;

import java.io.IOException;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import service.NalogService;

/**
 *
 * @author Tijana Lakic
 */
public class Main extends Application {

    public static Stage stage;

    @Override
    public void start(Stage stage) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("FXMLLoginForma.fxml"));

        Scene scene = new Scene(root);
        this.stage = stage;
        stage.setScene(scene);
//        stage.getIcons().add(new Image("admin.jpg"));
        stage.setTitle("Analiza troskova");
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
