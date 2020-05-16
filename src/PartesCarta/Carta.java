/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PartesCarta;

import PartesTablero.Tablero;
import Edificaciones.*;
import Excepciones.*;
import PartesCasilla.*;
import Usuario.*;
import static Menu.Juego.consola;

/**
 *
 * @author eci-toshiba
 */
public abstract class Carta {
    private final Tablero tablero;
    
    private final String descripcion;
    private final Casilla destino;
    private final double cantidadAPagar;
    private final boolean aTodos;

    public Carta(){
        this.tablero=null;
        this.descripcion=null;
        this.destino=null;
        this.aTodos=false;
        this.cantidadAPagar=0;
    }
    
    public Carta(Tablero tablero, String descripcion,Casilla destino){
        this.tablero=tablero;
        this.descripcion=descripcion;
        this.destino=destino;
        this.aTodos=false;
        this.cantidadAPagar=0;
        
    }
    
    public Carta(Tablero tablero, String descripcion,double pago,boolean aTodos){
        this.tablero=tablero;
        this.descripcion=descripcion;
        this.destino=null;
        this.cantidadAPagar=pago;
        this.aTodos=aTodos;
        
    }
    
    public void accion(Jugador dueño) throws PeticionIncorrectaException{
        if(destino!=null){          //Moverse a destino
            if(destino.getNombre().equalsIgnoreCase("carcere")){ consola.imprimir(dueño.getAvatar().irCarcel()); } 
            else{ consola.imprimir(dueño.getAvatar().irACasilla(destino)); }
        }   
        else if(cantidadAPagar<0){             //Cobrar
            dueño.aumentaPremiosInversionsOuBote(cantidadAPagar*(-1));
            if(dueño.getAvatar() instanceof Esfinge || dueño.getAvatar() instanceof Sombrero){
                ((Esfinge) dueño.getAvatar()).setBalance(((Esfinge) dueño.getAvatar()).getBalance()-cantidadAPagar);
            }
            consola.imprimir(tablero.getLaBanca().pagar(dueño, (cantidadAPagar*(-1))));
        }
        else if(cantidadAPagar>0){             //Pagar a Banca
            if(aTodos){       //Pagar a todos
                if(dueño.capitalDisponible()*(tablero.getJugadores().size()-1)<cantidadAPagar){
                    dueño.getAvatar().getCasillaActual().aumentaRentabilidade(dueño.capitalDisponible());
                    consola.imprimir(dueño.bancarrota(tablero.getLaBanca(), 0));
                    tablero.getJugadores().forEach((jug) -> {
                        try{
                            consola.imprimir(tablero.getLaBanca().pagar(jug, cantidadAPagar));
                        }catch(DatoIncorrectoException exception){
                            consola.imprimirErro("Erro cando ABANCA intentou pagarlle"+cantidadAPagar+" ferrados a "+jug+".");
                        }
                    });
                }
                else{tablero.getJugadores().forEach((jug) -> {if(!jug.equals(dueño)){
                    try{
                        dueño.aumentaPagoImpostosTasas(cantidadAPagar);
                        dueño.getAvatar().getCasillaActual().aumentaRentabilidade(cantidadAPagar);
                        if(dueño.getAvatar() instanceof Esfinge || dueño.getAvatar() instanceof Sombrero){
                            ((Esfinge) dueño.getAvatar()).setBalance(((Esfinge) dueño.getAvatar()).getBalance()-cantidadAPagar);
                        }
                        consola.imprimir(dueño.pagar(jug, cantidadAPagar)); 
                    }catch(DatoIncorrectoException exception){
                        consola.imprimirErro("Erro cando "+dueño+" intentou pagarlle"+cantidadAPagar+" ferrados a "+jug+".");
                    }
                    
                }});}
            }
            else{
                if(dueño.getAvatar() instanceof Esfinge || dueño.getAvatar() instanceof Sombrero){
                    ((Esfinge) dueño.getAvatar()).setBalance(((Esfinge) dueño.getAvatar()).getBalance()-cantidadAPagar);
                }
                ((Accion)tablero.buscarCasilla("parking")).aumentaBote(cantidadAPagar);
                dueño.getAvatar().getCasillaActual().aumentaRentabilidade(cantidadAPagar);
                consola.imprimir(dueño.pagar(tablero.getLaBanca(), cantidadAPagar));
            }
        }            //Pagar a Banca por edificios
        else{
            double pagoTotal=0;
            for(Edificio e: dueño.getEdificios()){
                if(e instanceof Casa){ pagoTotal+=30; }
                else if(e instanceof Hotel){ pagoTotal+=60; }
                else if(e instanceof Piscina){ pagoTotal+=20; }
                else { pagoTotal+=50; }
            }
            if(dueño.getAvatar() instanceof Esfinge || dueño.getAvatar() instanceof Sombrero){
                ((Esfinge) dueño.getAvatar()).setBalance(((Esfinge) dueño.getAvatar()).getBalance()-pagoTotal);
            }
            dueño.getAvatar().getCasillaActual().aumentaRentabilidade(pagoTotal);
            consola.imprimir(dueño.pagar(tablero.getLaBanca(), pagoTotal));
        }
    }
      
    @Override
    public String toString(){return descripcion;}
}
