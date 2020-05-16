/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PartesCasilla;

import PartesTablero.Tablero;
import Interfaces.IValor;

/**
 *
 * @author eci-toshiba
 */
public final class Especial extends Casilla{

    public Especial() {
        super();
    }

    public Especial(Tablero tablero, String nombre, int numeroCasilla) {
        super(tablero, nombre, numeroCasilla);
    }
    
    @Override                            
    public String toString(){           
        String toret;
        StringBuilder aux=new StringBuilder();
        
        
        aux.append(super.toString());
        if(super.getNombre().equalsIgnoreCase("carcere")){
            aux.append(",\nCusto para sair: ").append(IValor.VALORSALIRCARCEL);
            super.getTablero().getJugadores().forEach((x) -> {if(x.getAvatar().isEncarcelado()){aux.append("\n").append(x.getNombre()).append(" leva encarcelado ").append(x.getAvatar().getNumTurnosCarcel()).append(" turnos.");}});
        }
        
        aux.append("\n}");
        toret=aux.toString();
        return toret;
    }   
    
    @Override
    public boolean equals(Object especial){
        if(especial==null){return false;}
        if(especial instanceof Especial){return  super.getNombre().equals(((Especial) especial).getNombre());}
        return false;
    }    
}
