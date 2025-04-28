package popup;

import Gerant.StockController;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import object.PersonnelS;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.time.LocalDate;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import object.Employé;
import object.PaiementE;
import object.Stock;

public class ModifierPaiementEmployéController {
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
    

    public void initData(Employé paiementE) {
       
           id = paiementE.getId();
            // Use the personnel object if it's not null
            String NomEtPrenom = paiementE.getEmploye();
            String[] NomEtPrenomArray = NomEtPrenom.split(" ");
            PEEmployéNInput.setText(NomEtPrenomArray[0]);
            PEEmployéPInput.setText(NomEtPrenomArray[1]);
            
            PEMontantInput.setText(paiementE.getMontant());
            PEPositionInput.setValue(paiementE.getPosition());
            PEDateInput.setValue(LocalDate.parse(paiementE.getDate()));
        
    }
    int id;
    @FXML
    private void ModifierPaiementEmployé(MouseEvent event) {
        try {
            // Extracting data from UI fields
            String employéN = PEEmployéNInput.getText();
            String employéP = PEEmployéPInput.getText();
            String employé = employéN +" "+ employéP;
            String montant = PEMontantInput.getText();
            String position = PEPositionInput.getValue();
            // Check if AEDateNaissanceInput value is null
        String date = PEDateInput.getValue() != null ? PEDateInput.getValue().toString() : "";

            // Create a new PersonnelS object with the extracted data
            Employé apaiementE = new Employé(id, employé, position, montant, date);

            // Call the method to start the update process
            startUpdateStock(apaiementE);
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../Gerant/Stock.fxml"));
            fxmlLoader.load();
        StockController stockController = fxmlLoader.getController();
        stockController.search("");
            // Close the window after saving
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void startUpdateStock(Employé p) {
        try (Socket socket = new Socket("localhost", 6666);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

            String toDo = "modifierPaiementEmployé";
            out.writeObject(toDo);
            out.flush();

            out.writeObject(p);
            out.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void AnnulerAjouterEmployé(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}
