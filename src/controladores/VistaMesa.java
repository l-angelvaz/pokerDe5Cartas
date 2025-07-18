/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package controladores;

import dominio.Jugador;
import java.util.ArrayList;
import panelCartasPoker.CartaPoker;

/**
 *
 * @author angel
 */
public interface VistaMesa {

    public void mostrarMesa(String nombreJugador, int nroMesa, String estado, int faltan, int cantMax);

    public void mostrarMensaje(String mensaje);

    public void mostrarMensajeAcciones(String mensaje);

    public void mostrarMano(ArrayList<String> figuras, int nroMano, ArrayList<Jugador> jugadores, int pozo, String estadoMano);

    public void mostrarDetallesJugador(ArrayList<CartaPoker> cartasJugador, String figuraJugador, int saldoJugador);

    public void limpiarMensajes();

    public void retirarse();

}
