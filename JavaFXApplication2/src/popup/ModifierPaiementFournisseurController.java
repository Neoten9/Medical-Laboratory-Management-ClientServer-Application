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
import object.Fournisseur;
import object.PaiementE;
import object.Stock;

public class ModifierPaiementFournisseurController {
    @FXML
    private TextField PFFournisseurInput;
    @FXML
    private TextField PFProduitInput;
    @FXML
    private TextField PFMontantInput;
    @FXML
    private DatePicker PFDateInput;
    

    public void initData(Fournisseur paiementF) {
       
           id = paiementF.getId();
            PFFournisseurInput.setText(paiementF.getFournisseur());
            PFProduitInput.setText(paiementF.getProduit());   
            PFMontantInput.setText(paiementF.getMontant());
            PFDateInput.setValue(LocalDate.parse(paiementF.getDate()));
        
    }
    int id;
    @FXML
    private void ModifierPaiementFournisseur(MouseEvent event) {
        try {
            // Extracting data from UI fields
            String fournisseur = PFFournisseurInput.getText();
            String produit = PFProduitInput.getText();
            String montant = PFMontantInput.getText();
            // Check if AEDateNaissanceInput value is null
        String date = PFDateInput.getValue() != null ? PFDateInput.getValue().toString() : "";

            // Create a new PersonnelS object with the extracted data
            Fournisseur apaiementF = new Fournisseur(id, fournisseur, produit, montant, date);

            // Call the method to start the update process
            startUpdateStock(apaiementF);
            // Close the window after saving
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void startUpdateStock(Fournisseur p) {
        try (Socket socket = new Socket("localhost", 6666);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

            String toDo = "modifierPaiementFournisseur";
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
