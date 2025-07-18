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
public class Par extends Figura {

    private int valorCartaAlta;

    public Par() {
        super("Par", "Dos cartas de igual valor", 2);
    }

    public int getValorCartaAlta() {
        return valorCartaAlta;
    }

    public void setValorCartaAlta(int valorCartaAlta) {
        this.valorCartaAlta = valorCartaAlta;
    }

    @Override
    public boolean esFigura(ArrayList<Carta> cartas) {
        Collections.sort(cartas);
        int parMayor = -1;

        for (int i = 0; i < cartas.size() - 1; i++) {
            if (cartas.get(i).getValorComparacionCarta() == cartas.get(i + 1).getValorComparacionCarta()) {
                int valorParActual = cartas.get(i).getValorComparacionCarta();

                if (valorParActual > parMayor) {
                    parMayor = valorParActual;
                    this.setValorPrincipalCarta(parMayor);

                    for (Carta carta : cartas) {
                        if (carta.getValorComparacionCarta() != parMayor) {
                            this.setValorCartaAlta(carta.getValorComparacionCarta());
                            this.setPaloCartaAlta(carta.getPaloComparacionCarta());
                        }
                    }
                }
                i++;
            }
        }
        return parMayor != -1;
    }

    @Override
    public String toString() {
        return "[ " + this.getNombre() + " ]";
    }

    @Override
    public Figura crearFigura() {
        return new Par();
    }

    @Override
    public int compareTo(Figura otraFigura) {
        int comparacionBase = super.compareTo(otraFigura);
        if (comparacionBase != 0) {
            return comparacionBase;
        }

        Par otroPar = (Par) otraFigura;
        int comparacionValor = Integer.compare(this.getValorCartaAlta(), otroPar.getValorCartaAlta());
        if (comparacionValor != 0) {
            return comparacionValor;
        }
        return Integer.compare(this.getPaloCartaAlta(), otroPar.getPaloCartaAlta());
    }
}
