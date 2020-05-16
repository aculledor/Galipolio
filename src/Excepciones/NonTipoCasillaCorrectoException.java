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
public final class NonTipoCasillaCorrectoException extends CondicionNonCumplidaException{
    public NonTipoCasillaCorrectoException() { super(); }
    public NonTipoCasillaCorrectoException(String message) { super("Estás intentando realizar en "+IValor.BROJO+message+IValor.RESET+" unha función pertencente a outro tipo de casilla."); }
    public NonTipoCasillaCorrectoException(String message, Throwable cause) { super(message, cause); }
    public NonTipoCasillaCorrectoException(Throwable cause) { super(cause); }
}
