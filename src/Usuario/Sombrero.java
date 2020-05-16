/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Usuario;

import PartesTablero.*;
import Excepciones.*;
import Interfaces.IValor;
import static Menu.Juego.consola;
import PartesCasilla.*;
import java.util.ArrayList;

/**
 *
 * @author eci-toshiba
 */
public final class Sombrero extends Avatar{
    private int movementosRestantesSombreiro;
    
    private Casilla casillaAnterior;
    private double bote;
    private double balance;
    private final ArrayList<Propiedad> propiedadesCompradas;
    private int vueltasAumentadas;
    private boolean puedeResetearTirada;
    
    public Sombrero() {
        super();
        this.puedeResetearTirada = false;
        this.vueltasAumentadas = 0;
        this.propiedadesCompradas = null;
        this.balance = 0;
        this.bote = 0;
        this.casillaAnterior = null;
        this.movementosRestantesSombreiro = IValor.MOVRESTANTESOMBREIRO;
    }

    public Sombrero(Tablero tablero, String nombreJug) throws PeticionIncorrectaException{
        super(tablero, nombreJug);
        this.puedeResetearTirada = false;
        this.vueltasAumentadas = 0;
        this.propiedadesCompradas = new ArrayList();
        this.balance = 0;
        this.bote = 0;
        this.casillaAnterior = null;
        this.movementosRestantesSombreiro = IValor.MOVRESTANTESOMBREIRO;
    }

    
    public int getMovementosRestantesSombreiro(){ return movementosRestantesSombreiro; }
    
    public void setMovementosRestantesSombreiro(int restantes){ this.movementosRestantesSombreiro = restantes; } 
    
    public Casilla getCasillaAnterior()         { return casillaAnterior; }

    public void setCasillaAnterior(Casilla casillaAnterior) { this.casillaAnterior = casillaAnterior; }

    public double getBote()     { return bote; }

    public void setBote(double bote)            { this.bote = bote; }

    public double getBalance()  { return balance; }

    public void setBalance(double balance)      { this.balance = balance; }

    public int getVueltasAumentadas()           { return vueltasAumentadas; }

    public void setVueltasAumentadas(int vueltasAumentadas) { this.vueltasAumentadas = vueltasAumentadas; }

    public ArrayList<Propiedad> getPropiedadesCompradas()   { return propiedadesCompradas; }
    
    public void resetAtributos(){
        balance=0;
        bote=0;
        casillaAnterior=getCasillaActual();
        for(int i=propiedadesCompradas.size();!propiedadesCompradas.isEmpty();i--){
            propiedadesCompradas.remove(i);
        }
        puedeResetearTirada=true;
    }
       
    @Override
    public String cambiarModo(){
        if(movementosRestantesSombreiro<IValor.MOVRESTANTESOMBREIRO){ return "\nNon podes cambiar o modo de movemento tras empregar o modo avanzado de sombreiro neste turno."; }
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
            boolean movArriba=true;
            
            if(getCasillaActual().getNumeroCasilla()>=30){
                movArriba=false;
            }
            for(int i=0;i<suma;i++){
                if(getCasillaActual().getNumeroCasilla()<=9){
                    getCasillaActual().getAvatares().remove(this);
                    setCasillaActual(getTablero().getCasillas().get(1).get(1));
                    this.getCasillaActual().getAvatares().add(this);
                    movArriba=true;
                }
                else if(getCasillaActual().getNumeroCasilla()<=29 && getCasillaActual().getNumeroCasilla()>=20){
                    getCasillaActual().getAvatares().remove(this);
                    setCasillaActual(getTablero().getCasillas().get(3).get(1));
                    this.getCasillaActual().getAvatares().add(this);
                    movArriba=false;
                }
                else if(getCasillaActual().getNumeroCasilla()>=10 && getCasillaActual().getNumeroCasilla()<=19){
                    if(movArriba){
                        int aux=49-getCasillaActual().getNumeroCasilla();
                        int fila=(int) Math.floor(aux/10);
                        getCasillaActual().getAvatares().remove(this);
                        setCasillaActual(getTablero().getCasillas().get(fila).get(aux-10*fila));
                        this.getCasillaActual().getAvatares().add(this);
                    }
                    else{
                        int aux=51-getCasillaActual().getNumeroCasilla();
                        if (aux==40){
                            getCasillaActual().getAvatares().remove(this);
                            setCasillaActual(getTablero().getCasillas().get(0).get(0));
                            this.getCasillaActual().getAvatares().add(this);
                            getTablero().getLaBanca().pagar(getJugador(), IValor.VALORSALIDA);  
                            consola.imprimir("Cobras "+IValor.VALORSALIDA+" ferrados por pasar pola saída.");
                            getJugador().aumentaCobroPorCasillaDeSaida();
                            balance+=IValor.VALORSALIDA;
                            vueltasAumentadas++;
                            aumentarNumeroVueltas();
                        }
                        else{
                            int fila=(int) Math.floor(aux/10);
                            getCasillaActual().getAvatares().remove(this);
                            setCasillaActual(getTablero().getCasillas().get(fila).get(aux-10*fila));
                            this.getCasillaActual().getAvatares().add(this);
                        }
                    }
                }
                else{
                    if(movArriba){
                        int aux=51-getCasillaActual().getNumeroCasilla();
                        int fila=(int) Math.floor(aux/10);
                        getCasillaActual().getAvatares().remove(this);
                        setCasillaActual(getTablero().getCasillas().get(fila).get(aux-10*fila));
                        this.getCasillaActual().getAvatares().add(this);
                    }
                    else{
                        int aux=49-getCasillaActual().getNumeroCasilla();
                        int fila=(int) Math.floor(aux/10);
                        getCasillaActual().getAvatares().remove(this);
                        setCasillaActual(getTablero().getCasillas().get(fila).get(aux-10*fila));
                        this.getCasillaActual().getAvatares().add(this);
                        if(getCasillaActual().getNumeroCasilla()==10){
                            movArriba=true;
                        }
                    }
                }
                consola.imprimir(resolverMovemento(suma));
                if(isEncarcelado()){break;}
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
                            propiedadesCompradas.add((Propiedad) getCasillaActual());
                            balance-=((Propiedad) getCasillaActual()).getValorActual();
                        }
                    }
                }
            }
            movementosRestantesSombreiro--;
            if(movementosRestantesSombreiro!=0){getTablero().setPodeLanzarDados(true);}
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
    public boolean equals(Object sombrero){
        if(sombrero==null){return false;}
        if(sombrero instanceof Sombrero){return  super.getNombre().equals(((Sombrero) sombrero).getNombre());}
        return false;
    }
}
