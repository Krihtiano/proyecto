
package clases;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;



public class Soci implements Serializable{
    
    private static final long serialVersionUID = 1L;

    private int id;
    private String nif;
    private String nom;
    private String cognom1;
    private String cognom2;
    private Date dataAlta;
    private String passwordHash;
    private byte[] foto;
    private int actiu;
    List<EstadisticaModalitat> em = new ArrayList<EstadisticaModalitat>();

    protected Soci(){
        
    }

    public Soci(int id, String nif, String nom, String cognom1, String cognom2, Date data_alta, String password_hash, byte[] foto, int actiu) {
        setId(id);
        setNif(nif);
        setNom(nom);
        setCognom1(cognom1);
        setCognom2(cognom2);
        setDataAlta(data_alta);
        setPasswordHash(password_hash);
        setFoto(foto);
        setActiu(actiu);
    }

    public Soci(int id, String nif, String nom, String cognom1, String cognom2, Date data_alta, String password_hash, int actiu) {
        setId(id);
        setNif(nif);
        setNom(nom);
        setCognom1(cognom1);
        setCognom2(cognom2);
        setDataAlta(data_alta);
        setPasswordHash(password_hash);
        setActiu(actiu);
    }
    
    public Soci(int id, String nif, String nom, String cognom1, String cognom2, Date data_alta, String password_hash, int actiu, List<EstadisticaModalitat> em) {
        setId(id);
        setNif(nif);
        setNom(nom);
        setCognom1(cognom1);
        setCognom2(cognom2);
        setDataAlta(data_alta);
        setPasswordHash(password_hash);
        setActiu(actiu);
        setEm(em);
    }

    public List<EstadisticaModalitat> getEm() {
        return em;
    }

    public void setEm(List<EstadisticaModalitat> em) {
        this.em = em;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getCognom1() {
        return cognom1;
    }

    public void setCognom1(String cognom1) {
        this.cognom1 = cognom1;
    }

    public String getCognom2() {
        return cognom2;
    }

    public void setCognom2(String cognom2) {
        this.cognom2 = cognom2;
    }

    public Date getData_alta() {
        return dataAlta;
    }

    public void setDataAlta(Date dataAlta) {
        this.dataAlta = dataAlta;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String pw) {
        this.passwordHash = pw;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public int isActiu() {
        return actiu;
    }

    public void setActiu(int actiu) {
        this.actiu = actiu;
    }

    @Override
    public String toString() {
        return  nom + " " + cognom1 + " " + cognom2;
    }
    
    
}
