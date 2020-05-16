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
import java.util.ArrayList;
import java.util.Random;
import static Menu.Juego.consola;
/**
 *
 * @author eci-toshiba
 */
public abstract class Avatar {
    private final Jugador jugador;
    private final Tablero tablero; 
    private final String nombre;
    private Casilla casillaActual;
    
    private boolean modoAvanzado;
    private boolean encarcelado;
    private int numTurnosCarcel;
    private int numeroVueltas;
   
    public Avatar(){
        this.jugador=null;
        this.tablero=null;
        this.casillaActual=null;
        this.modoAvanzado=false;
        this.nombre = null;
        this.encarcelado=false;
        this.numTurnosCarcel=0;
        this.numeroVueltas=0;
    }

    public Avatar(Tablero tablero, String nombreJug) throws NonTipoCasillaCorrectoException, DatoIncorrectoException{
        this.tablero=tablero;
        this.jugador=new Jugador(nombreJug,this,tablero);
        this.modoAvanzado=false;
        this.casillaActual=tablero.buscarCasilla("saida");
        this.encarcelado = false;
        this.numTurnosCarcel=0;
        this.numeroVueltas=0;
        Random rn=new Random();
        boolean exit;
        String auxNombre;
        do{
            exit=true;
            char aux = (char) (rn.nextInt(126-34)+33);
            auxNombre= "" +aux ;
            for (Avatar auxiliar : this.casillaActual.getAvatares()) {
                if(!(this.casillaActual.getAvatares().isEmpty())  && auxiliar.getNombre().equals(auxNombre)){
                    exit=false;
                }
            }
        }while(!exit);
        this.nombre=auxNombre;
        this.casillaActual.getAvatares().add(this);
    }
    
    //GETTERS Y SETTERS
    public String getNombre()       {return nombre;}

    public Casilla getCasillaActual(){return casillaActual;}

    public Jugador getJugador()     {return jugador;}

    public Tablero getTablero()     { return tablero; }

    public int getNumTurnosCarcel() {return numTurnosCarcel;}

    public int getNumeroVueltas()   {return numeroVueltas;}

    public boolean isEncarcelado()  {return encarcelado;}

    public boolean isModoAvanzado() {return modoAvanzado;}

    public void setModoAvanzado(boolean modoAvanzado)   {this.modoAvanzado = modoAvanzado;}

    public void setEncarcelado(boolean encarcelado)     {this.encarcelado = encarcelado;}
    
    public void setNumTurnosCarcel(int numTurnosCarcel) {if(numTurnosCarcel>=0&&numTurnosCarcel<=4)this.numTurnosCarcel = numTurnosCarcel;}    

    public void setNumeroVueltas(int numeroVueltas)     {this.numeroVueltas = numeroVueltas;} 

    public void setCasillaActual(Casilla proximaCasilla){if(!proximaCasilla.equals(this.casillaActual))this.casillaActual = proximaCasilla;}
    
    //METODOS
    
    public void aumentarNumTurnosCarcel()   {this.numTurnosCarcel++;}    

    public void aumentarNumeroVueltas()     {this.numeroVueltas++;}
    
    public void reducirNumeroVueltas()      {this.numeroVueltas--;}  

    public String cambiarModo(){
        modoAvanzado=(!modoAvanzado);
        if(modoAvanzado){ return "\nModo cambiado.\nModo actual: Modo avanzado.\n";}
        return "\nModo cambiado.\nModo actual: Modo normal.";
    }
    
    public String irCarcel() throws NonTipoCasillaCorrectoException, DatoIncorrectoException{     
        casillaActual.getAvatares().remove(this);
        casillaActual=tablero.buscarCasilla("carcere");
        casillaActual.getAvatares().add(this);
        encarcelado=true;
        jugador.aumentaVecesNoCarcere();
        return ("Vas ao cárcere!!\nTermina o teu turno.\n"+tablero.toString()+tablero.resetTurno(false));
    }
    
    public String moverse(int suma)throws PeticionIncorrectaException{
        if(!isModoAvanzado()){ return moverseEnBasico(suma);}
        return moverseEnAvanzado(suma);
    }
    
    public abstract String moverseEnAvanzado(int suma) throws PeticionIncorrectaException;
    
    public String moverseEnBasico(int suma) throws PeticionIncorrectaException{
        int fila, posicionEnFila;
        int aux=casillaActual.getNumeroCasilla()+suma;
        String toret="";
        casillaActual.getAvatares().remove(this);

        if(aux>39){         
            aux-=40;
            tablero.getLaBanca().pagar(jugador, IValor.VALORSALIDA);     
            toret+=("Cobras "+IValor.VALORSALIDA+" ferrados por pasar pola saída.\n");
            jugador.aumentaCobroPorCasillaDeSaida();
            numeroVueltas++;
        }                          
        fila=(int) Math.floor(aux/10);                 //checkeamos la fila
        posicionEnFila=aux-10*fila;                    //checkeamo la casilla

        setCasillaActual(tablero.getCasillas().get(fila).get(posicionEnFila));
        casillaActual.getAvatares().add(this);
        return toret+=resolverMovemento(suma);
    }    
    
    public String resolverMovemento(int suma) throws PeticionIncorrectaException{ 
        String toret="\nAvanzas ata a casilla "+casillaActual.nombreAColor()+".\n";
        casillaActual.aumentaVecesPisada();
        
        if(casillaActual instanceof Especial)       { return toret; }
        else if(casillaActual instanceof Impuesto)  {
            double imposto = IValor.VALORIMPOSTOBARATO;
            if(casillaActual.getNombre().equalsIgnoreCase("imposto")){ imposto=IValor.VALORIMPOSTOCARO; }
            jugador.aumentaPagoImpostosTasas(imposto);
            casillaActual.aumentaRentabilidade(imposto);
            ((Accion) tablero.buscarCasilla("parking")).aumentaBote(imposto); 
            return toret + jugador.pagar(tablero.getLaBanca(), imposto)+"\n";   
        }
        else if(casillaActual instanceof Accion){
            if(casillaActual.getNombre().equalsIgnoreCase("ir ao carcere")){ return toret + irCarcel(); }
            else if(casillaActual.getNombre().equalsIgnoreCase("parking")){ 
                jugador.aumentaPremiosInversionsOuBote(((Accion) casillaActual).getBote());
                tablero.getLaBanca().pagar(jugador, ((Accion) casillaActual).getBote());   
                toret+="Cobras todo o acumulado no bote, que suma: "+((Accion) casillaActual).getBote()+" ferrados!\n";
                ((Accion)casillaActual).setBote(0);
                return (toret);
            }
            else if(casillaActual instanceof CasillaComunidade){
                int eleccion=0;
                ArrayList<Integer> mezcla;
                //Barajar las cartas
                mezcla=tablero.barajarCartas("cajacomunidad");
                consola.imprimir(toret+"\nTes que coller unha carta de Comunidade.");
                //CAMBIAR
                do{
                    try{
                        eleccion = Integer.parseInt(consola.leer("Escolle unha carta da 1 á "+tablero.getCartasComunidade().size() +":"));
                    }catch(NumberFormatException parseIntNoNumException){
                        consola.imprimirErro("Debes introducir un NÚMERO!!!!!!\n");
                    } 
                }while(eleccion<1 || eleccion>tablero.getCartasComunidade().size());
                consola.imprimir(tablero.getCartasComunidade().get((mezcla.get(eleccion-1))-1).toString());
                tablero.getCartasComunidade().get((mezcla.get(eleccion-1))-1).accion(jugador);
                return "\n";
            }
            else if(casillaActual instanceof CasillaSorte){
                int eleccion = 0;
                ArrayList<Integer> mezcla;
                //Barajar las cartas
                mezcla=tablero.barajarCartas("suerte");
                consola.imprimir(toret+"\nTes que coller unha carta de Sorte.");
                //CAMBIAR
                do{
                    try{
                        eleccion = Integer.parseInt(consola.leer("Escolle unha carta da 1 á "+tablero.getCartasSorte().size() +":"));
                    }catch(NumberFormatException parseIntNoNumException){
                        consola.imprimirErro("Debes introducir un NÚMERO!!!!!!\n");
                    } 
                }while(eleccion<1 || eleccion>tablero.getCartasSorte().size());
                consola.imprimir(tablero.getCartasSorte().get((mezcla.get(eleccion-1))-1).toString());
                tablero.getCartasSorte().get((mezcla.get(eleccion-1))-1).accion(jugador);
                return "\n";    
            }
            else{ return toret+"\n Este mensaxe non se debería estar imprimindo.(Source: Avatar.resolverMovemento())"; }
        }
        else {
            if((casillaActual instanceof Solar)&&((Propiedad)casillaActual).getDueño().equals(jugador)){ 
                ((Solar)casillaActual).aumentaVecesPisadaDono(); return toret; 
            }
            if(((Propiedad) casillaActual).getNoPagarAlquiler().containsKey(jugador.getNombre())){
                ((Propiedad) casillaActual).getNoPagarAlquiler().replace(jugador.getNombre(), ((Propiedad) casillaActual).getNoPagarAlquiler().get(jugador.getNombre())-1);
                if(((Propiedad) casillaActual).getNoPagarAlquiler().get(jugador.getNombre())==0){
                    ((Propiedad) casillaActual).getNoPagarAlquiler().remove(jugador.getNombre());
                }
                return toret;
            }
            else if(!((Propiedad)casillaActual).getDueño().equals(tablero.getLaBanca()) && 
                    !((Propiedad)casillaActual).getDueño().equals(jugador) 
                    && !((Propiedad)casillaActual).isHipotecada()){ 
                        if((casillaActual instanceof Servicio)){  
                            jugador.aumentaPagoDeAlquileres(((Propiedad)casillaActual).getValorAlquiler(), ((Propiedad)casillaActual).getDueño());
                            return toret+(jugador.pagar(((Propiedad)casillaActual).getDueño(), ((Propiedad)casillaActual).getValorAlquiler()*suma));
                        }else{ 
                        jugador.aumentaPagoDeAlquileres(((Propiedad)casillaActual).getValorAlquiler(), ((Propiedad)casillaActual).getDueño());
                        return toret+(jugador.pagar(((Propiedad)casillaActual).getDueño(), ((Propiedad)casillaActual).getValorAlquiler())); 
                        }
            }
            else{ return toret;}
        }
    }    
    
    public String irACasilla(Casilla destino) throws DatoIncorrectoException{
        String toret="";
        if(casillaActual.getNumeroCasilla()>destino.getNumeroCasilla()){
            toret+=(tablero.getLaBanca().pagar(jugador, IValor.VALORSALIDA));
            jugador.aumentaCobroPorCasillaDeSaida();
            numeroVueltas++;
        }
        casillaActual.getAvatares().remove(this);
        setCasillaActual(destino);
        casillaActual.getAvatares().add(this);
        casillaActual.aumentaVecesPisada();
        if(casillaActual instanceof Propiedad){
            if(((Propiedad) casillaActual).getDueño()!=tablero.getLaBanca() && !((Propiedad) casillaActual).getDueño().equals(jugador) 
                    && !((Propiedad) casillaActual).isHipotecada()){ 
                jugador.aumentaPagoDeAlquileres(((Propiedad) casillaActual).getValorAlquiler(), ((Propiedad) casillaActual).getDueño());
                return toret+(jugador.pagar(((Propiedad) casillaActual).getDueño(), ((Propiedad) casillaActual).getValorAlquiler()));
            }
        }
        return "";
    }
    
    //OVERRIDES
    @Override
    public String toString(){           
        String toret;
        StringBuilder aux=new StringBuilder();
        
        aux.append("{\nNome: ").append(nombre).
            append(",\nTipo: ").append(this.getClass().getSimpleName());
        aux.append(",\nCasilla: ").append(casillaActual.nombreAColor());
        if(isEncarcelado()){aux.append(" -Está preso!!!");}
        aux.append(",\nXogador: ").append(jugador.getNombre()).
        append(",\nModoAvanzado: ").append(modoAvanzado).    
        append("\n}");
                    
        toret=aux.toString();
        return toret;
    }
    
}
