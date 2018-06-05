/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Julian
 */
public class Torneig implements Serializable
{
    private static final long serialVersionUID = 4L;

    private int id;
    private Modalitat modalitat;
    private String nom;
    private Date dataInici;
    private Date dataFi;
    private boolean preinscripcioOberta;
    private boolean actiu;
    private List<Grup> grups;
    private List<Inscrit> inscripcions = new ArrayList<>();

    protected Torneig() { }

    public Torneig(Modalitat modalitat, String nom, Date dataInici, Date dataFi, boolean preinscripcioOberta, boolean actiu)
    {
        id = 0;
        setModalitat(modalitat);
        setNom(nom);
        setDataInici(dataInici);
        setDataFi(dataFi);
        setPreinscripcioOberta(preinscripcioOberta);
        setActiu(actiu);
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public Modalitat getModalitat()
    {
        return modalitat;
    }

    public void setModalitat(Modalitat modalitat)
    {
        this.modalitat = modalitat;
    }

    public String getNom()
    {
        return nom;
    }

    public void setNom(String nom)
    {
        this.nom = nom;
    }

    public Date getDataInici()
    {
        return dataInici;
    }

    public void setDataInici(Date dataInici)
    {
        this.dataInici = dataInici;
    }

    public Date getDataFi()
    {
        return dataFi;
    }

    public void setDataFi(Date dataFi)
    {
        this.dataFi = dataFi;
    }

    public boolean isPreinscripcioOberta()
    {
        return preinscripcioOberta;
    }

    public void setPreinscripcioOberta(boolean preinscripcioOberta)
    {
        this.preinscripcioOberta = preinscripcioOberta;
    }

    public boolean isActiu()
    {
        return actiu;
    }

    public void setActiu(boolean actiu)
    {
        this.actiu = actiu;
    }

    protected List<Grup> getGrups()
    {
        return grups;
    }
        
    public Iterator<Grup> iteGrups()
    {
        return grups.iterator();
    }

    protected void setGrups(List<Grup> grups)
    {
        this.grups = grups;
    }

    public Iterator<Inscrit> iteInscripcions()
    {
        return inscripcions.iterator();
    }

    protected void setInscripcions(List<Inscrit> inscripcions)
    {
        this.inscripcions = inscripcions;
    }
    
    public Inscrit getInscripcio(int idx)
    {
        return inscripcions.get(idx);
    }
    
    public void addInscripcio(Inscrit inscripcio)
    {
        this.inscripcions.add(inscripcio);
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 23 * hash + this.id;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Torneig other = (Torneig) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }
    
}
