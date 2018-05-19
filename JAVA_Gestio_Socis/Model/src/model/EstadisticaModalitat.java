/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="estadistica_modalitat")
public class EstadisticaModalitat implements Serializable{
    
    @Id
    @ManyToOne(optional = false, cascade = CascadeType.PERSIST,fetch = FetchType.LAZY)
    @JoinColumn(name = "soci_id")
    private Soci soci;
    
    @Id
    @ManyToOne(optional = false, cascade = CascadeType.PERSIST,fetch = FetchType.LAZY)
    @JoinColumn(name = "modalitat_id")
    private Modalitat modalitat;
    
    @Basic(optional = false)
    @Column(nullable = false, name = "coeficient_base")
    private int coeficientBase;
    
    @Basic(optional = false)
    @Column(nullable = false, name = "total_caramboles_temporada_actual")
    private int totalCarambolesTemporadaActual;
    
    @Basic(optional = false)
    @Column(nullable = false, name = "total_entrades_temporada_actual")
    private int totalEntradesTemporadaActual;

    protected EstadisticaModalitat() {
    }
    
    public Soci getSoci() {
        return soci;
    }

    public void setSoci(Soci soci) {
        this.soci = soci;
    }

    public Modalitat getModalitat() {
        return modalitat;
    }

    public void setModalitat(Modalitat modalitat) {
        this.modalitat = modalitat;
    }

    public int getCoeficientBase() {
        return coeficientBase;
    }

    public void setCoeficientBase(int coeficientBase) {
        this.coeficientBase = coeficientBase;
    }

    public int getTotalCarambolesTemporadaActual() {
        return totalCarambolesTemporadaActual;
    }

    public void setTotalCarambolesTemporadaActual(int totalCarambolesTemporadaActual) {
        this.totalCarambolesTemporadaActual = totalCarambolesTemporadaActual;
    }

    public int getTotalEntradesTemporadaActual() {
        return totalEntradesTemporadaActual;
    }

    public void setTotalEntradesTemporadaActual(int totalEntradesTemporadaActual) {
        this.totalEntradesTemporadaActual = totalEntradesTemporadaActual;
    }

    
}
