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
public class PeticionIncorrectaException extends Exception{
    public PeticionIncorrectaException() { super(); }
    public PeticionIncorrectaException(String message) { super(message); }
    public PeticionIncorrectaException(String message, Throwable cause) { super(message, cause); }
    public PeticionIncorrectaException(Throwable cause) { super(cause); }
}
