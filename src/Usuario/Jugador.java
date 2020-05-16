/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Usuario;
import PartesTablero.*;
import Interfaces.IValor;
import java.util.ArrayList;
import Edificaciones.*;
import Excepciones.*;
import PartesCasilla.*;

import static Menu.Juego.consola;
import static Menu.Juego.submenu;

/**
 *
 * @author lois.rego
 */
public final class Jugador implements IValor{
    //ATRIBUTOS
    private final Tablero tablero;
    private final Avatar avatar;
    private final Dados dados;
    
    private final String nombre;
    private double dineroActual;
    private double dineroGastado;
    private ArrayList<Propiedad> propiedades;
    private ArrayList<Propiedad> hipotecas;
    private ArrayList<Edificio> edificios;
    private final ArrayList<Trato> tratosPropuestos;
    private final ArrayList<Trato> tratosPendientes;
    //ESTADISTICAS
    private double cartosInvertidos;        //gastado en compara casillas ou edificios      LISTO
    private double pagoDeAlquileres;        //pago alquileres e impostos                    LISTO
    private double cobroDeAlquileres;       //cobro de alquileres
    private double pagoImpostosTasas;       //pago de impostos ou tasas
    private double cobroPorCasillaDeSaida;  //ganado da saída
    private double premiosInversionsOuBote; //cobro de alquileres
    private int    vecesNoCarcere;    //veces que se caeu no cacere
    private int    numTiradasDados;  //Veces que tirou os dados        
    
    //CONSTRUCTORES
    
    public Jugador(){
        this.tratosPendientes = new ArrayList<>();
        this.tratosPropuestos = new ArrayList<>();
        this.tablero=null;
        this.avatar=null;
        this.dados=null;
        this.nombre=null;
        this.dineroActual=0;
        this.dineroGastado=0;
        this.propiedades=null;
        this.hipotecas=null;
        this.edificios=null;
        this.pagoDeAlquileres=0;
        this.cobroDeAlquileres=0;
        this.cartosInvertidos=0;
        this.pagoImpostosTasas=0;
        this.cobroPorCasillaDeSaida=0;
        this.premiosInversionsOuBote=0;
        this.vecesNoCarcere=0;
        this.numTiradasDados = 0;
    }

    public Jugador(String nombre, Avatar avatar, Tablero tablero) {
        this.tratosPendientes = new ArrayList<>();
        this.tratosPropuestos = new ArrayList<>();
        this.tablero=tablero;
        this.avatar=avatar;
        this.dados=new Dados();
        this.nombre=nombre;
        this.dineroActual=IValor.CAPITALINICIAL;
        this.dineroGastado=0;
        this.propiedades=new ArrayList<>();
        this.hipotecas=new ArrayList<>();
        this.edificios=new ArrayList<>();
        this.pagoDeAlquileres=0;
        this.cobroDeAlquileres=0;
        this.cartosInvertidos=0;
        this.pagoImpostosTasas=0;
        this.cobroPorCasillaDeSaida=0;
        this.premiosInversionsOuBote=0;
        this.vecesNoCarcere=0;   
        this.numTiradasDados = 0;
    }
    
    public Jugador(Tablero tablero){ 
        //BANCA 
        this.tratosPendientes = null;
        this.tratosPropuestos = null;
        this.tablero=tablero;   
        this.avatar=null;
        this.dados=null;
        this.nombre="Banca";
        this.dineroActual=999999999;
        this.dineroGastado=0;
        this.propiedades=new ArrayList<>();
        this.hipotecas=null;
        this.edificios=null;
        this.pagoDeAlquileres=0;
        this.cobroDeAlquileres=0;
        this.cartosInvertidos=0;
        this.pagoImpostosTasas=0;
        this.cobroPorCasillaDeSaida=0;
        this.premiosInversionsOuBote=0;
        this.vecesNoCarcere=0;  
        this.numTiradasDados = 0;
    }
    
    
    //GETTERS AND SETTERS
    public String getNombre() {return nombre;}

    public Avatar getAvatar() {return avatar;}
    
    public double getDineroActual() {double toret= dineroActual; return toret;}

    public Dados getDados() {return dados;}     

    public ArrayList<Propiedad> getHipotecas() {return hipotecas;}
    
    public ArrayList<Propiedad> getPropiedades() {return propiedades;}

    public ArrayList<Edificio> getEdificios() {return edificios;}

    public ArrayList<Trato> getTratosPropuestos() {return tratosPropuestos;}

    public ArrayList<Trato> getTratosPendientes() { return tratosPendientes; }

    public void setDineroActual(double dineroActual) {if(dineroActual>=-1)this.dineroActual = dineroActual;}
    
//GETTER Y SUMADORES PARA ESTADISTICA

    public double getDineroGastado()    {return dineroGastado;}

    public double getCartosInvertidos() {return cartosInvertidos;}

    public double getPagoDeAlquileres() {return pagoDeAlquileres;}

    public double getCobroDeAlquileres(){return cobroDeAlquileres;}

    public double getPagoImpostosTasas(){return pagoImpostosTasas;}

    public double getCobroPorCasillaDeSaida()   {return cobroPorCasillaDeSaida;}

    public double getPremiosInversionsOuBote()  {return premiosInversionsOuBote;}

    public int getVecesNoCarcere()  {return vecesNoCarcere;}
    
    public int getNumTiradasDados() { return numTiradasDados; }
    
    //MÉTODOS
    public void aumentaPagoDeAlquileres(double aumento, Jugador jug){
        pagoDeAlquileres+=aumento; 
        jug.aumentaCobroDeAlquileres(aumento);
        avatar.getCasillaActual().aumentaRentabilidade(aumento);
    }

    public void aumentaDineroGastado(double dineroGastado)      { this.dineroGastado += dineroGastado; }

    public void aumentaCartosInvertidos(double cartosInvertidos){ this.cartosInvertidos += cartosInvertidos; }
    
    private void aumentaCobroDeAlquileres(double aumento)       {cobroDeAlquileres+=aumento;}
    
    public void aumentaPagoImpostosTasas(double aumento)        {pagoImpostosTasas+=aumento;}
    
    public void aumentaCobroPorCasillaDeSaida()                 {cobroPorCasillaDeSaida+=IValor.VALORSALIDA;}
    
    public void aumentaPremiosInversionsOuBote(double aumento)  {premiosInversionsOuBote+=aumento;}
    
    public void aumentaVecesNoCarcere()                         {vecesNoCarcere++;}
    
    public void cobrar(double cantidad){if(cantidad>0)          {dineroActual+=cantidad;}} 
    
    
    public double capitalDisponible(){
        double toret=dineroActual;
        for(Propiedad p : propiedades){ if(!p.isHipotecada()){ toret+=p.getValorHipoteca();} }
        for(Edificio e: edificios){toret += e.getValorVenta();}
        return toret;
    }    
    
    public String lanzarDados() throws PeticionIncorrectaException{
        if(tablero.isPodeLanzarDados()){
            String toret="";
            dados.reroll();
            consola.imprimir(dados.toString());
            numTiradasDados++;
            
            tablero.setPodeLanzarDados(false);
            if(avatar.isEncarcelado()){
                if(dados.getDobles()){
                    avatar.setEncarcelado(false);
                    avatar.setNumTurnosCarcel(0);
                    return ("Saes do cárcere e móveste ata "+avatar.getCasillaActual().getNombre()+".\n"+
                            avatar.moverse(dados.getSuma()));
                }
                tablero.setIntento(true);
                return "Permaneces no cárcere.\n";
            }
            if(dados.getDobles()){
                tablero.setNumDobles(tablero.getNumDobles()+1);
                tablero.setPodeLanzarDados(true);
            }
            if(tablero.getNumDobles()==IValor.MAXDADOSDOBLES){ 
                    return("Sacaches dados dobles por "+IValor.MAXDADOSDOBLES+" vez consecutiva!\n"+avatar.irCarcel()+tablero.toString());
            }
            toret+=avatar.moverse(dados.getSuma());
            if(!avatar.isEncarcelado()){return toret+tablero.toString();}
            return toret;         
        }
        return "Non podes moverte.\n";
    }
    
    public String bancarrota(Jugador receptor, double aPagar) throws DatoIncorrectoException{  
        for(int i=tratosPropuestos.size();i>0;i--){ tratosPropuestos.get(i-1).eliminarTrato(); }     
        if(!receptor.equals(tablero.getLaBanca())){tablero.getLaBanca().pagar(receptor, aPagar);}  
        for(int i=edificios.size();i>0;i--){ edificios.get(i-1).eliminarEdificio(); } 
        for(Propiedad propiedad : propiedades){
            if(propiedad instanceof Solar){ ((Solar)propiedad).setVecesPisadaDono(0); }
            if(receptor.equals(tablero.getLaBanca())){ propiedad.resetAlquiler(); }
            propiedad.setDueño(receptor);
            receptor.getPropiedades().add(propiedad);
            if(propiedad.isHipotecada()){
                if(receptor.equals(tablero.getLaBanca())) {propiedad.setHipotecada(false);}
                else {receptor.getHipotecas().add(propiedad);}
            }
        }
        for(Propiedad prop : receptor.getPropiedades()){ prop.alquiler(); } 
        
        return ("O xogador "+nombre+ " entra en BANCARROTA. Todas as súas propiedades pasan a ser de "+receptor.getNombre()+".\n" + (this.eliminarJugador())) ; ///////////////////
    }
    
    public String eliminarJugador(){ 
        propiedades=null;
        dineroGastado=0;
        hipotecas=null;
        edificios=null;
        dineroActual=-1;
        avatar.getCasillaActual().getAvatares().remove(avatar);
        tablero.getJugadores().remove(this);
        return tablero.resetTurno(true);
    }    

    public String pagar(Jugador jug, double aPagar) throws DatoIncorrectoException{        
        if(this.equals(tablero.getLaBanca())){
            if(dineroActual<aPagar){ dineroActual=aPagar+500000; }
            dineroActual-=aPagar;
            dineroGastado+=aPagar;
            jug.cobrar(aPagar);
            return ("\nA Banca págache "+aPagar+" ferrados.");
        }
        else{
            if(capitalDisponible()<aPagar){ return (this.bancarrota(jug,aPagar)); }
            else{
                if(dineroActual<aPagar){
                    consola.imprimir(tablero.toString()+"\nNon tes suficientes ferrados.");
                    submenu(aPagar, this); 
                    return "";
                }
                dineroActual-=aPagar;
                dineroGastado+=aPagar;
                jug.cobrar(aPagar);
                return ("\nPágaslle "+aPagar+" ferrados a "+jug.getNombre()+".\n");
            }
        }
    }
    
    public String pagoSalirCarcel() throws DatoIncorrectoException{
        if(capitalDisponible()<IValor.VALORSALIRCARCEL){ return this.bancarrota(tablero.getLaBanca(),0); }
        if(dineroActual<IValor.VALORSALIRCARCEL){
            consola.imprimir(tablero.toString()+"\nNon tes suficientes ferrados.");
            submenu(IValor.VALORSALIRCARCEL, this);
        }
        dineroActual-=IValor.VALORSALIRCARCEL;
        dineroGastado+=IValor.VALORSALIRCARCEL;
        avatar.getCasillaActual().aumentaRentabilidade(IValor.VALORSALIRCARCEL);
        pagoImpostosTasas+=IValor.VALORSALIRCARCEL;
        ((Accion)tablero.buscarCasilla("parking")).aumentaBote(IValor.VALORSALIRCARCEL);
        tablero.setPodeLanzarDados(true);
        tablero.getJugadores().get(tablero.getTurnoJugador()).getAvatar().setEncarcelado(false);
        tablero.getJugadores().get(tablero.getTurnoJugador()).getAvatar().setNumTurnosCarcel(0);
        return "\nPagas "+IValor.VALORSALIRCARCEL+" ferrados para sair do cárcere.\nSaes do cárcere.\nPodes moverte.";
    }
    
    
    public String proponerTrato(Jugador receptor,Casilla auxOfrecer,Casilla auxPedir,double auxDinero,Casilla auxNoPagar,int numTurnos) throws PeticionIncorrectaException{
        if(auxOfrecer==null){    
            try{
                if(((Propiedad) auxPedir).getDueño().equals(receptor)){
                    Trato trato=new Trato(this,receptor,auxDinero,((Propiedad) auxPedir));
                    return "\nTrato proposto";
                }
                throw new DatoIncorrectoException("\nO trato non pode ser proposto porque "+IValor.BROJO+receptor.getNombre()+" no posúe esa propiedade."+IValor.RESET);
            }catch(ClassCastException malClase){ return "\nSo podes intercambiar propiedades."; }
        }
        if(auxPedir==null){
            try{
                if(((Propiedad) auxOfrecer).getDueño().equals(this)){
                    Trato trato=new Trato(this,receptor,((Propiedad) auxOfrecer),auxDinero);
                    return "\nTrato proposto";
                }
                throw new DatoIncorrectoException("\nO trato non pode ser proposto porque"+IValor.BROJO+" non posues esa propiedade."+IValor.RESET);
            }catch(ClassCastException malClase){ return "\nSo podes intercambiar propiedades."; }
        }
        else if(auxOfrecer instanceof Propiedad && auxPedir instanceof Propiedad){
            if(((Propiedad) auxPedir).getDueño().equals(receptor)){
                if(((Propiedad) auxOfrecer).getDueño().equals(this)){
                    if(auxDinero!=0){
                        Trato trato=new Trato(this,receptor,((Propiedad) auxOfrecer),((Propiedad) auxPedir),auxDinero);
                        return "\nTrato proposto";
                    }else{
                        if(auxNoPagar==null){
                            Trato trato=new Trato(this,receptor,((Propiedad) auxOfrecer),((Propiedad) auxPedir));
                            return "\nTrato proposto";
                        }
                        else if(auxNoPagar instanceof Propiedad){
                            if(((Propiedad) auxNoPagar).getDueño().equals(receptor)){
                                if(!((Propiedad) auxNoPagar).equals(auxPedir)){
                                    Trato trato=new Trato(this,receptor,((Propiedad) auxOfrecer),((Propiedad) auxPedir),((Propiedad)auxNoPagar),numTurnos);
                                    return "\nTrato proposto";
                                }return "\nNon poder pedir non pagar alquiler nunha propiedade que estas pedindo";
                            }return "\nO trato non pode ser proposto porque "+receptor.getNombre()+" no posue esa propiedade.";    
                        }return "\nSo podes intercambiar propiedades.";
                    }
                }return "\nO trato non pode ser proposto porque non posúes esa propiedade.";
            }return "\nO trato non pode ser proposto porque "+receptor.getNombre()+" no posúe esa propiedade.";
        }return "\nSo podes intercambiar propiedades.";
    }
    
    public String listarTratos(){
        String toret;
        StringBuilder aux=new StringBuilder();
        
        if(tratosPendientes.isEmpty()){aux.append("\nNon tes ningún trato pendiente.\n");}
        else{
            aux.append("\n{\nTratos Pendientes: ");
            for(Trato t:tratosPendientes){aux.append("\n - ").append(t.toString());}
            aux.append("\n}");
        }
        
        toret=aux.toString();
        return toret;
    }

    public String listarTratosPropuestos(){
        String toret;
        StringBuilder aux=new StringBuilder();
        
        if(tratosPropuestos.isEmpty()){aux.append("\nNon tes ningún trato proposto sen aceptar.\n");}
        else{
            aux.append("\n{\nTratos Propostos: ");
            for(Trato t:tratosPropuestos){aux.append("\n - ").append(t.toString());}
            aux.append("\n}");
        }
            
        toret=aux.toString();
        return toret;
    }
    
    public String estadisticas(){
        String toret;
        StringBuilder aux=new StringBuilder();
        aux.append("{\n\tFerrados Investidos: ").append(cartosInvertidos).
            append(",\n\tCapital Dispoñivel(Ferrados e Valor das Propiedades e Edificios): ").append(this.capitalDisponible()).
            append(",\n\tPago de Taxas e Impostos: ").append(pagoImpostosTasas).
            append(",\n\tCobro de Alugueres: ").append(cobroDeAlquileres).
            append(",\n\tPago de Alugueres: ").append(pagoDeAlquileres).
            append(",\n\tFerrados recibidos ao pasar por Saída: ").append(cobroPorCasillaDeSaida).
            append(",\n\tCobro de Premios, Investimentos ou do Bote: ").append(premiosInversionsOuBote).
            append(",\n\tNúmero de veces no Cárcere: ").append(vecesNoCarcere).
            append("\n}");
        
        toret=aux.toString();
        return toret;
    }    
    
    //OVERRIDES
    @Override
    public String toString(){
        String toret;
        StringBuilder aux=new StringBuilder();
        aux.append("{\nNome: ").append(nombre).
            append(",\nAvatar: ").append(avatar.getNombre()).
            append(",\nTipo de Avatar: ").append(avatar.getClass().getSimpleName());
            if(avatar.isEncarcelado()) {aux.append(",\nO xogador está encarcelado");}    
        aux.append(",\nFortuna: ").append(dineroActual).
            append(",\nFerrados Gastados: ").append(dineroGastado).    
            append(",\nPropiedades: ");
        
        if(propiedades.isEmpty()){ aux.append("-");}    //PROPIEDADES
        else{
            aux.append("[ ");
            propiedades.forEach((i) -> {
                aux.append(i.nombreAColor());
                if(!(propiedades.indexOf(i)==propiedades.size()-1)) {aux.append(", ");} });
            aux.append(" ],");}
        
        aux.append("\nHipotecas: "); 
        if(hipotecas.isEmpty()){ aux.append("- ,");} //HIPOTECAS
        else{
            aux.append("[ ");
            hipotecas.forEach((i) -> {
                aux.append(i.nombreAColor());                  
                if(!(hipotecas.indexOf(i)==hipotecas.size()-1)) {aux.append(", ");} });
            aux.append(" ],");}
        
        aux.append("\nEdificios: "); 
        if(edificios.isEmpty()){ aux.append("-");} //EDIFICIOS
        else{edificios.forEach((i) -> {
                aux.append("[").append(i.getIdEdificio()).append("]");                  
                if(!(edificios.indexOf(i)==edificios.size()-1)) aux.append(", "); });
        }
        
        aux.append("\n}");
        toret=aux.toString();
        return toret;
    }
    
    @Override
    public boolean equals(Object jugador){
        if(jugador==null){return false;}
        if(jugador instanceof Jugador){return  nombre.equals(((Jugador) jugador).getNombre());}
        return false;
    }    
}
    