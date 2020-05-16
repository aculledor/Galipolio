/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PartesTablero;

import Excepciones.*;
import PartesCasilla.*;
import Usuario.*;

public final class Trato {
    private final Jugador emisor;
    private final Jugador receptor;
    private final String idTrato;
    private final Propiedad propiedadOfrecida;
    private final Propiedad propiedadPedida;
    private final Propiedad propiedadNoPagar;
    private final double dineroPagaEmisor;
    private int turnosNoPagar;

    public Trato() {
        this.emisor = null;
        this.receptor = null;
        this.idTrato=null;
        this.propiedadOfrecida = null;
        this.propiedadPedida = null;
        this.propiedadNoPagar = null;
        this.dineroPagaEmisor=0;
        this.turnosNoPagar = 0;
    }
        
    public Trato(Jugador emisor, Jugador receptor, Propiedad propiedadOfrecida, Propiedad propiedadPedida) {
        this.emisor = emisor;
        this.receptor = receptor;
        this.idTrato="Trato-"+emisor.getAvatar().getNombre()+"-"+emisor.getTratosPropuestos().size();
        this.propiedadOfrecida = propiedadOfrecida;
        this.propiedadPedida = propiedadPedida;
        this.propiedadNoPagar = null;
        this.dineroPagaEmisor=0;
        this.turnosNoPagar = 0;
        this.emisor.getTratosPropuestos().add(this);
        this.receptor.getTratosPendientes().add(this);
    }
         
        
    public Trato(Jugador emisor, Jugador receptor, Propiedad propiedadOfrecida, double cantidadDinero) {    //ofreces casilla por dinero
        this.emisor = emisor;
        this.receptor = receptor;
        this.idTrato="Trato-"+emisor.getAvatar().getNombre()+"-"+emisor.getTratosPropuestos().size();
        this.propiedadOfrecida = propiedadOfrecida;
        this.propiedadPedida = null;
        this.propiedadNoPagar = null;
        this.dineroPagaEmisor=cantidadDinero;
        this.turnosNoPagar = 0;
        this.emisor.getTratosPropuestos().add(this);
        this.receptor.getTratosPendientes().add(this);
    }
         
    public Trato(Jugador emisor, Jugador receptor, double cantidadDinero, Propiedad propiedadPedida) {  //ofreces dinero por casilla
        this.emisor = emisor;
        this.receptor = receptor;
        this.idTrato="Trato-"+emisor.getAvatar().getNombre()+"-"+emisor.getTratosPropuestos().size();
        this.propiedadOfrecida = null;
        this.propiedadPedida = propiedadPedida;
        this.propiedadNoPagar = null;
        this.dineroPagaEmisor=cantidadDinero;
        this.turnosNoPagar = 0;
        this.emisor.getTratosPropuestos().add(this);
        this.receptor.getTratosPendientes().add(this);
    }
         

    public Trato(Jugador emisor, Jugador receptor, Propiedad propiedadOfrecida, Propiedad propiedadPedida, double cantidadDinero) {
        this.emisor = emisor;
        this.receptor = receptor;
        this.idTrato="Trato-"+emisor.getAvatar().getNombre()+"-"+emisor.getTratosPropuestos().size();
        this.propiedadOfrecida = propiedadOfrecida;
        this.propiedadPedida = propiedadPedida;
        this.propiedadNoPagar = null;
        this.dineroPagaEmisor=cantidadDinero;       //si es NEGATIVO->EMISOR COBRA, si es POSITIVO EMISOR->PAGA
        this.turnosNoPagar = 0;
        this.emisor.getTratosPropuestos().add(this);
        this.receptor.getTratosPendientes().add(this);
    }
  
    public Trato(Jugador emisor, Jugador receptor, Propiedad propiedadOfrecida, Propiedad propiedadPedida, Propiedad propiedadNoPagar, int turnosNoPagar) {
        this.emisor = emisor;
        this.receptor = receptor;
        this.idTrato="Trato-"+emisor.getAvatar().getNombre()+"-"+emisor.getTratosPropuestos().size();
        this.propiedadOfrecida = propiedadOfrecida;
        this.propiedadPedida = propiedadPedida;
        this.propiedadNoPagar = propiedadNoPagar;
        this.dineroPagaEmisor=0;
        this.turnosNoPagar=(turnosNoPagar<1)? 1:turnosNoPagar;
        this.emisor.getTratosPropuestos().add(this);
        this.receptor.getTratosPendientes().add(this);
    }

    public Jugador getEmisor()      { return emisor; }

    public Jugador getReceptor()    { return receptor; }

    public String getIdTrato()      { return idTrato; }

    public Propiedad getPropiedadOfrecida() { return propiedadOfrecida; }

    public Propiedad getPropiedadPedida()   { return propiedadPedida; }

    public Propiedad getPropiedadNoPagar()  { return propiedadNoPagar; }

    public double getDineroPagaEmisor()     { return dineroPagaEmisor; }

    public int getTurnosNoPagar()           { return turnosNoPagar; } 
    
    public void reducirTurnosNoPagar()      { this.turnosNoPagar--; }
    
    public String aceptarTrato() throws PeticionIncorrectaException{ ////////////PENSAR BIEN Y TERMINAR
        if(propiedadOfrecida==null){
            if(propiedadPedida.getDueño().equals(receptor)){
                if(emisor.getDineroActual()>dineroPagaEmisor){
                    emisor.pagar(receptor, dineroPagaEmisor);
                    propiedadPedida.cambiarDueño(emisor);
                    receptor.getTratosPendientes().remove(this);
                    emisor.getTratosPropuestos().remove(this);
                    return "\nTrato aceptado: "+propiedadPedida.getNombre()+" pasa a pertencer a "+emisor.getNombre();
                }
                return "\nO trato non pode ser aceptado: "+emisor.getNombre()+" non ten suficientes ferrados.";
            }
            return "\nO trato non pode ser aceptado: "+receptor.getNombre()+" non posúe esa propiedade.";
        }
        if(propiedadPedida==null){
            if(propiedadOfrecida.getDueño().equals(emisor)){
                if(receptor.getDineroActual()>(dineroPagaEmisor*(-1))){
                    receptor.pagar(emisor, dineroPagaEmisor*(-1));
                    propiedadOfrecida.cambiarDueño(receptor);
                    receptor.getTratosPendientes().remove(this);
                    emisor.getTratosPropuestos().remove(this);
                    return "\nTrato aceptado: "+propiedadOfrecida.getNombre()+" pasa a pertencer a "+receptor.getNombre();
                }
                return "\nO trato non pode ser aceptado: "+receptor.getNombre()+" non ten suficientes ferrados.";
            }
            return "\nO trato non pode ser aceptado: "+emisor.getNombre()+" non posúe esa propiedade.";
        }
        if(dineroPagaEmisor<0){
            if(propiedadPedida.getDueño().equals(receptor)){
                if(propiedadOfrecida.getDueño().equals(emisor)){
                    if(receptor.getDineroActual()>(dineroPagaEmisor*(-1))){
                        receptor.pagar(emisor, dineroPagaEmisor*(-1));
                        propiedadOfrecida.cambiarDueño(receptor);
                        propiedadPedida.cambiarDueño(emisor);
                        receptor.getTratosPendientes().remove(this);
                        emisor.getTratosPropuestos().remove(this);
                        return "\nTrato aceptado: "+propiedadOfrecida.getNombre()+" pasa a pertencer a "+receptor.getNombre()+
                                " e "+propiedadPedida.getNombre()+" pasa a pertencer a "+emisor.getNombre();
                    }
                    return "\nO trato non pode ser aceptado: "+receptor.getNombre()+" non ten suficientes ferrados.";
                }
                return "\nO trato non pode ser aceptado: "+emisor.getNombre()+" non posúe esa propiedade.";
            }
            return "\nO trato non pode ser aceptado: "+receptor.getNombre()+" non posúe esa propiedade.";
        }
        if(dineroPagaEmisor>0){
            if(propiedadPedida.getDueño().equals(receptor)){
                if(propiedadOfrecida.getDueño().equals(emisor)){
                    if(emisor.getDineroActual()>dineroPagaEmisor){
                        emisor.pagar(receptor, dineroPagaEmisor);
                        propiedadOfrecida.cambiarDueño(receptor);
                        propiedadPedida.cambiarDueño(emisor);
                        receptor.getTratosPendientes().remove(this);
                        emisor.getTratosPropuestos().remove(this);
                        return "\nTrato aceptado: "+propiedadOfrecida.getNombre()+" pasa a pertencer a "+receptor.getNombre()+
                                " e "+propiedadPedida.getNombre()+" pasa a pertencer a "+emisor.getNombre();
                    }
                    return "\nO trato non pode ser aceptado: "+receptor.getNombre()+" non ten suficientes ferrados.";
                }
                return "\nO trato non pode ser aceptado: "+emisor.getNombre()+" non posúe esa propiedade.";
            }
            return "\nO trato non pode ser aceptado: "+receptor.getNombre()+" non posúe esa propiedade.";
        }
        if(propiedadNoPagar==null){
            if(propiedadPedida.getDueño().equals(receptor)){
                if(propiedadOfrecida.getDueño().equals(emisor)){
                    propiedadOfrecida.cambiarDueño(receptor);
                    propiedadPedida.cambiarDueño(emisor);
                    receptor.getTratosPendientes().remove(this);
                    emisor.getTratosPropuestos().remove(this);
                    return "\nTrato aceptado: "+propiedadOfrecida.getNombre()+" pasa a pertencer a "+receptor.getNombre()+
                            " e "+propiedadPedida.getNombre()+" pasa a pertencer a "+emisor.getNombre();
                }
                return "\nO trato non pode ser aceptado: "+emisor.getNombre()+" non posúe esa propiedade.";
            }
            return "\nO trato non pode ser aceptado: "+receptor.getNombre()+" non posúe esa propiedade.";
        }
        if(propiedadPedida.getDueño().equals(receptor)){
            if(propiedadOfrecida.getDueño().equals(emisor)){
                if(propiedadNoPagar.getDueño().equals(receptor)){
                    propiedadOfrecida.cambiarDueño(receptor);
                    propiedadPedida.cambiarDueño(emisor);
                    receptor.getTratosPendientes().remove(this);
                    emisor.getTratosPropuestos().remove(this);
                    if(propiedadNoPagar.getNoPagarAlquiler().get(emisor.getNombre())!=null){
                        propiedadNoPagar.getNoPagarAlquiler().put(emisor.getNombre(), turnosNoPagar+propiedadNoPagar.getNoPagarAlquiler().get(emisor.getNombre()));
                    }
                    else{
                        propiedadNoPagar.getNoPagarAlquiler().put(emisor.getNombre(), turnosNoPagar);
                    }
                    return "\nTrato aceptado: "+propiedadOfrecida.getNombre()+" pasa a pertencer a "+receptor.getNombre()+
                            ", "+propiedadPedida.getNombre()+" pasa a pertencer a "+emisor.getNombre()+" e "+
                            emisor.getNombre()+ " non pagará aluguer en "+propiedadNoPagar.getNombre()+" as proximas "+
                            propiedadNoPagar.getNoPagarAlquiler().get(emisor.getNombre())+" veces.";
                }
                return "\nO trato non pode ser aceptado: "+emisor.getNombre()+" non posúe esa propiedade.";
            }
            return "\nO trato non pode ser aceptado: "+emisor.getNombre()+" non posúe esa propiedade.";
        }
        return "\nO trato non pode ser aceptado: "+receptor.getNombre()+" non posúe esa propiedade.";
    }
    
    public void eliminarTrato(){
        emisor.getTratosPropuestos().remove(this);
        receptor.getTratosPendientes().remove(this);
    }
    
    @Override
    public String toString(){
        String toret;
        StringBuilder aux=new StringBuilder();
        
        aux.append(idTrato).append(": ");
        aux.append(emisor.getNombre()).append(" ofrecelle a ").append(receptor.getNombre()).append(" cambiar ");
        if(propiedadOfrecida!=null){
            aux.append(propiedadOfrecida.nombreAColor());
            if(dineroPagaEmisor>0){
                aux.append(" e ").append(dineroPagaEmisor).append(" Ferrados.");
            }
        }
        else { aux.append(dineroPagaEmisor).append(" Ferrados."); }
        
        aux.append(" a cambio de ");
        if (propiedadPedida!=null){
            aux.append(propiedadPedida.nombreAColor());
            if(dineroPagaEmisor<0){
                aux.append(" e ").append(dineroPagaEmisor*(-1)).append(" Ferrados.");
            }
            else if(propiedadNoPagar!=null && turnosNoPagar>0){
                aux.append(" e non pagar aluguer en ").append(propiedadNoPagar.nombreAColor()).append(" durante ").
                        append(turnosNoPagar).append(" turnos.");
            }
        }
        else{ aux.append(dineroPagaEmisor*(-1)).append(" Ferrados."); }

        toret=aux.toString();
        return toret;
    }
    
    @Override
    public boolean equals(Object trato){
        if(trato==null){return false;}
        if(trato instanceof Trato){return((Trato) trato).getIdTrato().equals(idTrato);}
        return false;
    } 
}
