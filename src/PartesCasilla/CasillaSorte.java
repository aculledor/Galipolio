/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PartesCasilla;

import PartesTablero.Tablero;
import PartesCarta.*;
import java.util.ArrayList;

/**
 *
 * @author aculledor
 */
public final class CasillaSorte extends Accion{
    private final ArrayList<Suerte> cartasSorte;

    public CasillaSorte() {
        super();
        this.cartasSorte = null;
    }

    public CasillaSorte(ArrayList<Suerte> cartas, Tablero tablero, String nombre, int numeroCasilla) {
        super(tablero, nombre, numeroCasilla);
        this.cartasSorte = cartas;
    }

    public ArrayList<Suerte> getCartas() { return cartasSorte; }

    @Override                            
    public String toString(){           
        String toret;
        StringBuilder aux=new StringBuilder();
        
        aux.append("\n{\nNome: ").append(getNombre()).
        append(",\nTipo: ").append(this.getClass().getSimpleName());
        if(getAvatares().size()>0){
            aux.append(",\nAvatares en Casilla: [ "); 
            getAvatares().forEach((a) ->{
                aux.append(a.getNombre()); 
                if(!(getAvatares().indexOf(a)==getAvatares().size()-1)) {aux.append(", ");}
            });
            aux.append(" ]");
        }
  
        aux.append("\nSe caes aquí roubas unha carta de Sorte.\n}");
        
        toret=aux.toString(); 
        return toret;
    }
    
    @Override
    public boolean equals(Object casSorte){
        if(casSorte==null){return false;}
        if(casSorte instanceof CasillaSorte){return  super.getNombre().equals(((CasillaSorte) casSorte).getNombre());}
        return false;
    } 
}
