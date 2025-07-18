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
public class EstadoMesaFinalizada extends EstadoMesa {

    public EstadoMesaFinalizada(Mesa mesa) {
        super(mesa, Mesa.Estados.FINALIZADA);
    }

    @Override
    public void abrirMesa() throws MesaException {
        throw new MesaException("La mesa no se puede abir ya que está finalizada");
    }

    @Override
    public void iniciarMesa() throws UsuarioException, MesaException {
        throw new MesaException("La mesa no se puede iniciar ya que está finalizada");
    }

    @Override
    public void finalizarMesa() throws MesaException {
        throw new MesaException("La mesa está finalizada");
    }

    @Override
    public void jugar() throws MesaException, ManoException {
        throw new MesaException("La mesa está finalizada");
    }

    @Override
    public void agregarJugador(Jugador j) throws UsuarioException, MesaException {
        throw new MesaException("La mesa está finalizada");
    }

    @Override
    public void quitarJugador(Jugador j) throws UsuarioException, MesaException, ManoException {
        getMesa().getParticipantes().remove(j);
    }

    @Override
    public void crearApuesta(Jugador j, int monto) throws MesaException {
        throw new MesaException("No puede apostar, la mesa ha finalizado");
    }

    @Override
    public void noApuesta(Jugador j) throws MesaException, UsuarioException, ManoException {
        throw new MesaException("No puede indicar que no apuesta, la mesa ha finalizado");
    }

    @Override
    public void pagarApuesta(Jugador j) throws UsuarioException, MesaException {
        throw new MesaException("No puede pagar, la mesa ha finalizado");
    }

    @Override
    public void pedirCartas(Jugador j) throws MesaException, ManoException {
        throw new MesaException("No puede pedir cartas, la mesa ha finalizado");
    }

    @Override
    public void jugadorPasa(Jugador j) throws MesaException, ManoException {
        throw new MesaException("No puede pasar, la mesa ha finalizado");
    }

    @Override
    public void terminarMano() throws MesaException, ManoException {
        throw new MesaException("La mesa ha finalizado");
    }

    @Override
    public void jugadorContinua(Jugador j) throws UsuarioException, MesaException, ManoException {
        throw new MesaException("La mesa ha finalizado");
    }

}
