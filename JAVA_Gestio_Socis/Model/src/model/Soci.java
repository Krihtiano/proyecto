
package model;

import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;

@Entity
public class Soci {
    
    @Id
    private int id;
    
    @Basic(optional=false)
    @Column(nullable=false,length=9)
    private String nif;
    
    @Basic(optional=false)
    @Column(nullable=false,length=30)
    private String nom;
    
    @Basic(optional=false)
    @Column(nullable=false,length=30)
    private String cognom1;
    
    @Basic(optional=false)
    @Column(nullable=false,length=30)
    private String cognom2;
    
    @Basic(optional=false)
    @Column(nullable=false, name="data_alta")
    private Date dataAlta;
    
    @Basic(optional=false)
    @Column(nullable=false,length=30, name="password_hash")
    private String passwordHash;
    
    @Lob
    private byte[] foto;
    
    @Column(name="actiu")
    private boolean actiu;

    public Soci(String nif, String nom, String cognom1, String cognom2, Date data_alta, String password_hash, byte[] foto, boolean actiu) {
        this.nif = nif;
        this.nom = nom;
        this.cognom1 = cognom1;
        this.cognom2 = cognom2;
        this.dataAlta = data_alta;
        this.passwordHash = password_hash;
        this.foto = foto;
        this.actiu = actiu;
    }

    public Soci(String nif, String nom, String cognom1, String cognom2, Date data_alta, String password_hash, boolean actiu) {
        this.nif = nif;
        this.nom = nom;
        this.cognom1 = cognom1;
        this.cognom2 = cognom2;
        this.dataAlta = data_alta;
        this.passwordHash = password_hash;
        this.actiu = actiu;
    }

    public int getId() {
        return id;
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

    public void setData_alta(Date data_alta) {
        this.dataAlta = data_alta;
    }

    public String getPassword_hash() {
        return passwordHash;
    }

    public void setPassword_hash(String password_hash) {
        this.passwordHash = password_hash;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public boolean isActiu() {
        return actiu;
    }

    public void setActiu(boolean actiu) {
        this.actiu = actiu;
    }
}
