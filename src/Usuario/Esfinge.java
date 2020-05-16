/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Usuario;

import Excepciones.*;
import Interfaces.IValor;
import static Menu.Juego.consola;
import PartesTablero.Tablero;
import PartesCasilla.*;
import java.util.ArrayList;

/**
 *
 * @author eci-toshiba
 */
public final class Esfinge extends Avatar{
    private int movementosRestantesEsfinxe;
    
    private Casilla casillaAnterior;
    private double bote;
    private double balance;
    private final ArrayList<Propiedad> propiedadesCompradas;
    private int vueltasAumentadas;
    private boolean puedeResetearTirada;

    public Esfinge() {
        super();
        this.puedeResetearTirada = false;
        this.vueltasAumentadas = 0;
        this.propiedadesCompradas = null;
        this.balance = 0;
        this.bote = 0;
        this.casillaAnterior = null;
        this.movementosRestantesEsfinxe = IValor.MOVRESTANTEESFINXE;
    }

    public Esfinge(Tablero tablero, String nombreJug) throws PeticionIncorrectaException{
        super(tablero, nombreJug);
        this.puedeResetearTirada = false;
        this.vueltasAumentadas = 0;
        this.propiedadesCompradas = new ArrayList();
        this.balance = 0;
        this.bote = 0;
        this.casillaAnterior = null;
        this.movementosRestantesEsfinxe = IValor.MOVRESTANTEESFINXE;
    }

    public int getMovementosRestantesEsfinxe()              { return movementosRestantesEsfinxe; }
    
    public void setMovementosRestantesEsfinxe(int restantes){ this.movementosRestantesEsfinxe = restantes; } 

    public Casilla getCasillaAnterior()     { return casillaAnterior; }

    public void setCasillaAnterior(Casilla casillaAnterior) { this.casillaAnterior = casillaAnterior; }

    public double getBote()                 { return bote; }

    public void setBote(double bote)        { this.bote = bote; }

    public double getBalance()              { return balance; }

    public void setBalance(double balance)  { this.balance = balance; }

    public int getVueltasAumentadas()       { return vueltasAumentadas; }

    public void setVueltasAumentadas(int vueltasAumentadas) { this.vueltasAumentadas = vueltasAumentadas; }

    public ArrayList<Propiedad> getPropiedadesCompradas()   { return propiedadesCompradas; }
    
    public void resetAtributos(){
        balance=0;
        bote=0;
        casillaAnterior=getCasillaActual();
        for(int i=propiedadesCompradas.size()-1;!propiedadesCompradas.isEmpty();i--){
            propiedadesCompradas.remove(i);
        }
        puedeResetearTirada=true;
    }
    
    @Override
    public String cambiarModo(){
        if(movementosRestantesEsfinxe<IValor.MOVRESTANTEESFINXE){ return "\nNon podes cambiar o modo de movemento tras empregar o modo avanzado de esfinxe neste turno."; }
        return super.cambiarModo();
    }
    
    @Override
    public String moverseEnBasico(int suma) throws PeticionIncorrectaException{
        resetAtributos();
        if(getCasillaActual().getNumeroCasilla()+suma>=40){
            balance+=IValor.VALORSALIDA;
            vueltasAumentadas++;
        }
        return super.moverseEnBasico(suma);
    }
    
    
    @Override
    public String moverseEnAvanzado(int suma) throws PeticionIncorrectaException{
        if(suma<=4){
            if(puedeResetearTirada){
                consola.imprimir("\nDesfaise o que fixeches na tirada anterior!!\n");
                getTablero().setPodeLanzarDados(false);

                getCasillaActual().getAvatares().remove(this);
                setCasillaActual(getTablero().buscarCasilla(casillaAnterior.getNombre()));
                this.getCasillaActual().getAvatares().add(this);
                consola.imprimir("Volves a "+casillaAnterior.getNombre()+".\n");

                ((Accion) getTablero().buscarCasilla("parking")).aumentaBote(bote);

                if(balance>0){ 
                    consola.imprimir(getJugador().pagar(getTablero().getLaBanca(), balance)+"\n"); 
                    getJugador().aumentaDineroGastado(balance);
                }
                
                else if(balance<0){ 
                    consola.imprimir(getTablero().getLaBanca().pagar(getJugador(), balance*(-1))+"\n"); 
                    getJugador().aumentaPremiosInversionsOuBote(balance*(-1));
                }

                for(Propiedad p:propiedadesCompradas){
                    if(p.getDueño().equals(getJugador())){
                        if(p instanceof Solar && !((Solar) p).getEdificios().isEmpty()){
                            for(int i=((Solar) p).getEdificios().size();i>0;i--){ ((Solar) p).getEdificios().get(i-1).eliminarEdificio(); }
                            consola.imprimir("Elimínanse os edificios de "+p.getNombre()+".\n");
                        }
                        if(p.isHipotecada()){
                            p.setHipotecada(false);
                        }
                        p.setDueño(getTablero().getLaBanca());
                        getJugador().getPropiedades().remove(p);
                        consola.imprimir(p.getNombre()+" pasa a pertencer á Banca de novo.\n");
                    }
                }

                for(int i=0;i<vueltasAumentadas;i++){reducirNumeroVueltas();}
                resetAtributos();
                puedeResetearTirada=false;
            }
            else{ return moverseEnBasico(suma); }
        }
        else{
            resetAtributos();
            boolean movIzquierda=true;
            
            if(getCasillaActual().getNumeroCasilla()<30 && getCasillaActual().getNumeroCasilla()>19){ movIzquierda=false; }
            
            for(int i=0;i<suma;i++){
                if(getCasillaActual().getNumeroCasilla()>9 && getCasillaActual().getNumeroCasilla()<20){
                    getCasillaActual().getAvatares().remove(this);
                    setCasillaActual(getTablero().getCasillas().get(2).get(1));
                    this.getCasillaActual().getAvatares().add(this);
                    movIzquierda=false;
                }
                else if(getCasillaActual().getNumeroCasilla()>29){
                    getCasillaActual().getAvatares().remove(this);
                    setCasillaActual(getTablero().getCasillas().get(0).get(1));
                    this.getCasillaActual().getAvatares().add(this);
                    getTablero().getLaBanca().pagar(getJugador(), IValor.VALORSALIDA);  
                    consola.imprimir("Cobras "+IValor.VALORSALIDA+" ferrados por pasar pola saída.");
                    getJugador().aumentaCobroPorCasillaDeSaida();
                    balance+=IValor.VALORSALIDA;
                    vueltasAumentadas++;
                    aumentarNumeroVueltas();
                    movIzquierda=true;
                }
                else if(getCasillaActual().getNumeroCasilla()<10){
                    if(movIzquierda){
                        int aux=9-getCasillaActual().getNumeroCasilla();
                        getCasillaActual().getAvatares().remove(this);
                        setCasillaActual(getTablero().getCasillas().get(2).get(aux));
                        this.getCasillaActual().getAvatares().add(this);
                        if(getCasillaActual().getNumeroCasilla()==20){
                            movIzquierda=false;
                        }
                    }
                    else{
                        int aux=31-getCasillaActual().getNumeroCasilla();
                        int fila=(int) Math.floor(aux/10);
                        getCasillaActual().getAvatares().remove(this);
                        setCasillaActual(getTablero().getCasillas().get(fila).get(aux-10*fila));
                        this.getCasillaActual().getAvatares().add(this);
                    }
                }
                else{
                    if(movIzquierda){
                        int aux=31-getCasillaActual().getNumeroCasilla();
                        int fila=(int) Math.floor(aux/10);
                        getCasillaActual().getAvatares().remove(this);
                        setCasillaActual(getTablero().getCasillas().get(fila).get(aux-10*fila));
                        this.getCasillaActual().getAvatares().add(this);
                    }
                    else{
                        int aux=29-getCasillaActual().getNumeroCasilla();
                        getCasillaActual().getAvatares().remove(this);
                        setCasillaActual(getTablero().getCasillas().get(0).get(aux));
                        this.getCasillaActual().getAvatares().add(this);
                        if(getCasillaActual().getNumeroCasilla()==0){
                            getTablero().getLaBanca().pagar(getJugador(), IValor.VALORSALIDA);  
                            consola.imprimir("Cobras "+IValor.VALORSALIDA+" ferrados por pasar pola saída.");
                            getJugador().aumentaCobroPorCasillaDeSaida();
                            balance+=IValor.VALORSALIDA;
                            vueltasAumentadas++;
                            aumentarNumeroVueltas();
                            movIzquierda=true;
                        }
                    }
                }
                consola.imprimir(resolverMovemento(suma));
                if(isEncarcelado()){break;}
                if(getCasillaActual() instanceof Propiedad){
                    if(((Propiedad)getCasillaActual()).getDueño().equals(getTablero().getLaBanca()) && 
                            getJugador().getDineroActual()> ((Propiedad)getCasillaActual()).getValorActual()){
                        consola.imprimir(getJugador().toString()+
                                "\n"+getCasillaActual().toString());
                        String comando;
                        do{
                            comando=consola.leer("\nQueres comprar a propiedade?(Si/Non) Se non, seguiraste movendo."); 
                        }while(!comando.equalsIgnoreCase("si")&&!comando.equalsIgnoreCase("non"));
                        if(comando.equalsIgnoreCase("si")){
                            consola.imprimir(((Propiedad)getCasillaActual()).comprar());
                            propiedadesCompradas.add((Propiedad) getCasillaActual());
                            balance-=((Propiedad) getCasillaActual()).getValorActual();
                        }
                    }
                }
            }
            movementosRestantesEsfinxe--;
            if(movementosRestantesEsfinxe!=0){getTablero().setPodeLanzarDados(true);}
        }
        if(!isEncarcelado()){return "Acabouse o teu movemento\n\n";}
        else{return "";}
    }
       
    @Override
    public String resolverMovemento(int suma) throws PeticionIncorrectaException{ 
        if(getCasillaActual() instanceof Impuesto)  {
            balance-=(getCasillaActual().getNombre().equalsIgnoreCase("imposto"))?  IValor.VALORIMPOSTOCARO:IValor.VALORIMPOSTOBARATO; 
            return super.resolverMovemento(suma);   
        }
        else if(getCasillaActual() instanceof Especial){
            return super.resolverMovemento(suma);
        }
        else if(getCasillaActual() instanceof Accion){
            if(getCasillaActual().getNombre().equalsIgnoreCase("parking")){ 
                bote=((Accion) getCasillaActual()).getBote();
                return super.resolverMovemento(suma);
            }
            else{ return super.resolverMovemento(suma); }
        }
        else {
            if(!((Propiedad)getCasillaActual()).getDueño().equals(getTablero().getLaBanca()) && 
                !((Propiedad)getCasillaActual()).getDueño().equals(getJugador()) && !((Propiedad)getCasillaActual()).isHipotecada()){ 
                balance-= ((getCasillaActual() instanceof Servicio))? ((Propiedad)getCasillaActual()).getValorAlquiler()*suma :((Propiedad)getCasillaActual()).getValorAlquiler();
                return super.resolverMovemento(suma);
            }
            else{ return super.resolverMovemento(suma);}
        }
    }    
    
    @Override
    public String irACasilla(Casilla destino) throws DatoIncorrectoException{
        String toret="";
        if(getCasillaActual().getNumeroCasilla()>destino.getNumeroCasilla()){
            toret+=(getTablero().getLaBanca().pagar(getJugador(), IValor.VALORSALIDA));
            getJugador().aumentaCobroPorCasillaDeSaida();
            balance+=IValor.VALORSALIDA;
            aumentarNumeroVueltas();
            vueltasAumentadas++;
        }
        getCasillaActual().getAvatares().remove(this);
        setCasillaActual(destino);
        getCasillaActual().getAvatares().add(this);
        getCasillaActual().aumentaVecesPisada();
        if(getCasillaActual() instanceof Propiedad){
            if(((Propiedad) getCasillaActual()).getDueño()!=getTablero().getLaBanca() && !((Propiedad) getCasillaActual()).getDueño().equals(getJugador()) 
                    && !((Propiedad) getCasillaActual()).isHipotecada()){ 
                getJugador().aumentaPagoDeAlquileres(((Propiedad) getCasillaActual()).getValorAlquiler(), ((Propiedad) getCasillaActual()).getDueño());
                return toret+(getJugador().pagar(((Propiedad) getCasillaActual()).getDueño(), ((Propiedad) getCasillaActual()).getValorAlquiler()));
            }
        }
        return "";
    }

    @Override
    public boolean equals(Object esfinxe){
        if(esfinxe==null){return false;}
        if(esfinxe instanceof Esfinge){return  super.getNombre().equals(((Esfinge) esfinxe).getNombre());}
        return false;
    }
}
