/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dominio;

import java.util.ArrayList;

/**
 *
 * @author angel
 */
public abstract class Figura implements Comparable<Figura> {

    private String nombre;
    private String descripcion;
    private int valorDePrioridad;
    private int valorPrincipalCarta;
    private int paloCartaAlta;
//    public static Figura[] figuras = {new Poker(), new Escalera(), new Pierna(), new Par(), new SinFigura()};

    protected Figura(String nombre, String descripcion, int valorDePrioridad) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.valorDePrioridad = valorDePrioridad;
        this.setValorPrincipalCarta(0);
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public int getValorDePrioridad() {
        return valorDePrioridad;
    }

    public int getValorPrincipalCarta() {
        return valorPrincipalCarta;
    }

    public void setValorPrincipalCarta(int valorPrincipalCarta) {
        this.valorPrincipalCarta = valorPrincipalCarta;
    }

    public int getPaloCartaAlta() {
        return paloCartaAlta;
    }

    public void setPaloCartaAlta(int paloCartaAlta) {
        this.paloCartaAlta = paloCartaAlta;
    }

    public static Figura evaluarFigura(ArrayList<Carta> cartasOriginal) {
        // definimos la prioridad según la jerarquía de figuras
        ArrayList<Carta> cartas = new ArrayList(cartasOriginal);
        Figura[] figuras = Fachada.getInstancia().getFigurasDefinidas();
        for (Figura figura : figuras) {
            Figura nuevaFigura = figura.crearFigura();
            if (nuevaFigura.esFigura(cartas)) {
                return nuevaFigura;
            }
        }
        return new SinFigura();
    }

    public abstract boolean esFigura(ArrayList<Carta> cartas);

    public abstract Figura crearFigura();

    @Override
    public int compareTo(Figura otraFigura) {
        // Para comparar por prioridad de la figura, las fig estan creadas desde la de mayor "nivel" a la de menor
        int comparacionPrioridad = Integer.compare(this.getValorDePrioridad(), otraFigura.getValorDePrioridad());
        if (comparacionPrioridad != 0) {
            return comparacionPrioridad;
        }

        // Si tienen la misma prioridad, comparar por valor principal
        return Integer.compare(this.getValorPrincipalCarta(), otraFigura.getValorPrincipalCarta());
    }

    @Override
    public abstract String toString();

}
