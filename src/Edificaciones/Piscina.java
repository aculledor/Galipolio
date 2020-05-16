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
public final class Piscina extends Edificio{

    public Piscina() {
        super();
    }
    
    public Piscina(Tablero tablero, Solar solar, Jugador propietario) {
        super(tablero, solar, propietario);
        super.setValorCompra(solar.getValorInicial()*IValor.FACTORPISCINA);
        super.setValorVenta(super.getValorCompra()/2);
        super.setIdEdificio("Piscina-"+super.getPropietario().getAvatar().getNombre()+"-"+super.getPropietario().getEdificios().size());       
        getSolar().getGrupo().cambiarNumPiscinas(true);
    }
    
    @Override
    public void eliminarEdificio()throws NullPointerException{
        getSolar().getGrupo().cambiarNumPiscinas(false);
        super.eliminarEdificio();
    }
   
    @Override
    public String toString(){
        return super.toString();
    }
    
    @Override
    public boolean equals(Object piscina){
        if(piscina==null){return false;}
        if(piscina instanceof Piscina){return  super.getIdEdificio().equals(((Piscina) piscina).getIdEdificio());}
        return false;
    }    
}
