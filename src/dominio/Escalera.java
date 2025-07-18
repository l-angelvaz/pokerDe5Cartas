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
public class Escalera extends Figura {

    public Escalera() {
        super("Escalera", "Cinco cartas en secuencia", 4);
    }

    @Override
    public boolean esFigura(ArrayList<Carta> cartas) {
        Collections.sort(cartas);

        if (cartas.get(0).getPaloComparacionCarta() == 2 && cartas.get(1).getValorComparacionCarta() == 3
                && cartas.get(2).getValorComparacionCarta() == 4
                && cartas.get(3).getValorComparacionCarta() == 5
                && cartas.get(4).getValorComparacionCarta() == 14) {
            setValorPrincipalCarta(cartas.get(3).getValorComparacionCarta());
            return true;  // Escalera (AS-2-3-4-5)
        }

        for (int i = 0; i < cartas.size() - 1; i++) {
            if (cartas.get(i).getValorComparacionCarta() + 1 != cartas.get(i + 1).getValorComparacionCarta()) {
                return false;
            }
        }
        setValorPrincipalCarta(cartas.get(4).getValorComparacionCarta());
        return true;
    }

    @Override
    public String toString() {
        return "[ " + this.getNombre() + " ]";
    }

    @Override
    public Figura crearFigura() {
        return new Escalera();
    }

    @Override
    public int compareTo(Figura otraFigura) {
        int comparacionBase = super.compareTo(otraFigura);
        if (comparacionBase != 0) {
            return comparacionBase;
        }
        // Si las figuras tienen la misma prioridad y valor principal, desempatar por el palo de la carta más alta
        Escalera otraEscalera = (Escalera) otraFigura;
        return Integer.compare(this.getPaloCartaAlta(), otraEscalera.getPaloCartaAlta());
    }

}
