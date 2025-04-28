package popup;

import Server.PaiementEmployé;
import java.io.IOException;
import java.sql.SQLException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import object.PaiementE;

public class PaiementEmployeController {
    @FXML
    private TextField PEEmployéNInput;
    @FXML
    private TextField PEEmployéPInput;
    @FXML
    private TextField PEMontantInput;
    @FXML
    private DatePicker PEDateInput;
    @FXML
    private ChoiceBox<String> PEPositionInput;

    // Method to initialize the choice box with positions
    public void initialize() {
        PEPositionInput.getItems().addAll("Gerant", "medecin", "biologiste", "recepsionist");
    }

    @FXML
    private void savePaiementE() {
        try {
            String employéN = PEEmployéNInput.getText();
            String employéP = PEEmployéPInput.getText();
            String montant = PEMontantInput.getText();
            String date = PEDateInput.getValue().toString();
            String position = PEPositionInput.getValue();

            PaiementE paiementE = new PaiementE(employéN, employéP, position, montant, date);

            // Call a method to save the payment to the database
            savePaiementToDatabase(paiementE);

            // Close the stage after saving
            Stage stage = (Stage) PEEmployéNInput.getScene().getWindow();
            stage.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Method to save the payment information to the database
    private void savePaiementToDatabase(PaiementE paiementE) throws SQLException {
        // Call your database access class method to save the payment information
        PaiementEmployé.ajouterPaiementE(paiementE);
    }

    @FXML
    private void close() {
        Stage stage = (Stage) PEEmployéNInput.getScene().getWindow();
        stage.close();
    } 
    @FXML
    public void AnnulerPaiementEmployé(MouseEvent event){
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
    private void handleOpenPaiementEmployé() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("PaiementEmploye.fxml"));
            Parent paiementemployéRoot = fxmlLoader.load();
            Stage paiementemployéStage = new Stage();
            paiementemployéStage.setTitle("Ajouter Paiement Employé Interface");
            paiementemployéStage.setScene(new Scene(paiementemployéRoot));
            paiementemployéStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

