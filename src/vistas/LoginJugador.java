/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vistas;

import controladores.ControladorLogin;
import controladores.ControladorLoginJugador;
import dominio.Jugador;
import iuGrafica.InicioJugador;
import java.awt.Frame;

/**
 *
 * @author angel
 */
public class LoginJugador extends LoginAbstracto {

    public LoginJugador(Frame parent, boolean modal) {
        super(parent, modal, "Inicio de Sesion de Jugador");
    }

    @Override
    public ControladorLogin crearControlador() {
        return new ControladorLoginJugador(this);
    }

    @Override
    public void accionSiguiente(Object u) {
        new InicioJugador((Jugador) u).setVisible(true);
    }

}
