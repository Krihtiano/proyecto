/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

import java.util.Date;
import java.util.Objects;

/**
 *
 * @author Julian
 */
public class Inscrit {
private static final long serialVersionUID = 6L;
    
    private int id;
    private Date dataCreacio;
    private Soci soci;
    private Torneig torneig;
    private Grup grup;

    public Inscrit(Date dataCreacio, Soci soci, Torneig torneig) {
        setDataCreacio(new Date());
        setSoci(soci);
        setTorneig(torneig);
    }

    public int getId() {
        return id;
    }

    protected void setId(int id) {
        this.id = id;
    }

    public Date getDataCreacio() {
        return dataCreacio;
    }

    protected void setDataCreacio(Date dataCreacio) {
        this.dataCreacio = dataCreacio;
    }

    public Soci getSoci() {
        return soci;
    }

    public void setSoci(Soci soci) {
        this.soci = soci;
    }

    public Torneig getTorneig() {
        return torneig;
    }

    public void setTorneig(Torneig torneig) {
        this.torneig = torneig;
    }

    public Grup getGrup() {
        return grup;
    }

    protected void setGrup(Grup grup) {
        this.grup = grup;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + this.id;
        hash = 17 * hash + Objects.hashCode(this.soci);
        hash = 17 * hash + Objects.hashCode(this.torneig);
        return hash;
    }    
    
    
}
