package popup;

import Server.PaiementEmployé;
import java.sql.SQLException;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import object.PersonnelS;
import object.PaiementE;
import Server.Personnel;


public class AjouterUnEmployéController {
    @FXML
    private TextField AENomInput;
    @FXML
    private TextField AEPrenomInput;
    @FXML
    private TextField AEMotDePassInput;
    @FXML
    private TextField AEEmailInput;
    
    @FXML
    private ChoiceBox<String> AEPositionInput;

    // Method to initialize the choice box with positions
    public void initialize() {
        AEPositionInput.getItems().addAll("gerant", "medecine", "biologist", "recepsionist");
        AEGenreInput.getItems().addAll("Homme","femme");
    }
   
    @FXML
    private TextField AETelephoneInput;
    
    @FXML
    private ChoiceBox<String> AEGenreInput;
    
    @FXML
    private DatePicker AEDateNaissanceInput;

    @FXML
private void saveAPersonnel() {
    try {
        String nom = AENomInput.getText();
        String prenom = AEPrenomInput.getText();
        String mot_de_pass = AEMotDePassInput.getText();
        String email = AEEmailInput.getText();
        String position = AEPositionInput.getValue();
        String telephone = AETelephoneInput.getText();
        String genre = AEGenreInput.getValue();
        String dateNaissance = AEDateNaissanceInput.getValue().toString();
        
        PersonnelS aPersonnel = new PersonnelS(nom, prenom, mot_de_pass, email, position, telephone, genre, dateNaissance);

        // Call a method to save the personnel to the database
        Personnel.ajouterPersonnel(aPersonnel);

        // Close the stage after saving
        Stage stage = (Stage) AENomInput.getScene().getWindow();
        stage.close();
    } catch (Exception e) {
        e.printStackTrace();
    }
}

private void savePaiementToDatabase(PersonnelS aPersonnel) {
    try {
        Personnel.ajouterPersonnel(aPersonnel);
    } catch (SQLException e) {
        e.printStackTrace();
        // Handle database error
    }
}

    // Method to save the payment information to the database


    @FXML
    private void close() {
        Stage stage = (Stage) AENomInput.getScene().getWindow();
        stage.close();
    } 
    @FXML
    public void AnnulerAjouterEmployé(MouseEvent event){
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

}