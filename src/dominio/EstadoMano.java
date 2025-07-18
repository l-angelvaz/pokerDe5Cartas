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
public abstract class EstadoMano {

    private Mano mano;
    private Mano.Estados estado;

    public EstadoMano(Mano mano, Mano.Estados estado) {
        this.mano = mano;
        this.estado = estado;
    }

    public Mano getMano() {
        return mano;
    }

    public Mano.Estados getEstado() {
        return estado;
    }
    
    /// ESTADOS
    public abstract void esperandoApuesta() throws ManoException;

    public abstract void apuestaIniciada() throws ManoException;

    public abstract void pidiendoCartas() throws ManoException;

    public abstract void terminada() throws ManoException;

    /// METODOS AUX
    public abstract void crearApuesta(Jugador j, int monto) throws ManoException, UsuarioException;
    
    public abstract void noApuesta(Jugador j) throws ManoException, UsuarioException;

    public abstract void pagarApuesta(Jugador j) throws ManoException, UsuarioException;

    public abstract void pedirCartas(Jugador j) throws ManoException;

    public abstract void jugadorPasa(Jugador j) throws ManoException;

    public abstract void jugadorContinua(Jugador j) throws ManoException;

    public abstract void jugadorAbandonaMesa(Jugador j) throws ManoException;
}
