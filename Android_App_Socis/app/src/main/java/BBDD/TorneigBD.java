package BBDD;

import com.example.julian.apppersocis.LoginActivity;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;

import clases.Soci;
import clases.Torneig;

public class TorneigBD {
    public static String server_ip = "192.168.0.169";
    public static int server_port = 1237;
    public static String sessionId;

    public static Soci login(String nif, String password){
        Soci soci = null;
        try {
            InetAddress serverAddress = InetAddress.getByName(server_ip);
            Socket socket = new Socket (serverAddress, server_port);

            ObjectInputStream datosEntrada = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream datosSalida = new ObjectOutputStream(socket.getOutputStream());

            //Envio la opcion que el servidor interpretará como Login
            datosSalida.writeInt(1);
            datosSalida.flush();

            //Envio nif
            datosSalida.writeObject(nif);
            datosSalida.flush();

            //Envio password
            datosSalida.writeObject(password);
            datosSalida.flush();

            //Recibo un Soci del servidor
            int respuesta = datosEntrada.readInt();

            if(respuesta == -1){
                return null;
            }else{
                sessionId = (String) datosEntrada.readObject();
                LoginActivity.editor.putString("session_id",sessionId);
                LoginActivity.editor.commit();
                soci = (Soci)datosEntrada.readObject();
            }
            socket.close();

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return soci;
    }


    public static List<Torneig> selectTornejosOberts(String string) {
        List<Torneig> tornejos = null;
        try {
            InetAddress serverAddress = InetAddress.getByName(server_ip);
            Socket socket = new Socket (serverAddress, server_port);

            ObjectInputStream datosEntrada = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream datosSalida = new ObjectOutputStream(socket.getOutputStream());

            //Envio la opcion que el servidor interpretará como Login
            datosSalida.writeInt(4);
            datosSalida.flush();

            //Envio nif
            datosSalida.writeObject(sessionId);
            datosSalida.flush();

            //Recibo un Soci del servidor
            int respuesta = datosEntrada.readInt();

            if(respuesta == -1){
                return null;
            }else{
                tornejos = (List<Torneig>)datosEntrada.readObject();
            }
            socket.close();

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return tornejos;
    }

    public static Boolean ferInscripcio(String sessionId, String sociId, String torneigId) {


        Boolean ok = false;
        try {
            InetAddress serverAddress = InetAddress.getByName(server_ip);
            Socket socket = new Socket (serverAddress, server_port);

            ObjectInputStream datosEntrada = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream datosSalida = new ObjectOutputStream(socket.getOutputStream());

            //Envio la opcion que el servidor interpretará como Login
            datosSalida.writeInt(5);
            datosSalida.flush();

            //Envio sessionId
            datosSalida.writeObject(sessionId);
            datosSalida.flush();

            //Envio sociId
            datosSalida.writeInt(Integer.parseInt(sociId));
            datosSalida.flush();

            //Envio torneigId
            datosSalida.writeInt(Integer.parseInt(torneigId));
            datosSalida.flush();

            //Recibo un Soci del servidor
            int respuesta = datosEntrada.readInt();

            if(respuesta == -1){
                return null;
            }else{
                ok = datosEntrada.readBoolean();
            }
            socket.close();

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ok;
    }
}
