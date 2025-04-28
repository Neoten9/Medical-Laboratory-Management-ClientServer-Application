package popup;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.Cursor;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import object.Employé;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class RechercheEmployéController {
    private ObjectOutputStream out;
    private ObjectInputStream in;
    @FXML
    private TextField employéSearchBar;

    @FXML
    private VBox VboxEmployé;

    

    private Socket socket;

    @FXML
    public void initialize() {
        //Open popup Ajouter Paiement Employé
        triggerPanePaiementEmployé.setOnMouseClicked(event -> handleOpenPaiementEmployé());
        // Initialize socket connection
        
    }

    @FXML
    public void searchEmployé(KeyEvent event) {
        String searchInput = employéSearchBar.getText();
        try {
            start(searchInput);
        } catch (IOException ex) {
            Logger.getLogger(RechercheEmployéController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void start(String searchInput) throws IOException {
        Socket socket = new Socket("localhost", 6666);
        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());
        
        try {
            String ToDo = "searchEmployé";
            out.writeObject(ToDo);
            out.writeObject(searchInput);
            out.flush();

            List<Employé> employéList = (List<Employé>) in.readObject();

            
                for (int i = VboxEmployé.getChildren().size() - 1; i > 0; i--) {
                    VboxEmployé.getChildren().remove(i); 
                }
                for (Employé employé : employéList) {
                    HBox employéBox = new HBox();

                    Label Nom = new Label(employé.getEmploye());
                    Nom.getStyleClass().add("custom-label");
                    Nom.setPrefHeight(55.0);
                    Nom.setPrefWidth(170.0);
                    Nom.setFont(new Font("Arial Bold", 14.0));
                    employéBox.getChildren().add(Nom);

                    Label Prenom = new Label(employé.getPosition());
                    Prenom.getStyleClass().add("custom-label");
                    Prenom.setPrefHeight(55.0);
                    Prenom.setPrefWidth(170.0);
                    Prenom.setFont(new Font("Arial Bold", 14.0));
                    employéBox.getChildren().add(Prenom);

                    Label DateNaissance = new Label(employé.getDate());
                    DateNaissance.getStyleClass().add("custom-label");
                    DateNaissance.setPrefHeight(55.0);
                    DateNaissance.setPrefWidth(170.0);
                    DateNaissance.setFont(new Font("Arial Bold", 14.0));
                    employéBox.getChildren().add(DateNaissance);

                    ImageView imageView = new ImageView(new Image(getClass().getResourceAsStream("../img/Vectorarrew-right.png")));
                    imageView.setFitHeight(30);
                    imageView.setFitWidth(30);
                    imageView.setCursor(Cursor.HAND);

                   imageView.setOnMouseClicked((MouseEvent event) -> {
                  
                handleOpenPaiementEmployé();
                Stage stage1 = (Stage)((Node) event.getSource()).getScene().getWindow();
                stage1.close();
            });

                    employéBox.getChildren().add(imageView);
                    VboxEmployé.getChildren().add(employéBox);
                }
            
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void cleanup() {
        try {
            // Close resources
            if (out != null) out.close();
            if (in != null) in.close();
            if (socket != null) socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private Pane triggerPanePaiementEmployé;
    
    private void handleOpenPaiementEmployé() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("PaiementEmploye.fxml"));
            Parent paiementemployéRoot = fxmlLoader.load();
            Stage paiementemployéStage = new Stage();
            paiementemployéStage.setTitle("Ajouter Paiement Employé Interface");
            paiementemployéStage.setScene(new Scene(paiementemployéRoot));
            paiementemployéStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
   
}
