/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Excepciones;

/**
 *
 * @author aculledor
 */
public class CondicionNonCumplidaException extends PeticionIncorrectaException{
    public CondicionNonCumplidaException() { super(); }
    public CondicionNonCumplidaException(String message) { super("Non podes "+message); }
    public CondicionNonCumplidaException(String message, Throwable cause) { super(message, cause); }
    public CondicionNonCumplidaException(Throwable cause) { super(cause); }
    
}
