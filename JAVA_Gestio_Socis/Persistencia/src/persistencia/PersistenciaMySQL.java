/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import interficie.BillarException;
import interficie.IBillar;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import model.EstadisticaModalitat;
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
    public void addSoci(Soci s, Modalitat m, Float coeficient, Integer caramboles, Integer entrades) {
        try {
            em.getTransaction().begin();
            EstadisticaModalitat emod = new EstadisticaModalitat(s,m,coeficient,caramboles,entrades);
            s.setPasswordHash(getHashFromPassowrd(s.getPasswordHash()));
            em.persist(s);
            em.persist(emod);
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
            Soci sociFinal = null;
            ArrayList<Soci> socis = this.getSocisValids();
            for(Soci ss : socis){
               if (ss.getId() == id){
                   sociFinal = ss;
               } 
            }
            sociFinal.setActiu(0);
            em.merge(sociFinal);
            em.getTransaction().commit();
        }
        catch (Exception ex) {
            throw new BillarException("Problemes en esborrar un Soci"+  ex.getMessage());
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

    @Override
    public Soci getSoci(int id) {
        Soci soci;
        String consulta = "select s from Soci s where id = " + id;
        Query qs = em.createQuery(consulta);
        soci = (Soci)qs.getSingleResult();
        return soci;   
    }

    @Override
    public int getIdModalitat(String nomModalitat) {
        String consulta = "select m.id from Modalitat m where m.description = '" + nomModalitat + "'";
        Query qs = em.createQuery(consulta);
        int idModalitat = (int)qs.getSingleResult();
        return idModalitat;   
    }

    @Override
    public EstadisticaModalitat getEstadisticaModalitat(int idSoci, int idModalitat) {
        String consulta = "select em from EstadisticaModalitat em where soci = " + idSoci + " and modalitat = " + idModalitat;
        Query qs = em.createQuery(consulta);
        EstadisticaModalitat estMod = (EstadisticaModalitat)qs.getSingleResult();
        return estMod;    
    }

    @Override
    public Modalitat getModalitat(String nomModalitat) {
        String consulta = "select m from Modalitat m where m.description = '" + nomModalitat + "'";
        Query qs = em.createQuery(consulta);
        Modalitat mod = (Modalitat)qs.getSingleResult();
        return mod;   

    }
    
    private String getHashFromPassowrd(String password) throws UnsupportedEncodingException, NoSuchAlgorithmException{
        byte[] bytesOfMessage = password.getBytes("UTF-8");
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        byte[] thedigest = md5.digest(bytesOfMessage);
        
        String encryptedString = thedigest.toString();
        return encryptedString;
    }

    @Override
    public void editarSoci(Soci s) {
        try {
            em.getTransaction().begin();
            em.merge(s);
            em.getTransaction().commit();
        }
        catch (Exception ex) {
            throw new BillarException("Problemes en editar un Soci"+  ex.getMessage());
        }
    }

    @Override
    public void editarEM(EstadisticaModalitat emod) {
        try {
            em.getTransaction().begin();
            em.merge(emod);
            em.getTransaction().commit();
        }
        catch (Exception ex) {
            throw new BillarException("Problemes en editar una estadística"+  ex.getMessage());
        } 
    }
}
