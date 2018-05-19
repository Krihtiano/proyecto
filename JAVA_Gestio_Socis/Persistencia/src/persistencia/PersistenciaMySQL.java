/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import interficie.BillarException;
import interficie.IBillar;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import model.Modalitat;
import model.Soci;

/**
 *
 * @author Usuari
 */
public class PersistenciaMySQL implements IBillar{
     
    private EntityManager em;
    EntityManagerFactory emf;
    
    public PersistenciaMySQL(String nomUnitatPersistencia) throws BillarException {
        try {
            emf = Persistence.createEntityManagerFactory(nomUnitatPersistencia);
            System.out.println("Capa de persistencia creada");
        } catch (PersistenceException ex) {
            throw new BillarException("Problemes en crear l'EntityManagerFactory", ex);
        }
        try {
            em = emf.createEntityManager();
        } catch (PersistenceException ex) {
            throw new BillarException("Problemes en crear l'EntityManager", ex);
        }
    }

   /* public static void main(String[] args) {
        PersistenciaMySQL pmys = new PersistenciaMySQL("UP-MySQL");
        ArrayList<Soci> socis = pmys.getSocis();
        for (Soci s: socis) {
            System.out.println(s.toString());
        }
        pmys.close();
    }*/
    
    @Override
    public ArrayList<Soci> getSocis() {
        ArrayList<Soci> socis = new ArrayList<Soci>();
        
        String consulta = "select s from Soci s";
        Query qs = em.createQuery(consulta);
        List<Soci> ls = qs.getResultList();
        socis.addAll(ls);
        return socis;
    }
    
    
    @Override
    public ArrayList<Soci> getSocisValids() {
        ArrayList<Soci> socis = new ArrayList<Soci>();
        
        String consulta = "select s from Soci s where actiu = 1";
        Query qs = em.createQuery(consulta);
        List<Soci> ls = qs.getResultList();
        socis.addAll(ls);
        return socis;    
    }

    @Override
    public void close() throws BillarException {
        if (em != null) {
            EntityManagerFactory emf = em.getEntityManagerFactory();
            try {
                em.close();
                em = null;
            } catch (Exception ex) {
                throw new BillarException("Problemes en tancar l'EntityManager", ex);
            }
            try {
                emf.close();
            } catch (Exception ex) {
                throw new BillarException("Problemes en tancar l'EntityManagerFactory", ex);
            }
        }
    }

    @Override
    public void closeTransaction(char typeClose) throws BillarException {
        typeClose = Character.toUpperCase(typeClose);
        if (typeClose != 'C' && typeClose != 'R') {
            throw new BillarException("Paràmetre " + typeClose + " erroni en closeTransaction");
        }
        EntityTransaction et = em.getTransaction();
        if (et.isActive()) {
            if (typeClose == 'C') {
                et.commit();
            } else {
                et.rollback();
            }
        }
    }

    @Override
    public void addSoci(Soci s) {
        try {
            em.getTransaction().begin();
            em.persist(s);
            em.getTransaction().commit();
            System.out.println(s);
           
        } catch (Exception ex) {
            throw new BillarException("Problemes al afegir un Soci "+ ex.getMessage());
        }
    }

    @Override
    public void removeSoci(int id) {
        try {

                em.getTransaction().begin();

            ArrayList<Soci> socis = this.getSocis();
            Soci s = socis.get(id-1);
            s.setActiu(0);
            em.merge(s);
            em.getTransaction().commit();
        }
        catch (Exception ex) {
            throw new BillarException("Problemes al esborrar un Soci", ex);
        }
    }

    @Override
    public ArrayList<Modalitat> getModalitats() {
        ArrayList<Modalitat> modalitats = new ArrayList<Modalitat>();
        
        String consulta = "select m from Modalitat m";
        Query qs = em.createQuery(consulta);
        List<Modalitat> lm = qs.getResultList();
        modalitats.addAll(lm);
        return modalitats;
    }

}
