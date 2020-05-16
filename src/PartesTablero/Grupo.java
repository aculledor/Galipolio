/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PartesTablero;


import Interfaces.IValor;
import Edificaciones.*;
import Excepciones.*;
import PartesCasilla.Propiedad;
import PartesCasilla.Solar;
import java.util.ArrayList;

/**
 *
 * @author eci-toshiba
 */
public final class Grupo {
    
    private final ArrayList<Propiedad> propiedadesGrupo;
    private final String  nombre;     
    private final Tablero tablero;
    private final String  color;
    
    private double precioGrupo; 
    private double rentabilidade;  
    private int numCasas;
    private int numHoteles;
    private int numPiscinas;
    private int numPistas;

    public Grupo() {
        this.tablero=null;
        this.nombre=null;
        this.propiedadesGrupo = null;
        this.color = null;
        this.precioGrupo = 0;
        this.numCasas = 0;
        this.numHoteles = 0;
        this.numPiscinas = 0;
        this.numPistas = 0;
        this.rentabilidade=0;
    }
        
    public Grupo(String color, double precioGrupo, Tablero tablero, String nombre) {
        this.propiedadesGrupo = new ArrayList();
        this.tablero=tablero;
        this.nombre=nombre;
        this.color = color;
        this.precioGrupo = precioGrupo;
        this.numCasas = 0;
        this.numHoteles = 0;
        this.numPiscinas = 0;
        this.numPistas = 0;
        this.rentabilidade=0;
    }

    public String getNombre()                           { return nombre; }

    public String getColor()                            { return color; }
 
    public ArrayList<Propiedad> getPropiedadesGrupo()   { return propiedadesGrupo; }

    public double getRentabilidade()                    { return rentabilidade; }

    public double getPrecioGrupo()                      { return precioGrupo; }

    public int getNumCasas()                            { return numCasas; }

    public int getNumHoteles()                          { return numHoteles; }

    public int getNumPistas()                           { return numPistas; }

    public int getNumPiscinas()                         { return numPiscinas; }

    public void setPrecioGrupo(double precioGrupo)      { this.precioGrupo = precioGrupo; }

    public void cambiarNumCasas(boolean aumentar) {
        if(aumentar){numCasas++;}
        else if(numCasas>0){numCasas--;}
    }   

    public void cambiarNumHoteles(boolean aumentar) {
        if(aumentar){numHoteles++;}
        else if(numHoteles>0){numHoteles--;}
    }

    public void aumentaRentabilidade(double rentabilidade) { this.rentabilidade += rentabilidade;}

    public void cambiarNumPiscinas(boolean aumentar) {
        if(aumentar){numPiscinas++;}
        else if(numPiscinas>0){numPiscinas--;}
    }

    public void cambiarNumPistas(boolean aumentar) {
        if(aumentar){numPistas++;}
        else if(numPistas>0){numPistas--;}
    }

    public String listarEdificiosGrupo() throws PeticionIncorrectaException{
        String toret;
        StringBuilder aux = new StringBuilder();
        
        if(!(propiedadesGrupo.get(0) instanceof Solar))                 { throw new DatoIncorrectoException("O grupo non pode ser edificado.\n"); }
        if(numPistas==0&&numHoteles==0&&numPiscinas==0&&numPistas==0)   { aux.append("O grupo non está edificado.\n"); }
        else{
            for(Propiedad solar:propiedadesGrupo){
                aux.append("Propietario: ").append(solar.getDueño().getNombre()).append("\n");
                aux.append("\nPropiedade: ").append(solar.getNombre()); 
                if(!((Solar) solar).getEdificios().isEmpty()){
                    aux.append("\n{\nCasas: ");
                    if(numCasas==0){aux.append("- ,");}
                    else{
                        for(Edificio e:((Solar) solar).getEdificios()){
                            if(e instanceof Casa){
                                aux.append("[").append(e.getIdEdificio()).append("] ");
                            }
                        }
                    }
                    aux.append("\nHoteis: ");
                    if(numHoteles==0){aux.append("- ,");}
                    else{
                        for(Edificio e:((Solar) solar).getEdificios()){
                            if(e instanceof Hotel){
                                aux.append("[").append(e.getIdEdificio()).append("] ");
                            }
                        }
                    }
                    aux.append("\nPiscinas: ");
                    if(numPiscinas==0){aux.append("- ,");}
                    else{
                        for(Edificio e:((Solar) solar).getEdificios()){
                            if(e instanceof Piscina){
                                aux.append("[").append(e.getIdEdificio()).append("] ");
                            }
                        }
                    }
                    aux.append("\nPistas De Deportes: ");
                    if(numPistas==0){aux.append("-");}
                    else{
                        for(Edificio e:((Solar) solar).getEdificios()){
                            if(e instanceof PistaDeportiva){
                                aux.append("[").append(e.getIdEdificio()).append("] ");

                            }
                        }    
                    }
                    aux.append("\n}\n");
                }
                else{ aux.append("\nEste Solar non ten Edificios.\n"); }
            }
        }    
        
        if(numHoteles<propiedadesGrupo.size()){
            if(numCasas<IValor.MAXCASASSOLAR){ aux.append("Ainda se poden edificar ").append(IValor.MAXCASASSOLAR-numCasas).append(" Casas.\n"); }
            else{ aux.append("Non se poden edificar mais Casas.\n"); }
            aux.append("Ainda se poden edificar ").append(propiedadesGrupo.size()-numHoteles).append(" Hoteis.\n");
        }
        else{
            if(numCasas<propiedadesGrupo.size()){ aux.append("Ainda se poden edificar ").append(propiedadesGrupo.size()-numCasas).append(" Casas.\n"); }
            else{ aux.append("Non se poden edificar mais Casas.\n"); }
            aux.append("Non se poden edificar mais Hoteis.\n");
        }
        if(numPiscinas<propiedadesGrupo.size()){
            aux.append("Ainda se poden edificar ").append(propiedadesGrupo.size()-numPiscinas).append(" Piscinas.\n");
        }else{ aux.append("Non se poden edificar mais Piscinas.\n"); }
        
        if(numPistas<propiedadesGrupo.size()){
            aux.append("Ainda se poden edificar ").append(propiedadesGrupo.size()-numPistas).append(" Pistas de Deportes.\n");
        }else{ aux.append("Non se poden edificar mais Pistas de Deportes.\n"); }
        
        toret=aux.toString();
        return toret;
    }
    
       //OVERRIDES
    @Override
    public String toString(){
        String toret;
        StringBuilder aux = new StringBuilder();
        boolean hayEnVenta=false;
        
        aux.append("{\nGrupo: ").append(nombre).
            append(",\nValor do Grupo: ").append(precioGrupo).
            append(",\nPropiedades: [ ");
        propiedadesGrupo.forEach((i) -> {
            aux.append(i.nombreAColor());
            if(!(propiedadesGrupo.indexOf(i)==propiedadesGrupo.size()-1)) {aux.append(", ");} });
        aux.append(" ]");
        
        aux.append(",\nEn Venta: [ ");
        for(Propiedad p : propiedadesGrupo){
            if(p.getDueño().equals(tablero.getLaBanca())){
                if(hayEnVenta){
                  aux.append(", ");
                }
                aux.append(p.nombreAColor());
                hayEnVenta=true;
            }
        }
        if(!hayEnVenta) aux.append("-");
        aux.append(" ],");
        
        aux.append("\nValor de compra das Propiedades: ").append(propiedadesGrupo.get(0).getValorActual()).
            append("\n}");
        
        toret=aux.toString();
        return toret;
    }
    
    @Override
    public boolean equals(Object grupo){
        if(grupo==null){return false;}
        if(grupo instanceof Grupo){return  nombre.equals(((Grupo) grupo).getNombre());}
        return false;
    } 
}

//R.I.P. boolean escrito
//Siempre estarás en nuestros corazones y tu legado no será olvidado.