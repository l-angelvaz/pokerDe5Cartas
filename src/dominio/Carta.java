/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dominio;

import panelCartasPoker.CartaPoker;

/**
 *
 * @author angel
 */
public class Carta implements CartaPoker, Comparable<Carta> {

    private final int valor;
    private final String palo;
    private boolean visible;

    public Carta(int valor, String palo) {
        this.valor = valor;
        this.palo = palo;
        this.visible = false;
    }

    @Override
    public int getValorCarta() {
        return valor;
    }

    public int getValorComparacionCarta() {
        if (valor == 1) {
            return 14;
        }
        return valor;
    }

    public int getPaloComparacionCarta() {
        return switch (palo) {
            case CORAZON ->
                4;
            case DIAMANTE ->
                3;
            case TREBOL ->
                2;
            case PIQUE ->
                1;
            default ->
                0;
        };
    }

    @Override
    public String getPaloCarta() {
        return palo;
    }

    @Override
    public boolean estaVisible() {
        return visible;
    }

    @Override
    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    @Override
    public int compareTo(Carta otraCarta) {
        int comparacionValor = Integer.compare(this.getValorComparacionCarta(), otraCarta.getValorComparacionCarta());
        if (comparacionValor != 0) {
            return comparacionValor;
        }
        return Integer.compare(this.getPaloComparacionCarta(), otraCarta.getPaloComparacionCarta());
    }

    /**
     *
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        Carta otra = (Carta) obj;
        return (this.palo == null ? otra.getPaloCarta() == null : this.palo.equals(otra.getPaloCarta())) && this.getValorComparacionCarta() == otra.getValorComparacionCarta();
    }

    @Override
    public String toString() {
        String valorStr = "";
        if (valor > 10) {
            switch (valor) {
                case 1:
                    valorStr = "AS";
                    break;
                case 11:
                    valorStr = "J";
                    break;
                case 12:
                    valorStr = "Q";
                    break;
                case 13:
                    valorStr = "K";
                    break;
            }
        } else {
            valorStr = Integer.toString(valor);
        }

        return ((visible && valorStr != null) ? valorStr + " de " + palo : "Carta oculta");
    }

}
