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
public final class Hotel extends Edificio{

    public Hotel() {
        super();
    }
    
    public Hotel(Tablero tablero, Solar solar, Jugador propietario) {
        super(tablero, solar, propietario);
        super.setValorCompra(solar.getValorInicial()*IValor.FACTORHOTEL);
        super.setValorVenta(super.getValorCompra()/2);
        super.setIdEdificio("Hotel-"+super.getPropietario().getAvatar().getNombre()+"-"+super.getPropietario().getEdificios().size());       
        getSolar().getGrupo().cambiarNumHoteles(true);
    }
    
    @Override
    public void eliminarEdificio()throws NullPointerException{
        getSolar().getGrupo().cambiarNumHoteles(false);
        super.eliminarEdificio();
    }
    
    @Override
    public String toString(){
        return super.toString();
    }
    
    @Override
    public boolean equals(Object hotel){
        if(hotel==null){return false;}
        if(hotel instanceof Hotel){return  super.getIdEdificio().equals(((Hotel) hotel).getIdEdificio());}
        return false;
    }   
}
