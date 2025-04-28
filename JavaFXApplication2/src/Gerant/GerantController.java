/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Gerant;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author mohci
 */
public class GerantController {

    private Stage stage;
    private Scene scene;
    private Parent root;
    
    @FXML
    Label UserNameLabel;
    
    String UserName;
    
    @FXML
    public void displayName(String userName){
        UserNameLabel.setText(userName);
        this.UserName = userName;
    }
    
    @FXML
    public void initialize() {
        //Accueil
        triggerPaneAccueil.setOnMouseClicked(event -> handleOpenAccueil());
        //Finance
        triggerPaneFinance.setOnMouseClicked(event -> handleOpenFinance());
        triggerPanerFinance.setOnMouseClicked(event -> handlerOpenFinance());
        //Stock
        triggerPaneStock.setOnMouseClicked(event -> handleOpenStock());
        triggerPanerStock.setOnMouseClicked(event -> handlerOpenStock());
        //Personnel
        triggerPanePersonnel.setOnMouseClicked(event -> handleOpenPersonnel());
        triggerPanerPersonnel.setOnMouseClicked(event -> handlerOpenPersonnel());
    }
    //Return to Accueil
    @FXML
    private Pane triggerPaneAccueil;

    

    private void handleOpenAccueil() {
    try {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Gerant.fxml"));
        Parent accueilRoot = fxmlLoader.load();
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
            Logger.getLogger(GerantController.class.getName()).log(Level.SEVERE, null, ex);
        }
}

     @FXML
    private Pane triggerPanerFinance;


    private void handlerOpenFinance() {
    try {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Finance.fxml"));
        Parent financeRoot = fxmlLoader.load();
        FinanceController financeController = fxmlLoader.getController();
        financeController.displayName(UserName);
        financeController.searchCF("");
        financeController.searchF("");
        financeController.searchE("");
        triggerPanerFinance.getScene().setRoot(financeRoot);
    } catch (IOException e) {
        e.printStackTrace();
    }   catch (Exception ex) {
            Logger.getLogger(GerantController.class.getName()).log(Level.SEVERE, null, ex);
        }
}
    
    //Open Stock interface from Gerant Interface
    @FXML
    private Pane triggerPaneStock;


    private void handleOpenStock() {
    try {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Stock.fxml"));
        Parent stockRoot = fxmlLoader.load();
        StockController stockController = fxmlLoader.getController();
        stockController.displayName(UserName);
        stockController.search("");
        triggerPaneStock.getScene().setRoot(stockRoot);
    } catch (IOException e) {
        e.printStackTrace();
    }   catch (Exception ex) {
            Logger.getLogger(GerantController.class.getName()).log(Level.SEVERE, null, ex);
        }
}

     @FXML
    private Pane triggerPanerStock;


    private void handlerOpenStock() {
    try {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Stock.fxml"));
        Parent stockRoot = fxmlLoader.load();
        StockController stockController = fxmlLoader.getController();
        stockController.displayName(UserName);
        stockController.search("");
        triggerPanerStock.getScene().setRoot(stockRoot);
    } catch (IOException e) {
        e.printStackTrace();
    }   catch (Exception ex) {
            Logger.getLogger(GerantController.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(GerantController.class.getName()).log(Level.SEVERE, null, ex);
        }
}

     @FXML
    private Pane triggerPanerPersonnel;


    private void handlerOpenPersonnel() {
    try {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Personnel.fxml"));
        Parent personnelRoot = fxmlLoader.load();
        PersonnelController personnelController = fxmlLoader.getController();
        personnelController.displayName(UserName);
        personnelController.searchP("");
        triggerPanerPersonnel.getScene().setRoot(personnelRoot);
    } catch (IOException e) {
        e.printStackTrace();
    }   catch (Exception ex) {
            Logger.getLogger(GerantController.class.getName()).log(Level.SEVERE, null, ex);
        }
}
    //LogOut Button
    @FXML
    public void LogOut(MouseEvent event) throws IOException {
        System.out.println("log out");
        root = FXMLLoader.load(getClass().getResource("../javafxapplication2/Login.fxml"));
        stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    
}
