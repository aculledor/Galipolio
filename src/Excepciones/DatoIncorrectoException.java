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
public final class DatoIncorrectoException extends PeticionIncorrectaException{
    public DatoIncorrectoException() { super(); }
    public DatoIncorrectoException(String message) { super(message); }
    public DatoIncorrectoException(String message, Throwable cause) { super(message, cause); }
    public DatoIncorrectoException(Throwable cause) { super(cause); }
    
}
