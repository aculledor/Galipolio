/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Usuario;

import PartesTablero.Tablero;
import Interfaces.IValor;
import Excepciones.*;
import PartesCasilla.*;
import static Menu.Juego.consola;
/**
 *
 * @author eci-toshiba
 */
public final class Pelota extends Avatar{

    //CONSTRUCTORES
    public Pelota() { super(); }

    public Pelota(Tablero tablero, String nombreJug) throws PeticionIncorrectaException{ 
        super(tablero, nombreJug); 
    }
    
    //OVERRIDES
    @Override
    public String moverseEnAvanzado(int suma) throws PeticionIncorrectaException{
        if(suma<=4){
            int aux;
            int valorAnterior=0;
            for(int i=1;i<=suma;i++){
                if((i%2==1)||(i==suma)){
                    int fila, posicionEnFila;
                    aux=getCasillaActual().getNumeroCasilla()-(i-valorAnterior);
                    valorAnterior=i;
                    getCasillaActual().getAvatares().remove(this);
                    if(aux<0){         
                        aux+=40;
                    }                           
                    fila=(int) Math.floor(aux/10);                 //checkeamos la fila
                    posicionEnFila=aux-10*fila;                                 //checkeamo la casilla
                    setCasillaActual(getTablero().getCasillas().get(fila).get(posicionEnFila));
                    this.getCasillaActual().getAvatares().add(this);
                    consola.imprimir(resolverMovemento(suma));
                    if(isEncarcelado()){return "";}
                }
            }
        }
        else{
            int aux;
            int valorAnterior=0;
            for(int i=5;i<=suma;i++){
                if((i%2==1)||(i==suma)){
                    int fila, posicionEnFila;
                    aux=getCasillaActual().getNumeroCasilla()+(i-valorAnterior);
                    valorAnterior=i;
                    getCasillaActual().getAvatares().remove(this);
                    if(aux>39){         
                        aux-=40;
                        getTablero().getLaBanca().pagar(getJugador(), IValor.VALORSALIDA);     //NON FAI FALTA IMPRIMIR
                        consola.imprimir("Cobras "+IValor.VALORSALIDA+" ferrados por pasar pola saída.");
                        getJugador().aumentaCobroPorCasillaDeSaida();
                        aumentarNumeroVueltas();
                    }                          
                    fila=(int) Math.floor(aux/10);                 //checkeamos la fila
                    posicionEnFila=aux-10*fila;                                 //checkeamo la casilla
                    setCasillaActual(getTablero().getCasillas().get(fila).get(posicionEnFila));
                    this.getCasillaActual().getAvatares().add(this);
                    consola.imprimir(resolverMovemento(suma));
                    if(isEncarcelado()){return "";}
                    if(getCasillaActual() instanceof Propiedad){
                        if(((Propiedad)getCasillaActual()).getDueño().equals(getTablero().getLaBanca()) && 
                                getJugador().getDineroActual()>= ((Propiedad)getCasillaActual()).getValorActual()){
                            consola.imprimir(getJugador().toString()+
                                    "\n"+getCasillaActual().toString());
                            String comando;
                            do{
                                comando=consola.leer("\nQueres comprar a propiedade?(Si/Non) Se non, seguiraste movendo."); 
                            }while(!comando.equalsIgnoreCase("si")&&!comando.equalsIgnoreCase("non"));
                            if(comando.equalsIgnoreCase("si")){
                                consola.imprimir(((Propiedad)getCasillaActual()).comprar());
                            }
                        }
                    }
                }
            }
            return "Acabouse o teu movemento\n";
        }
        return "";
    }
    
    @Override
    public boolean equals(Object pelota){
        if(pelota==null){return false;}
        if(pelota instanceof Pelota){return  super.getNombre().equals(((Pelota) pelota).getNombre());}
        return false;
    }   
}
