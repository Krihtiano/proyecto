/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

import java.io.Serializable;


public class Modalitat implements Serializable{
    private static final long serialVersionUID = 3L;

    private int id;
    private String description;
    
    protected Modalitat(){
    }

    public Modalitat(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }    
}
