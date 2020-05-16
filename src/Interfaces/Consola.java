/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaces;

/**
 *
 * @author aculledor
 */
public interface Consola {
    
    public void imprimir(String mensaxe);
    
    public void imprimirErro(String mensaxe);
    
    public String leer(String mensaxe); //O mensaxe é o que se debe imprimir antes de scanear   
}
