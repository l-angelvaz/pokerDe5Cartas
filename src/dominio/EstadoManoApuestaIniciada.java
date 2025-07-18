/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dominio;

import excepciones.ManoException;
import excepciones.UsuarioException;

/**
 *
 * @author angel
 */
public class EstadoManoApuestaIniciada extends EstadoMano {

    public EstadoManoApuestaIniciada(Mano mano) {
        super(mano, Mano.Estados.APUESTA_INICIADA);
    }

    @Override
    public void esperandoApuesta() throws ManoException {
        throw new ManoException("La mano no puede esperar apuesta porque ya tiene una apuesta iniciada.");
    }

    @Override
    public void apuestaIniciada() throws ManoException {
        throw new ManoException("La mano tiene una apuesta iniciada.");
    }

    @Override
    public void pidiendoCartas() throws ManoException {
        if (getMano().todosPagaron()) {
            getMano().cambiarEstado(new EstadoManoPidiendoCartas(getMano()));
        }
    }

    @Override
    public void terminada() throws ManoException {
        getMano().hacerFinalizarMano();
        getMano().cambiarEstado(new EstadoManoTerminada(getMano()));
    }

    @Override
    public void pagarApuesta(Jugador j) throws ManoException, UsuarioException {
        if (getMano().getApuesta() != null) {
            getMano().hacerPagarApuesta(j);
        }
    }

    @Override
    public void pedirCartas(Jugador j) throws ManoException {
        throw new ManoException("No se pueden pedir cartas hasta que todos hayan pagado la apuesta o esten fuera.");
    }

    @Override
    public void jugadorPasa(Jugador j) throws ManoException {
        if (getMano().getApuesta().getCreador().equals(j)) {
            throw new ManoException("No puedes pasar, tu inciaste la apuesta.");
        } else {
            getMano().hacerJugadorPasa(j);
        }
    }

    @Override
    public void jugadorAbandonaMesa(Jugador j) throws ManoException {
        throw new ManoException("No se puede abandonar la mesa en esta fase.");
    }

    @Override
    public void jugadorContinua(Jugador j) throws ManoException {
        throw new ManoException("La ronda ya ha iniciado.");
    }

    @Override
    public void crearApuesta(Jugador j, int monto) throws ManoException, UsuarioException {
        if (getMano().getApuesta() != null && getMano().getApuesta().equals(j)) {
            throw new ManoException("Ya has iniciado una apuesta.");
        } else {
            throw new ManoException("Ya hay una apuesta en curso.");
        }
    }

    @Override
    public void noApuesta(Jugador j) throws ManoException, UsuarioException {
        throw new ManoException("No es posible indicar que no deseas iniciar una apuesta en este momento.");
    }

}
