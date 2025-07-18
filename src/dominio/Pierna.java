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
public class Pierna extends Figura {

    public Pierna() {
        super("Pierna", "Tres cartas de igual valor", 3);
    }

    @Override
    public boolean esFigura(ArrayList<Carta> cartas) {
        Collections.sort(cartas);

        int contador;
        for (int i = 0; i < cartas.size(); i++) {
            contador = 0;
            for (int j = 0; j < cartas.size(); j++) {
                if (cartas.get(i).getValorComparacionCarta() == cartas.get(j).getValorComparacionCarta()) {
                    contador++;
                }
            }
            if (contador == 3) {
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
        return new Pierna();
    }
}
