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

    
    protected BillarPersistence() {
    }
    
    static public IBillar getInstance(String nomClasseComponent, String parametrePelConstructorDelComponent) throws BillarException {
        IBillar obj = null;
        try {
            Class c = Class.forName(nomClasseComponent);
            if (parametrePelConstructorDelComponent==null ||
                    parametrePelConstructorDelComponent.length()==0) {
                obj = (IBillar) c.newInstance();
            } else {
                Constructor co = c.getConstructor(String.class);
                obj = (IBillar) co.newInstance(parametrePelConstructorDelComponent);
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | NoSuchMethodException | SecurityException | IllegalArgumentException | InvocationTargetException ex) {
            throw new BillarException("Error " + nomClasseComponent, ex);
        }
        return obj;
    }
}
