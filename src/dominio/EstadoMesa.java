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
public abstract class EstadoMesa {

    private Mesa mesa;
    private Mesa.Estados estado;

    public EstadoMesa(Mesa mesa, Mesa.Estados estado) {
        this.mesa = mesa;
        this.estado = estado;
    }

    public Mesa getMesa() {
        return mesa;
    }

    public Mesa.Estados getEstado() {
        return estado;
    }

    public abstract void abrirMesa() throws MesaException;

    public abstract void iniciarMesa() throws UsuarioException, MesaException;

    public abstract void finalizarMesa() throws MesaException, ManoException;

    ///
    public abstract void jugar() throws MesaException, ManoException, UsuarioException;
    
    public abstract void agregarJugador(Jugador j) throws UsuarioException, MesaException;

    public abstract void quitarJugador(Jugador j) throws UsuarioException, MesaException, ManoException;

    public abstract void crearApuesta(Jugador j, int monto) throws MesaException, UsuarioException, ManoException;
    
    public abstract void noApuesta(Jugador j) throws MesaException, UsuarioException, ManoException;

    public abstract void pagarApuesta(Jugador j) throws UsuarioException, MesaException, ManoException;

    public abstract void pedirCartas(Jugador j) throws MesaException, ManoException;

    public abstract void jugadorPasa(Jugador j) throws MesaException, ManoException;

    public abstract void terminarMano() throws MesaException, ManoException;

    public abstract void jugadorContinua(Jugador j) throws UsuarioException, MesaException, ManoException;

}
