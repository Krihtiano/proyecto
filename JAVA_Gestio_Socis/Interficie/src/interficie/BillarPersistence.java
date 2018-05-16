/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interficie;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 *
 * @author Usuari
 */
public class BillarPersistence {

    
    private BillarPersistence() {
    }

    static public IBillar getInstance(String nomClasse) {
        return getInstance(nomClasse,null);
    }
    
    static public IBillar getInstance(String nomClasse, String nomFitxerPropietats) {
        IBillar obj;
        if (nomClasse==null) {
            throw new BillarException("Nom de la classe erroni. No pot ser null");
        }
        try {
            if (nomFitxerPropietats == null) {
                // S'invoca constructor sense paràmetres
                obj = (IBillar) Class.forName(nomClasse).newInstance();
            } else {
                // S'invoca constructor amb 1 paràmetre
                Class c = Class.forName(nomClasse);
                Constructor co = c.getConstructor(String.class);
                obj = (IBillar) co.newInstance(nomFitxerPropietats);
            }
            return obj;
        } catch (InstantiationException | ClassNotFoundException | NoSuchMethodException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            throw new BillarException("No es pot crear l'objecte de la classe " + nomClasse, ex);
        }
    }
}
