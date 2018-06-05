/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

import java.io.Serializable;

public class EstadisticaModalitat implements Serializable{
    private static final long serialVersionUID = 2L;
    
    private Soci soci;
    private Modalitat modalitat;
    private Float coeficientBase;
    private Integer totalCarambolesTemporadaActual;
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
    
    public EstadisticaModalitat(Modalitat modalitat, Float coeficientBase, Integer totalCarambolesTemporadaActual, Integer totalEntradesTemporadaActual) {
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
