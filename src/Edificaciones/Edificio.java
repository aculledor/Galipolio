/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Edificaciones;

import PartesTablero.*;
import PartesCasilla.*;
import Usuario.*;

/**
 *
 * @author eci-toshiba
 */
public abstract class Edificio {
    //ATRIBUTOS
    private final Tablero tablero;
    private Jugador propietario;
    private final Solar solar;
    
    private String idEdificio;
    private double valorCompra;
    private double valorVenta;
    
    
    public Edificio(){
        this.tablero=null;
        this.propietario=null;
        this.solar=null;
        this.idEdificio=null;
        this.valorCompra=0;
        this.valorVenta=0;
    }

    public Edificio(Tablero tablero, Solar solar, Jugador propietario) {
        this.tablero=tablero;
        this.propietario=propietario;
        this.solar=solar;
        this.valorCompra=0;
        this.valorVenta=0;
        this.idEdificio=null;       
    }
    
    //GETTERS Y SETTERS
    public Jugador getPropietario() {return propietario;}
    
    public Solar getSolar()         {return solar;}

    public String getIdEdificio()   {return idEdificio;}

    public double getValorCompra()  {return valorCompra;}

    public double getValorVenta()   {return valorVenta;}

    public void setPropietario(Jugador propietario) {this.propietario = propietario;} 

    public void setIdEdificio(String idEdificio)    { this.idEdificio = idEdificio; }

    public void setValorCompra(double valorCompra)  { this.valorCompra = valorCompra; }

    public void setValorVenta(double valorVenta)    { this.valorVenta = valorVenta; }
 
    
    public void eliminarEdificio() throws NullPointerException{
        tablero.getEdificios().remove(this);
        propietario.getEdificios().remove(this);
        solar.getEdificios().remove(this);
    }    
    
    //OVERRIDES
    
    @Override
    public String toString(){
        String toret;
        StringBuilder aux=new StringBuilder();
    
        aux.append("\n{\nID: [").append(idEdificio);
        aux.append("],\nPropietario: ").append(propietario.getNombre());
        aux.append(",\nCasilla: ").append(solar.nombreAColor());
        aux.append(",\nGrupo: ").append(solar.getGrupo().getNombre());
        aux.append(",\nPrecio de Compra: ").append(valorCompra);
        aux.append(",\nPrecio de Venta: ").append(valorVenta);
                
        aux.append("\n} ");
        toret=aux.toString();
        return toret;
    } 
}
