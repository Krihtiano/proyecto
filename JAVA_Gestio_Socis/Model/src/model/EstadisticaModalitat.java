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
    private Float coeficientBase;
    
    @Basic(optional = false)
    @Column(nullable = false, name = "total_caramboles_temporada_actual")
    private Integer totalCarambolesTemporadaActual;
    
    @Basic(optional = false)
    @Column(nullable = false, name = "total_entrades_temporada_actual")
    private Integer totalEntradesTemporadaActual;

    protected EstadisticaModalitat() {
    }

    public EstadisticaModalitat(Soci soci, Modalitat modalitat, Float coeficientBase, Integer totalCarambolesTemporadaActual, Integer totalEntradesTemporadaActual) {
        this.soci = soci;
        this.modalitat = modalitat;
        this.coeficientBase = coeficientBase;
        this.totalCarambolesTemporadaActual = totalCarambolesTemporadaActual;
        this.totalEntradesTemporadaActual = totalEntradesTemporadaActual;
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

    public Float getCoeficientBase() {
        return coeficientBase;
    }

    public void setCoeficientBase(Float coeficientBase) {
        this.coeficientBase = coeficientBase;
    }

    public Integer getTotalCarambolesTemporadaActual() {
        return totalCarambolesTemporadaActual;
    }

    public void setTotalCarambolesTemporadaActual(Integer totalCarambolesTemporadaActual) {
        this.totalCarambolesTemporadaActual = totalCarambolesTemporadaActual;
    }

    public Integer getTotalEntradesTemporadaActual() {
        return totalEntradesTemporadaActual;
    }

    public void setTotalEntradesTemporadaActual(Integer totalEntradesTemporadaActual) {
        this.totalEntradesTemporadaActual = totalEntradesTemporadaActual;
    }

    
}
