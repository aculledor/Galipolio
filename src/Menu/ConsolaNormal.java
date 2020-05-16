/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Menu;

import Interfaces.IValor;
import Interfaces.Consola;
import java.util.Scanner;

/**
 *
 * @author aculledor
 */
public final class ConsolaNormal implements Consola{
    //OVERRIDES
    @Override
    public void imprimir(String mensaxe) throws NullPointerException{
        System.out.println(mensaxe);
    }
    
    @Override
    public void imprimirErro(String mensaxe)throws NullPointerException {
        //if(mensaxe==null){mensaxe="O mensaxe a imprimir é NULL.";}
        System.out.println("\n"+IValor.BROJO+mensaxe+IValor.RESET);
    }
    
    @Override
    public String leer(String mensaxe)throws NullPointerException{
        String comando;
        imprimir(mensaxe);
        comando= new Scanner(System.in).nextLine();
        return comando;
    }
}
