/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interficie;

import java.util.ArrayList;
import java.util.List;
import model.Soci;

/**
 *
 * @author Usuari
 */
public interface IBillar {
    
    /**
     * Tanca la connexió amb el SGBD
     */
    void close () throws BillarException;
    
    /**
     * Tanca la transacció activa fent commit o rollback segons paràmetre
     * @param typeClose r o R per rollback / c o C per commit
     */
    void closeTransaction (char typeClose) throws BillarException;
    
    public ArrayList<Soci> getSocis();
    
    public void addSoci(Soci s);
    
}
