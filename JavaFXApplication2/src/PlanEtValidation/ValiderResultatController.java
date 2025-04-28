/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package PlanEtValidation;


import javafx.scene.canvas.Canvas;
import java.awt.Desktop;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URI;
import java.net.URL;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import object.Analyse;
import object.Bilan;
import object.Patient;
import object.Resultat;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.commons.logging.LogFactory;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;


/**
 * FXML Controller class
 *
 * @author mohci
 */
public class ValiderResultatController implements Initializable{
    
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
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Validation.fxml"));
        root = loader.load();
                
        ValidationController ValidationController = loader.getController();
        ValidationController.displayName(UserName);
        
        stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    
    
    @FXML
    VBox validationVbox;
    
    @FXML
    public void search(String searchInput) throws IOException, ClassNotFoundException{
        Socket socket = new Socket("localhost", 6666);
        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());
        
        String ToDo = "searchValidation";

        out.writeObject(ToDo);
        out.flush();
        out.writeObject(searchInput);
        out.flush();
    
        List<Resultat> resultList = (List<Resultat>) in.readObject();
        
        for (int i = validationVbox.getChildren().size() - 1; i > 0; i--) {
            validationVbox.getChildren().remove(i); 
        }
        
        for (Resultat res : resultList){
            HBox echantillonBox = new HBox();
            
            Bilan bilan = getBilan(res.getId_echantillon().get());
            
            String idB = Integer.toString(bilan.getId().get());

            Label label0 = new Label(idB);
            label0.setPrefHeight(49.0);
            label0.setPrefWidth(100.0);
            label0.setFont(new Font("Arial Bold", 14.0));
            label0.setPadding(new Insets(0, 0, 0, 15.0));
            echantillonBox.getChildren().add(label0);
            
            
            
            Analyse analyse = getAnalyse(res.getId_analyse().get());
            
            Label label2 = new Label(analyse.getNomCourant());
            label2.setPrefHeight(49.0);
            label2.setPrefWidth(150.0);
            label2.setFont(new Font("Arial Bold", 14.0));
            label2.setPadding(new Insets(0, 0, 0, 10.0));
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
                try {
                    desplayResultat(res, analyse, bilan);
                } catch (IOException ex) {
                    Logger.getLogger(ValiderResultatController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(ValiderResultatController.class.getName()).log(Level.SEVERE, null, ex);
                }
                    
                
            });
            
            imagePane2.getChildren().add(imageView);
            
            echantillonBox.getChildren().add(imagePane2);
            
            validationVbox.getChildren().add(echantillonBox);
            
        }
    } 
    
    @FXML    
    TextField resultatPatient;
    
    @FXML    
    TextField CodeTube;
    
    @FXML    
    TextField medcinRefirant;
    
    @FXML    
    TextField dateDemande;
    
    @FXML    
    TextArea RemarqueR;
    
    Bilan b;
    Analyse a;
    Resultat r;
    
    public void desplayResultat(Resultat resultat, Analyse analyse, Bilan bilan) throws IOException, ClassNotFoundException{
        
        b = bilan;
        a = analyse;
        r = resultat;
        
        CodeTube.setText(resultat.getId_echantillon().get());
        Patient patient = getPatient(bilan.getId_patient().get());
        
        String birthDateString = patient.getDateNaissance();  // Example birth date
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate birthDate = LocalDate.parse(birthDateString, formatter);
        LocalDate currentDate = LocalDate.now();  // Today's date
        Period age = Period.between(birthDate, currentDate);
        
        String patientInfo = patient.getNom() + " " + patient.getPrenom() + " - " + age.getYears() + " ans - " + patient.getGenre();
        resultatPatient.setText(patientInfo);
        
        dateDemande.setText(bilan.getDate().get());
        
        medcinRefirant.setText(bilan.getMedcin().get());
        
        RemarqueR.setText(bilan.getRemarque().get());
        
        String dossier = Integer.toString(bilan.getId().get());
        String datePrelevement = bilan.getDate().get();
        String nom = patient.getNom();
        String prenom = patient.getPrenom();
        String dateNaissance = patient.getDateNaissance();
        String nomAnalyse = analyse.getNomAnalyse();
        String nomCAnalyse = analyse.getNomCourant();
        
        List<String> testDetailsList = analyse.getDesc();
        
        // Close the PDF document if it's open
            if (document != null) {
                document.close();
                document = null;
            }
        
        try (PDDocument document = new PDDocument()) {
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
                for (int i = 0; i < analyse.getDesc().size(); i++) {
                    String desc = analyse.getDesc().get(i);
                    String unite = analyse.getUnite().get(i);
                    String valeurUs = analyse.getValeurUs().get(i);
                    String result = resultat.getResult().get(i);
                   

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
            
            String fileUrl = resultat.getId_echantillon().get() + ".pdf";
            document.save(fileUrl);
        }
        
            String fileUrl = resultat.getId_echantillon().get() + ".pdf";
                File pdfFile = new File(fileUrl);
                
//        if (pdfFile.exists()) {
//            if (Desktop.isDesktopSupported()) {
//                Desktop.getDesktop().open(pdfFile);
//            } else {
//                System.out.println("Desktop is not supported, cannot open PDF.");
//            }
//        } else {
//            System.out.println("PDF file not found.");
////        }
        
        String pdfFilePath = resultat.getId_echantillon().get() + ".pdf";
        
        try {
            document = PDDocument.load(new File(pdfFilePath));
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        
        // Create a Canvas to display the PDF
        Canvas canvas = new Canvas(770, 1300);  // Adjust the size as needed
        GraphicsContext gc = canvas.getGraphicsContext2D();
        
        // Render the first page to the Canvas
        PDFRenderer pdfRenderer = new PDFRenderer(document);
        BufferedImage bufferedImage;
        try {
            bufferedImage = pdfRenderer.renderImageWithDPI(0, 100, ImageType.RGB);  // Render the first page at 300 DPI
            Image image = SwingFXUtils.toFXImage(bufferedImage, null);
            gc.drawImage(image, 0, 0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Create a ScrollPane and add the PdfDecoderFX to it
       
        scrollPane.setContent(canvas);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
 
        
    }
    
    PDDocument document = null;
    
    @FXML
    ScrollPane scrollPane;
    @FXML
    WebView WebView1;
    
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
    
    @FXML
    public Patient getPatient(int id) throws IOException, ClassNotFoundException{
        Socket socket = new Socket("localhost", 6666);
        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());
        
        
        String ToDo = "getPatient";

        out.writeObject(ToDo);
        out.flush();
        out.writeObject(id);
        out.flush();
        
        Patient patient = (Patient) in.readObject();
        return patient;
    }
    
    @FXML
    public Bilan getBilan(String id) throws IOException, ClassNotFoundException{
        Socket socket = new Socket("localhost", 6666);
        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());
        
        
        String ToDo = "getBilan";

        out.writeObject(ToDo);
        out.flush();
        out.writeObject(id);
        out.flush();
        
        Bilan bilan = (Bilan) in.readObject();
        return bilan;
    }
    
    @FXML
    public void valider(MouseEvent event) throws IOException, ClassNotFoundException{
        Socket socket = new Socket("localhost", 6666);
        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());
        
        String ToDo = "validerMedResultat";

        out.writeObject(ToDo);
        out.flush();
        out.writeObject(r);
        out.flush();
        
        System.out.println("222");
        
        resultatPatient.setText("");
        CodeTube.setText("");
        medcinRefirant.setText("");
        dateDemande.setText("");
        RemarqueR.setText("");
        System.out.println("333");
            // Close the PDF document if it's open
            if (document != null) {
                document.close();
                document = null;
            }
            
        String fileUrl = r.getId_echantillon() + ".pdf";
        File file = new File(fileUrl);
         // Delete the PDF file
            if (file != null && file.exists()) {
                boolean deleted = file.delete();
                if (deleted) {
                    System.out.println("PDF file deleted successfully.");
                } else {
                    System.out.println("Failed to delete the PDF file.");
                }
            }

        
        scrollPane.setContent(null);
        search("");
        
    }
    
    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
       
        try {
            search("");
        } catch (IOException ex) {
            Logger.getLogger(ValiderResultatController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ValiderResultatController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
