/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package inicio;

import excepciones.MesaException;
import excepciones.UsuarioException;
import iuGrafica.MenuInicio;

/**
 *
 * @author angel
 */
public class Main {

    public static void main(String[] args) throws MesaException, UsuarioException {

        try {
            DatosPrueba.cargar();
            new MenuInicio().setVisible(true);

            //CASOS DE TESTING PARA PROBAR FIGURAS
//            Mazo mazo = new Mazo();
//            mazo.cartas.forEach(System.out::println);
//            ArrayList<Carta> mano1 = new ArrayList<>();
//            mano1.add(new Carta(4, "D"));
//            mano1.add(new Carta(4, "C"));
//            mano1.add(new Carta(1, "D"));
//            mano1.add(new Carta(1, "D"));
//            mano1.add(new Carta(8, "T"));
//            
//            ArrayList<Carta> mano2 = new ArrayList<>();
//            mano2.add(new Carta(1, "C"));
//            mano2.add(new Carta(6, "T"));
//            mano2.add(new Carta(1, "C"));
//            mano2.add(new Carta(9, "D"));
//            mano2.add(new Carta(9, "T"));
//
//            // Evaluar las figuras
//            Figura figura1 = Figura.evaluarFigura(mano1);
//            Figura figura2 = Figura.evaluarFigura(mano2);
//
//            System.out.println("Mano 1: " + figura1);
//            System.out.println("Mano 2: " + figura2);
//
//            if (figura1.compareTo(figura2) > 0) {
//                System.out.println("La Mano 1 gana");
//            } else if (figura1.compareTo(figura2) < 0) {
//                System.out.println("La Mano 2 gana");
//            } else {
//                System.out.println("Empate");
//            }
            //
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
