/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXML2.java to edit this template
 */
package javafxapplication2;


import Gerant.GerantController;
import PlanEtValidation.PlanController;
import PlanEtValidation.ValidationController;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;

/**
 *
 * @author mohci
 */
public class FXMLDocumentController {
    
    private Stage stage;
    private Scene scene;
    private Parent root;
    
    private ObjectOutputStream out;
    private ObjectInputStream in;
    
    @FXML
    private TextField UserName;
    @FXML
    private TextField PassWord;
    
    @FXML
    public void handleLogin(ActionEvent event) throws IOException, Exception {
        System.out.println("123");
  
        String userName = UserName.getText();
        String password = PassWord.getText();
        
        String position = start(userName, password);
        FXMLLoader loader;
        
        switch (position) {
            case "receptionist":
                loader = new FXMLLoader(getClass().getResource("receptionist.fxml"));
                root = loader.load();
                
                ReceptionistController receptionistController = loader.getController();
                receptionistController.displayName(userName);
        
                //root = FXMLLoader.load(getClass().getResource("receptionist.fxml"));
                break;
            case "biologist":
                loader = new FXMLLoader(getClass().getResource("../PlanEtValidation/Plan.fxml"));
                root = loader.load();
                
                PlanController planController = loader.getController();
                planController.displayName(userName);

                //root = FXMLLoader.load(getClass().getResource("../PlanEtValidation/Plan.fxml"));
                break;
            case "medcine":
                loader = new FXMLLoader(getClass().getResource("../PlanEtValidation/Validation.fxml"));
                root = loader.load();
                
                ValidationController validationController = loader.getController();
                validationController.displayName(userName);
                
                //root = FXMLLoader.load(getClass().getResource("../PlanEtValidation/Validation.fxml"));
                break;
            case "gerant":
                loader = new FXMLLoader(getClass().getResource("../Gerant/Gerant.fxml"));
                root = loader.load();
                
                GerantController gerantController = loader.getController();
                gerantController.displayName(userName);
                
                //root = FXMLLoader.load(getClass().getResource("../Gerant/Gerant.fxml"));
                break;
            default:
                loader = new FXMLLoader(getClass().getResource("Login.fxml"));
                root = loader.load();
                showAlert();
                break;
        }
        
        
        
        stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        
    }
    
    
    public String start(String userName, String password) throws Exception {
        Socket socket = new Socket("localhost", 6666);
        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());

            //String message = UserName.getText();
            String ToDo = "Login";
            
            out.writeObject(ToDo);
            out.flush();
            
            out.writeObject(userName);
            out.flush();
            
            out.writeObject(password);
            out.flush();
            
            String position = (String) in.readObject();
            System.out.println(position);
            
            return position; 
    }
    
    public void showAlert() {
    Alert alert = new Alert(AlertType.INFORMATION); // You can change the type to WARNING, ERROR, etc.
    alert.setTitle("error");
    alert.setHeaderText("Username or password is wrong");
    alert.setContentText("try again");

    alert.showAndWait(); // This will block user interaction until the alert is dismissed
}
    
       
    
}
