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
public final class CasillaComunidade extends Accion{
    private final ArrayList<CajaComunidad> cartasComunidade;

    public CasillaComunidade() {
        super();
        this.cartasComunidade = null;
    }

    public CasillaComunidade(ArrayList<CajaComunidad> cartas, Tablero tablero, String nombre, int numeroCasilla) {
        super(tablero, nombre, numeroCasilla);
        this.cartasComunidade = cartas;
    }

    public ArrayList<CajaComunidad> getCartas() { return cartasComunidade; }

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
  
        aux.append("\nSe caes aquí roubas unha carta de Caixa de Comunidade.\n}");
        
        toret=aux.toString(); 
        return toret;
    }
    
    @Override
    public boolean equals(Object casComun){
        if(casComun==null){return false;}
        if(casComun instanceof CasillaComunidade){return  super.getNombre().equals(((CasillaComunidade) casComun).getNombre());}
        return false;
    } 
}
