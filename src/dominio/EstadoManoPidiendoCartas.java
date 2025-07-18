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
public class EstadoManoPidiendoCartas extends EstadoMano {

    public EstadoManoPidiendoCartas(Mano mano) {
        super(mano, Mano.Estados.PIDIENDO_CARTAS);
    }

    @Override
    public void esperandoApuesta() throws ManoException {
        throw new ManoException("La mano no puede esperar apuestas ya que esta pidiendo cartas.");
    }

    @Override
    public void apuestaIniciada() throws ManoException {
        throw new ManoException("No se puede iniciar apuestas ya que se esta pidiendo cartas.");
    }

    @Override
    public void pidiendoCartas() throws ManoException {
        throw new ManoException("Ya se encuentra pidiendo cartas.");
    }

    @Override
    public void terminada() throws ManoException {
        getMano().evaluarGanador();
        getMano().cambiarEstado(new EstadoManoTerminada(getMano()));
    }

    @Override
    public void crearApuesta(Jugador j, int monto) throws ManoException {
        throw new ManoException("Ya no se aceptan apuestas, pidiendo cartas.");
    }

    @Override
    public void noApuesta(Jugador j) throws ManoException, UsuarioException {
        throw new ManoException("No es posible indicar que no deseas iniciar una apuesta en este momento.");
    }
    
    @Override
    public void pedirCartas(Jugador j) throws ManoException {
        getMano().hacerPedirCartas(j);
    }

    @Override
    public void jugadorPasa(Jugador j) throws ManoException {
        throw new ManoException("No se puede pasar, ya se encuentra pidiendo cartas.");
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
    public void pagarApuesta(Jugador j) throws ManoException, UsuarioException {
        throw new ManoException("No se puede pagar apuesta, pidiendo cartas.");
    }

}
