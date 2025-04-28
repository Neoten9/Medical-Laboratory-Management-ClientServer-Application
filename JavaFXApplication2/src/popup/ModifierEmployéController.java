package popup;

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

public class ModifierEmployéController {
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
    @FXML
    private TextField AETelephoneInput;
    @FXML
    private ChoiceBox<String> AEGenreInput;
    @FXML
    private DatePicker AEDateNaissanceInput;

    // Method to initialize the choice boxes with positions and genres
    public void initialize() {
        AEPositionInput.getItems().addAll("gerant", "medecine", "biologist", "recepsionist");
        AEGenreInput.getItems().addAll("Homme", "femme");
    }

    public void initData(PersonnelS personnel) {
       
           id = personnel.getId();
            // Use the personnel object if it's not null
            AENomInput.setText(personnel.getNom());
            AEPrenomInput.setText(personnel.getPrenom());
            AEMotDePassInput.setText(personnel.getMotDePass());
            AEEmailInput.setText(personnel.getEmail());
            AEPositionInput.setValue(personnel.getPosition());
            AETelephoneInput.setText(personnel.getTelephone());
            AEGenreInput.setValue(personnel.getGenre());
            AEDateNaissanceInput.setValue(LocalDate.parse(personnel.getDateNaissance()));
        
    }
    int id;
    @FXML
    private void ModifierPersonnel(MouseEvent event) {
        try {
            // Extracting data from UI fields
            String nom = AENomInput.getText();
            String prenom = AEPrenomInput.getText();
            String motDePass = AEMotDePassInput.getText();
            String email = AEEmailInput.getText();
            String position = AEPositionInput.getValue();
            String telephone = AETelephoneInput.getText();
            String genre = AEGenreInput.getValue();
            // Check if AEDateNaissanceInput value is null
        String dateNaissance = AEDateNaissanceInput.getValue() != null ? AEDateNaissanceInput.getValue().toString() : "";

            // Create a new PersonnelS object with the extracted data
            PersonnelS aPersonnel = new PersonnelS(id, nom, prenom, motDePass, email, position, telephone, genre, dateNaissance);

            // Call the method to start the update process
            startUpdatePersonnel(aPersonnel);

            // Close the window after saving
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void startUpdatePersonnel(PersonnelS p) {
        try (Socket socket = new Socket("localhost", 6666);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

            String toDo = "modifierPersonnel";
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
