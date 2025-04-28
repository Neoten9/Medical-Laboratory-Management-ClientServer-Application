/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package javafxapplication2;

import static Server.reception.SearchAnalyse;
import java.awt.Desktop;
import java.awt.image.BufferedImage;
import java.awt.print.PrinterException;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URL;
import java.nio.file.Files;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import object.Patient;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javax.print.PrintException;
import object.Analyse;
import object.Bilan;
import object.Caisse;
import object.Demande;
import object.Echantillon;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.krysalis.barcode4j.impl.code39.Code39Bean;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;
import popup.AjouterUnDemandeController;

/**
 * FXML Controller class
 *
 * @author mohci
 */
public class SelectionDesAnalysesController implements Initializable{
    
    private ObjectOutputStream out;
    private ObjectInputStream in;

    private Stage stage;
    private Scene scene;
    private Parent root;
    
    @FXML
    TextField PatientInfo;
    
    @FXML
    TextField AnalyseSearchBar;
    
    @FXML
    VBox VboxLesAnalyses;
    @FXML
    VBox AnalyseDemande;
    @FXML
    VBox echantillonVbox;
    
    @FXML
    Label UserNameLabel;
    Patient p;
    String UserName;
    
    @FXML
    public void displayName(String userName){
        UserNameLabel.setText(userName);
        this.UserName = userName;
    }
    
    @FXML
    public void ParsePatient(Patient patient){
        p = patient;
        String birthDateString = patient.getDateNaissance();  // Example birth date
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate birthDate = LocalDate.parse(birthDateString, formatter);
        LocalDate currentDate = LocalDate.now();  // Today's date
        Period age = Period.between(birthDate, currentDate);
        
        String patientInfo = patient.getNom() + " " + patient.getPrenom() + " - " + age.getYears() + " ans - " + patient.getGenre();
        PatientInfo.setText(patientInfo);
        
        System.out.println(patient.getNom());
    }
    
    @FXML
    public void GoToListDeDemande(MouseEvent event) throws IOException {
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ListDeDemande.fxml"));
        root = loader.load();
        
        ListDeDemandeController listDeDemandeController = loader.getController();
        listDeDemandeController.displayName(UserName);
                
        stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
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
    
    String searchInput;
    
    List<Analyse> analyseDemande = new ArrayList<>();
    List<Echantillon> echantillon = new ArrayList<>();
    
    @FXML
    public void searchAnalyse(KeyEvent event) throws Exception{
        searchInput = AnalyseSearchBar.getText();
        
        search(searchInput);
    }
    
    
    @FXML
    public void search(String searchInput) throws Exception{
        Socket socket = new Socket("localhost", 6666);
        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());
        
       
        String ToDo = "searchAnalyse";

        out.writeObject(ToDo);
        out.flush();
        out.writeObject(searchInput);
        out.flush();
        
        List<Analyse> analyseList = (List<Analyse>) in.readObject();
        
        for (int i = VboxLesAnalyses.getChildren().size() - 1; i > 0; i--) {
             VboxLesAnalyses.getChildren().remove(i); 
        }
        
        for (Analyse analyse : analyseList) {
            HBox analyseBox = new HBox();
            
            Label nomAnalyse = new Label(analyse.getNomAnalyse());
            nomAnalyse.getStyleClass().add("nom-analyse-label");
            nomAnalyse.setFont(new Font("Arial Bold", 14.0));
            analyseBox.getChildren().add(nomAnalyse);

            Label nomCourant = new Label(analyse.getNomCourant());
            nomCourant.getStyleClass().add("nom-Courant-label");
            nomCourant.setFont(new Font("Arial Bold", 14.0));
            analyseBox.getChildren().add(nomCourant);

            // Create an ImageView and set an image
            ImageView imageView = new ImageView(new Image(getClass().getResourceAsStream("../img/Vectoradd.png")));
            imageView.getStyleClass().add(".search-img");
            imageView.setCursor(Cursor.HAND);
            
            imageView.setOnMouseClicked((MouseEvent e) -> {
                try {
                    String exist = "false";
                    for (Analyse an : analyseDemande){
                        if(analyse.getId() == an.getId()){
                            exist = "true";
                        }
                    }
                    if("false".equals(exist)){
                        analyseDemande.add(analyse);
                    }
                    CaisseDesplay();
                    EchantillonDesplay();
                    AnalyseDemande();
                } catch (IOException ex) {
                    Logger.getLogger(AjouterUnDemandeController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });

            
            analyseBox.getChildren().add(imageView);
            
            VboxLesAnalyses.getChildren().add(analyseBox);
        }        
    }

    public void AnalyseDemande() throws IOException{
        for (int i = AnalyseDemande.getChildren().size() - 1; i > 0; i--) {
             AnalyseDemande.getChildren().remove(i); 
        }
        
        for (Analyse analyse : analyseDemande) {
            HBox analyseBox = new HBox();
            
            Label nomAnalyse = new Label(analyse.getNomAnalyse());
            nomAnalyse.getStyleClass().add("nom-analyse-label-demande");
            nomAnalyse.setFont(new Font("Arial Bold", 14.0));
            analyseBox.getChildren().add(nomAnalyse);

            Label nomCourant = new Label(analyse.getNomCourant());
            nomCourant.getStyleClass().add("nom-Courant-label-demande");
            nomCourant.setFont(new Font("Arial Bold", 14.0));
            analyseBox.getChildren().add(nomCourant);
            
            Label Prix = new Label(analyse.getPrix());
            Prix.getStyleClass().add("nom-Courant-label-demande");
            Prix.setFont(new Font("Arial Bold", 14.0));
            analyseBox.getChildren().add(Prix);

            // Create an ImageView and set an image
            ImageView imageView = new ImageView(new Image(getClass().getResourceAsStream("../img/Vectordelete.png")));
            imageView.getStyleClass().add(".search-img");
            imageView.setCursor(Cursor.HAND);
            
            imageView.setOnMouseClicked((MouseEvent e) -> {
                try {
                    SimpleIntegerProperty idToDelete = analyse.getId();
                    for (int i = 0; i < analyseDemande.size(); i++) {
                        if (analyseDemande.get(i).getId() == idToDelete) {
                            analyseDemande.remove(i);
                            break; // Exit loop once the object is removed
                        }
                    }
                    CaisseDesplay();
                    EchantillonDesplay();
                    AnalyseDemande();
                } catch (IOException ex) {
                    Logger.getLogger(SelectionDesAnalysesController.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            });

            
            analyseBox.getChildren().add(imageView);
            
            AnalyseDemande.getChildren().add(analyseBox);
        }        
        
    }
    
    public void EchantillonDesplay(){
        echantillon = new ArrayList<>();
        
        List<Integer> Edta = new ArrayList<>();
        List<Integer> Héparinate = new ArrayList<>();
        List<Integer> Sec = new ArrayList<>();
        for (Analyse analyse : analyseDemande){
            if("Edta".equals(analyse.getEchantillon())){
                Edta.add(analyse.getId().get());
            }
            if("Héparinate".equals(analyse.getEchantillon())){
                Héparinate.add(analyse.getId().get());
            }
            if("Sec".equals(analyse.getEchantillon())){
                Sec.add(analyse.getId().get());
            }
        }
        System.out.println("111");
        System.out.println(Edta);
        
        String idP = Integer.toString( p.getId());
        
        LocalDate currentDate = LocalDate.now();
        String formattedDate = currentDate.toString().replace("-", "");
        String uniqueCode;
        
        if(!Edta.isEmpty()){
            uniqueCode = "001" + formattedDate + idP;
            echantillon.add(new Echantillon(uniqueCode, "Edta", Edta));
            System.out.println(uniqueCode);
        }
        if(!Héparinate.isEmpty()){
            uniqueCode = "002" + formattedDate + idP;
            echantillon.add(new Echantillon(uniqueCode, "Héparinate", Héparinate));
        }
        if(!Sec.isEmpty()){
            uniqueCode = "003" + formattedDate + idP;
            echantillon.add(new Echantillon(uniqueCode, "Sec", Sec));
        }
        
        for (int i = echantillonVbox.getChildren().size() - 1; i > 0; i--) {
             echantillonVbox.getChildren().remove(i); 
        }
        
        for (Echantillon echan : echantillon){
            final String test;
            HBox echantillonBox = new HBox();
            
            Label typeEchantillon = new Label(echan.getType());
            typeEchantillon.getStyleClass().add("type-echantillon-label");
            typeEchantillon.setFont(new Font("Arial Bold", 14.0));
            
            if ("Edta".equals(echan.getType())){
                typeEchantillon.getStyleClass().add("typeEdta");
                typeEchantillon.setTextFill(Color.web("#8956F8"));
            }else if("Héparinate".equals(echan.getType())){
                typeEchantillon.getStyleClass().add("typeHéparinate");
                typeEchantillon.setTextFill(Color.web("#26D66C"));
            }else {
                typeEchantillon.getStyleClass().add("typeSec");
                typeEchantillon.setTextFill(Color.web("#FF4820"));
            }
            echantillonBox.getChildren().add(typeEchantillon);
            
            Label echantillonSource = new Label("Sang");
            echantillonSource.getStyleClass().add("source-echantillon-label");
            echantillonSource.setFont(new Font("Arial Bold", 14.0));
            echantillonBox.getChildren().add(echantillonSource);
            
            String tests = "";
            for (Integer idtest : echan.getIdTests()){
                for (Analyse analyse : analyseDemande){
                    if(Objects.equals(idtest, analyse.getId().get())){
                        tests = tests + analyse.getNomCourant() + ", ";
                    }
                } 
            }
            System.out.println(tests);
            test = tests;
            Label lesAnalyses = new Label(tests);
            lesAnalyses.getStyleClass().add("source-echantillon-label");
            lesAnalyses.setFont(new Font("Arial Bold", 14.0));
            echantillonBox.getChildren().add(lesAnalyses);
            
            ImageView imageView = new ImageView(new Image(getClass().getResourceAsStream("../img/Vectorimp2.png")));
            imageView.getStyleClass().add(".search-img");
            imageView.setCursor(Cursor.HAND);
            imageView.setOnMouseClicked((MouseEvent ev) -> {
                    String filePath = "echantillon.pdf";
                    
                try {
                    createPdfEchan(echan,filePath,test);
                } catch (IOException ex) {
                    Logger.getLogger(SelectionDesAnalysesController.class.getName()).log(Level.SEVERE, null, ex);
                }
                });
            
            
            
            echantillonBox.getChildren().add(imageView);
            
            echantillonVbox.getChildren().add(echantillonBox);
        
        }
    }
    
    public void createPdfEchan(Echantillon echan, String filePath, String tests) throws IOException {
        try (PDDocument document = new PDDocument()) {
            
            
                
            
            
            String codeEchan = echan.getCode().get();
            String typeEchan = echan.getType();
            
            LocalDate currentDate = LocalDate.now();
            String formattedDate = currentDate.toString();
            
            
            
            PDPage page = new PDPage(new PDRectangle(300, 150));
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                // Header
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 14);
                contentStream.newLineAtOffset(50, 100);
                contentStream.showText(typeEchan);
                contentStream.newLineAtOffset(80, 0);
                contentStream.setFont(PDType1Font.HELVETICA, 12);
                contentStream.showText(formattedDate);
                contentStream.newLineAtOffset(-80, -15);
                contentStream.showText(tests);
                contentStream.endText();
                
                BufferedImage barcodeImage = generateBarcodeImage(codeEchan);
                File tempFile = File.createTempFile("barcode", ".png");
                try (OutputStream os = Files.newOutputStream(tempFile.toPath())) {
                    javax.imageio.ImageIO.write(barcodeImage, "png", os);
                }
                PDImageXObject barcode = PDImageXObject.createFromFile(tempFile.getAbsolutePath(), document);
                contentStream.drawImage(barcode, 50, 30, 200, 50);
                tempFile.delete();
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
    
    private static BufferedImage generateBarcodeImage(String code) {
        Code39Bean barcode39Bean = new Code39Bean();
        final int dpi = 150;

        barcode39Bean.setModuleWidth(0.2);
        barcode39Bean.setWideFactor(3);
        barcode39Bean.doQuietZone(false);

        BitmapCanvasProvider canvas = new BitmapCanvasProvider(
                dpi, BufferedImage.TYPE_BYTE_BINARY, false, 0);

        barcode39Bean.generateBarcode(canvas, code);
        try {
            canvas.finish();
        } catch (IOException ex) {
            Logger.getLogger(SelectionDesAnalysesController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return canvas.getBufferedImage();
    }
    @FXML
    public void impTicket(MouseEvent ev) throws IOException{
        String filePath = "ticket.pdf";
        String mont = CaisseMontant.getText();
        String pay = CaissePaye.getText();
        String rest = CaisseReste.getText();
        String pInfo = PatientInfo.getText();
        String tests = "";   
        for (Analyse analyse : analyseDemande){
            
                tests = tests + analyse.getNomCourant() + ", ";
            
        } 
        LocalDate currentDate = LocalDate.now();
        String formattedDate = currentDate.toString();
        try (PDDocument document = new PDDocument()) {   
            
            PDPage page = new PDPage(new PDRectangle(300, 180));
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                // Header
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                contentStream.newLineAtOffset(50, 150);
                contentStream.showText(pInfo);
                contentStream.newLineAtOffset(0, -25);
                contentStream.setFont(PDType1Font.HELVETICA, 12);
                contentStream.showText("Demandé le: " +formattedDate);
                contentStream.newLineAtOffset(0, -15);
                contentStream.showText("tests: " +tests);
                contentStream.newLineAtOffset(0, -25);
                contentStream.showText("Montant: " + mont);
                contentStream.newLineAtOffset(0, -15);
                contentStream.showText("Payé: " +pay+ ".00 DA");
                contentStream.newLineAtOffset(0, -15);
                contentStream.showText("Reste: " + rest);
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
    
    @FXML
    TextField CaisseMontant;
    @FXML
    TextField CaissePaye;
    @FXML
    TextField CaisseReste;
    
    int paye = 0;
    int montant = 0;
    int old_paye = 0;
    
    public void CaisseDesplay(){
        
        
        montant = 0;
        for (Analyse analyse : analyseDemande){
            int prix = Integer.parseInt(analyse.getPrix());
            montant = montant + prix;
        }
        
        String CaisseM = Integer.toString(montant) + ".00 DA";
        CaisseMontant.setText(CaisseM);
        
        //String CaisseP = Integer.toString(paye + old_paye);
        //CaissePaye.setText(CaisseP);
        try{
            int paid=Integer.parseInt(CaissePaye.getText());
            int reste = montant - paid;
            String CaisseR = Integer.toString(reste) + ".00 DA";
            CaisseReste.setText(CaisseR);
        }catch(NumberFormatException e)
        {
            CaisseReste.setText(CaisseM);
        }
        
        
        
    }
    
    @FXML
    void setPaye(KeyEvent event) {
        paye = Integer.parseInt(CaissePaye.getText());
        CaisseDesplay();
    }
    
    @FXML
    TextField medcinRefirant;
    @FXML
    DatePicker DateD;
    @FXML
    TextArea Remarque;
    
    String todo = "";
    Caisse cai;
    
    @FXML
    public void SauvgarderBilan(MouseEvent event) throws IOException{
        Socket socket = new Socket("localhost", 6666);
        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());
        
        if ("updateBilan".equals(todo)){
            String ToDo = todo;
            
            out.writeObject(ToDo);
            out.flush();
            
            LocalDate currentDate = LocalDate.now();
            String formattedDate = currentDate.toString();
            
            Caisse cais = new Caisse(cai.getId().get(), cai.getMontant().get(), paye+old_paye, cai.getTests(), cai.getPatient().get(), formattedDate);
            
            out.writeObject(cais);
            out.flush();
        }else{
            String ToDo = "addBilan";
        
        

        out.writeObject(ToDo);
        out.flush();
        
        out.writeObject(echantillon);
        out.flush();
        
        String tests = "";
        for (Analyse analyse : analyseDemande){
            tests = tests + analyse.getNomCourant() + ",";
        }
        
        String patient = p.getNom() + " " + p.getPrenom();
        
        LocalDate currentDate = LocalDate.now();
        String formattedDate = currentDate.toString();
        
        Caisse c = new Caisse(0, montant, paye, tests, patient, formattedDate);
        
        out.writeObject(c);
        out.flush();
        
        List<String> codeEchantillon = new ArrayList<>();
        for (Echantillon echan : echantillon){
            codeEchantillon.add(echan.getCode().get());
        }
        
        List<Integer> idTests = new ArrayList<>();
        for (Analyse analyse : analyseDemande){
            idTests.add(analyse.getId().get());
        }
        
        String medcinR = medcinRefirant.getText();
        String dateString = DateD.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String remarque = Remarque.getText();
        
        Bilan b = new Bilan(0, p.getId(), 0, codeEchantillon, idTests, medcinR, remarque, dateString);
        
        out.writeObject(b);
        out.flush();
        }
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ListDeDemande.fxml"));
        root = loader.load();
                
        ListDeDemandeController listDeDemandeController = loader.getController();
        listDeDemandeController.displayName(UserName);
        
        stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        
        
    }
    
    @FXML
    public void displayBilan(Demande demande) throws IOException{
        todo = "updateBilan";
        analyseDemande = demande.getAnalyse();
        cai = demande.getCaisse(); 
        
        medcinRefirant.setText(demande.getBilan().getMedcin().get());
        Remarque.setText(demande.getBilan().getRemarque().get());
        
        montant = demande.getCaisse().getMontant().get();
//        String CaisseM = Integer.toString(montant) + ".00 DA";
//        CaisseMontant.setText(CaisseM);
        
        old_paye = demande.getCaisse().getPaye().get();
        
        ParsePatient(demande.getPatient());
        
        AnalyseDemande();
        CaisseDesplay();
        EchantillonDesplay();
        
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try { 
            search("");
        } catch (Exception ex) {
            Logger.getLogger(SelectionDesAnalysesController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
