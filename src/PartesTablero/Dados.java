/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package PartesTablero;
/**
 *
 * @author eci-toshiba
 */
import Interfaces.IValor;
import java.util.Random;
        
public final class Dados {
    private int x, y, suma;
    private boolean dobles;
    

    public Dados() {
        x=0;
        y=0;
        suma=0;
        dobles=false;
    }
    
    public void reroll(){
        Random rn=new Random();
        x = rn.nextInt(IValor.MAXDADOS - IValor.MINDADOS + 1) + IValor.MINDADOS;
        rn=new Random();
        y = rn.nextInt(IValor.MAXDADOS - IValor.MINDADOS + 1) + IValor.MINDADOS;
        suma = x+y;
        dobles = x==y;
    }
    
    public int getSuma()        { return suma; }
    
    public boolean getDobles()  { return dobles; }
    
    @Override
    public String toString()    { return "Tirada: " + x + " y " + y; }
}
