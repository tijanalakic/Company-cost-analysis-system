/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import java.io.Serializable;
import java.util.Objects;



/**
 *
 * @author Tijana Lakic
 */
public class Stavka extends Proizvod implements Serializable {
    
  
    private Integer kolicina;

    private Double ukupno;
    
    public Stavka(){}
    
    public Stavka(String nazivProizvoda,Integer kolicina,Double cijena){
    
        super(nazivProizvoda,cijena);
        this.kolicina=kolicina;
        this.ukupno=cijena*kolicina;
    
    }

   
    /**
     * @return the kolicina
     */
    public Integer getKolicina() {
        return kolicina;
    }

    /**
     * @param kolicina the kolicina to set
     */
    public void setKolicina(Integer kolicina) {
        this.kolicina = kolicina;
    }
    public Double getUkupno(){
        return getCijena()*getKolicina();
    }
    public void setUkupno(Integer kolicina,Double cijena)
    {
      this.ukupno=cijena*kolicina;
    }
    @Override
    public boolean equals(Object objekat){
        return this.getNazivProizvoda().equals(((Stavka)objekat).getNazivProizvoda());
    }
    @Override
    public int hashCode(){
        return Objects.hash(this.getNazivProizvoda());
    }
    
    public String toString(String separator){
        if("-".equals(separator)){
            separator="  "+separator+"  ";
        }
        return getNazivProizvoda()+separator+getKolicina()+separator+getCijena()+separator+getUkupno();
        }   
    }

