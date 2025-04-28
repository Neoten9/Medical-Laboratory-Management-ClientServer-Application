package popup;

import Gerant.StockController;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.time.format.DateTimeFormatter;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import object.Stock;

public class AjouterProduitController {

    @FXML
    private DatePicker APDateInput;

    @FXML
    private TextField APQuantitéInput;

    @FXML
    private TextField APFournisseurInput;

    @FXML
    private TextField APProduitInput;
 
    
    @FXML
    public void saveProduit(MouseEvent event) {
        try {
            String dateString = APDateInput.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            Stock newStock = new Stock(0, APProduitInput.getText(), APQuantitéInput.getText(), APFournisseurInput.getText(), dateString);
            
            start(newStock);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.close();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Stock.fxml"));
            Parent stockRoot = fxmlLoader.load();
            
            StockController stockController = fxmlLoader.getController();
            stockController.search("");
            
            
            
        } catch (Exception e) {
            e.printStackTrace();
            // Optionally, show an error dialog to the user
        }
    }

    public void start(Stock p) throws Exception {
        try (Socket socket = new Socket("localhost", 6666);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

            String toDo = "ajouterProduit";
            out.writeObject(toDo);
            out.flush();

            out.writeObject(p);
            out.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void AnnulerStock(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}
