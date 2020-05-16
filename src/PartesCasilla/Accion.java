/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PartesCasilla;

import PartesTablero.Tablero;
import Menu.*;

/**
 *
 * @author eci-toshiba
 */
public class Accion extends Casilla{
    private double bote;

    public Accion() {
        super();
        this.bote = 0;
    }

    public Accion(Tablero tablero, String nombre, int numeroCasilla) {
        super(tablero, nombre, numeroCasilla);
        this.bote = 0;
    }

    public double getBote() { return bote; }

    public void setBote(double bote) { this.bote = bote; }
    
    public void aumentaBote(double bote){ this.bote+=bote; }
    
    @Override                            
    public String toString(){           
        String toret;
        StringBuilder aux=new StringBuilder();
        
        aux.append(super.toString());
        if(super.getNombre().equalsIgnoreCase("parking")){
            aux.append("\nSe caes aquí cobrarás o bote acumulado de Impostos e cartas de Sorte e Caixa de Comunidade;\n  -Bote actual: ").append (bote);
        }
        if(super.getNombre().equalsIgnoreCase("ir ao carcere")){
            aux.append("\nSe caes aquí serás enviado ao cárcere. (Sen pasar por SAÍDA)");
        }
        
        aux.append("\n}");
        toret=aux.toString();
        return toret;
    }   
    
    @Override
    public boolean equals(Object accion){
        if(accion==null){return false;}
        if(accion instanceof Accion){return  super.getNombre().equals(((Accion) accion).getNombre());}
        return false;
    } 
}
