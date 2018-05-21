/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interficie;

import java.util.ArrayList;
import java.util.List;
import model.EstadisticaModalitat;
import model.Modalitat;
import model.Soci;

/**
 *
 * @author Usuari
 */
public interface IBillar {
    

    //Tanca la connexió amb el SGBD
    void close () throws BillarException;
   
     //Tanca la transacció activa fent commit o rollback segons paràmetre
     //@param typeClose r o R per rollback / c o C per commit
    void closeTransaction (char typeClose) throws BillarException;
    
    //Retorna tots els socis
    public ArrayList<Soci> getSocis();
    
    //Retorna només els socis actius (que no han sigut esborrats de la bd
    public ArrayList<Soci> getSocisValids();
    
    //Afegir un soci.
    public void addSoci(Soci s, Modalitat m, Float coeficient, Integer caramboles, Integer entrades);
    
    //Retorna el soci passant per paràmetre un id
    public Soci getSoci(int id);
    
    //Posa un soci a actiu = 0; per a que no es mostri mes com a un soci, pero poder guardar el registre a la bd.
    public void removeSoci(int id);
    
    //Retorna la llista de les modalitats
    public ArrayList<Modalitat> getModalitats();
    
    //Retorna el id de la modalitat segons el nom que li passem.
    public int getIdModalitat(String nomModalitat);
    
    //Retorna un objecte EstadisticaModalitat segons idSoci i idModalitat
    public EstadisticaModalitat getEstadisticaModalitat(int idSoci, int idModalitat);
    
    //Retorna una modalitat passant per paràmetre un nom de modalitat
    public Modalitat getModalitat(String nomModalitat);
    
    //Passant un soci, l'edita i el desa a la base de dades.
    public void editarSoci(Soci s);
    
    //Modifica una estadistica de modalitat passant per parametre aquesta.
    public void editarEM(EstadisticaModalitat emod);
   

}
