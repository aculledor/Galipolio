/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Edificaciones;

import PartesTablero.*;
import Interfaces.IValor;
import PartesCasilla.Solar;
import Usuario.Jugador;

/**
 *
 * @author eci-toshiba
 */
public final class PistaDeportiva extends Edificio{

    public PistaDeportiva() {
        super();
    }
    
    public PistaDeportiva(Tablero tablero, Solar solar, Jugador propietario) {
        super(tablero, solar, propietario);
        super.setValorCompra(solar.getValorInicial()*IValor.FACTORPISTADEPORTIVA);
        super.setValorVenta(super.getValorCompra()/2);
        super.setIdEdificio("PistaDeportiva-"+super.getPropietario().getAvatar().getNombre()+"-"+super.getPropietario().getEdificios().size());       
        getSolar().getGrupo().cambiarNumPistas(true);
    }
    
    @Override
    public void eliminarEdificio()throws NullPointerException{
        getSolar().getGrupo().cambiarNumPistas(false);
        super.eliminarEdificio();
    }
    
    @Override
    public String toString(){
        return super.toString();
    }
    
    @Override
    public boolean equals(Object pistaDeporte){
        if(pistaDeporte==null){return false;}
        if(pistaDeporte instanceof PistaDeportiva){return  super.getIdEdificio().equals(((PistaDeportiva) pistaDeporte).getIdEdificio());}
        return false;
    }    
}
