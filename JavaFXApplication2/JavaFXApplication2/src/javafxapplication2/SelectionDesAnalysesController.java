/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package javafxapplication2;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import object.Patient;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author mohci
 */
public class SelectionDesAnalysesController {

    private Stage stage;
    private Scene scene;
    private Parent root;
    
    @FXML
    TextField PatientInfo;
    
    @FXML
    Label UserNameLabel;
    
    String UserName;
    
    @FXML
    public void displayName(String userName){
        UserNameLabel.setText(userName);
        this.UserName = userName;
    }
    
    @FXML
    public void ParsePatient(Patient patient){
        
        String birthDateString = patient.getDateNaissance();  // Example birth date
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate birthDate = LocalDate.parse(birthDateString, formatter);
        LocalDate currentDate = LocalDate.now();  // Today's date
        Period age = Period.between(birthDate, currentDate);
        
        String patientInfo = patient.getNom() + " " + patient.getPrenom() + " - " + age.getYears() + " ans - " + patient.getGenre();
        PatientInfo.setText(patientInfo);
        
        System.out.println(patient.getNom());
    }
    
    @FXML
    public void GoToListDeDemande(MouseEvent event) throws IOException {
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ListDeDemande.fxml"));
        root = loader.load();
        
        ListDeDemandeController listDeDemandeController = loader.getController();
        listDeDemandeController.displayName(UserName);
                
        stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
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
        
    
}
