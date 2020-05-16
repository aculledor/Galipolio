/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Excepciones;

import Interfaces.IValor;

/**
 *
 * @author aculledor
 */
public final class NonPosuidoException extends CondicionNonCumplidaException{
    public NonPosuidoException() { super(); }
    public NonPosuidoException(String message) { super(IValor.BROJO+message+IValor.RESET+" non che pertence."); }
    public NonPosuidoException(String message, Throwable cause) { super(message, cause); }
    public NonPosuidoException(Throwable cause) { super(cause); }
}
