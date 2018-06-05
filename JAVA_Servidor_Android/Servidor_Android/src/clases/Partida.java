/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Julian
 */
public class Partida  implements Serializable{
    
private static final long serialVersionUID = 7L;
    
    private int id;
    private Soci inscrit_a;
    private Soci inscrit_b;
    private Torneig torneig;
    private Grup grup;
    private int carambolesA;
    private int carambolesB;
    private int numEntrades;
    private Date data;
    private int entradesTotals;
    private String motiuVictoria;
    private String guanyador;
    private String estatPartida;
    
    protected Partida () {}

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

    public Grup getGrup() {
        return grup;
    }

    protected void setGrup(Grup grup) {
        this.grup = grup;
    }

    public int getCarambolesA() {
        return carambolesA;
    }

    public void setCarambolesA(int carambolesA) {
        this.carambolesA = carambolesA;
    }

    public int getCarambolesB() {
        return carambolesB;
    }

    public void setCarambolesB(int carambolesB) {
        this.carambolesB = carambolesB;
    }

    public int getEntradesTotals() {
        return entradesTotals;
    }

    public void setEntradesTotals(int entradesTotals) {
        this.entradesTotals = entradesTotals;
    }

    public Soci getInscrit_a() {
        return inscrit_a;
    }

    public void setInscrit_a(Soci inscrit_a) {
        this.inscrit_a = inscrit_a;
    }

    public Soci getInscrit_b() {
        return inscrit_b;
    }

    public void setInscrit_b(Soci inscrit_b) {
        this.inscrit_b = inscrit_b;
    }

    public int getNumEntrades() {
        return numEntrades;
    }

    public void setNumEntrades(int numEntrades) {
        this.numEntrades = numEntrades;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getMotiuVictoria() {
        return motiuVictoria;
    }

    public void setMotiuVictoria(String motiuVictoria) {
        this.motiuVictoria = motiuVictoria;
    }

    public String getGuanyador() {
        return guanyador;
    }

    public void setGuanyador(String guanyador) {
        this.guanyador = guanyador;
    }

    public String getEstatPartida() {
        return estatPartida;
    }

    public void setEstatPartida(String estatPartida) {
        this.estatPartida = estatPartida;
    }
    
    

}
