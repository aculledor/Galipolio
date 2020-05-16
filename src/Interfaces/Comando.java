/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaces;

import Excepciones.*;

/**
 *
 * @author aculledor
 */
public interface Comando {
   
    public void venderEdificio(String orde)             throws PeticionIncorrectaException;
    
    public void describirXogador(String nomeXogador)    throws PeticionIncorrectaException;
    
    public void describirAvatar(String nomeAvatar)      throws PeticionIncorrectaException;
    
    public void describirCasilla(String nomeCasilla)    throws PeticionIncorrectaException;
    
    public void comprarPropiedade(String nomeCasilla)   throws PeticionIncorrectaException;
    
    public void construirEdificio(String tipoEdificio)  throws PeticionIncorrectaException;
    
    public void listarEdificiosGrupo(String nomeGrupo)  throws PeticionIncorrectaException;
    
    public void listarEdificios() throws PeticionIncorrectaException;
    
    public void listarComandos();
    
    public void listarXogadores();
    
    public void listarAvatares();
    
    public void listarEnVenta();
    
    public void verTaboleiro();
    
    public void cambiarModo();
    
    public void lanzarDados() throws PeticionIncorrectaException;
    
    public void sairCarcere() throws PeticionIncorrectaException;
    
    public void acabarTurno() throws PeticionIncorrectaException;
    
    public void informacionEdificios();
    
    public void informacionMovementoAvanzado();
    
    public void estadisticasXogador(String nomeXogador)throws PeticionIncorrectaException;
    
    public void estadisticas();
    
    public void hipotecarPropiedade()   throws PeticionIncorrectaException;
    
    public void deshipotecarPropiedade()throws PeticionIncorrectaException;
    
    public void voltas();
    
    public void xogador();
    
    public void rendirse() throws PeticionIncorrectaException;
    
    public void proponerTrato(String nombre, String condiciones)throws PeticionIncorrectaException;
    
    public void aceptarTrato(String nomeTrato) throws PeticionIncorrectaException;
    
    public void eliminarTrato(String nomeTrato)throws PeticionIncorrectaException;
    
    public void listarTratos();
    
    public void listarTratosPropuestos();
    
    public void informacionTratos();    
}
