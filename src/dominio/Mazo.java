/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dominio;

import java.util.ArrayList;
import java.util.Collections;
import panelCartasPoker.CartaPoker;

/**
 *
 * @author angel
 */
public class Mazo {
    private ArrayList<Carta> cartas;

    public Mazo() {
        cartas = new ArrayList<Carta>();
        for (String palo : new String[]{CartaPoker.CORAZON, CartaPoker.DIAMANTE, CartaPoker.TREBOL, CartaPoker.PIQUE}) {
            for (int valor = CartaPoker.AS; valor <= CartaPoker.K; valor++) {
                cartas.add(new Carta(valor, palo));
            }
        }
    }

    public void barajar() {
        Collections.shuffle(cartas);
    }

    public Carta repartirCarta() {
        if (!cartas.isEmpty()) {
            Carta aRepartir = cartas.remove(0);
            aRepartir.setVisible(true);
            return aRepartir; 
        }
        return null;
    }

    public boolean estaVacio() {
        return cartas.isEmpty();
    }

    public int cantidadDeCartas() {
        return cartas.size();
    }
}
