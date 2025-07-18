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
public class EstadoMesaIniciada extends EstadoMesa {

    public EstadoMesaIniciada(Mesa mesa) {
        super(mesa, Mesa.Estados.INICIADA);
    }

    @Override
    public void abrirMesa() throws MesaException {
        throw new MesaException("La mesa no se puede abir, ya que está iniciada");
    }

    @Override
    public void iniciarMesa() throws UsuarioException, MesaException {
        throw new MesaException("La mesa está iniciada");
    }

    @Override
    public void finalizarMesa() throws MesaException, ManoException {
        this.getMesa().cambiarEstado(new EstadoMesaFinalizada(getMesa()));
        this.getMesa().hacerFinalizarMesa();
    }

    @Override
    public void jugar() throws MesaException, ManoException, UsuarioException {
        getMesa().hacerJugar();
    }

    @Override
    public void agregarJugador(Jugador j) throws UsuarioException, MesaException {
        throw new MesaException("No se puede ingresar, la mesa está iniciada");
    }

    @Override
    public void quitarJugador(Jugador j) throws UsuarioException, MesaException, ManoException {
        getMesa().hacerQuitarJugador(j);
    }

    @Override
    public void crearApuesta(Jugador j, int monto) throws MesaException, UsuarioException, ManoException {
        getMesa().hacerCrearApuesta(j, monto);
    }

    @Override
    public void noApuesta(Jugador j) throws MesaException, UsuarioException, ManoException {
        getMesa().hacerJugadorNoApuesta(j);
    }

    @Override
    public void pagarApuesta(Jugador j) throws UsuarioException, MesaException, ManoException {
        getMesa().hacerPagarApuesta(j);
    }

    @Override
    public void pedirCartas(Jugador j) throws MesaException, ManoException {
        getMesa().hacerPedirCartas(j);
    }

    @Override
    public void jugadorPasa(Jugador j) throws MesaException, ManoException {
        getMesa().hacerJugadorPasa(j);
    }

    @Override
    public void terminarMano() throws MesaException, ManoException {
        getMesa().hacerTerminarMano();
    }

    @Override
    public void jugadorContinua(Jugador j) throws UsuarioException, MesaException, ManoException {
        getMesa().hacerJugadorContinua(j);
    }

}
