/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dominio;

/**
 *
 * @author angel
 */
import excepciones.ManoException;
import excepciones.UsuarioException;
import java.util.ArrayList;

public final class Mano {

    private int nroMano;
    private static int ultimoNroMano = 0;
    private Jugador ganador;
    private ArrayList<Jugador> jugadoresMano;
    private Figura figGanadora;
    private EstadoMano estadoMano;
    private Pozo pozo;
    private Pozo pozoAcumulado;
    private int montoPagado;
    private Mazo mazo;
    private Apuesta apuesta;
    private final float comision;

    public void cambiarEstado(EstadoMano estadoMano) {
        this.estadoMano = estadoMano;
    }

    public enum Estados {
        ESPERANDO_APUESTA, APUESTA_INICIADA, PIDIENDO_CARTAS, TERMINADA;
    }

    public Mano(ArrayList<Jugador> jugadoresMano, int apuestaBase, int montoAcumulado, float comision) throws UsuarioException {
        this.jugadoresMano = jugadoresMano;
        this.pozo = new Pozo(montoAcumulado);
        this.pozoAcumulado = new Pozo(0);
        this.comision = comision;
        this.nroMano = ultimoNroMano;
        ultimoNroMano++;
        this.mazo = new Mazo();
        mazo.barajar();
        ingresarJugadoresYaportarBase(apuestaBase);
        repartirCartas();
        estadoMano = new EstadoManoEsperandoApuesta(this);
    }

    private void ingresarJugadoresYaportarBase(int apuestaBase) {
        int sumaLuces = 0;
        for (Jugador j : jugadoresMano) {
            int luz = j.aportarApuestaBase(apuestaBase);
            if (luz == -1) {
                retirarJugador(j);
            } else {
                j.setManoActual(this);
                sumaLuces += luz;
            }
        }
        getPozo().incrementarMonto(sumaLuces);
    }

    protected void jugadorAbandonaMesa(Jugador j) throws ManoException {
        getEstadoMano().jugadorAbandonaMesa(j);
    }

    protected void hacerJugadorAbandonaMesa(Jugador j) {
        j.retirarse();
        getJugadoresMano().remove(j);
    }

    protected void retirarJugador(Jugador jugador) {
        jugador.retirarse();
    }

    public void repartirCartas() {
        for (Jugador jugador : getJugadoresMano()) {
            for (int i = 0; i < 5; i++) {
                jugador.recibirCarta(getMazo().repartirCarta());
            }
        }
    }

    public void crearApuesta(Jugador jugador, int monto) throws ManoException, UsuarioException {
        getEstadoMano().crearApuesta(jugador, monto);
    }

    private int saldoMasBajo() {
        int menorSaldo = Integer.MAX_VALUE;
        for (Jugador j : jugadoresMano) {
            if (j.getSaldo() < menorSaldo) {
                menorSaldo = j.getSaldo();
            }
        }
        return menorSaldo;
    }

    protected void hacerCrearApuesta(Jugador jugador, int monto) throws UsuarioException, ManoException {
        if (apuesta == null) {
            if (monto < 1) {
                throw new ManoException("Apuesta minima $1.");
            }
            int menorSaldo = saldoMasBajo();
            if (menorSaldo < monto) {
                throw new ManoException("Apuesta maxima $" + menorSaldo);
            }
            if (jugador.isNoApuesta()) {
                throw new ManoException("Ya no puede apostar");
            } else {
                apuesta = new Apuesta(monto, jugador);
                jugador.iniciarApuesta(monto);
                pozo.incrementarMonto(apuesta.getValor());
            }

        }
    }

    public void jugadorNoApuesta(Jugador j) throws ManoException, UsuarioException {
        getEstadoMano().noApuesta(j);
    }

    protected void hacerJugadorNoApuesta(Jugador j) throws ManoException {
        if (!j.isNoApuesta()) {
            j.setNoApuesta(true);
        } else {
            throw new ManoException("Ya indicaste que no inicias apuesta.");
        }
    }

    public void pagarApuesta(Jugador jugador) throws ManoException, UsuarioException {
        getEstadoMano().pagarApuesta(jugador);
    }

    protected void hacerPagarApuesta(Jugador jugador) throws ManoException, UsuarioException {
        if (apuesta.getCreador().equals(jugador)) {
            throw new ManoException("No puedes pagar tu propia apuesta.");
        } else {
            apuesta.agregarPagador(jugador);
            jugador.pagarApuesta();
            pozo.incrementarMonto(apuesta.getValor());
        }
        iniciarPidiendoCartas();
    }

    public void jugadorPasa(Jugador j) throws ManoException {
        getEstadoMano().jugadorPasa(j);
    }

    protected void hacerJugadorPasa(Jugador j) {
        retirarJugador(j);
    }

    public void iniciarPidiendoCartas() throws ManoException {
        if (todosPagaron()) {
            getEstadoMano().pidiendoCartas();
        }
    }

    public void pedirCartas(Jugador j) throws ManoException {
        getEstadoMano().pedirCartas(j);
    }

    protected void hacerPedirCartas(Jugador j) throws ManoException {
        if (j.getSituacion().equals(Jugador.Situacion.fuera)) {
            throw new ManoException("No pagaste la apuesta, no puedes pedir cartas.");
        } else if (j.isPidioCartas()) {
            throw new ManoException("Ya pediste cartas.");
        } else {
            ArrayList<Carta> aModificar = new ArrayList();
            for (Carta c : j.getCartas()) {
                if (!c.estaVisible()) {
                    aModificar.add(c);
                }
            }
            int cantidadPedida = aModificar.size();
            if (cantidadPedida > 0) {
                j.descartarCartas(aModificar);
                repartirNuevasCartas(j, cantidadPedida);
            }
            j.setPidioCartas(true);
        }
    }

    private void repartirNuevasCartas(Jugador j, int cantidadPedida) {
        for (int i = 0; i < cantidadPedida; i++) {
            j.recibirCarta(getMazo().repartirCarta());
        }
    }

    protected void jugadorContinua(Jugador j) throws ManoException {
        getEstadoMano().jugadorContinua(j);
    }

    protected void hacerJugadorContinua(Jugador j) {
        j.devolverCartas();
        j.setSituacion(Jugador.Situacion.accionPendiente);
    }

    public boolean todosPagaron() {
        if (getApuesta() != null) {
            for (Jugador j : jugadoresMano) {
                if (!apuesta.getPagaron().contains(j) && !j.getSituacion().equals(Jugador.Situacion.fuera)) {
                    if (!j.equals(apuesta.getCreador())) {
                        return false;
                    }
                }
            }
            if (jugadoresQueAvanzan() > 1) {
                return true;
            }
        }
        return false;
    }

    protected int jugadoresQueAvanzan() {
        int contador = 0;
        for (Jugador j : jugadoresMano) {
            if (!j.getSituacion().equals(Jugador.Situacion.fuera)) {
                contador++;
            }
        }
        return contador;
    }

    protected int cantJugadoresQueNoApuestan() {
        int contador = 0;
        for (Jugador j : jugadoresMano) {
            if (j.isNoApuesta()) {
                contador++;
            }
        }
        return contador;
    }

    public boolean todosPasaron() {
        for (Jugador j : jugadoresMano) {
            if (!j.getSituacion().equals(Jugador.Situacion.fuera)) {
                return false;
            }
        }
        return true;
    }

    public boolean todosPidieronCartas() {
        for (Jugador j : jugadoresMano) {
            if (!j.getSituacion().equals(Jugador.Situacion.fuera)) {
                if (!j.isPidioCartas()) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean nadieIniciaApuesta() {
        for (Jugador j : jugadoresMano) {
            if (!j.isNoApuesta()) {
                return false;
            }
        }
        retirarJugadores();
        return true;
    }

    private void retirarJugadores() {
        for (Jugador j : jugadoresMano) {
            j.retirarse();
        }
    }

    public void evaluarGanador() {
        Figura figuraGanadora = null;
        Jugador jugadorGanador = null;

        for (Jugador jugador : jugadoresMano) {
            if (!jugador.getSituacion().equals(Jugador.Situacion.fuera)) {
                Figura figuraActual = Figura.evaluarFigura(jugador.getCartas());
                if (figuraGanadora == null || figuraActual.compareTo(figuraGanadora) > 0) {
                    figuraGanadora = figuraActual;
                    jugadorGanador = jugador;
                }
            }
        }
        if (jugadorGanador != null) {
            ganador = jugadorGanador;
            figGanadora = figuraGanadora;
            float montocomision = pozo.getMonto() * (comision / 100);
            float montoGanado = pozo.getMonto() - montocomision;
            setMontoPagado((int) montoGanado);
            ganador.aumentarSaldo(getMontoPagado());
            System.out.println("El ganador es " + ganador.getNombreCompleto() + " con " + figGanadora + ganador.getCartas().toString());
        }
    }

    public void finalizarMano() throws ManoException {
        getEstadoMano().terminada();
    }

    protected void hacerFinalizarMano() {
        if (getApuesta() == null && (todosPasaron() || nadieIniciaApuesta())) {
            this.pozoAcumulado.incrementarMonto(getPozo().getMonto());
        } else {
            evaluarGanador();
        }
    }
    

    public Figura getFigGanadora() {
        return figGanadora;
    }

    public EstadoMano getEstadoMano() {
        return estadoMano;
    }

    public int getNroMano() {
        return nroMano;
    }

    public Jugador getGanador() {
        return ganador;
    }

    public Pozo getPozo() {
        return pozo;
    }

    public Pozo getPozoAcumulado() {
        return pozoAcumulado;
    }

    public int getMontoPagado() {
        return montoPagado;
    }

    public void setMontoPagado(int montoPagado) {
        this.montoPagado = montoPagado;
    }

    public Apuesta getApuesta() {
        return apuesta;
    }

    public ArrayList<Jugador> getJugadoresMano() {
        return jugadoresMano;
    }

    public Mazo getMazo() {
        return mazo;
    }
}
