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
public final class Servicio extends Propiedad{
    
    
    
    public Servicio(){
        super();
    }

    public Servicio(Tablero tablero, String nombre, Grupo grupo, int numeroCasilla) {
        super(IValor.VALORSERVIZO, tablero, nombre, grupo, numeroCasilla);
        super.setValorAlquiler(IValor.VALORSALIDA*IValor.FACTORALQUILERSERVICIO);
    }
    
    @Override
    public void resetAlquiler(){ super.setValorAlquiler(super.getValorInicial()*IValor.FACTORALQUILERSERVICIO); }

    @Override
    public void alquiler(){
        boolean tieneGrupo =true;
        for(Propiedad servizo : getGrupo().getPropiedadesGrupo()){
            if(!servizo.getDueño().equals(this.getDueño())){
                tieneGrupo=false;
            }
        }
        this.resetAlquiler();
        if(tieneGrupo){ setValorAlquiler(getValorAlquiler()*IValor.FACTORDOUSSERVIZOS); }
        else{ setValorAlquiler(getValorAlquiler()*IValor.FACTORUNSERVIZO); }
    }
    
    @Override
    public String toString(){           
        String toret;
        StringBuilder aux=new StringBuilder();
        
        aux.append(super.toString());
        aux.append(",\nValor de Aluguer: ").append(getValorAlquiler()).append(" veces a tirada de dados");
        
        aux.append("\n}");
        toret=aux.toString();
        return toret;
    }
    
    @Override
    public boolean equals(Object servicio){
        if(servicio==null){return false;}
        if(servicio instanceof Servicio){return  super.getNombre().equals(((Servicio) servicio).getNombre());}
        return false;
    }
    
}
