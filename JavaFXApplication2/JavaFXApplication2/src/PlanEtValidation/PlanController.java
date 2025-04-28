/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package PlanEtValidation;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author mohci
 */
public class PlanController {
    
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    public void LogOut(MouseEvent event) throws IOException {
        System.out.println("log out");
        root = FXMLLoader.load(getClass().getResource("../javafxapplication2/Login.fxml"));
        stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    @FXML
    Label UserNameLabel;
    
    @FXML
    public void displayName(String userName){
        UserNameLabel.setText(userName);
    }
    
}
