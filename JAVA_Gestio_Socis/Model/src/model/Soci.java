
package model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

@Entity
@Access(AccessType.FIELD)
@Table(name="soci")
public class Soci implements Serializable{
    
    @Id
    @TableGenerator(name = "gen_soci",
<<<<<<< HEAD
            initialValue=100,
=======
            initialValue = 100,
>>>>>>> d1b35c8d5be8b5c75e21623d49495d6ec59ed8e3
            table = "COMPTADORS",
            pkColumnName = "CLAU",
            valueColumnName = "COMPTADOR",
            pkColumnValue = "SOCI",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "gen_soci")
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
    private int actiu;
    
    protected Soci(){
        
    }

    public Soci(String nif, String nom, String cognom1, String cognom2, Date data_alta, String password_hash, byte[] foto, int actiu) {
        setNif(nif);
        setNom(nom);
        setCognom1(cognom1);
        setCognom2(cognom2);
        setDataAlta(data_alta);
        setPasswordHash(password_hash);
        setFoto(foto);
        setActiu(actiu);
    }

    public Soci(String nif, String nom, String cognom1, String cognom2, Date data_alta, String password_hash, int actiu) {
        setNif(nif);
        setNom(nom);
        setCognom1(cognom1);
        setCognom2(cognom2);
        setDataAlta(data_alta);
        setPasswordHash(password_hash);
        setActiu(actiu);
    }

    public int getId() {
        return id;
    }

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        if(nif.length() == 0 || nif == null){
            throw new RuntimeException("El nif no pot ser vuit o null");
        } if(nif.length() > 11){
            throw new RuntimeException("El nif no pot ser superior a 11 caracters");
        }
        this.nif = nif;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        if(nom.length() == 0 || nom == null){
            throw new RuntimeException("El nom no pot ser vuit o null");
        } 
        if(nom.length()> 30){
            throw new RuntimeException("El nom no pot ser major de 30 caracters");
        }
        this.nom = nom;
    }

    public String getCognom1() {
        return cognom1;
    }

    public void setCognom1(String cognom1) {
        if(cognom1.length() == 0 || cognom1 == null){
            throw new RuntimeException("El cognom no pot ser vuit o null");
        }
        if(cognom1.length() > 30){
            throw new RuntimeException("El cognom no pot ser major de 30 caracters");
        }
        this.cognom1 = cognom1;
    }

    public String getCognom2() {
        return cognom2;
    }

    public void setCognom2(String cognom2) {
        if(cognom2.length() == 0 || cognom2 == null){
            throw new RuntimeException("El segón cognom no pot ser vuit o null");
        }
        if(cognom2.length() > 30){
            throw new RuntimeException("El segon cognom no pot ser major de 30 caracters");
        }
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
        if(pw.length() == 0 || pw == null){
            throw new RuntimeException("El password no pot ser vuit o null");
        }
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
