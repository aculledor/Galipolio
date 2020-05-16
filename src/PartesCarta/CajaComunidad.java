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
public final class CajaComunidad extends Carta{

    public CajaComunidad() {
        super();
    }

    public CajaComunidad(Tablero tablero, String descripcion, Casilla destino) {
        super(tablero, descripcion, destino);
    }

    public CajaComunidad(Tablero tablero, String descripcion, double pago, boolean aTodos) {
        super(tablero, descripcion, pago, aTodos);
    }
    
}
