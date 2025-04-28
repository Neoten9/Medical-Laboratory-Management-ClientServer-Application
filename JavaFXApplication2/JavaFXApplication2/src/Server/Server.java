/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Server;

import static Server.Login.HandleLogin;
import static Server.reception.SearchPatient;
import static Server.reception.ajouterPatient;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import object.Patient;

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
                
                if ("addPatient".equals(ToDo)){
                    Patient P = (Patient) in.readObject();
                    Patient NP = ajouterPatient(P);
                                        
                    out.writeObject(NP);
                    out.flush();
                }
                
                in.close();
                out.close();
                clientSocket.close();
                System.out.println("Close");
            } catch (IOException e) {
                e.printStackTrace();
            } catch (SQLException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static void main(String[] args) {
        Server server = new Server();
        server.start(6666); // Port 6666 is used as an example
    }

}
