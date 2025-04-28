package Gerant;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URL;
import java.util.List;
import java.util.Objects;
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
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import object.Analyse;
import object.Caisse;
import object.CaisseF;
import object.Demande;
import object.Employé;
import object.Fournisseur;
import object.PaiementE;
import object.PaiementF;
import object.Resultat;
import popup.AjouterUnDemandeController;
import popup.ModifierPaiementEmployéController;
import popup.ModifierPaiementFournisseurController;

public class FinanceController {
    
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
        //Stock
        triggerPaneStock.setOnMouseClicked(event -> handleOpenStock());
        //Personnel
        triggerPanePersonnel.setOnMouseClicked(event -> handleOpenPersonnel());
        //Open Total
        triggerPaneTotal.setOnMouseClicked(event -> handleOpenTotal());
        //Open popup Recherche Employé
        triggerPaneRechercheEmployé.setOnMouseClicked(event -> handleOpenRechercheEmployé());
        //Open popup Ajouter Paiement Fournisseur
        triggerPanePaiementFournisseur.setOnMouseClicked(event -> handleOpenPaiementFournisseur());
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
            Logger.getLogger(FinanceController.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(FinanceController.class.getName()).log(Level.SEVERE, null, ex);
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
    //Open popup Total
    @FXML
    private Pane triggerPaneTotal;
    
    private void handleOpenTotal() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../popup/Total.fxml"));
            Parent totalRoot = fxmlLoader.load();
            Stage totalStage = new Stage();
            totalStage.setTitle("Total Interface");
            totalStage.setScene(new Scene(totalRoot));
            totalStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //Open popup Recherche Employé
    @FXML
    private Pane triggerPaneRechercheEmployé;
    
    private void handleOpenRechercheEmployé() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../popup/RechercheEmployé.fxml"));
            Parent rechercheemployéRoot = fxmlLoader.load();
            Stage rechercheemployéStage = new Stage();
            rechercheemployéStage.setTitle("Recherche Employé Interface");
            rechercheemployéStage.setScene(new Scene(rechercheemployéRoot));
            rechercheemployéStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //Open popup Ajouter Paiement Fournisseur
    @FXML
    private Pane triggerPanePaiementFournisseur;
    
    private void handleOpenPaiementFournisseur() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../popup/PaiementFournisseur.fxml"));
            Parent paiementFournisseurRoot = fxmlLoader.load();
            Stage paiementFournisseurStage = new Stage();
            paiementFournisseurStage.setTitle("Ajouter Paiement Fournisseur Interface");
            paiementFournisseurStage.setScene(new Scene(paiementFournisseurRoot));
            paiementFournisseurStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void GoToFinance(MouseEvent event, PaiementF paiementF, String username) throws IOException {
        System.out.println("Accueil");
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Finance.fxml"));
        Parent root = loader.load(); 
        
        FinanceController financeController = loader.getController();
        financeController.displayName(username);
        financeController.ParsePaiementF(paiementF);
        
        //root = FXMLLoader.load(getClass().getResource("SelectionDesAnalyses.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        
        
    }

    

    private void ParsePaiementF(PaiementF paiementF) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public static class PFdata {

        public static void add(PaiementF returnedPaiementF) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        public static void add(PaiementE returnedPaiementE) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        public PFdata() {
        }
    }
    
   @FXML
    TextField caisseFSearchBar;
    
    @FXML
    VBox CaisseFVbox;
    
    @FXML
    public void searchCaisseF(KeyEvent event) throws Exception{
        String searchInput = caisseFSearchBar.getText();
        searchCF(searchInput);
    }
    @FXML
    public void searchCF(String searchInput) throws Exception{
        Socket socket = new Socket("localhost", 6666);
        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());
        
        
        
        String ToDo = "searchCaisseF";

        out.writeObject(ToDo);
        out.flush();
        out.writeObject(searchInput);
        out.flush();
        
        List<CaisseF> caisseF = (List<CaisseF>) in.readObject();
        
        for (int i = CaisseFVbox.getChildren().size() - 1; i > 0; i--) {
             CaisseFVbox.getChildren().remove(i); 
        }
        
        for (CaisseF c : caisseF){
            HBox hBox = new HBox();
            hBox.setPrefHeight(50.0);
            hBox.setPrefWidth(1105.0);
            
            String idB = Integer.toString(c.getMontant().get());
            Label label1 = new Label(idB);
            label1.setPrefHeight(55.0);
            label1.setPrefWidth(171.0);
            label1.setFont(new Font("Arial Bold", 14.0));
            label1.setPadding(new Insets(0, 0, 0, 15.0));
            label1.setCursor(Cursor.HAND);
            label1.setOnMouseClicked((MouseEvent ev) -> {
                
            });
            
            String idP = Integer.toString(c.getPaye().get());
            Label label2 = new Label(idP);
            label2.setPrefHeight(49.0);
            label2.setPrefWidth(173.0);
            label2.setFont(new Font("Arial Bold", 14.0));
            label2.setPadding(new Insets(0, 0, 0, 15.0));
            label2.setCursor(Cursor.HAND);
            label2.setOnMouseClicked((MouseEvent ev) -> {
                
            });
            
            int reste = c.getMontant().get() - c.getPaye().get();
            String r = Integer.toString(reste) + ".00 DA";
            Label labelR = new Label(r);
            labelR.setPrefHeight(49.0);
            labelR.setPrefWidth(201.0);
            labelR.setFont(new Font("Arial Bold", 14.0));
            labelR.setPadding(new Insets(0, 0, 0, 15.0));
            if (reste > 0){
                labelR.setTextFill(Color.web("#F9603E"));
            }else {
                labelR.setTextFill(Color.web("#26D66C"));
            }
            
           Label label3 = new Label(c.getTests());
           
            label3.setPrefHeight(55.0);
            label3.setPrefWidth(200.0);
            label3.setFont(new Font("Arial Bold", 14.0));
            label3.setPadding(new Insets(0, 0, 0, 15.0));
             
            Label label4 = new Label(c.getPatient().get());
            label4.setPrefHeight(55.0);
            label4.setPrefWidth(200.0);
            label4.setFont(new Font("Arial Bold", 14.0));
            label4.setPadding(new Insets(0, 0, 0, 15.0));
            
            Label label5 = new Label(c.getDate().get());
            label5.setPrefHeight(49.0);
            label5.setPrefWidth(200.0);
            label5.setFont(new Font("Arial Bold", 14.0));
            label5.setPadding(new Insets(0, 0, 0, 15.0));
            
            
  
            hBox.getChildren().addAll(label1, label2, labelR, label3, label4, label5);
            
            CaisseFVbox.getChildren().add(hBox);
        }
    }
    public void ReloadCF(MouseEvent event) throws Exception{
    searchCF("");
    }
    //open modify Employé pop up
    @FXML
    private Pane triggerPaneModifierPaiementFournisseur;
    
    private void handleOpenModifierPaiementFournisseur(Fournisseur paiementF) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../popup/ModifierPaiementFournisseur.fxml"));
            Parent modifierEmployéRoot = fxmlLoader.load();
            Stage modifierEmployéStage = new Stage();
            
            // Obtenir le contrôleur et passer l'identifiant
        ModifierPaiementFournisseurController controller = fxmlLoader.getController();
          
        controller.initData(paiementF);
            modifierEmployéStage.setTitle("Modifier Paiement Founrnisseur");
            modifierEmployéStage.setScene(new Scene(modifierEmployéRoot));
            modifierEmployéStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    //Fournisseur search bar
    @FXML
    TextField fournisseurSearchBar;
    
    @FXML
    VBox FournisseurVbox;
    
    @FXML
    public void searchFournisseur(KeyEvent event) throws Exception{
        String searchInput = fournisseurSearchBar.getText();
        searchF(searchInput);
    }
    public void ReloadF(MouseEvent event) throws Exception{
    searchF("");
    }
    @FXML
    public void searchF(String searchInput) throws Exception{
        Socket socket = new Socket("localhost", 6666);
        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());
        
        
        
        String ToDo = "searchFournisseur";

        out.writeObject(ToDo);
        out.flush();
        out.writeObject(searchInput);
        out.flush();
        
        List<Fournisseur> fournisseurList = (List<Fournisseur>) in.readObject();
        
        for (int i = FournisseurVbox.getChildren().size() - 1; i > 0; i--) {
             FournisseurVbox.getChildren().remove(i); 
        }
        
        for (Fournisseur fournisseur : fournisseurList){
            HBox hBox = new HBox();
            hBox.setPrefHeight(50.0);
            hBox.setPrefWidth(1105.0);
            
           
             Label label1 = new Label(fournisseur.getMontant());
           
            label1.setPrefHeight(55.0);
            label1.setPrefWidth(179.0);
            label1.setFont(new Font("Arial Bold", 14.0));
            label1.setPadding(new Insets(0, 0, 0, 15.0));
           
            
            Label label2 = new Label(fournisseur.getFournisseur());
            label2.setPrefHeight(49.0);
            label2.setPrefWidth(231.0);
            label2.setFont(new Font("Arial Bold", 14.0));
            label2.setPadding(new Insets(0, 0, 0, 15.0));
            
            Label label3 = new Label(fournisseur.getProduit());
            label3.setPrefHeight(49.0);
            label3.setPrefWidth(186.0);
            label3.setFont(new Font("Arial Bold", 14.0));
            label3.setPadding(new Insets(0, 0, 0, 15.0));
            
            Label label4 = new Label(fournisseur.getDate());
            label4.setPrefHeight(49.0);
            label4.setPrefWidth(244.0);
            label4.setFont(new Font("Arial Bold", 14.0));
            label4.setPadding(new Insets(0, 0, 0, 15.0));
            
            ImageView imageView = new ImageView(new Image(getClass().getResourceAsStream("../img/VectorModify.jpg")));
                    imageView.setFitHeight(30);
                    imageView.setFitWidth(30);
                    imageView.setCursor(Cursor.HAND);

                   imageView.setOnMouseClicked((MouseEvent event) -> {
                  
                handleOpenModifierPaiementFournisseur(fournisseur);
                
            });
            
       
            hBox.getChildren().addAll(label1, label2, label3, label4, imageView);
            
            FournisseurVbox.getChildren().add(hBox);
        }
    }
    
    //open modify Employé pop up
    @FXML
    private Pane triggerPaneModifierProduit;
    
    private void handleOpenModifierPaiementEmployé(Employé paiementE) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../popup/ModifierPaiementEmployé.fxml"));
            Parent modifierEmployéRoot = fxmlLoader.load();
            Stage modifierEmployéStage = new Stage();
            
            // Obtenir le contrôleur et passer l'identifiant
        ModifierPaiementEmployéController controller = fxmlLoader.getController();
          
        controller.initData(paiementE);
            modifierEmployéStage.setTitle("Modifier Paiement Employé");
            modifierEmployéStage.setScene(new Scene(modifierEmployéRoot));
            modifierEmployéStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //Employé search bar
    @FXML
    TextField employéSearchBar;
    
    @FXML
    VBox EmployéVbox;
    
    
    @FXML
    public void searchEmployé(KeyEvent event) throws Exception{
        String searchInput = employéSearchBar.getText();
        searchE(searchInput);
    }
    public void ReloadE(MouseEvent event) throws Exception{
    searchE("");
    }
    @FXML
    public void searchE(String searchInput) throws Exception{
        Socket socket = new Socket("localhost", 6666);
        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());
        
        
        
        String ToDo = "searchEmployé";

        out.writeObject(ToDo);
        out.flush();
        out.writeObject(searchInput);
        out.flush();
        
        List<Employé> employéList = (List<Employé>) in.readObject();
        
        for (int i = EmployéVbox.getChildren().size() - 1; i > 0; i--) {
            EmployéVbox.getChildren().remove(i); 
        }
        
        for (Employé employé : employéList){
            HBox hBox = new HBox();
            hBox.setPrefHeight(50.0);
            hBox.setPrefWidth(1105.0);
            
           
             Label label1 = new Label(employé.getEmploye());
           
            label1.setPrefHeight(55.0);
            label1.setPrefWidth(179.0);
            label1.setFont(new Font("Arial Bold", 14.0));
            label1.setPadding(new Insets(0, 0, 0, 15.0));
           
            
                
            
            
            Label label2 = new Label(employé.getMontant());
            label2.setPrefHeight(49.0);
            label2.setPrefWidth(231.0);
            label2.setFont(new Font("Arial Bold", 14.0));
            label2.setPadding(new Insets(0, 0, 0, 15.0));
            
            Label label3 = new Label(employé.getPosition());
            label3.setPrefHeight(49.0);
            label3.setPrefWidth(186.0);
            label3.setFont(new Font("Arial Bold", 14.0));
            label3.setPadding(new Insets(0, 0, 0, 15.0));
            
            Label label4 = new Label(employé.getDate());
            label4.setPrefHeight(49.0);
            label4.setPrefWidth(244.0);
            label4.setFont(new Font("Arial Bold", 14.0));
            label4.setPadding(new Insets(0, 0, 0, 15.0));
            
            ImageView imageView = new ImageView(new Image(getClass().getResourceAsStream("../img/VectorModify.jpg")));
                    imageView.setFitHeight(30);
                    imageView.setFitWidth(30);
                    imageView.setCursor(Cursor.HAND);

                   imageView.setOnMouseClicked((MouseEvent event) -> {
                  
                handleOpenModifierPaiementEmployé(employé);
                
            });
            
            hBox.getChildren().addAll(label1, label2, label3, label4, imageView);
            
            EmployéVbox.getChildren().add(hBox);
        }
    }
      
    
   
      
    
}