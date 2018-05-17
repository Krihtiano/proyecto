/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proves;

import interficie.BillarException;
import interficie.BillarPersistence;
import interficie.IBillar;
import java.util.ArrayList;
import model.Soci;

/**
 *
 * @author Usuari
 */
public class Proves {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        Gestio g = new Gestio();
        g.setVisible(true);
        
        IBillar obj = null;
        String tcomponent[] = {"persistencia.PersistenciaMySQL"};
        String tparametre[] = {"UP-MySQL"};
        
        try {
            System.out.println("Prova de component " + tcomponent[0]);
            obj = BillarPersistence.getInstance(tcomponent[0], tparametre[0]);
            System.out.println("Component " + tcomponent[0] + " creat");
        } catch (BillarException ex) {
            System.out.println("Error en generar objecte " + tcomponent[0]);
            if (ex.getMessage() != null) {
                System.out.println("Info: " + ex.getMessage());
            }
        }  
        
        obj.close();
    }
}
