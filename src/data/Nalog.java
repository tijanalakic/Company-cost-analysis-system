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
public class Nalog implements Serializable{
    private String ime;
    private String prezime;
    private String korisnickoIme;
    private String lozinka;
    private String korisnickaGrupa;
    
    /**
     *
     */
    public Nalog(String ime, String prezime, String korisnickoIme, String lozinka,String korisnickaGrupa){
    
        this.ime=ime;
        this.prezime=prezime;
        this.korisnickoIme=korisnickoIme;
        this.lozinka=lozinka;
        this.korisnickaGrupa=korisnickaGrupa;
    }
    
    public Nalog(String ime, String prezime, String korisnickoIme,String korisnickaGrupa){
    
        this.ime=ime;
        this.prezime=prezime;
        this.korisnickoIme=korisnickoIme;
        this.korisnickaGrupa=korisnickaGrupa;
    }
    
    @Override
    public String toString() {
        return getIme() + "-"+getPrezime() + "-"+ getKorisnickoIme() + "-"+ lozinka + "-"+ getKorisnickaGrupa();
    }

    /**
     * @return the ime
     */
    public String getIme() {
        return ime;
    }

    /**
     * @param ime the ime to set
     */
    public void setIme(String ime) {
        this.ime = ime;
    }

    /**
     * @return the prezime
     */
    public String getPrezime() {
        return prezime;
    }

    /**
     * @param prezime the prezime to set
     */
    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    /**
     * @return the korisnickoIme
     */
    public String getKorisnickoIme() {
        return korisnickoIme;
    }

    /**
     * @param korisnickoIme the korisnickoIme to set
     */
    public void setKorisnickoIme(String korisnickoIme) {
        this.korisnickoIme = korisnickoIme;
    }

    /**
     * @return the korisnickaGrupa
     */
    public String getKorisnickaGrupa() {
        return korisnickaGrupa;
    }

    /**
     * @param korisnickaGrupa the korisnickaGrupa to set
     */
    public void setKorisnickaGrupa(String korisnickaGrupa) {
        this.korisnickaGrupa = korisnickaGrupa;
    }
    
    public boolean checkPassword(String password){
        return this.lozinka.equals(password);
    }
}
