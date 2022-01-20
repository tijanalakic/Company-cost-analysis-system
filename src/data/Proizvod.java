/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import java.io.Serializable;



/**
 *
 * @author Tijana Lakic
 */
public class Proizvod implements Serializable {
    
    private String nazivProizvoda;
    private Double cijena;
    
    public Proizvod(){}
    
    public Proizvod(String nazivProizvoda,Double cijena){
    
        this.nazivProizvoda=nazivProizvoda;
        this.cijena=cijena;    
    }

 
    /**
     * @return the nazivProizvoda
     */
    public String getNazivProizvoda() {
        return nazivProizvoda;
    }

    /**
     * @param nazivProizvoda the nazivProizvoda to set
     */
    public void setNazivProizvoda(String nazivProizvoda) {
        this.nazivProizvoda = nazivProizvoda;
    }

    /**
     * @return the cijena
     */
    public Double getCijena() {
        return cijena;
    }

    /**
     * @param cijena the cijena to set
     */
    public void setCijena(Double cijena) {
        this.cijena = cijena;
    }
}
