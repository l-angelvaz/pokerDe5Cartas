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
public class EstadoManoTerminada extends EstadoMano {

    public EstadoManoTerminada(Mano mano) {
        super(mano, Mano.Estados.TERMINADA);
    }

    @Override
    public void esperandoApuesta() throws ManoException {
        throw new ManoException("La mano no puede esperar apuestas ya que está finalizada.");
    }

    @Override
    public void apuestaIniciada() throws ManoException {
        throw new ManoException("La mano no puede iniciar apuesta ya que está finalizada.");
    }

    @Override
    public void pidiendoCartas() throws ManoException {
        throw new ManoException("La mano no puede pedir cartas ya que está finalizada.");
    }

    @Override
    public void terminada() throws ManoException {
        throw new ManoException("La mano ya está finalizada.");
    }

    @Override
    public void crearApuesta(Jugador j, int monto) throws ManoException {
        throw new ManoException("La mano está finalizada.");
    }

    @Override
    public void noApuesta(Jugador j) throws ManoException, UsuarioException {
        throw new ManoException("No es posible indicar que no deseas iniciar una apuesta en este momento.");
    }

    @Override
    public void pagarApuesta(Jugador j) throws ManoException, UsuarioException {
        throw new ManoException("La mano está finalizada.");
    }

    @Override
    public void pedirCartas(Jugador j) throws ManoException {
        throw new ManoException("La mano está finalizada, no se pueden pedir cartas.");
    }

    @Override
    public void jugadorPasa(Jugador j) throws ManoException {
        throw new ManoException("La mano está finalizada, no se puede pasar.");
    }

    @Override
    public void jugadorAbandonaMesa(Jugador j) {
        getMano().hacerJugadorAbandonaMesa(j);
    }

    @Override
    public void jugadorContinua(Jugador j) throws ManoException {
        getMano().hacerJugadorContinua(j);
    }

}
