/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PartesCasilla;

import PartesTablero.Grupo;
import PartesTablero.Tablero;
import Interfaces.IValor;

/**
 *
 * @author eci-toshiba
 */
public final class Transporte extends Propiedad{

    public Transporte() {
        super();
    }

    public Transporte( Tablero tablero, String nombre, Grupo grupo, int numeroCasilla) {
        super(IValor.VALORTRANSPORTE, tablero, nombre, grupo, numeroCasilla);
        super.setValorAlquiler(IValor.VALORSALIDA*IValor.FACTORALQUILERTRANSPORTE);
    }
    
    @Override
    public void resetAlquiler(){
        super.setValorAlquiler(super.getValorInicial()*IValor.FACTORALQUILERTRANSPORTE);
    }

    
    @Override
    public void alquiler(){
        int numCasillas=0;
        for(Propiedad p : getGrupo().getPropiedadesGrupo()){ 
            if(getDueño().equals(p.getDueño())) { 
                numCasillas++; 
            } 
        }
        resetAlquiler();
        setValorAlquiler(getValorAlquiler()*numCasillas);
    }    
    
    @Override
    public String toString(){           
        String toret;
        StringBuilder aux=new StringBuilder();
        
        aux.append(super.toString());
        aux.append(",\nValor de Aluguer: ").append(getValorAlquiler());
        
        aux.append("\n}");
        toret=aux.toString();
        return toret;
    }
    
    @Override
    public boolean equals(Object transporte){
        if(transporte==null){return false;}
        if(transporte instanceof Transporte){return  super.getNombre().equals(((Transporte) transporte).getNombre());}
        return false;
    }    
}
