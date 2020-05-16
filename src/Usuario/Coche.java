/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Usuario;

import PartesTablero.Tablero;
import Interfaces.IValor;
import Excepciones.*;
import PartesCasilla.Propiedad;
import static Menu.Juego.consola;

/**
 *
 * @author eci-toshiba
 */
public final class Coche extends Avatar{
    private int sinTurnoCoche;
    private int movementosRestantesCoche;
    
    public Coche(){
        super();
        this.sinTurnoCoche=0;
        this.movementosRestantesCoche=IValor.MOVRESTANTECOCHE;
    }

    public Coche(Tablero tablero, String nombreJug) throws PeticionIncorrectaException{
        super(tablero, nombreJug);
        this.sinTurnoCoche=0;
        this.movementosRestantesCoche=IValor.MOVRESTANTECOCHE;
    }
    
    //SETTERS AND GETTERS
    public int getMovementosRestantesCoche()                { return movementosRestantesCoche; }

    public void setMovementosRestantesCoche(int restantes)  { this.movementosRestantesCoche = restantes; }
    
    public int getSinTurnoCoche()                           { return sinTurnoCoche; }
    
    public void reducirSinTurnoCoche()                      { sinTurnoCoche--; }
    
    
    @Override
    public String cambiarModo(){
        if(movementosRestantesCoche<IValor.MOVRESTANTECOCHE){ return "\nNon podes cambiar o modo de movemento tras empregar o modo avanzado de coche neste turno."; }
        return super.cambiarModo();
    }
    
    @Override
    public String moverseEnAvanzado(int suma) throws PeticionIncorrectaException{
        if(suma<=4){
            int fila, posicionEnFila;
            int aux=getCasillaActual().getNumeroCasilla()-suma;
            getCasillaActual().getAvatares().remove(this);
            
            if(aux<0){ aux+=40; }                          //por si pasas para atras de saída     
            fila=(int) Math.floor(aux/10);                 //checkeamos la fila
            posicionEnFila=aux-10*fila;                    //checkeamo la casilla

            setCasillaActual(getTablero().getCasillas().get(fila).get(posicionEnFila));
            getCasillaActual().getAvatares().add(this);
            getTablero().setPodeLanzarDados(false);
            sinTurnoCoche=IValor.TURNOSQUIETOCOCHE;
            return (resolverMovemento(suma));
        }
        else{
            int fila, posicionEnFila;
            int aux=getCasillaActual().getNumeroCasilla()+suma;
            getCasillaActual().getAvatares().remove(this);
            
            if(aux>39){         
                aux-=40;
                getTablero().getLaBanca().pagar(getJugador(), IValor.VALORSALIDA);  
                consola.imprimir("Cobras "+IValor.VALORSALIDA+" ferrados por pasar pola saída.");
                getJugador().aumentaCobroPorCasillaDeSaida();
                aumentarNumeroVueltas();
            }                          
            fila=(int) Math.floor(aux/10);                 //checkeamos la fila
            posicionEnFila=aux-10*fila;                    //checkeamo la casilla

            setCasillaActual(getTablero().getCasillas().get(fila).get(posicionEnFila));
            this.getCasillaActual().getAvatares().add(this);
            movementosRestantesCoche--;
            if(movementosRestantesCoche!=0){getTablero().setPodeLanzarDados(true);}
            return (resolverMovemento(suma));
        }
    }
    
    @Override
    public boolean equals(Object coche){
        if(coche==null){return false;}
        if(coche instanceof Coche){return  super.getNombre().equals(((Coche) coche).getNombre());}
        return false;
    }
}
