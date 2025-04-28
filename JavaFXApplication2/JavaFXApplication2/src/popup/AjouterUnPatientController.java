/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package popup;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafxapplication2.ListDeDemandeController;
import javafxapplication2.SelectionDesAnalysesController;
import object.Patient;

/**
 * FXML Controller class
 *
 * @author mohci
 */
public class AjouterUnPatientController implements Initializable{
    
    private ObjectOutputStream out;
    private ObjectInputStream in;

    
    String UserName;
    MouseEvent e = null;
    
    @FXML
    public void parse(MouseEvent event, String userName){
        e = event;
        UserName = userName;
    }
    
    @FXML
    private DatePicker DateNaissanceInput;

    @FXML
    private TextField EmailInput;

    @FXML
    private TextField NomInput;

    @FXML
    private TextField PrenomInput;

    @FXML
    private ChoiceBox<String> SexeInput;
    
    private String[] Sexe = {"Male", "Female"};
            
    @FXML
    private TextField TelephoneInput;

    
    @FXML
    public void cancel(MouseEvent event){
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
    
    @FXML
    public void savePatient(MouseEvent event) throws IOException, Exception{
        
        String dateString = DateNaissanceInput.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                
        Patient NewPatient = new Patient(0, NomInput.getText(), PrenomInput.getText(), dateString, TelephoneInput.getText(), EmailInput.getText(), SexeInput.getValue());
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../javafxapplication2/ListDeDemande.fxml"));
        loader.load();
        
        System.out.println(NewPatient.getNom() + " - " + NewPatient.getPrenom() + " - " + NewPatient.getDateNaissance() + " - " + NewPatient.getTelephone() + " - " + NewPatient.getEmail() + " - " + NewPatient.getGenre());
        
        Patient NP = start(NewPatient);
        System.out.println(NP.getNom());
        
        ListDeDemandeController listDeDemandeController = loader.getController();
        listDeDemandeController.GoToSelectionDesAnalyse(e, NP, UserName);
        System.out.println("1");
//        FXMLLoader loader1 = new FXMLLoader(getClass().getResource("../javafxapplication2/SelectionDesAnalyses.fxml"));
//        loader.load();       
//        SelectionDesAnalysesController selectionDesAnalysesController = loader1.getController();
//        selectionDesAnalysesController.displayName(UserName);
        System.out.println("2");
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.close();
        System.out.println("3");
    } 
    
    public Patient start(Patient p) throws Exception {
        Socket socket = new Socket("localhost", 6666);
        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());

        String ToDo = "addPatient";
        
        out.writeObject(ToDo);
        out.flush();
        
        out.writeObject(p);
        out.flush();
        
        Patient NP = (Patient) in.readObject();
        
        return NP;
    }
    
    @Override
    public void initialize(URL arg0, ResourceBundle arg1){
        SexeInput.getItems().setAll(Sexe);
    }
    
}
