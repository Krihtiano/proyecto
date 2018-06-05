/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidor_android;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Julian
 */
public class Main {

    public static Map<String, String> MapUsuarios = new HashMap<>();
    public static int puertoServidor = 1237;
    
    public static void main(String[] args) {
       
        MetodosMySQL mysql = new MetodosMySQL("mysql.txt");
        
        try{
            InetAddress addr = InetAddress.getByName("192.168.0.169");
            ServerSocket sockServer = new ServerSocket(puertoServidor, 100, addr);
            System.out.println("Servidor iniciado...");
            
            while(true){
                Socket socket = sockServer.accept();
                System.out.println("Cliente aceptado");
                Thread thread = new ThreadCliente(socket, mysql);
                thread.start();   
            }
        }catch(IOException ex){
            System.out.println("Error en la conexión del server");
        }  
    }
}
