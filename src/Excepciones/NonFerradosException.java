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
public final class NonFerradosException extends CondicionNonCumplidaException{
    public NonFerradosException() { super(); }
    public NonFerradosException(String message) { super("Non tes suficientes Ferrados para "+message+"."); }
    public NonFerradosException(String message, Throwable cause) { super(message, cause); }
    public NonFerradosException(Throwable cause) { super(cause); }
}
