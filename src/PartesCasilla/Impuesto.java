/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PartesCasilla;

import PartesTablero.Tablero;

/**
 *
 * @author eci-toshiba
 */
public final class Impuesto extends Casilla{
    private final double valorImpuesto;

    public Impuesto() {
        super();
        this.valorImpuesto = 0;
    }

    public Impuesto(double valorImpuesto, Tablero tablero, String nombre, int numeroCasilla) {
        super(tablero, nombre, numeroCasilla);
        this.valorImpuesto = valorImpuesto;
    }

    public double getValorImpuesto() { return valorImpuesto; }
    
    @Override                            
    public String toString(){           
        String toret;
        StringBuilder aux=new StringBuilder();
        
        aux.append("\n{\nNome: ").append(super.getNombre());
        aux.append(",\nTipo: Imposto");
        aux.append(",\nPrecio do imposto: ").append(valorImpuesto);
        aux.append(super.toString());
        
        aux.append("\n}");
        toret=aux.toString();
        return toret;
    }
    
    @Override
    public boolean equals(Object impuesto){
        if(impuesto==null){return false;}
        if(impuesto instanceof Impuesto){return  super.getNombre().equals(((Impuesto) impuesto).getNombre());}
        return false;
    }    
}
