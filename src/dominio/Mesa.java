/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dominio;

import excepciones.ManoException;
import excepciones.MesaException;
import excepciones.UsuarioException;
import java.util.ArrayList;
import observador.Observable;

/**
 *
 * @author angel
 */
public class Mesa extends Observable {

    private final int nroMesa;
    private static int ultNroMesa = 1;
    private final ArrayList<Mano> todasLasManos = new ArrayList();
    private Mano manoActual;
    private final ArrayList<Jugador> participantes;
    private final int cantMaxParticipantes;
    private final int apuestaBase;
    private final float comision;
    private int pozoAcumulado = 0;
    private int totalRecaudado = 0;
    private int totalApostado = 0;
    private EstadoMesa estado = new EstadoMesaAbierta(this);

    public enum Eventos {
        cambioEstadoMesa, cambioEstadoMano, cambioSituacionJugador, retiraJugador
    }

    public enum Estados {
        ABIERTA,
        INICIADA,
        FINALIZADA;
    };

    public Mesa(int apuestaBase, float comision, int cantJugadores) throws UsuarioException {
        this.apuestaBase = apuestaBase;
        this.comision = comision;
        this.participantes = new ArrayList<>(cantJugadores);
        this.cantMaxParticipantes = cantJugadores;
        this.nroMesa = ultNroMesa;
        ultNroMesa++;
    }

    public boolean evaluarSaldoJugador(Jugador j) {
        if (j.getSaldo() == 0) {
            return true;
        }
        return false;
    }

    protected void hacerIniciarMesa() throws UsuarioException, MesaException {
        avisar(Eventos.cambioEstadoMesa);
        Fachada.getInstancia().avisar(EventosGenerales.Eventos.cambioListaTodasMesas);
    }

    public void iniciarMesa() throws UsuarioException, MesaException {
        if (getFaltan() == 0) {
            getEstado().iniciarMesa();
        }
    }

    public void jugar() throws MesaException, UsuarioException, ManoException {
        getEstado().jugar();
    }

    protected void hacerJugar() throws ManoException, MesaException, UsuarioException {
        if (jugadoresListos()) {
            nuevaMano();
            avisar(Eventos.cambioEstadoMano);
            Fachada.getInstancia().avisar(EventosGenerales.Eventos.cambioListaTodasMesas);
        }
    }

    protected boolean jugadoresListos() {
        if (getParticipantes().size() > 1) {
            for (Jugador j : getParticipantes()) {
                if (!j.getSituacion().equals(Jugador.Situacion.accionPendiente)) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    public void nuevaMano() throws UsuarioException {
        ArrayList<Jugador> jugadores = new ArrayList();
        for (Jugador j : participantes) {
            jugadores.add(j);
        }
        Mano nueva = new Mano(jugadores, getApuestaBase(), this.pozoAcumulado, this.comision);
        this.pozoAcumulado = 0;
        this.manoActual = nueva;
    }

    public void crearApuesta(Jugador j, int monto) throws ManoException, UsuarioException, MesaException {
        getEstado().crearApuesta(j, monto);
    }

    protected void hacerCrearApuesta(Jugador j, int monto) throws ManoException, UsuarioException {
        getManoActual().crearApuesta(j, monto);
        avisar(Mesa.Eventos.cambioEstadoMano);
        avisar(Mesa.Eventos.cambioSituacionJugador);
    }

    public void hacerJugadorNoApuesta(Jugador j) throws ManoException, UsuarioException, MesaException {
        getManoActual().jugadorNoApuesta(j);
        if (getManoActual().cantJugadoresQueNoApuestan() == getParticipantes().size()) {
            hacerTerminarMano();
            avisar(Mesa.Eventos.cambioEstadoMano);
            Fachada.getInstancia().avisar(EventosGenerales.Eventos.cambioListaTodasMesas);
        }
    }

    public void jugadorNoApuesta(Jugador jugador) throws MesaException, UsuarioException, ManoException {
        getEstado().noApuesta(jugador);
    }

    protected void hacerPagarApuesta(Jugador j) throws MesaException, ManoException, UsuarioException {
        getManoActual().pagarApuesta(j);
        avisar(Mesa.Eventos.cambioSituacionJugador);
    }

    public void pagarApuesta(Jugador j) throws ManoException, UsuarioException, MesaException {
        getEstado().pagarApuesta(j);
    }

    public void iniciarPidiendoCartas() throws ManoException {
        if (getManoActual().getApuesta() != null) {
            getManoActual().iniciarPidiendoCartas();
            avisar(Mesa.Eventos.cambioEstadoMano);
        }
    }

    protected void hacerPedirCartas(Jugador j) throws ManoException, MesaException {
        getManoActual().pedirCartas(j);
        if (getManoActual().todosPidieronCartas()) {
            hacerTerminarMano();
            avisar(Mesa.Eventos.cambioEstadoMano);
            avisar(Mesa.Eventos.cambioSituacionJugador);
        }
    }

    public void pedirCartas(Jugador j) throws ManoException, UsuarioException, MesaException {
        getEstado().pedirCartas(j);
    }

    public void jugadorPasa(Jugador j) throws ManoException, UsuarioException, MesaException {
        getEstado().jugadorPasa(j);
    }

    protected void hacerJugadorPasa(Jugador j) throws ManoException, MesaException {
        getManoActual().jugadorPasa(j);
        avisar(Mesa.Eventos.cambioSituacionJugador);
        if ((getManoActual().todosPasaron() || getManoActual().jugadoresQueAvanzan() == 1)) {
            hacerTerminarMano();
            avisar(Mesa.Eventos.cambioEstadoMano);
            Fachada.getInstancia().avisar(EventosGenerales.Eventos.cambioListaTodasMesas);
        } else if (getManoActual().todosPagaron()) {
            iniciarPidiendoCartas();
        }
    }

    protected void acumularPozo() {
        this.pozoAcumulado += getManoActual().getPozoAcumulado().getMonto();
    }

    protected void incrementarTotales() {
        if (getManoActual().getPozoAcumulado().getMonto() != 0) {
            acumularPozo();
        } else {
            int montoPozo = getManoActual().getPozo().getMonto();
            int recaudado = ((int) (montoPozo * comision)) / 100;
            incrementarTotalApostado(montoPozo);
            incrementarTotalRecaudado(recaudado);
        }
    }

    public void jugadorContinua(Jugador j) throws ManoException, MesaException, UsuarioException {
        getEstado().jugadorContinua(j);
    }

    protected void hacerJugadorContinua(Jugador j) throws ManoException, MesaException, UsuarioException {
        if (j.getSaldo() == 0) {
            quitarJugador(j);
            j.setSituacion(Jugador.Situacion.fuera);
            avisar(Eventos.cambioSituacionJugador);
            throw new MesaException("No tienes saldo para continuar jugando");
        }
        getManoActual().jugadorContinua(j);
        avisar(Eventos.cambioSituacionJugador);
        jugar();
    }

    protected void hacerQuitarJugador(Jugador jugador) throws MesaException, ManoException {
        if (getParticipantes().contains(jugador)) {
            getManoActual().jugadorAbandonaMesa(jugador);
            getParticipantes().remove(jugador);
            avisar(Eventos.cambioEstadoMano);
            avisar(Eventos.retiraJugador);
            finalizarMesa();
        }
    }

    public void quitarJugador(Jugador j) throws UsuarioException, MesaException, ManoException {
        getEstado().quitarJugador(j);
        Fachada.getInstancia().avisar(EventosGenerales.Eventos.cambioListaTodasMesas);
    }

    protected void hacerAgregarJugador(Jugador jugador) throws MesaException, UsuarioException {
        if (jugador.getSaldo() < (getApuestaBase() * 10)) {
            throw new MesaException("El saldo del jugador es insuficiente para ingresar a la mesa.");
        }
        if (participantes.size() == cantMaxParticipantes) {
            throw new MesaException("No puede ingresar, la mesa ha llegado al maximo de participantes.");
        }
        participantes.add(jugador);
        jugador.setSituacion(Jugador.Situacion.accionPendiente); //
        iniciarMesa();
        Fachada.getInstancia().avisar(EventosGenerales.Eventos.cambioListaTodasMesas);
    }

    public void agregarJugador(Jugador jugador) throws MesaException, UsuarioException {
        getEstado().agregarJugador(jugador);
    }

    protected void hacerTerminarMano() throws ManoException, MesaException {
        manoActual.finalizarMano();
        todasLasManos.add(manoActual);
        incrementarTotales();
        Fachada.getInstancia().avisar(EventosGenerales.Eventos.cambioListaTodasMesas);
    }

    public void terminarMano() throws ManoException, MesaException {
        getEstado().terminarMano();
    }

    public void finalizarMesa() throws MesaException, ManoException {
        if (getParticipantes().size() == 1) {
            getEstado().finalizarMesa();
        }
    }

    protected void hacerFinalizarMesa() throws MesaException, ManoException {
        avisar(Mesa.Eventos.cambioEstadoMano);
        avisar(Mesa.Eventos.cambioEstadoMesa);
        Fachada.getInstancia().avisar(EventosGenerales.Eventos.cambioListaTodasMesas);
    }

    public ArrayList<String> listarFigurasPosibles() {
        ArrayList<String> figurasPosibles = new ArrayList();
        figurasPosibles.add("Figuras posibles:");
        Figura[] figuras = Fachada.getInstancia().getFigurasDefinidas();
        for (Figura f : figuras) {
            String datos = f.getNombre() + " - " + f.getDescripcion();
            figurasPosibles.add(datos);
        }
        return figurasPosibles;
    }

    public void validar() throws MesaException {
        if (cantMaxParticipantes < 2 || cantMaxParticipantes > 5) {
            throw new MesaException("Cantidad de jugadores no válida");
        }
        if (apuestaBase < 1) {
            throw new MesaException("Apuesta base inválida");
        }
        if (comision < 1 || comision > 50) {
            throw new MesaException("Comisión inválida");
        }
    }

    public int getNroMesa() {
        return nroMesa;
    }

    public ArrayList<Mano> getTodasLasManos() {
        return todasLasManos;
    }

    public Mano getManoActual() {
        return manoActual;
    }

    public ArrayList<Jugador> getParticipantes() {
        return participantes;
    }

    public int getApuestaBase() {
        return apuestaBase;
    }

    public float getComision() {
        return comision;
    }

    public EstadoMesa getEstado() {
        return estado;
    }

    public int getCantMaxParticipantes() {
        return cantMaxParticipantes;
    }

    public void cambiarEstado(EstadoMesa estado) {
        this.estado = estado;
    }

    public int getFaltan() {
        return this.getCantMaxParticipantes() - this.getParticipantes().size();
    }

    public int getTotalRecaudado() {
        return totalRecaudado;
    }

    public void incrementarTotalRecaudado(int totalRecaudado) {
        this.totalRecaudado += totalRecaudado;
    }

    public int getTotalApostado() {
        return totalApostado;
    }

    public void incrementarTotalApostado(int totalApostado) {
        this.totalApostado += totalApostado;
    }

    @Override
    public String toString() {
        return "Mesa [ " + "NroMesa: " + nroMesa + ", ApuestaBase: $" + apuestaBase + ", Comision: " + comision + "0%, Estado: " + estado.getEstado() + " ]";
    }
}
