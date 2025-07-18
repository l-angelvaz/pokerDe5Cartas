/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dominio;

import excepciones.ManoException;
import excepciones.MesaException;
import excepciones.UsuarioException;

/**
 *
 * @author angel
 */
public class EstadoMesaAbierta extends EstadoMesa {

    public EstadoMesaAbierta(Mesa mesa) {
        super(mesa, Mesa.Estados.ABIERTA);
    }

    @Override
    public void abrirMesa() throws MesaException {
        throw new MesaException("La mesa ya esta abierta");
    }

    @Override
    public void iniciarMesa() throws UsuarioException, MesaException {
        if (getMesa().getFaltan() == 0) {
            getMesa().cambiarEstado(new EstadoMesaIniciada(this.getMesa()));
            getMesa().hacerIniciarMesa();
        }
    }

    @Override
    public void finalizarMesa() throws MesaException {
        throw new MesaException("La mesa aun no ha iniciado");
    }

    @Override
    public void jugar() throws MesaException, ManoException {
        throw new MesaException("La mesa aun no ha sido iniciada");
    }

    @Override
    public void agregarJugador(Jugador jugador) throws UsuarioException, MesaException {
        getMesa().hacerAgregarJugador(jugador);
    }

    @Override
    public void quitarJugador(Jugador j) throws UsuarioException, MesaException, ManoException {
        getMesa().getParticipantes().remove(j);
    }

    @Override
    public void crearApuesta(Jugador j, int monto) throws MesaException {
        throw new MesaException("No puede apostar, la mesa no se ha iniciado");
    }

    @Override
    public void pagarApuesta(Jugador j) throws MesaException {
        throw new MesaException("No puede pagar, la mesa no se ha iniciado");
    }

    @Override
    public void noApuesta(Jugador j) throws MesaException, UsuarioException, ManoException {
        throw new MesaException("No puede indicar que no apuesta, la mesa no se ha iniciado");
    }

    @Override
    public void pedirCartas(Jugador j) throws MesaException, ManoException {
        throw new MesaException("No puede pedir cartas, la mesa no se ha iniciado");
    }

    @Override
    public void jugadorPasa(Jugador j) throws MesaException, ManoException {
        throw new MesaException("No puede pasar, la mesa no se ha iniciado");
    }

    @Override
    public void terminarMano() throws MesaException, ManoException {
        throw new MesaException("La mesa no se ha iniciado");
    }

    @Override
    public void jugadorContinua(Jugador j) throws UsuarioException, MesaException, ManoException {
        throw new MesaException("Espera a los demas jugadores para comenzar");
    }

}
