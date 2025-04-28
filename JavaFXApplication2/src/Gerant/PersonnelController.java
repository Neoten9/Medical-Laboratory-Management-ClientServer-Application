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
import object.PersonnelS;
import object.PersonnelS;
import popup.ModifierEmployéController;

/**
 * FXML Controller class
 *
 * @author mohci
 */
public class PersonnelController {
    
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
        //Stock
        triggerPaneStock.setOnMouseClicked(event -> handleOpenStock());

        //Add Personnel
        triggerPaneAddPersonnel.setOnMouseClicked(event -> handleOpenAddPersonnel());
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
            Logger.getLogger(PersonnelController.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(PersonnelController.class.getName()).log(Level.SEVERE, null, ex);
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
    //open modify pop up
    @FXML
    private Pane triggerPaneModifierEmployé;
    
    private void handleOpenModifierEmployé(PersonnelS personnel) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../popup/ModifierEmployé.fxml"));
            Parent modifierEmployéRoot = fxmlLoader.load();
            Stage modifierEmployéStage = new Stage();
            
            // Obtenir le contrôleur et passer l'identifiant
        ModifierEmployéController controller = fxmlLoader.getController();
          
        controller.initData(personnel);
            modifierEmployéStage.setTitle("Modifier Employé");
            modifierEmployéStage.setScene(new Scene(modifierEmployéRoot));
            modifierEmployéStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //add Personnel
    @FXML
    private Pane triggerPaneAddPersonnel;
    
    private void handleOpenAddPersonnel() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../popup/AjouterUnEmployé.fxml"));
            Parent addpersonnelRoot = fxmlLoader.load();
            Stage addpersonnelStage = new Stage();
            addpersonnelStage.setTitle("Ajouter Employé Interface");
            addpersonnelStage.setScene(new Scene(addpersonnelRoot));
            addpersonnelStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    TextField personnelSearchBar;
    
    @FXML
    VBox PersonnelVbox;
    
    @FXML
    public void searchPersonnel(KeyEvent event) throws Exception{
        String searchInput = personnelSearchBar.getText();
        searchP(searchInput);
    }
    public void ReloadP(MouseEvent event) throws Exception{
    searchP("");
    }
    @FXML
    public void searchP(String searchInput) throws Exception{
        Socket socket = new Socket("localhost", 6666);
        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());
        
        
        
        String ToDo = "searchPersonnel";

        out.writeObject(ToDo);
        out.flush();
        out.writeObject(searchInput);
        out.flush();
        
        List<PersonnelS> personnelSList = (List<PersonnelS>) in.readObject();
        
        for (int i = PersonnelVbox.getChildren().size() - 1; i > 0; i--) {
             PersonnelVbox.getChildren().remove(i); 
        }
        
        for (PersonnelS personnelS : personnelSList){
            HBox hBox = new HBox();
            hBox.setPrefHeight(50.0);
            hBox.setPrefWidth(1105.0);
            
           
            String idD = Integer.toString(personnelS.getId());
            Label label1 = new Label(idD);
           
            label1.setPrefHeight(55.0);
            label1.setPrefWidth(97.0);
            label1.setFont(new Font("Arial Bold", 14.0));
            label1.setPadding(new Insets(0, 0, 0, 15.0));
           
            
                
            
            
            Label label2 = new Label(personnelS.getNom());
            label2.setPrefHeight(49.0);
            label2.setPrefWidth(129.0);
            label2.setFont(new Font("Arial Bold", 14.0));
            label2.setPadding(new Insets(0, 0, 0, 15.0));
            
            Label label3 = new Label(personnelS.getPrenom());
            label3.setPrefHeight(49.0);
            label3.setPrefWidth(140.0);
            label3.setFont(new Font("Arial Bold", 14.0));
            label3.setPadding(new Insets(0, 0, 0, 15.0));
            
            Label label4 = new Label(personnelS.getPosition());
            label4.setPrefHeight(49.0);
            label4.setPrefWidth(158.0);
            label4.setFont(new Font("Arial Bold", 14.0));
            label4.setPadding(new Insets(0, 0, 0, 15.0));
            
            Label label5 = new Label(personnelS.getTelephone());
            label5.setPrefHeight(49.0);
            label5.setPrefWidth(218.0);
            label5.setFont(new Font("Arial Bold", 14.0));
            label5.setPadding(new Insets(0, 0, 0, 15.0));
            
            Label label6 = new Label(personnelS.getMotDePass());
            label6.setPrefHeight(49.0);
            label6.setPrefWidth(220.0);
            label6.setFont(new Font("Arial Bold", 14.0));
            label6.setPadding(new Insets(0, 0, 0, 15.0));
            
            ImageView imageView = new ImageView(new Image(getClass().getResourceAsStream("../img/VectorModify.jpg")));
                    imageView.setFitHeight(30);
                    imageView.setFitWidth(30);
                    imageView.setCursor(Cursor.HAND);

                   imageView.setOnMouseClicked((MouseEvent event) -> {
                  
                handleOpenModifierEmployé(personnelS);
                
            });
            
            hBox.getChildren().addAll(label1, label2, label3, label4, label5, label6, imageView);
            
            PersonnelVbox.getChildren().add(hBox);
        }
    }
    
}
