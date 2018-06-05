/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidor_android;

import clases.Soci;
import clases.Torneig;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.security.SecureRandom;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Julian
 */
public class ThreadCliente extends Thread {

    private Socket socketCliente;
    private String sessionID;
    
    MetodosMySQL mysql;

    public ThreadCliente(Socket socket, MetodosMySQL mysql) {
        setSocketCliente(socket);
        setMysql(mysql);
    }

    public MetodosMySQL getMysql() {
        return mysql;
    }

    public void setMysql(MetodosMySQL mysql) {
        this.mysql = mysql;
    }
    
    

    @Override
    public void run() {
    
        ObjectInputStream datosEntrada = null;
        ObjectOutputStream datosSalida = null;
        
        try {
            datosSalida = new ObjectOutputStream(socketCliente.getOutputStream());
            datosEntrada = new ObjectInputStream(socketCliente.getInputStream());
            
            int line = datosEntrada.readInt();
            
            System.out.println("Ejecutar acción: " + line);
            sessionID = "";
            switch (line) {
                case 1: {
                    //Recibo un nif
                    String nif = (String) datosEntrada.readObject();
                    String pass = (String) datosEntrada.readObject();
                    
                    Soci soci = mysql.login(nif, pass);
                    if (soci == null) {
                        datosSalida.writeInt(-1);
                        datosSalida.flush();
                        System.out.println("Soci inexistent a la BD");
                    } else {
                        datosSalida.writeInt(1);
                        datosSalida.flush();
                        sessionID = generateSessionID();
                        Main.MapUsuarios.put(sessionID, nif);
                        
                        datosSalida.writeObject(sessionID);
                        datosSalida.flush();
                        datosSalida.writeObject(soci);
                        datosSalida.flush();
                        System.out.println("Soci enviat: " + soci.getNif());
                    }
                    break;
                }
                
                case 4: {
                    //Recibo un sessionId
                    String sessionId = (String) datosEntrada.readObject();
                    String nif = Main.MapUsuarios.get(sessionId);
                    
                    if (nif == null || nif.length() == 0) {
                        datosSalida.writeInt(-1);
                        datosSalida.flush();
                        System.out.println("Soci no logejat");
                    } else {
                        datosSalida.writeInt(1);
                        datosSalida.flush();

                        List<Torneig> tornejos = mysql.selectTornejosOberts();
                        
                        datosSalida.writeObject(tornejos);
                        datosSalida.flush();
                    }
                    break;
                }
                case 5: {
                    //Recibo un sessionId
                    String sessionId = (String) datosEntrada.readObject();
                    int sociId = datosEntrada.readInt();
                    int torneigId = datosEntrada.readInt();
                    
                    String nif = Main.MapUsuarios.get(sessionId);
                    
                    if (nif == null || nif.length() == 0) {
                        datosSalida.writeInt(-1);
                        datosSalida.flush();
                        System.out.println("Soci no logejat");
                    } else {
                        datosSalida.writeInt(1);
                        datosSalida.flush();

                        Boolean ok = mysql.ferInscripcio(sociId, torneigId);
                        
                        datosSalida.writeBoolean(ok);
                        datosSalida.flush();
                    }
                    break;
                }
                    
            }
            try {
                socketCliente.close();
            } catch (IOException ex) {
                Logger.getLogger(ThreadCliente.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        } catch (IOException ex) {
            Logger.getLogger(ThreadCliente.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ThreadCliente.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public Socket getSocketCliente() {
        return socketCliente;
    }

    public void setSocketCliente(Socket socketCliente) {
        this.socketCliente = socketCliente;
    }

    
    
    
    
    private final static char[] hexArray = "0123456789ABCDEF".toCharArray();
    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for ( int j = 0; j < bytes.length; j++ ) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }
    
    private String generateSessionID(){
        SecureRandom r = new SecureRandom();
        byte[] aesKey = new byte[16];
        r.nextBytes(aesKey);
        return bytesToHex(aesKey);
    }
}
