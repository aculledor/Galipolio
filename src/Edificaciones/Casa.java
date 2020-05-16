/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Edificaciones;

import PartesTablero.*;
import Interfaces.IValor;
import PartesCasilla.*;
import Usuario.*;

/**
 *
 * @author eci-toshiba
 */
public final class Casa extends Edificio{

    public Casa() {
        super();
    }

    public Casa(Tablero tablero, Solar solar, Jugador propietario) {
        super(tablero, solar, propietario);
        super.setValorCompra(solar.getValorInicial()*IValor.FACTORCASA);
        super.setValorVenta(super.getValorCompra()/2);
        super.setIdEdificio("Casa-"+super.getPropietario().getAvatar().getNombre()+"-"+super.getPropietario().getEdificios().size());       
        getSolar().getGrupo().cambiarNumCasas(true);
    }
    
    @Override
    public void eliminarEdificio() throws NullPointerException{
        getSolar().getGrupo().cambiarNumCasas(false);
        super.eliminarEdificio();
    }
    
    @Override
    public String toString(){
        return super.toString();
    }
    
    @Override
    public boolean equals(Object casa){
        if(casa==null){return false;}
        if(casa instanceof Casa){return  super.getIdEdificio().equals(((Casa) casa).getIdEdificio());}
        return false;
    }   
}
