/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Server;

import static Server.Caise.SearchCaisseF;
import static Server.Login.HandleLogin;
import static Server.PaiementEmployé.SearchEmployé;
import static Server.PaiementEmployé.updatePaiementEmployé;
import static Server.PaiementFournisseur.SearchFournisseur;
import static Server.PaiementFournisseur.ajouterPaiementF;
import static Server.PaiementFournisseur.updatePaiementFournisseur;
import static Server.Personnel.SearchPersonnel;
import static Server.Personnel.updatePersonnel;
import static Server.Plan.getAnalyse;
import static Server.Plan.searchEchantillon;
import static Server.Plan.validerResultat;
import static Server.Produit.AjouterProduit;
import static Server.Produit.SearchStock;
import static Server.Produit.updateStock;
import static Server.Validation.getBilan;
import static Server.Validation.getPatient;
import static Server.Validation.searchValidation;
import static Server.Validation.validerMedResultat;
import static Server.reception.SearchAnalyse;
import static Server.reception.SearchDemande;
import static Server.reception.SearchPatient;
import static Server.reception.ajouterPatient;
import static Server.reception.sauvBilan;
import static Server.reception.updateCaisse;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import object.Analyse;
import object.Bilan;
import object.Caisse;
import object.CaisseF;
import object.Demande;
import object.Echantillon;
import object.Employé;
import object.Fournisseur;
import object.PaiementE;
import object.PaiementF;
import object.Patient;
import object.PersonnelS;
import object.Resultat;
import object.Stock;

/**
 *
 * @author mohci
 */
public class Server {
    private ServerSocket serverSocket;
    
    public void start(int port) {
        try {
            serverSocket = new ServerSocket(port);
            while (true) {
                System.out.println("server start");
                new ClientHandler(serverSocket.accept()).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void stop() {
        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private static class ClientHandler extends Thread {
        private Socket clientSocket;
        private ObjectOutputStream out;
        private ObjectInputStream in;
        

        public ClientHandler(Socket socket) {
            this.clientSocket = socket;
        }

        @Override
        public void run() {
            try {
                
                out = new ObjectOutputStream(clientSocket.getOutputStream());
                in = new ObjectInputStream(clientSocket.getInputStream());
                
                String ToDo = (String) in.readObject();
                System.out.println(ToDo);
                
                if ("Login".equals(ToDo)){
                    String userName = (String) in.readObject();
                    String password = (String) in.readObject();
                    System.out.println(HandleLogin(userName, password));
                    out.writeObject(HandleLogin(userName, password));
                    out.flush();
                }
                
                if ("searchPatient".equals(ToDo)){
                    
                    String searchInput = (String) in.readObject();
                    List<Patient> patientList = SearchPatient(searchInput);
                    
                    out.writeObject(patientList);
                    out.flush();
                }
                if ("searchEmployé".equals(ToDo)){
                    
                    String searchInput = (String) in.readObject();
                    List<Employé> employéList = SearchEmployé(searchInput);
                    
                    out.writeObject(employéList);
                    out.flush();
                }
                if ("searchFournisseur".equals(ToDo)){
                    
                    String searchInput = (String) in.readObject();
                    List<Fournisseur> fournisseurList = SearchFournisseur(searchInput);
                    
                    out.writeObject(fournisseurList);
                    out.flush();
                }
                if ("searchPersonnel".equals(ToDo)){
                    
                    String searchInput = (String) in.readObject();
                    List<PersonnelS> personnelSList = SearchPersonnel(searchInput);
                    
                    out.writeObject(personnelSList);
                    out.flush();
                }
               
                if ("searchStock".equals(ToDo)){
                    
                    String searchInput = (String) in.readObject();
                    List<Stock> stockList = SearchStock(searchInput);
                    
                    out.writeObject(stockList);
                    out.flush();
                }
                if ("addPatient".equals(ToDo)){
                    Patient P = (Patient) in.readObject();
                    Patient NP = ajouterPatient(P);
                                        
                    out.writeObject(NP);
                    out.flush();
                }
                
                if ("ajouterProduit".equals(ToDo)){
                    Stock P = (Stock) in.readObject();
                    AjouterProduit(P);
                                        
                }
                if ("modifierPersonnel".equals(ToDo)){
                     try {
       
                         PersonnelS P = (PersonnelS) in.readObject();  // Then read the PersonnelS object
                         updatePersonnel(P);
                    } catch (IOException | ClassNotFoundException | SQLException e) {
                   e.printStackTrace();
                     }                     
                }
                if ("modifierProduit".equals(ToDo)){
                     try {
       
                         Stock P = (Stock) in.readObject();  // Then read the PersonnelS object
                         updateStock(P);
                    } catch (IOException | ClassNotFoundException | SQLException e) {
                   e.printStackTrace();
                     }                     
                }
                if ("modifierPaiementEmployé".equals(ToDo)){
                     try {
       
                         Employé P = (Employé) in.readObject();  // Then read the PersonnelS object
                         updatePaiementEmployé(P);
                    } catch (IOException | ClassNotFoundException | SQLException e) {
                   e.printStackTrace();
                     }                     
                }
                if ("modifierPaiementFournisseur".equals(ToDo)){
                     try {
       
                         Fournisseur P = (Fournisseur) in.readObject();  // Then read the PersonnelS object
                         updatePaiementFournisseur(P);
                    } catch (IOException | ClassNotFoundException | SQLException e) {
                   e.printStackTrace();
                     }                     
                }
                if ("addPaiementF".equals(ToDo)){
                    PaiementF P = (PaiementF) in.readObject();
                    ajouterPaiementF(P);
                                        
                }
                
                if ("searchAnalyse".equals(ToDo)){
                    
                    String searchInput = (String) in.readObject();
                    List<Analyse> analyseList = SearchAnalyse(searchInput);
                    
                    out.writeObject(analyseList);
                    out.flush();
                }
                
                if ("addBilan".equals(ToDo)){
                    List<Echantillon> echantillon = (List<Echantillon>) in.readObject();
                    Caisse caisse = (Caisse) in.readObject();
                    Bilan bilan = (Bilan) in.readObject();
                    sauvBilan(echantillon, caisse, bilan);
                }
                
                if ("searchBilan".equals(ToDo)){
                    
                    String searchInput = (String) in.readObject();
                    List<Demande> demandeList = SearchDemande(searchInput);
                    
                    out.writeObject(demandeList);
                    out.flush();
                }
                
                if ("updateBilan".equals(ToDo)){
                    
                    Caisse caisse = (Caisse) in.readObject();
                    
                    updateCaisse(caisse);
                }
                
                if ("searchEchantillon".equals(ToDo)){
                    String searchInput = (String) in.readObject();
                    List<Resultat> resultList = searchEchantillon(searchInput);
                    
                    out.writeObject(resultList);
                    out.flush();
                    
                }
                
                if ("getAnalyse".equals(ToDo)){
                    int id = (int) in.readObject();
                    Analyse analyse = getAnalyse(id);
                    
                    out.writeObject(analyse);
                    out.flush();
                    
                }
                
                if ("validerResultat".equals(ToDo)){
                    Resultat resultat = (Resultat) in.readObject();
                    validerResultat(resultat);
                    
                }
                
                if ("searchCaisseF".equals(ToDo)){
                    String caisseF = (String) in.readObject();
                    List<CaisseF> caisseFList = SearchCaisseF(caisseF);
                    out.writeObject(caisseFList);
                    out.flush();
                }
                
                
                if ("searchValidation".equals(ToDo)){
                    String searchInput = (String) in.readObject();
                    List<Resultat> resultList = searchValidation(searchInput);
                    
                    out.writeObject(resultList);
                    out.flush();
                    
                }
                
                if ("getBilan".equals(ToDo)){
                    String id = (String) in.readObject();
                    Bilan bilan = getBilan(id);
                    
                    out.writeObject(bilan);
                    out.flush();
                    
                }
                
                if ("getPatient".equals(ToDo)){
                    int id = (int) in.readObject();
                    Patient patient = getPatient(id);
                    
                    out.writeObject(patient);
                    out.flush();
                    
                }
                
                if ("validerMedResultat".equals(ToDo)){
                    Resultat resultat = (Resultat) in.readObject();
                    validerMedResultat(resultat);
                    
                }

                
                
                in.close();
                out.close();
                clientSocket.close();
                System.out.println("Close");
            } catch (IOException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            } 
        }
    }
    
    public static void main(String[] args) {
        Server server = new Server();
        server.start(6666); // Port 6666 is used as an example
    }
}
