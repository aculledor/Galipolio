/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PartesCasilla;

import PartesTablero.Grupo;
import PartesTablero.Tablero;
import Interfaces.IValor;
import Edificaciones.*;
import Excepciones.*;
import Usuario.*;
import static java.lang.Math.pow;
import java.util.ArrayList;

/**
 *
 * @author eci-toshiba
 */
public final class Solar extends Propiedad{
    private final ArrayList<Edificio> edificios;
    private int vecesPisadaDono;
    private final double valorCasa;
    private final double valorHotel;
    private final double valorPiscina;
    private final double valorPista;

    public Solar() {
        super();
        this.edificios = null;
        this.vecesPisadaDono = 0;
        this.valorPista = 0;
        this.valorPiscina = 0;
        this.valorHotel = 0;
        this.valorCasa = 0;
    }
    

    public Solar(double valorInicial, Tablero tablero, String nombre, Grupo grupo, int numeroCasilla) {
        super(valorInicial, tablero, nombre, grupo, numeroCasilla);
        this.edificios = new ArrayList<>();
        this.vecesPisadaDono = 0;
        super.setValorAlquiler(super.getValorInicial()*IValor.FACTORALQUILERSOLAR);
        this.valorCasa = super.getValorInicial()*IValor.FACTORCASA;
        this.valorHotel = super.getValorInicial()*IValor.FACTORHOTEL;
        this.valorPiscina = super.getValorInicial()*IValor.FACTORPISCINA;
        this.valorPista = super.getValorInicial()*IValor.FACTORPISTADEPORTIVA;
    }
    
    //SETTERS AND GETTERS
    
    public ArrayList<Edificio> getEdificios(){return edificios;}

    public int getVecesPisadaDono() { return vecesPisadaDono; }
    
    public void aumentaVecesPisadaDono(){vecesPisadaDono++;}

    public void setVecesPisadaDono(int vecesPisadaDono) { this.vecesPisadaDono = vecesPisadaDono; }
    
    //METODOS
    
    private boolean isEdificable(Jugador jug){
        if(getDueño().equals(jug)){
            boolean tieneGrupo = true;
            for(Propiedad solar : getGrupo().getPropiedadesGrupo()){
                if(!solar.getDueño().equals(jug)){ tieneGrupo=false; break;}
            }
            if((vecesPisadaDono>=IValor.PISADADONOEDIFICAR)||tieneGrupo){
                return true;
            }
        }
        return false;
    }
    
    public String edificar(String claseEdificio) throws DatoIncorrectoException, CondicionNonCumplidaException{
        Jugador jug = getTablero().getJugadores().get(getTablero().getTurnoJugador());
        if(isEdificable(jug)){
            double cartosPago;
            Edificio edificioAux;
            int numCasas=0;
            
            for(Edificio e : edificios){
                if(e instanceof Casa){numCasas++;}
            }
            switch(claseEdificio.toLowerCase()){ 
                case "casa":{
                    if((numCasas<IValor.MAXCASASSOLAR && getGrupo().getNumHoteles()<getGrupo().getPropiedadesGrupo().size() && getGrupo().getNumCasas()<(IValor.MAXCASASSOLAR*(getGrupo().getPropiedadesGrupo().size()-getGrupo().getNumHoteles())))||
                        (getGrupo().getNumHoteles()==getGrupo().getPropiedadesGrupo().size() && getGrupo().getNumCasas()<getGrupo().getPropiedadesGrupo().size())){
                        if((jug.getDineroActual()<valorCasa)) { throw new NonFerradosException(" construir unha "+claseEdificio+" en "+this.nombreAColor()); } 
                        cartosPago=valorCasa;
                        edificioAux = new Casa(getTablero(), this, jug);
                        break;
                    }
                    throw new DatoIncorrectoException("O edificio "+claseEdificio + " non puido ser construido na casilla "+ nombreAColor()+".");
                }
                case "hotel":{
                    if(((numCasas==IValor.MAXCASASSOLAR&&getGrupo().getNumHoteles()<getGrupo().getPropiedadesGrupo().size()))){
                        if((jug.getDineroActual()<valorHotel)) { throw new NonFerradosException(" construir unha "+claseEdificio+" en "+this.nombreAColor()); }
                        cartosPago=valorHotel;
                        try{
                        for(Edificio e : edificios){ if(e instanceof Casa){ e.eliminarEdificio(); }}
                        }catch(NullPointerException nonCasas){
                                throw new DatoIncorrectoException("Ocurriu un problema á hora de eliminar as "+IValor.MAXCASASSOLAR+" Casas de"+
                                        this.nombreAColor()+" co fin de contruir un Hotel.");
                        }
                        
                        edificioAux = new Hotel(getTablero(), this ,jug);
                        break;
                    }
                    throw new DatoIncorrectoException("O edificio "+claseEdificio + " non puido ser construido na casilla "+ nombreAColor()+".");
                }
                case "piscina":{
                    if(getGrupo().getNumCasas()>=2 && getGrupo().getNumHoteles()>=1 &&
                            getGrupo().getNumPiscinas()<getGrupo().getPropiedadesGrupo().size()){
                        if((jug.getDineroActual()<valorPiscina)) { throw new NonFerradosException(" construir unha "+claseEdificio+" en "+this.nombreAColor()); }
                        cartosPago=valorPiscina;
                        edificioAux = new Piscina(getTablero(), this, jug);
                        break;
                    }
                    throw new DatoIncorrectoException("O edificio "+claseEdificio + " non puido ser construido na casilla "+ nombreAColor()+".");
                    
                }
                case "pista deportiva":{
                    if(getGrupo().getNumHoteles()>=2 && getGrupo().getNumPistas()<getGrupo().getPropiedadesGrupo().size()){
                        if((jug.getDineroActual()<valorPista)) { throw new NonFerradosException(" construir unha "+claseEdificio+" en "+this.nombreAColor()); }
                        cartosPago=valorPista;
                        edificioAux = new PistaDeportiva(getTablero(), this, jug);
                        break;
                    }
                    throw new DatoIncorrectoException("O edificio "+claseEdificio + " non puido ser construido na casilla "+ nombreAColor()+".");
                }
                default:{ throw new DatoIncorrectoException("O edificio "+claseEdificio + " non puido ser construido na casilla "+ nombreAColor()+"."); }
            }
            jug.aumentaCartosInvertidos(cartosPago);
            ////////////////////
            if(jug.getAvatar() instanceof Esfinge||jug.getAvatar() instanceof Sombrero){
                ((Esfinge) jug.getAvatar()).setBalance(((Esfinge) jug.getAvatar()).getBalance()-cartosPago);
            }
            edificios.add(edificioAux); getDueño().getEdificios().add(edificioAux); getTablero().getEdificios().add(edificioAux);              
            alquiler();
            return (jug.pagar(getTablero().getLaBanca(),cartosPago)+"O edificio: ["+ edificios.get(edificios.size()-1).getIdEdificio()+"] foi construido na casilla "+ nombreAColor()+".");            
        }
        return "Este Solar non che pertence ou aínda non podes edificar nel.";    
    }
    
    public String venderEdificios(String tipoEdificio, int cantidad) throws DatoIncorrectoException, NonPosuidoException{
        int numEdificios=0;
        int numEliminados=0;
        double precioVenta=0;
        if(getDueño().equals(getTablero().getJugadores().get(getTablero().getTurnoJugador()))){
            for(Edificio e:edificios){
                if(e.getClass().getSimpleName().equalsIgnoreCase(tipoEdificio)){
                    numEdificios++;
                    precioVenta=e.getValorVenta();
                }
            }
            if(numEdificios>=cantidad && cantidad>0){
                for(int i=0;i<edificios.size();i++){
                    if(edificios.get(i).getClass().getSimpleName().equalsIgnoreCase(tipoEdificio)){
                        getTablero().getLaBanca().pagar(getDueño(), precioVenta);          //NON FAI FALTA IMPRIMIR CADA VEZ
                        edificios.get(i).eliminarEdificio();
                        i--;
                        numEliminados++;
                    }
                    if(numEliminados==cantidad){  break; }
                }
                return "\nVendéronse "+cantidad+" edificios de tipo "+tipoEdificio+" por valor de "+(cantidad*precioVenta)+" ferrados."; 
            }
            if(numEdificios==0){throw new DatoIncorrectoException("Non se atopou o edificio de tipo "+tipoEdificio+" en "+nombreAColor());}
            throw new DatoIncorrectoException("Non tes suficientes edificios de tipo "+tipoEdificio+", poderías vender "+numEdificios+" por valor de "
                    +(numEdificios*precioVenta)+" ferrados ("+precioVenta+" cada un).");
        }
        throw new NonPosuidoException(nombreAColor());
    }
    
    
    
    //OVERRRIDE
    @Override
    public void valor(){
        this.setValorActual(getValorInicial()*pow(IValor.INFLACION,getTablero().getVecesAumentoSolares()));
    }
    
    @Override
    public void cambiarDueño(Jugador jug){
        for(Edificio e: edificios){
            for(Edificio edif: getDueño().getEdificios()){
                if(e.equals(edif)){
                    edif.setPropietario(jug);
                    jug.getEdificios().add(edif);
                    getDueño().getEdificios().remove(edif);
                    break;
                }
            }
        }
        vecesPisadaDono = 0;
        super.cambiarDueño(jug);
    }
    
    @Override
    public void resetAlquiler(){
        super.setValorAlquiler(super.getValorInicial()*IValor.FACTORALQUILERSOLAR);
    }
    
    @Override
    public void alquiler(){
        boolean grupo=true;
        int numCasas=0;
        this.resetAlquiler(); double toret=getValorAlquiler();
        for(Propiedad p : getGrupo().getPropiedadesGrupo()){ 
            if(!getDueño().equals(p.getDueño())) { 
                grupo=false; break; 
            } 
        }
        if(!edificios.isEmpty()){
            toret=0;
            for(Edificio e : edificios){
                if(e instanceof Casa){ numCasas++;}
                else if(e instanceof Hotel){ toret+=getValorAlquiler()*IValor.ALQUILERHOTEL;}
                else if(e instanceof Piscina){ toret+=getValorAlquiler()*IValor.ALQUILERPISCINA;}
                else{ toret+=getValorAlquiler()*IValor.ALQUILERPISTADEPORTIVA;}
            }
            switch(numCasas){
                case 1:{  toret+=getValorAlquiler()*IValor.ALQUILERUNACASA;    break;}
                case 2:{  toret+=getValorAlquiler()*IValor.ALQUILERDOSCASAS;   break;}
                case 3:{  toret+=getValorAlquiler()*IValor.ALQUILERTRESCASAS;  break;}
                case 4:{  toret+=getValorAlquiler()*IValor.ALQUILERCUATROCASAS;break;}
            }  
        }
        if(grupo){ setValorAlquiler(toret*2);}
        else{ setValorAlquiler(toret); }
    }    

    
    @Override                            
    public String toString(){           
        String toret;
        StringBuilder aux=new StringBuilder();
        
        aux.append(super.toString());
        aux.append(",\nValor de Aluguer: ").append(getValorAlquiler());
        aux.append(",\nEdificios: ");
        if(edificios.isEmpty()){aux.append("-");}
        else{edificios.forEach((e) -> {
            aux.append("[").append(e.getIdEdificio()).append("]");                
            if(!(edificios.indexOf(e)==edificios.size()-1)) aux.append(", "); });
        }
        aux.append(",\nValor da compra de edificios{ ");
        aux.append("\n\tCasa:").append(valorCasa);
        aux.append("\n\tHotel:").append(valorHotel);
        aux.append("\n\tPiscina:").append(valorPiscina);
        aux.append("\n\tPista deportiva:").append(valorPista);
        aux.append("\n\t} ");
        
        aux.append("\n}");
        toret=aux.toString();
        return toret;
    }
    
    @Override
    public boolean equals(Object solar){
        if(solar==null){return false;}
        if(solar instanceof Solar){return  super.getNombre().equals(((Solar) solar).getNombre());}
        return false;
    }    
}
