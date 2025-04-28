/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package javafxapplication2;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import object.Patient;
import popup.AjouterUnDemandeController;
import popup.AjouterUnPatientController;


/**
 * FXML Controller class
 *
 * @author mohci
 */
public class ListDeDemandeController {

    private Stage stage;
    private Scene scene;
    private Parent root;
    
    @FXML
    Label UserNameLabel;
    
    String UserName;
    
    @FXML
    public void displayName(String userName){
        UserNameLabel.setText(userName);
        this.UserName = userName;
    }
    
    @FXML
    public void GoToAccueilReceptionist(MouseEvent event) throws IOException {
        System.out.println("Acueil");
        //root = FXMLLoader.load(getClass().getResource("receptionist.fxml"));
        FXMLLoader loader = new FXMLLoader(getClass().getResource("receptionist.fxml"));
        root = loader.load();
        
        ReceptionistController receptionistController = loader.getController();
        receptionistController.displayName(UserName);
        
        stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    public void LogOut(MouseEvent event) throws IOException {
        System.out.println("log out");
        root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    public void nouveauDemande (MouseEvent event) throws IOException {
        //root = FXMLLoader.load(getClass().getResource("../popup/AjouterUnDemande.fxml"));
                
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../popup/AjouterUnDemande.fxml"));
        root = loader.load();
        
        AjouterUnDemandeController ajouterUnDemandeController = loader.getController();
        ajouterUnDemandeController.parse(event, UserName);
        
        scene = new Scene(root);
        Stage stage1 = new Stage();
        stage1.setScene(scene);
        stage1.showAndWait();
    }
    
    public void GoToSelectionDesAnalyse(MouseEvent event, Patient patient, String username) throws IOException {
        System.out.println("Acueil");
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("SelectionDesAnalyses.fxml"));
        root = loader.load(); 
        
        SelectionDesAnalysesController selectionDesAnalysesController = loader.getController();
        selectionDesAnalysesController.displayName(username);
        selectionDesAnalysesController.ParsePatient(patient);
        
        //root = FXMLLoader.load(getClass().getResource("SelectionDesAnalyses.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        
        
    }
    
      
    
}
