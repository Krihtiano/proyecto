/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

import java.io.Serializable;

/**
 *
 * @author Julian
 */
public class Grup implements Serializable
{
    private static final long serialVersionUID = 5L;
    
    private int id;
    private Torneig torneig;
    private String descripcio;
    private int carambolesVictoria;
    private int limitEntrades;
    private boolean actiu;
    
    protected Grup() {}

    public Grup(Torneig torneig, String descripcio, int carambolesVictoria, int limitEntrades, boolean actiu) {
        id = 0;
        setTorneig(torneig);
        setDescripcio(descripcio);
        setCarambolesVictoria(carambolesVictoria);
        setLimitEntrades(limitEntrades);
        setActiu(actiu);
    }

    public int getId() {
        return id;
    }

    protected void setId(int id) {
        this.id = id;
    }

    public Torneig getTorneig() {
        return torneig;
    }

    protected void setTorneig(Torneig torneig) {
        this.torneig = torneig;
    }

    public String getDescripcio() {
        return descripcio;
    }

    public void setDescripcio(String descripcio) {
        this.descripcio = descripcio;
    }

    public int getCarambolesVictoria() {
        return carambolesVictoria;
    }

    public void setCarambolesVictoria(int carambolesVictoria) {
        this.carambolesVictoria = carambolesVictoria;
    }

    public int getLimitEntrades() {
        return limitEntrades;
    }

    public void setLimitEntrades(int limitEntrades) {
        this.limitEntrades = limitEntrades;
    }

    public boolean isActiu() {
        return actiu;
    }

    public void setActiu(boolean actiu) {
        this.actiu = actiu;
    }
    
}
