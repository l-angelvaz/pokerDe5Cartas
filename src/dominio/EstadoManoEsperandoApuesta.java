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
public class EstadoManoEsperandoApuesta extends EstadoMano {

    public EstadoManoEsperandoApuesta(Mano mano) {
        super(mano, Mano.Estados.ESPERANDO_APUESTA);
    }

    @Override
    public void esperandoApuesta() throws ManoException {
        throw new ManoException("La mano se encuentra esperando apuestas.");
    }

    @Override
    public void apuestaIniciada() throws ManoException {
        if (getMano().getApuesta() == null) {
            throw new ManoException("Ningun jugador ha iniciado apuesta.");
        } else {
            getMano().cambiarEstado(new EstadoManoApuestaIniciada(getMano()));
        }
    }

    @Override
    public void pidiendoCartas() throws ManoException {
        throw new ManoException("No se ha iniciado ninguna apuesta, no se puede pedir cartas.");
    }

    @Override
    public void terminada() throws ManoException {
        getMano().hacerFinalizarMano();
        getMano().cambiarEstado(new EstadoManoTerminada(getMano()));
    }

    @Override
    public void crearApuesta(Jugador j, int monto) throws ManoException, UsuarioException {
        if (getMano().getApuesta() == null) {
            getMano().hacerCrearApuesta(j, monto);
            getMano().cambiarEstado(new EstadoManoApuestaIniciada(getMano()));
        }
    }

    @Override
    public void noApuesta(Jugador j) throws ManoException, UsuarioException {
        getMano().hacerJugadorNoApuesta(j);
    }

    @Override
    public void pagarApuesta(Jugador j) throws ManoException, UsuarioException {
        throw new ManoException("La mano se encuentra esperando apuestas, no puede pagar.");
    }

    @Override
    public void pedirCartas(Jugador j) throws ManoException {
        throw new ManoException("La mano se encuentra esperando apuestas, no se pueden pedir cartas.");
    }

    @Override
    public void jugadorPasa(Jugador j) throws ManoException {
        throw new ManoException("La mano se encuentra esperando apuestas, no se puede pasar.");
//        getMano().hacerJugadorPasa(j);
    }

    @Override
    public void jugadorAbandonaMesa(Jugador j) throws ManoException {
        throw new ManoException("No se puede abandonar la mesa en esta fase.");
    }

    @Override
    public void jugadorContinua(Jugador j) throws ManoException {
        throw new ManoException("La ronda ya ha iniciado.");
    }

}
