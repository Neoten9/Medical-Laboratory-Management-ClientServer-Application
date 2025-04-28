/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package javafxapplication2;

import java.awt.Desktop;
import java.awt.print.PrinterException;
import java.io.File;
import java.io.FileInputStream;
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
import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.event.PrintJobAdapter;
import javax.print.event.PrintJobEvent;
import object.Analyse;
import object.Demande;
import object.Patient;
import object.Resultat;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import popup.AjouterUnDemandeController;


/**
 * FXML Controller class
 *
 * @author mohci
 */
public class ListDeDemandeController implements Initializable{
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
        try {
            search("");
        } catch (Exception ex) {
            Logger.getLogger(ListDeDemandeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void Reload(MouseEvent event) throws Exception{
    search("");
    }
    @FXML
    public void GoToAccueilReceptionist(MouseEvent event) throws IOException {
        System.out.println("Acueil");
        //root = FXMLLoader.load(getClass().getResource("receptionist.fxml"));
        FXMLLoader loader = new FXMLLoader(getClass().getResource("receptionist.fxml"));
        root = loader.load();
        
        ReceptionistController receptionistController = loader.getController();
        receptionistController.displayName(UserName);
        
        stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    public void LogOut(MouseEvent event) throws IOException {
        System.out.println("log out");
        root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    public void nouveauDemande (MouseEvent event) throws IOException {
        //root = FXMLLoader.load(getClass().getResource("../popup/AjouterUnDemande.fxml"));
                
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../popup/AjouterUnDemande.fxml"));
        root = loader.load();
        
        AjouterUnDemandeController ajouterUnDemandeController = loader.getController();
        ajouterUnDemandeController.parse(event, UserName);
        
        scene = new Scene(root);
        Stage stage1 = new Stage();
        stage1.setScene(scene);
        stage1.showAndWait();
    }
    
    public void GoToSelectionDesAnalyse(MouseEvent event, Patient patient, String username) throws IOException {
        System.out.println("Acueil");
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("SelectionDesAnalyses.fxml"));
        root = loader.load(); 
        
        SelectionDesAnalysesController selectionDesAnalysesController = loader.getController();
        selectionDesAnalysesController.displayName(username);
        selectionDesAnalysesController.ParsePatient(patient);
        
        //root = FXMLLoader.load(getClass().getResource("SelectionDesAnalyses.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        
        
    }
    
    @FXML
    TextField demandeSearchBar;
    
    @FXML
    VBox ListDemandeVbox;
    
    @FXML
    public void searchDemande(KeyEvent event) throws Exception{
        String searchInput = demandeSearchBar.getText();
        
        search(searchInput);
    }
    
    @FXML
    public void search(String searchInput) throws Exception{
        Socket socket = new Socket("localhost", 6666);
        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());
        
        
        
        String ToDo = "searchBilan";

        out.writeObject(ToDo);
        out.flush();
        out.writeObject(searchInput);
        out.flush();
        
        List<Demande> demandeList = (List<Demande>) in.readObject();
        
        for (int i = ListDemandeVbox.getChildren().size() - 1; i > 0; i--) {
             ListDemandeVbox.getChildren().remove(i); 
        }
        
        for (Demande demande : demandeList){
            HBox hBox = new HBox();
            hBox.setPrefHeight(50.0);
            hBox.setPrefWidth(1105.0);
            
            String idB = Integer.toString(demande.getBilan().getId().get());
            Label label1 = new Label(idB);
            label1.setPrefHeight(55.0);
            label1.setPrefWidth(100.0);
            label1.setFont(new Font("Arial Bold", 14.0));
            label1.setPadding(new Insets(0, 0, 0, 15.0));
            label1.setCursor(Cursor.HAND);
            label1.setOnMouseClicked((MouseEvent ev) -> {
                try {
                    handleBilanClick(demande, ev);
                } catch (IOException ex) {
                    Logger.getLogger(AjouterUnDemandeController.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            });
            
            
            Label label2 = new Label(demande.getCaisse().getPatient().get());
            label2.setPrefHeight(49.0);
            label2.setPrefWidth(250.0);
            label2.setFont(new Font("Arial Bold", 14.0));
            label2.setPadding(new Insets(0, 0, 0, 15.0));
            label2.setCursor(Cursor.HAND);
            label2.setOnMouseClicked((MouseEvent ev) -> {
                try {
                    handleBilanClick(demande, ev);
                } catch (IOException ex) {
                    Logger.getLogger(AjouterUnDemandeController.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            });
            
            Pane imagePane = new Pane();
            imagePane.setPrefHeight(50.0);
            imagePane.setPrefWidth(150.0);
            imagePane.setCursor(Cursor.HAND);
            imagePane.setOnMouseClicked((MouseEvent ev) -> {
                try {
                    handleBilanClick(demande, ev);
                } catch (IOException ex) {
                    Logger.getLogger(AjouterUnDemandeController.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            });
            
            ImageView imageView1 = new ImageView(new Image(getClass().getResource("../img/Vectorpre.png").toExternalForm()));
            imageView1.setFitHeight(30.0);
            imageView1.setFitWidth(30.0);
            imageView1.setLayoutX(6.0);
            imageView1.setLayoutY(10.0);
            imageView1.setPreserveRatio(true);

            ImageView imageView2 = new ImageView(new Image(getClass().getResource("../img/Vectorvmed.png").toExternalForm()));
            imageView2.setFitHeight(30.0);
            imageView2.setFitWidth(30.0);
            imageView2.setLayoutX(45.0);
            imageView2.setLayoutY(10.0);
            imageView2.setPreserveRatio(true);

            ImageView imageView3 = new ImageView(new Image(getClass().getResource("../img/Vectorvbio.png").toExternalForm()));
            imageView3.setFitHeight(30.0);
            imageView3.setFitWidth(30.0);
            imageView3.setLayoutX(85.0);
            imageView3.setLayoutY(10.0);
            imageView3.setPreserveRatio(true);
            
            Pane labelPane = new Pane();
            labelPane.setPrefHeight(50.0);
            labelPane.setPrefWidth(250.0);
            labelPane.setPadding(new Insets(10.0, 0, 0, 15.0));
            labelPane.setCursor(Cursor.HAND);
            labelPane.setOnMouseClicked((MouseEvent ev) -> {
                try {
                    handleBilanClick(demande, ev);
                } catch (IOException ex) {
                    Logger.getLogger(AjouterUnDemandeController.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            });
            
            String VB = "false";
            String VM = "false";
            String VR = "false";
            String imp = "true";
            Label innerLabel1 = null;
            int i = 0;
            double initialX = 14.0;
            double spacing = 40.0;
            for( Resultat resultat : demande.getResultat()){
                if("valide".equals(resultat.getV_medcin().get())){
                    VM = "true";
                    int idA = resultat.getId_analyse().get();
                    for (Analyse analyse : demande.getAnalyse()){
                        if(Objects.equals(idA, analyse.getId().get())){
                            
                            
                            String AnC = analyse.getNomCourant() + ",";
                            innerLabel1 = new Label(AnC);
                            innerLabel1.setLayoutX(initialX + i * spacing);
                            innerLabel1.setLayoutY(17.0); 
                            innerLabel1.setFont(new Font("Arial Bold", 14.0));
                            innerLabel1.setTextFill(Color.web("#26D66C"));
                        }
                    } 
                }
                else if("valide".equals(resultat.getV_biologist().get())){
                    VB = "true";
                    imp = "false";
                    int idA = resultat.getId_analyse().get();
                    for (Analyse analyse : demande.getAnalyse()){
                        if(Objects.equals(idA, analyse.getId().get())){
                            
                           
                            String AnC = analyse.getNomCourant() + ",";
                            innerLabel1 = new Label(AnC);
                            innerLabel1.setLayoutX(initialX + i * spacing);
                            innerLabel1.setLayoutY(17.0); 
                            innerLabel1.setFont(new Font("Arial Bold", 14.0));
                            innerLabel1.setTextFill(Color.web("#1F76C7"));
                            
                        }
                    } 
                }
                else{
                    VR = "true";
                    imp = "false";
                    int idA = resultat.getId_analyse().get();
                    for (Analyse analyse : demande.getAnalyse()){
                        if(Objects.equals(idA, analyse.getId().get())){
                            
                            String AnC = analyse.getNomCourant() + ",";
                            innerLabel1 = new Label(AnC);
                            innerLabel1.setLayoutX(initialX + i * spacing);
                            innerLabel1.setLayoutY(17.0); 
                            innerLabel1.setFont(new Font("Arial Bold", 14.0));
                            innerLabel1.setTextFill(Color.web("#F9603E"));
                            
                        }
                    } 
                }
                
                labelPane.getChildren().addAll(innerLabel1);
                i++;
            }
            if ("true".equals(VR)){
                imagePane.getChildren().addAll(imageView3);
            }
            if ("true".equals(VB)){
                imagePane.getChildren().addAll(imageView2);
            }
            if ("true".equals(VM)){
                imagePane.getChildren().addAll(imageView1);
            }
            
            int reste = demande.getCaisse().getMontant().get() - demande.getCaisse().getPaye().get();
            String r = Integer.toString(reste) + ".00 DA";
            Label labelR = new Label(r);
            labelR.setPrefHeight(49.0);
            labelR.setPrefWidth(250.0);
            labelR.setFont(new Font("Arial Bold", 14.0));
            labelR.setPadding(new Insets(0, 0, 0, 15.0));
            if (reste > 0){
                labelR.setTextFill(Color.web("#F9603E"));
            }else {
                labelR.setTextFill(Color.web("#26D66C"));
            }
            
            Pane imagePane2 = new Pane();
            imagePane2.setPrefHeight(50.0);
            imagePane2.setPrefWidth(100.0);
            
            if ("true".equals(imp)){
                ImageView imageViewImp = new ImageView(new Image(getClass().getResource("../img/Vectorimp2.png").toExternalForm()));            
                imageViewImp.setFitHeight(30.0);
                imageViewImp.setFitWidth(30.0);
                imageViewImp.setLayoutX(6.0);
                imageViewImp.setLayoutY(10.0);
                imageViewImp.setPreserveRatio(true);
                imageViewImp.setCursor(Cursor.HAND);
                imageViewImp.setOnMouseClicked((MouseEvent ev) -> {
                    String filePath = "analyses.pdf";
                    try {
                        // Create PDF
                        createPdfWithMultiplePages(demande, filePath);
                        
                        // Print PDF
                        printPdf(filePath);
                    } catch (IOException ex) {
                        Logger.getLogger(AjouterUnDemandeController.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (PrinterException ex) {
                        Logger.getLogger(ListDeDemandeController.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (PrintException ex) {
                        Logger.getLogger(ListDeDemandeController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                
                });
                
                
                ImageView imageViewfac = new ImageView(new Image(getClass().getResource("../img/Vectorfac2.png").toExternalForm()));            
                imageViewfac.setFitHeight(30.0);
                imageViewfac.setFitWidth(30.0);
                imageViewfac.setLayoutX(45.0);
                imageViewfac.setLayoutY(10.0);
                imageViewfac.setPreserveRatio(true);
                imageViewfac.setCursor(Cursor.HAND);
                imageViewfac.setOnMouseClicked((MouseEvent ev) -> {
                    String filePath = "facture.pdf";
                    try {
                        // Create PDF
                        createPdfFac(demande, filePath);
                        
                        // Print PDF
                        printPdf(filePath);
                    } catch (IOException ex) {
                        Logger.getLogger(AjouterUnDemandeController.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (PrinterException ex) {
                        Logger.getLogger(ListDeDemandeController.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (PrintException ex) {
                        Logger.getLogger(ListDeDemandeController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                
                });
                
                imagePane2.getChildren().addAll(imageViewImp, imageViewfac);
                
            }else {
                ImageView imageViewImp = new ImageView(new Image(getClass().getResource("../img/Vectorimp.png").toExternalForm()));
                imageViewImp.setFitHeight(30.0);
                imageViewImp.setFitWidth(30.0);
                imageViewImp.setLayoutX(6.0);
                imageViewImp.setLayoutY(10.0);
                imageViewImp.setPreserveRatio(true);
                
                ImageView imageViewfac = new ImageView(new Image(getClass().getResource("../img/Vectorfac.png").toExternalForm()));
                imageViewfac.setFitHeight(30.0);
                imageViewfac.setFitWidth(30.0);
                imageViewfac.setLayoutX(45.0);
                imageViewfac.setLayoutY(10.0);
                imageViewfac.setPreserveRatio(true);
                
                imagePane2.getChildren().addAll(imageViewImp, imageViewfac);
            }
            
            
            
            hBox.getChildren().addAll(label1, label2, imagePane, labelPane, labelR, imagePane2);
            
            ListDemandeVbox.getChildren().add(hBox);
        }
    }
      
    public void handleBilanClick(Demande demande, MouseEvent ev) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("SelectionDesAnalyses.fxml"));
        root = loader.load();
        
        SelectionDesAnalysesController selectionDesAnalyses = loader.getController();
        selectionDesAnalyses.displayBilan(demande);
        selectionDesAnalyses.displayName(UserName);
        
        stage = (Stage)((Node) ev.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    public void createPdfWithMultiplePages(Demande demande, String filePath) throws IOException {
        try (PDDocument document = new PDDocument()) {
            
            for(int i = 0; i < demande.getAnalyse().size(); i++){
                
            String dossier = Integer.toString(demande.getBilan().getId().get());
            String datePrelevement = demande.getBilan().getDate().get();
            String nom = demande.getPatient().getNom();
            String prenom = demande.getPatient().getPrenom();
            String dateNaissance = demande.getPatient().getDateNaissance();
            String nomAnalyse = demande.getAnalyse().get(i).getNomAnalyse();
            String nomCAnalyse = demande.getAnalyse().get(i).getNomCourant();
            
            PDPage page = new PDPage();
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                // Header
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 14);
                contentStream.newLineAtOffset(150, 750);
                contentStream.showText("LABORATOIRE D'ANALYSES MEDICALES");
                contentStream.setFont(PDType1Font.HELVETICA, 10);
                contentStream.newLineAtOffset(-100, -15);
                contentStream.showText("Cité 38+10 logts, bourmel (Route moustapha), JIJEL Tel/fax : 024 21 65 84, Mob : 0560 085 222.");
                contentStream.newLineAtOffset(0, -15);
                contentStream.showText("Laboratoire agréé par le ministère de la santé sous le N° : 3200");
                contentStream.endText();

                // Dossier details
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                contentStream.newLineAtOffset(50, 675);
                contentStream.showText("Demande N° : ");
                contentStream.setFont(PDType1Font.HELVETICA, 10);
                contentStream.showText(dossier);
                contentStream.newLineAtOffset(0, -15);
                contentStream.showText("demandé le : " + datePrelevement);
                contentStream.endText();

                // Patient details
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                contentStream.newLineAtOffset(300, 675);
                contentStream.showText("Nom : " + nom);
                contentStream.newLineAtOffset(0, -15);
                contentStream.showText("Prénom : " + prenom);
                contentStream.newLineAtOffset(0, -15);
                contentStream.showText("Date de naissance : " + dateNaissance);
                contentStream.endText();


                // Section title
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                contentStream.newLineAtOffset(50, 575);
                contentStream.showText("Examen demandé : " + nomAnalyse + " (" + nomCAnalyse + ")");
                contentStream.endText();

                // Table header
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                contentStream.newLineAtOffset(50, 550);
                contentStream.showText("Tests");
                contentStream.newLineAtOffset(200, 0);
                contentStream.showText("Résultats");
                contentStream.newLineAtOffset(100, 0);
                contentStream.showText("Unité");
                contentStream.newLineAtOffset(100, 0);
                contentStream.showText("Normes");
                contentStream.endText();

                // Loop through test details
                int yPosition = 525;
                for (int j = 0; j < demande.getAnalyse().get(i).getDesc().size(); j++) {
                    String desc = demande.getAnalyse().get(i).getDesc().get(j);
                    String unite = demande.getAnalyse().get(i).getUnite().get(j);
                    String valeurUs = demande.getAnalyse().get(i).getValeurUs().get(j);
                    String result = demande.getResultat().get(i).getResult().get(j);
                   

                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.HELVETICA, 10);
                    contentStream.newLineAtOffset(50, yPosition);
                    contentStream.showText(desc.replace("\n", "").replace("\r", ""));
                    contentStream.newLineAtOffset(200, 0);
                    contentStream.showText(result.replace("\n", "").replace("\r", ""));
                    contentStream.newLineAtOffset(100, 0);
                    contentStream.showText(unite.replace("\n", "").replace("\r", ""));
                    contentStream.newLineAtOffset(100, 0);
                    contentStream.showText(valeurUs.replace("\n", "").replace("\r", ""));
                    contentStream.endText();

                    yPosition -= 25;
                }
                

                // Footer
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA, 8);
                contentStream.newLineAtOffset(50, 50);
                contentStream.showText("Site web : https://laboratoire.business.site --- Email : laboratoire@gmail.com");
                contentStream.newLineAtOffset(400, 0);
                contentStream.showText("Page : 1/1");
                contentStream.endText();
            }
            
            //String fileUrl = resultat.getId_echantillon().get() + ".pdf";
            document.save(filePath);
        }}
        File pdfFile = new File(filePath);
        if (pdfFile.exists()) {
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().open(pdfFile);
            } else {
                System.out.println("Desktop is not supported, cannot open PDF.");
            }
        } else {
            System.out.println("PDF file not found.");
        }
    }
    
    public void createPdfFac(Demande demande, String filePath) throws IOException {
        try (PDDocument document = new PDDocument()) {
            
            
                
            String dossier = Integer.toString(demande.getBilan().getId().get());
            String datePrelevement = demande.getBilan().getDate().get();
            String nom = demande.getPatient().getNom();
            String prenom = demande.getPatient().getPrenom();
            String dateNaissance = demande.getPatient().getDateNaissance();
            
            
            PDPage page = new PDPage();
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                // Header
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 14);
                contentStream.newLineAtOffset(150, 750);
                contentStream.showText("LABORATOIRE D'ANALYSES MEDICALES");
                contentStream.setFont(PDType1Font.HELVETICA, 10);
                contentStream.newLineAtOffset(-100, -15);
                contentStream.showText("Cité 38+10 logts, bourmel (Route moustapha), JIJEL Tel/fax : 024 21 65 84, Mob : 0560 085 222.");
                contentStream.newLineAtOffset(0, -15);
                contentStream.showText("Laboratoire agréé par le ministère de la santé sous le N° : 3200");
                contentStream.endText();

                // Dossier details
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                contentStream.newLineAtOffset(50, 675);
                contentStream.showText("Demande N° : ");
                contentStream.setFont(PDType1Font.HELVETICA, 10);
                contentStream.showText(dossier);
                contentStream.newLineAtOffset(0, -15);
                contentStream.showText("demandé le : " + datePrelevement);
                contentStream.endText();

                // Patient details
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                contentStream.newLineAtOffset(300, 675);
                contentStream.showText("Nom : " + nom);
                contentStream.newLineAtOffset(0, -15);
                contentStream.showText("Prénom : " + prenom);
                contentStream.newLineAtOffset(0, -15);
                contentStream.showText("Date de naissance : " + dateNaissance);
                contentStream.endText();


                // Section title
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 16);
                contentStream.newLineAtOffset(150, 575);
                contentStream.showText("Facture");
                contentStream.endText();

                // Table header
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                contentStream.newLineAtOffset(50, 550);
                contentStream.showText("Analyse");
                contentStream.newLineAtOffset(350, 0);
                contentStream.showText("Prix");
                
                contentStream.endText();

                // Loop through test details
                int prixT = 0;
                int yPosition = 525;
                for (int i = 0; i < demande.getAnalyse().size(); i++) {
                    
                    String nomAnalyse = demande.getAnalyse().get(i).getNomAnalyse();
                    String nomCAnalyse = demande.getAnalyse().get(i).getNomCourant();
                    String prix = demande.getAnalyse().get(i).getPrix();
                    prixT = prixT + Integer.parseInt(prix);
                    
                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.HELVETICA, 10);
                    contentStream.newLineAtOffset(50, yPosition);
                    contentStream.showText("" + nomAnalyse + " (" + nomCAnalyse + ")");
                    contentStream.newLineAtOffset(350, 0);
                    contentStream.showText(prix + ".00 DA");
                    contentStream.endText();

                    yPosition -= 25;
                }
                String pT = Integer.toString(prixT) + ".00 DA";
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                contentStream.newLineAtOffset(50, yPosition - 10);
                contentStream.showText("Total");
                contentStream.newLineAtOffset(350, 0);
                contentStream.showText(pT);
                contentStream.endText();

                // Footer
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA, 8);
                contentStream.newLineAtOffset(50, 50);
                contentStream.showText("Site web : https://laboratoire.business.site --- Email : laboratoire@gmail.com");
                contentStream.newLineAtOffset(400, 0);
                contentStream.showText("Page : 1/1");
                contentStream.endText();
            }
            
            //String fileUrl = resultat.getId_echantillon().get() + ".pdf";
            document.save(filePath);
        }
        File pdfFile = new File(filePath);
        if (pdfFile.exists()) {
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().open(pdfFile);
            } else {
                System.out.println("Desktop is not supported, cannot open PDF.");
            }
        } else {
            System.out.println("PDF file not found.");
        }
    }
    
    public void printPdf(String filePath) throws IOException, PrinterException, PrintException {
        FileInputStream fis = new FileInputStream(filePath);
        Doc pdfDoc = new SimpleDoc(fis, DocFlavor.INPUT_STREAM.AUTOSENSE, null);
        PrintRequestAttributeSet attributes = new HashPrintRequestAttributeSet();
        PrintService defaultPrintService = PrintServiceLookup.lookupDefaultPrintService();

        if (defaultPrintService != null) {
            DocPrintJob printJob = defaultPrintService.createPrintJob();
            printJob.addPrintJobListener(new PrintJobAdapter() {
                public void printJobCompleted(PrintJobEvent pje) {
                    System.out.println("Print job completed");
                }

                public void printJobFailed(PrintJobEvent pje) {
                    System.out.println("Print job failed");
                }
            });
            printJob.print(pdfDoc, attributes);
        } else {
            System.err.println("No printers installed");
        }
        fis.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            search("");
        } catch (Exception ex) {
            Logger.getLogger(ListDeDemandeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
