/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package listeners;

/**
 *
 * @author Tijana Lakic
 */
public enum EnumEvent {
    OBRISAN_FAJL("Obrisan fajl"), DODAN_FAJL("Dodan fajl");
    
    private String poruka;
    private EnumEvent(String poruka) {
        this.poruka=poruka;
    }
    @Override
    public String toString(){
        return poruka;
    }
}
