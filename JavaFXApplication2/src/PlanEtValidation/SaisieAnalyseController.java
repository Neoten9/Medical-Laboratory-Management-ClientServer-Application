/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package PlanEtValidation;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
import javafxapplication2.SelectionDesAnalysesController;
import object.Analyse;
import object.Resultat;

/**
 * FXML Controller class
 *
 * @author mohci
 */
public class SaisieAnalyseController implements Initializable {
    
    private ObjectOutputStream out;
    private ObjectInputStream in;
    
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
    public void GoBack(MouseEvent event) throws IOException {
        System.out.println("log out");
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Plan.fxml"));
        root = loader.load();
                
        PlanController planController = loader.getController();
        planController.displayName(UserName);
        
        stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    @FXML    
    TextField EchantillonSearchbar;
    
    @FXML    
    TextField CodeEchantillon;
    
    @FXML    
    TextField NomAnalyse;
    
    
    
    @FXML
    VBox PlanEchanVbox;
    @FXML
    VBox PlanVbox;
    
    @FXML
    public void searchEchantillon(KeyEvent event) throws Exception{
        String searchInput = EchantillonSearchbar.getText();
        
        search(searchInput);
    }
    
    @FXML
    public void search(String searchInput) throws IOException, ClassNotFoundException{
        Socket socket = new Socket("localhost", 6666);
        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());
        
        String ToDo = "searchEchantillon";

        out.writeObject(ToDo);
        out.flush();
        out.writeObject(searchInput);
        out.flush();
        
        List<Resultat> resultList = (List<Resultat>) in.readObject();
        
        for (int i = PlanEchanVbox.getChildren().size() - 1; i > 0; i--) {
            PlanEchanVbox.getChildren().remove(i); 
        }
        
        
        for (Resultat res : resultList){
            HBox echantillonBox = new HBox();
            
            Label label1 = new Label(res.getId_echantillon().get());
            label1.setPrefHeight(49.0);
            label1.setPrefWidth(200.0);
            label1.setFont(new Font("Arial Bold", 14.0));
            label1.setPadding(new Insets(0, 0, 0, 15.0));
            echantillonBox.getChildren().add(label1);
            
            Analyse analyse = getAnalyse(res.getId_analyse().get());
            
            Label label2 = new Label(analyse.getNomCourant());
            label2.setPrefHeight(49.0);
            label2.setPrefWidth(200.0);
            label2.setFont(new Font("Arial Bold", 14.0));
            label2.setPadding(new Insets(0, 0, 0, 15.0));
            echantillonBox.getChildren().add(label2);
            
            Pane imagePane2 = new Pane();
            imagePane2.setPrefHeight(50.0);
            imagePane2.setPrefWidth(50.0);
            
            ImageView imageView = new ImageView(new Image(getClass().getResourceAsStream("../img/Vectorarrew-right.png")));
            imageView.setFitHeight(30.0);
            imageView.setFitWidth(30.0);
            imageView.setLayoutX(6.0);
            imageView.setLayoutY(10.0);
            imageView.setPreserveRatio(true);
            imageView.setCursor(Cursor.HAND);
            imageView.setOnMouseClicked((MouseEvent e) -> {
                desplayAnalyse(res, analyse);
                    
                
            });
            
            imagePane2.getChildren().add(imageView);
            
            echantillonBox.getChildren().add(imagePane2);
            
            PlanEchanVbox.getChildren().add(echantillonBox);
            
        }
    }
    
    List<TextField> textFieldList = new ArrayList<>();
    Resultat res;
    public void desplayAnalyse(Resultat resultat, Analyse analyse){
        CodeEchantillon.setText(resultat.getId_echantillon().get());
        NomAnalyse.setText(analyse.getNomCourant());
        res = resultat;
        textFieldList = new ArrayList<>();
        
        for (int i = PlanVbox.getChildren().size() - 1; i > 0; i--) {
            PlanVbox.getChildren().remove(i); 
        }
        
        for (int i = 0; i < analyse.getDesc().size(); i++) {
            String desc = analyse.getDesc().get(i);
            String unite = analyse.getUnite().get(i);
            String valeurUs = analyse.getValeurUs().get(i);

            // Create an HBox for each pair of values
            HBox hbox = new HBox();
            
            Label label1 = new Label(desc);
            label1.setPrefHeight(49.0);
            label1.setPrefWidth(200.0);
            label1.setFont(new Font("Arial Bold", 14.0));
            label1.setPadding(new Insets(0, 0, 0, 15.0));
            hbox.getChildren().add(label1);
            
            Pane textPane2 = new Pane();
            textPane2.setPrefHeight(50.0);
            textPane2.setPrefWidth(150.0);
            
            TextField inputField = new TextField();
            inputField.setLayoutX(6.0);
            inputField.setLayoutY(15.0);
            inputField.setStyle(
                "-fx-background-color: #f1f1f1; " +
                "-fx-border-color: #d3d3d3; " +
                "-fx-border-radius: 5; " +
                "-fx-background-radius: 5; " +
                "-fx-padding: 5 10 5 15; " +
                "-fx-font-size: 14px; " +
                "-fx-font-family: 'Arial'; " +
                "-fx-text-fill: #333333;" + 
                "-fx-pref-width: 100px;"
            );
            textFieldList.add(inputField);
            textPane2.getChildren().add(inputField);
            
            hbox.getChildren().add(textPane2);
            
            Label label2 = new Label(valeurUs);
            label2.setPrefHeight(49.0);
            label2.setPrefWidth(150.0);
            label2.setFont(new Font("Arial Bold", 14.0));
            label2.setPadding(new Insets(0, 0, 0, 15.0));
            hbox.getChildren().add(label2);
            
            Label label3 = new Label(unite);
            label3.setPrefHeight(49.0);
            label3.setPrefWidth(150.0);
            label3.setFont(new Font("Arial Bold", 14.0));
            label3.setPadding(new Insets(0, 0, 0, 15.0));
            hbox.getChildren().add(label3);
            

            // Add HBox to the VBox
            PlanVbox.getChildren().add(hbox);
            
            
        }
        

    }
    
    @FXML
    public Analyse getAnalyse(int id) throws IOException, ClassNotFoundException{
        Socket socket = new Socket("localhost", 6666);
        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());
        
        
        String ToDo = "getAnalyse";

        out.writeObject(ToDo);
        out.flush();
        out.writeObject(id);
        out.flush();
        
        Analyse analyse = (Analyse) in.readObject();
        return analyse;
    }
    
    public void valider(MouseEvent e) throws IOException, ClassNotFoundException{
        Socket socket = new Socket("localhost", 6666);
        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());
        
        List<String> inputValues = new ArrayList<>();
        for (TextField textField : textFieldList) {
            inputValues.add(textField.getText());
        }
        System.out.println("Collected values: " + inputValues);
        System.out.println(res.getId_echantillon().get());
        
        Resultat resultat = new Resultat(res.getId().get(), res.getId_analyse().get(), res.getId_echantillon().get(), res.getV_biologist().get(), res.getV_medcin().get(), inputValues);
        
        String ToDo = "validerResultat";

        out.writeObject(ToDo);
        out.flush();
        out.writeObject(resultat);
        out.flush();
        
        CodeEchantillon.setText("");
        NomAnalyse.setText("");
        
        for (int i = PlanVbox.getChildren().size() - 1; i > 0; i--) {
            PlanVbox.getChildren().remove(i); 
        }
        
        search("");
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            search("");
        } catch (IOException ex) {
            Logger.getLogger(SaisieAnalyseController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SaisieAnalyseController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
