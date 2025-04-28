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
import object.Stock;

public class ModifierProduitController {
    @FXML
    private DatePicker APDateInput;

    @FXML
    private TextField APQuantitéInput;

    @FXML
    private TextField APFournisseurInput;

    @FXML
    private TextField APProduitInput;

    

    public void initData(Stock stock) {
       
           id = stock.getId();
            // Use the personnel object if it's not null
            APQuantitéInput.setText(stock.getQuantité());
            APFournisseurInput.setText(stock.getFournisseur());
            APProduitInput.setText(stock.getProduit());
            APDateInput.setValue(LocalDate.parse(stock.getDate()));
        
    }
    int id;
    @FXML
    private void ModifierProduit(MouseEvent event) {
        try {
            // Extracting data from UI fields
            String quantité = APQuantitéInput.getText();
            String fournisseur = APFournisseurInput.getText();
            String produit = APProduitInput.getText();
            // Check if AEDateNaissanceInput value is null
        String date = APDateInput.getValue() != null ? APDateInput.getValue().toString() : "";

            // Create a new PersonnelS object with the extracted data
            Stock astock = new Stock(id, produit, quantité, fournisseur, date);

            // Call the method to start the update process
            startUpdateStock(astock);
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

    public void startUpdateStock(Stock p) {
        try (Socket socket = new Socket("localhost", 6666);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

            String toDo = "modifierProduit";
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
