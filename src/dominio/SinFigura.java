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
public class SinFigura extends Figura {

    public SinFigura() {
        super("Sin Figura", "No cumple ninguna de las figuras anteriores", 1);
    }

    @Override
    public boolean esFigura(ArrayList<Carta> cartas) {
        Collections.sort(cartas, Collections.reverseOrder());
        this.setValorPrincipalCarta(cartas.get(0).getValorComparacionCarta());
        this.setPaloCartaAlta(cartas.get(0).getPaloComparacionCarta());
        return true;
    }

    @Override
    public String toString() {
        return "[ Carta Alta ]";
    }

    @Override
    public Figura crearFigura() {
        return new SinFigura();
    }

    @Override
    public int compareTo(Figura otraFigura) {
        int comparacionBase = super.compareTo(otraFigura);
        if (comparacionBase != 0) {
            return comparacionBase;
        }
        // Si ninguna tiene fig y ademas tienen mismo valor, comparamos por la carta de mejor palo
        SinFigura otraSinFigura = (SinFigura) otraFigura;
        return Integer.compare(this.getPaloCartaAlta(), otraSinFigura.getPaloCartaAlta());
    }
}
