package popup;

import Gerant.FinanceController;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import object.PaiementF;

public class PaiementFournisseurController implements Initializable {

    @FXML
    private TextField PFFournisseurInput;
    @FXML
    private TextField PFProduitInput;
    @FXML
    private TextField PFMontantInput;
    @FXML
    private DatePicker PFDateInput;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    public void savePaiementF(MouseEvent event) {
        try {
            String dateString = PFDateInput.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            PaiementF newPaiementF = new PaiementF(0, PFFournisseurInput.getText(), PFProduitInput.getText(), PFMontantInput.getText(), dateString);

            ajouterPaiementF(newPaiementF);
            Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
            stage.close();

            
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    private void ajouterPaiementF(PaiementF paiementF) throws IOException, ClassNotFoundException {
        try (Socket socket = new Socket("127.0.0.1", 6666);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

            out.writeObject("addPaiementF");
            out.writeObject(paiementF);
            out.flush();

            
        }
    }

    private void closeStage(MouseEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void close(MouseEvent event) {
        closeStage(event);
    }
    @FXML
    public void AnnulerPaiementFournisseur(MouseEvent event){
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}
