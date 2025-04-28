/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package popup;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafxapplication2.ListDeDemandeController;
import javafxapplication2.SelectionDesAnalysesController;
import object.Patient;

/**
 * FXML Controller class
 *
 * @author mohci
 */
public class AjouterUnDemandeController {
    
    private ObjectOutputStream out;
    private ObjectInputStream in;
    
    private Stage stage;
    private Scene scene;
    private Parent root;
    
    MouseEvent e = null;
    String UserName;
    
    @FXML
    public void parse(MouseEvent event, String userName){
        e = event;
        UserName = userName;
    }


    @FXML
    public void nouveauPatient(MouseEvent event) throws IOException {
        System.out.println("ajouterdemand");
        //root = FXMLLoader.load(getClass().getResource("ListDeDemande.fxml"));
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AjouterUnPatient.fxml"));
        root = loader.load();
        
        AjouterUnPatientController ajouterUnPatientController = loader.getController();
        ajouterUnPatientController.parse(e, UserName);
        
        
        stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    @FXML
    TextField patientSearchBar;
    
    @FXML
    VBox VboxPatient;
    
    
    String searchInput;
    
    @FXML
    public void searchPatient(KeyEvent event) throws Exception{
        searchInput = patientSearchBar.getText();
        start(searchInput);
        
    }
    
    public void start(String searchInput) throws Exception {
        Socket socket = new Socket("localhost", 6666);
        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());

        String ToDo = "searchPatient";

        out.writeObject(ToDo);
        out.flush();
        out.writeObject(searchInput);
        out.flush();

        List<Patient> patientList = (List<Patient>) in.readObject();
        System.out.println("Data received from the server");

        for (int i = VboxPatient.getChildren().size() - 1; i > 0; i--) {
             VboxPatient.getChildren().remove(i); 
        }

        for (Patient patient : patientList) {
            System.out.println(patient.getNom() + " - " + patient.getEmail());

            HBox patientBox = new HBox();  // Spacing between elements in the HBox

            Label Nom = new Label(patient.getNom());
            Nom.getStyleClass().add("custom-label");
            Nom.setFont(new Font("Arial Bold", 14.0));
            patientBox.getChildren().add(Nom);

            Label Prenom = new Label(patient.getPrenom());
            Prenom.getStyleClass().add("custom-label");
            Prenom.setFont(new Font("Arial Bold", 14.0));
            patientBox.getChildren().add(Prenom);

            Label DateNaissance = new Label(patient.getDateNaissance());
            DateNaissance.getStyleClass().add("custom-label");
            DateNaissance.setFont(new Font("Arial Bold", 14.0));
            patientBox.getChildren().add(DateNaissance);

            // Create an ImageView and set an image
            ImageView imageView = new ImageView(new Image(getClass().getResourceAsStream("../img/Vectorarrew-right.png")));
            imageView.setFitHeight(30);  // Set the size of the image
            imageView.setFitWidth(30);
            imageView.setCursor(Cursor.HAND);

            // Set a mouse click handler on the ImageView
            imageView.setOnMouseClicked((MouseEvent event) -> {
                try {
                    handleImageClick(patient);
                } catch (IOException ex) {
                    Logger.getLogger(AjouterUnDemandeController.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                Stage stage1 = (Stage)((Node) event.getSource()).getScene().getWindow();
                stage1.close();
            });

            patientBox.getChildren().add(imageView);
            VboxPatient.getChildren().add(patientBox);
        }
    }
    
    public void handleImageClick(Patient patient) throws IOException{
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../javafxapplication2/ListDeDemande.fxml"));
        loader.load();
                
        ListDeDemandeController listDeDemandeController = loader.getController();
        listDeDemandeController.GoToSelectionDesAnalyse(e, patient, UserName);
        
    }
}
