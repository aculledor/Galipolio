/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PartesCarta;

import PartesTablero.Tablero;
import PartesCasilla.Casilla;

/**
 *
 * @author eci-toshiba
 */
public final class Suerte extends Carta{
    
    public Suerte(){
        super();
    }

    public Suerte(Tablero tablero, String descripcion, Casilla destino) {
        super(tablero, descripcion, destino);
    }

    public Suerte(Tablero tablero, String descripcion, double pago, boolean aTodos) {
        super(tablero, descripcion, pago, aTodos);
    }

}
