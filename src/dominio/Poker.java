/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dominio;

import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author angel
 */
public class Poker extends Figura {

    public Poker() {
        super("Poker", "Cuatro cartas de igual valor", 5);
    }

    @Override
    public boolean esFigura(ArrayList<Carta> cartas) {
        Collections.sort(cartas);
        int contador = 1;
        for (int i = 1; i < cartas.size(); i++) {
            if (cartas.get(i).getValorComparacionCarta() == cartas.get(i - 1).getValorComparacionCarta()) {
                contador++;
            } else {
                contador = 1;
            }
            if (contador == 4) {
                this.setValorPrincipalCarta(cartas.get(i).getValorComparacionCarta());
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "[ " + this.getNombre() + " ]";
    }

    @Override
    public Figura crearFigura() {
        return new Poker();
    }

}
