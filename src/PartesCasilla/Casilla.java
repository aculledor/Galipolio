/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PartesCasilla;

import PartesTablero.Tablero;
import Interfaces.IValor;
import Usuario.*;
import java.util.ArrayList;

/**
 *
 * @author eci-toshiba
 */
public abstract class Casilla {
    //ATRIBUTOS
    private final Tablero tablero;
    private final ArrayList<Avatar> avatares;
    
    private final String nombre;      ////////////////////////////PARA GETNOMBREACOLOR
    private final int numeroCasilla;
    
    private double rentabilidade;
    private int vecesPisada;
    
    //CONSTRUCTORES
    
    public Casilla(){
        this.rentabilidade = 0;
        this.vecesPisada = 0;
        this.tablero=null;
        this.avatares=null;
        this.nombre=null;
        this.numeroCasilla=0;
    }

    public Casilla(Tablero tablero, String nombre, int numeroCasilla) {
        this.rentabilidade = 0;
        this.vecesPisada = 0;
        this.tablero=tablero;
        this.avatares=new ArrayList<>();
        this.nombre=nombre;   
        this.numeroCasilla=numeroCasilla;
    }
    
    //SETTERS AND GETTERS
    
    public Tablero getTablero(){return tablero;}

    public ArrayList<Avatar> getAvatares() {return avatares;}

    public int getNumeroCasilla() {return numeroCasilla;}

    public double getRentabilidade() {return rentabilidade; }

    public void aumentaRentabilidade(double rentabilidade) { this.rentabilidade += rentabilidade;}

    public int getVecesPisada() { return vecesPisada; }

    public void aumentaVecesPisada() { vecesPisada++;}
    
    public String getNombre(){return nombre;}
    
    public String nombreAColor() { return IValor.NEGRO + nombre + IValor.RESET; }
    
    //METODOS
    public boolean estaAvatar(Avatar avatar){
        if(avatares.isEmpty()){return false;}
        for(Avatar av : avatares){
            if(av.equals(avatar)){return true;}
        }
        return false;
    }
    
    public String frecuenciaVisita(){
        if(tablero.getRonda()>0){ return ("A frecuencia coa que esta casilla é visitada é de: "+vecesPisada/tablero.getRonda()+ " veces pisada/ronda.");}
        return nombreAColor()+" foi pisada: "+vecesPisada+" veces nesta primeira ronda.";
    }
    
    //OVERRIDES
    @Override                            
    public String toString(){           
        String toret;
        StringBuilder aux=new StringBuilder();
        
        aux.append("\n{\nNome: ").append(nombreAColor()).
        append(",\nTipo: ").append(this.getClass().getSimpleName());
        if(getAvatares().size()>0){
            aux.append(",\nAvatares en Casilla: [ "); 
            getAvatares().forEach((a) ->{
                aux.append(a.getNombre()); 
                if(!(avatares.indexOf(a)==avatares.size()-1)) {aux.append(", ");}
            });
            aux.append(" ]");
        }

        toret=aux.toString();
        return toret;
    }
    
}
    