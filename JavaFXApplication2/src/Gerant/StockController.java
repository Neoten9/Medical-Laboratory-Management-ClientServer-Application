/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Gerant;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
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
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafxapplication2.ListDeDemandeController;
import object.Stock;
import popup.ModifierProduitController;

/**
 * FXML Controller class
 *
 * @author mohci
 */
public class StockController {
    
    @FXML
    Label UserNameLabel;
    
    String UserName;
    
    @FXML
    public void displayName(String userName){
        UserNameLabel.setText(userName);
        this.UserName = userName;
        
    }
    
    private ObjectOutputStream out;
    private ObjectInputStream in;
    
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    public void initialize() {
        //Accueil
        triggerPaneAccueil.setOnMouseClicked(event -> handleOpenAccueil());
        
        //Finance
        triggerPaneFinance.setOnMouseClicked(event -> handleOpenFinance());
        //Personnel
        triggerPanePersonnel.setOnMouseClicked(event -> handleOpenPersonnel());
        
        //Open popup Ajouter Paiement Employé
        triggerPaneAjouterProduit.setOnMouseClicked(event -> handleOpenAjouterProduit());
    }
    //Return to Accueil
    @FXML
    private Pane triggerPaneAccueil;
    
    

    private void handleOpenAccueil() {
    try {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Gerant.fxml"));
        Parent accueilRoot = fxmlLoader.load();
        GerantController gerantController = fxmlLoader.getController();
        gerantController.displayName(UserName);
        triggerPaneAccueil.getScene().setRoot(accueilRoot);
    } catch (IOException e) {
        e.printStackTrace();
    }
}
    //Open Finance interface from Gerant interface 
     @FXML
    private Pane triggerPaneFinance;

    

    private void handleOpenFinance() {
    try {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Finance.fxml"));
        Parent financeRoot = fxmlLoader.load();
        FinanceController financeController = fxmlLoader.getController();
        financeController.displayName(UserName);
        financeController.searchCF("");
        financeController.searchF("");
        financeController.searchE("");
        triggerPaneFinance.getScene().setRoot(financeRoot);
    } catch (IOException e) {
        e.printStackTrace();
    }   catch (Exception ex) {
            Logger.getLogger(StockController.class.getName()).log(Level.SEVERE, null, ex);
        }
}

   //Open Personnel interface from Gerant Interface
    @FXML
    private Pane triggerPanePersonnel;


    private void handleOpenPersonnel() {
    try {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Personnel.fxml"));
        Parent personnelRoot = fxmlLoader.load();
        PersonnelController personnelController = fxmlLoader.getController();
        personnelController.displayName(UserName);
        personnelController.searchP("");
        triggerPanePersonnel.getScene().setRoot(personnelRoot);
    } catch (IOException e) {
        e.printStackTrace();
    }   catch (Exception ex) {
            Logger.getLogger(StockController.class.getName()).log(Level.SEVERE, null, ex);
        }
}
    //GoBack 
     @FXML
    public void GoToAccueilGerant(MouseEvent event) throws IOException {
        System.out.println("Accueil");
        //root = FXMLLoader.load(getClass().getResource("receptionist.fxml"));
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Gerant.fxml"));
        root = loader.load();
        
        GerantController gerantController = loader.getController();
        gerantController.displayName(UserName);
        
        stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    //Open popup Ajouter Paiement Employé
    @FXML
    private Pane triggerPaneAjouterProduit;
    
    private void handleOpenAjouterProduit() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../popup/AjouterProduit.fxml"));
            Parent ajouterProduitRoot = fxmlLoader.load();
            Stage ajouterProduitStage = new Stage();
            ajouterProduitStage.setTitle("AjouterProduit Interface");
            ajouterProduitStage.setScene(new Scene(ajouterProduitRoot));
            ajouterProduitStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    private Pane triggerPaneModifierProduit;
    
    private void handleOpenModifierProduit(Stock stock) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../popup/ModifierProduit.fxml"));
            Parent modifierEmployéRoot = fxmlLoader.load();
            Stage modifierEmployéStage = new Stage();
            
            // Obtenir le contrôleur et passer l'identifiant
        ModifierProduitController controller = fxmlLoader.getController();
          
        controller.initData(stock);
            modifierEmployéStage.setTitle("Modifier Produit");
            modifierEmployéStage.setScene(new Scene(modifierEmployéRoot));
            modifierEmployéStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void GoToStock(MouseEvent event, Stock stock, String username) throws IOException {
        System.out.println("Accueil");
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Stock.fxml"));
        Parent root = loader.load(); 
        
        StockController stockController = loader.getController();
        stockController.displayName(username);
        stockController.ParseStock(stock);
        
        //root = FXMLLoader.load(getClass().getResource("SelectionDesAnalyses.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        
        
    }

    private void ParseStock(Stock stock) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    
    
    //Stock search bar
    @FXML
    TextField stockSearchBar;
    
    @FXML
    VBox StockVbox;
    
    @FXML
    public void searchStock(KeyEvent event) throws Exception{
        String searchInput = stockSearchBar.getText();
        search(searchInput);
    }
    public void ReloadS(MouseEvent event) throws Exception{
    search("");
    }
    
    @FXML
    public void search(String searchInput) throws Exception{
        Socket socket = new Socket("localhost", 6666);
        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());
        
        
        
        String ToDo = "searchStock";

        out.writeObject(ToDo);
        out.flush();
        out.writeObject(searchInput);
        out.flush();
        
        List<Stock> stockList = (List<Stock>) in.readObject();
        
        for (int i = StockVbox.getChildren().size() - 1; i > 0; i--) {
            StockVbox.getChildren().remove(i); 
        }
        
        for (Stock stock : stockList){
            HBox hBox = new HBox();
            hBox.setPrefHeight(50.0);
            hBox.setPrefWidth(1105.0);
            
           
             Label label1 = new Label(stock.getProduit());
           
            label1.setPrefHeight(55.0);
            label1.setPrefWidth(195.0);
            label1.setFont(new Font("Arial Bold", 14.0));
            label1.setPadding(new Insets(0, 0, 0, 15.0));
           
            
                
            
            
            Label label2 = new Label(stock.getFournisseur());
            label2.setPrefHeight(49.0);
            label2.setPrefWidth(213.0);
            label2.setFont(new Font("Arial Bold", 14.0));
            label2.setPadding(new Insets(0, 0, 0, 15.0));
            
            Label label3 = new Label(stock.getQuantité());
            label3.setPrefHeight(49.0);
            label3.setPrefWidth(196.0);
            label3.setFont(new Font("Arial Bold", 14.0));
            label3.setPadding(new Insets(0, 0, 0, 15.0));
            
            Label label4 = new Label(stock.getDate());
            label4.setPrefHeight(49.0);
            label4.setPrefWidth(229.0);
            label4.setFont(new Font("Arial Bold", 14.0));
            label4.setPadding(new Insets(0, 0, 0, 15.0));
            
            ImageView imageView = new ImageView(new Image(getClass().getResourceAsStream("../img/VectorModify.jpg")));
                    imageView.setFitHeight(30);
                    imageView.setFitWidth(30);
                    imageView.setCursor(Cursor.HAND);

                   imageView.setOnMouseClicked((MouseEvent event) -> {
                  
                handleOpenModifierProduit(stock);
                
            });
            
            hBox.getChildren().addAll(label1, label2, label3, label4, imageView);
            
            StockVbox.getChildren().add(hBox);
        }
    }

    
   

}
