/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PartesCasilla;

import PartesTablero.Grupo;
import PartesTablero.Tablero;
import Interfaces.IValor;
import Excepciones.*;
import Usuario.*;
import java.util.HashMap;


/**
 *
 * @author eci-toshiba
 */
public abstract class Propiedad extends Casilla{
    
    private Jugador dueño;
    private final Grupo grupo;
    private final double valorInicial;
    private final double valorHipoteca;
    private boolean hipotecada;
    private double valorActual;
    private double valorAlquiler;
    private final HashMap<String, Integer> noPagarAlquiler;

    public Propiedad() {
        super();
        this.dueño = null;
        this.grupo=null;
        this.valorInicial = 0;
        this.valorHipoteca = 0;
        this.hipotecada = false;
        this.valorActual = 0;
        this.valorAlquiler = 0;
        this.noPagarAlquiler=null;
    }

    public Propiedad(double valorInicial, Tablero tablero, String nombre, Grupo grupo, int numeroCasilla) {
        super(tablero, nombre, numeroCasilla);
        this.dueño = tablero.getLaBanca();
        this.grupo=grupo;
        this.valorInicial = valorInicial;
        this.valorHipoteca = valorInicial/2;
        this.hipotecada = false;
        this.valorActual = valorInicial;
        this.valorAlquiler = 0;
        this.noPagarAlquiler=new HashMap<>();
    }
    
    //GETTERS AND SETTERS
    public Grupo getGrupo()         {return grupo;}
    
    public Jugador getDueño()       {return dueño;}

    public double getValorActual()  {return valorActual;}

    public double getValorAlquiler(){return valorAlquiler;}

    public double getValorInicial() {return valorInicial;}

    public double getValorHipoteca(){return valorHipoteca;}
    
    public boolean isHipotecada()   {boolean toret= hipotecada; return toret;}
     
    public void setHipotecada(boolean hipotecada)   {this.hipotecada = hipotecada;}
    
    public void setDueño(Jugador dueño)             {this.dueño = dueño;}

    public void setValorAlquiler(double valorAlquiler) {this.valorAlquiler = valorAlquiler;}

    public void setValorActual(double valorActual)  {if(valorActual>=0)this.valorActual = valorActual;}

    public HashMap<String, Integer> getNoPagarAlquiler(){return noPagarAlquiler;}
    
    //METODOS
    public boolean perteneceAJugador(Jugador jugador){
        return jugador.equals(dueño);
    }
    
    public abstract void alquiler();
    
    public abstract void resetAlquiler();
    
    public void valor(){
        this.setValorActual(valorInicial);
    }
    
    public String comprar() throws DatoIncorrectoException, NonFerradosException{
        Jugador jug = getTablero().getJugadores().get(getTablero().getTurnoJugador());
        if(!this.equals(jug.getAvatar().getCasillaActual())){throw new DatoIncorrectoException("Para poder comprar unha propiedade debes estar nela.");}
        if(!dueño.equals(getTablero().getLaBanca())){throw new DatoIncorrectoException("Esta propiedade xa ten dono: "+dueño.getNombre()+".");}
        if((jug.getAvatar() instanceof Coche)&& getTablero().isComprouseCoche()){throw new DatoIncorrectoException("Non podes comprar mais propiedades este turno.");}
        if(jug.getDineroActual()>=valorActual){
            jug.aumentaCartosInvertidos(valorActual);   //estadísticas
            dueño=jug;
            jug.getPropiedades().add(this);
            if(getTablero().getJugadores().get(getTablero().getTurnoJugador()).getAvatar() instanceof Coche &&
              ((Coche)getTablero().getJugadores().get(getTablero().getTurnoJugador()).getAvatar()).getMovementosRestantesCoche()<IValor.MOVRESTANTECOCHE){
                getTablero().setComprouseCoche(true);}
            ///////////////////////
            if(getTablero().getJugadores().get(getTablero().getTurnoJugador()).getAvatar() instanceof Esfinge){
                ((Esfinge) getTablero().getJugadores().get(getTablero().getTurnoJugador()).getAvatar()).getPropiedadesCompradas().add(this);
            }
            if(getTablero().getJugadores().get(getTablero().getTurnoJugador()).getAvatar() instanceof Sombrero){
                ((Sombrero) getTablero().getJugadores().get(getTablero().getTurnoJugador()).getAvatar()).getPropiedadesCompradas().add(this);
            }
            return (jug.pagar(getTablero().getLaBanca(), valorActual)+"\nCasilla comprada."); 
        }
        throw new NonFerradosException("comprar "+nombreAColor() +", fáltanche "+(valorActual-jug.getDineroActual())+" Ferrados.");
    }
    
    public String hipotecar(){
        hipotecada=true;
        dueño.getHipotecas().add(this);
        dueño.cobrar(valorHipoteca);
        dueño.aumentaCartosInvertidos(valorHipoteca);
        return ("\nPropiedade hipotecada, gañas "+ valorHipoteca +" ferrados."+dueño.toString());
    }
    
    public String deshipotecar() throws DatoIncorrectoException{
        hipotecada=false;
        dueño.getHipotecas().remove(this);
        return(("Propiedade deshipotecada") + dueño.pagar(getTablero().getLaBanca(),valorHipoteca*1.1) + dueño.toString());
    }
    
    public void cambiarDueño(Jugador jug){
        dueño.getPropiedades().remove(this);
        this.setDueño(jug);
        jug.getPropiedades().add(this);
    }
    
    
    //OVERRIDES
    @Override
    public String nombreAColor() { return grupo.getColor() + super.getNombre() + IValor.RESET; }
    
    @Override
    public void aumentaRentabilidade(double rentabilidade) { 
        super.aumentaRentabilidade(rentabilidade);
        grupo.aumentaRentabilidade(rentabilidade);
    }

    @Override
    public String toString(){           
        String toret;
        StringBuilder aux=new StringBuilder();
        
        aux.append(super.toString());
        if(!dueño.equals(super.getTablero().getLaBanca())){
            aux.append(",\nDono: ").append(dueño.getNombre());
        }  //Imprime Dueño si no es la banca
        else {aux.append(",\nDono: Sen dono actual");}
        aux.append(",\nGrupo: ").append(grupo.getNombre());
        aux.append(",\nPrezo de Compra: ").append(valorActual);
        if(!hipotecada){aux.append(",\nValor de Hipoteca: ").append(valorHipoteca);}
        else{aux.append(",\nCusto de Deshipotecar: ").append(valorHipoteca*IValor.INTERESDESHIPOTECAR);}
        
        
        toret=aux.toString();
        return toret;
    }  
 
}

//R.I.P. int numeroDeCasillasDelMismoGrupoPoseidasPorEsteMismoJugadorEnEsteMismoInstanteDeTiempo
//Siempre en nuestros corazones.
//Nunca variable mas específica existió.