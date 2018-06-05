/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidor_android;

import clases.EstadisticaModalitat;
import clases.Modalitat;
import clases.Soci;
import clases.Torneig;
import com.mysql.jdbc.Connection;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 *
 * @author Julian
 */
public class MetodosMySQL {

    private Connection con;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

    public MetodosMySQL(String nomFitxerPropietats) {
        Properties p = new Properties();
        try {
            p.load(new FileInputStream(nomFitxerPropietats));
        } catch (IOException ex) {
            throw new RuntimeException("Error en llegir de fitxer de propietats", ex);
        }
        String url = p.getProperty("url");
        if (url == null || url.length() == 0) {
            throw new RuntimeException("Fitxer de propietats " + nomFitxerPropietats + " no inclou propietat \"url\"");
        }
        String user = p.getProperty("user");
        String password = p.getProperty("password");
        String driver = p.getProperty("driver");
        if (driver != null && driver.length() > 0) {
            try {
                Class.forName(driver).newInstance();
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
                throw new RuntimeException("Problemes en carregar el driver ", ex);
            }
        }
        try {
            if (user != null && user.length() > 0) {
                con = (Connection) DriverManager.getConnection(url, user, password);
            } else {
                con = (Connection) DriverManager.getConnection(url);
            }
        } catch (SQLException ex) {
            throw new RuntimeException("Problemes en establir la connexió ", ex);
        }
    }

    public MetodosMySQL() {
        this("mysql.txt");
    }

    public Soci login(String nif, String password) {

        Soci s = null;
        String consulta = "select * from soci where nif = ? and password_hash = ?";

        ResultSet rs = null;
        try {
            PreparedStatement preparedStatment = con.prepareStatement(consulta);
            preparedStatment.setString(1, nif);
            preparedStatment.setString(2, password);
            rs = preparedStatment.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String niff = rs.getString("nif");
                String nom = rs.getString("nom");
                String cognom1 = rs.getString("cognom1");
                String cognom2 = rs.getString("cognom2");
                Date dataAlta = rs.getDate("data_alta");
                String passwordHash = rs.getString("password_hash");
                int actiu = rs.getInt("actiu");
                s = new Soci(id, niff, nom, cognom1, cognom2, dataAlta, passwordHash, actiu);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return s;

    }

    private List<EstadisticaModalitat> selectEstadisticaModalitatDeSoci(int id) {
        List<EstadisticaModalitat> em = new ArrayList<EstadisticaModalitat>();

        String consulta = "select * from estadistica_modalitat where soci_id = ?";
        ResultSet rs = null;
        try {
            PreparedStatement preparedStatment = con.prepareStatement(consulta);
            preparedStatment.setInt(1, id);
            rs = preparedStatment.executeQuery();

            while (rs.next()) {
                int idMod = rs.getInt("modalitat_id");
                Modalitat m = selectModalitat(idMod);
                float coeficient = rs.getFloat("coeficient_base");
                int carambolas = rs.getInt("total_caramboles_temporada_actual");
                int entradas = rs.getInt("total_entrades_temporada_actual");
                EstadisticaModalitat em2 = new EstadisticaModalitat(m, coeficient, carambolas, entradas);
                em.add(em2);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return em;
    }

    private Modalitat selectModalitat(int idMod) {
        Modalitat m = null;
        
        String consulta = "select * from modalitat where id = ?";   
        ResultSet rs = null;
        
        try {
            PreparedStatement preparedStatment = con.prepareStatement(consulta);
            preparedStatment.setInt(1,idMod);
            rs = preparedStatment.executeQuery();
            
            while (rs.next()) {
                int id = rs.getInt("id");
                String descripcio = rs.getString("description");
                m = new Modalitat(id,descripcio);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        
        return m;
    }

    public List<Torneig> selectTornejosOberts() {
        
        List<Torneig> llt = null;
        
        String consulta = "select t.*, m.description\n" +
        "from torneig t\n" +
        "left join modalitat m on t.modalitat_id = m.id\n" +
        "where t.preinscripcio_oberta = 1";
        ResultSet rs = null;
        
        try {
            Statement Statment = con.createStatement();
            rs = Statment.executeQuery(consulta);
            llt = new ArrayList<Torneig>();
            
            while (rs.next()) {
                int id = rs.getInt("id");
                String nom = rs.getString("nom");
                Date data_inici = rs.getDate("data_inici");
                Date data_finalitzacio = rs.getDate("data_finalitzacio");
                int preincripcio = rs.getInt("preinscripcio_oberta");
                int grups_creats = rs.getInt("grups_creats");
                int idModalitat = rs.getInt("modalitat_id");
                String descriptionModalitat = rs.getString("description");
                Modalitat m = new Modalitat(idModalitat, descriptionModalitat);
                Torneig t = new Torneig(m, nom, data_inici, data_finalitzacio, (preincripcio==1), true);
                t.setId(id);
                llt.add(t);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        
        return llt;
    }

    public Boolean ferInscripcio(int sociId, int torneigId) {
        Boolean ok = false;
        
        String consulta = "insert into inscrit(data,soci_id,torneig_id) values(?,?,?)";
        
        ResultSet rs = null;
        
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        
	LocalDateTime now = LocalDateTime.now();
        Date dt = new Date(now.getYear() - 1900 ,now.getMonthValue() -1,now.getDayOfMonth());
        try {
            
            PreparedStatement preparedStatment = con.prepareStatement(consulta);
            preparedStatment.setDate(1,dt);
            preparedStatment.setInt(2,sociId);
            preparedStatment.setInt(3,torneigId);
            ok = (preparedStatment.executeUpdate()>0);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
        
        return ok;
    }
}
