/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controladores;

import dominio.Fachada;
import dominio.Figura;
import dominio.Jugador;
import dominio.Mesa;
import excepciones.ManoException;
import excepciones.MesaException;
import excepciones.UsuarioException;
import java.util.ArrayList;

import observador.Observable;
import observador.Observador;
import panelCartasPoker.CartaPoker;

/**
 *
 * @author angel
 */
public class ControladorMesa implements Observador {

    private VistaMesa vista;
    private Jugador jugador;
    private Mesa mesa;

    public ControladorMesa(VistaMesa vista, Jugador jugador, Mesa mesa) {
        this.vista = vista;
        this.jugador = jugador;
        this.mesa = mesa;
        mesa.agregarObservador(this);
        agregarJugadorAmesa();
        inicializar();
    }

    private void agregarJugadorAmesa() {
        try {
            Fachada.getInstancia().agregarJugadorAmesa(mesa.getNroMesa(), jugador);
        } catch (MesaException | UsuarioException ex) {
            vista.mostrarMensaje(ex.getMessage());
        }
    }

    @Override
    public void actualizar(Object evento, Observable origen) {
        if (evento.equals(Mesa.Eventos.cambioEstadoMesa)) {
            iniciarJuego();
            mostrarMesa();
        }
        if (evento.equals(Mesa.Eventos.cambioEstadoMano)) {
            mostrarDetallesJugador();
            mostrarMano();
        }
        if (evento.equals(Mesa.Eventos.cambioSituacionJugador)) {
            mostrarDetallesJugador();
            mostrarMano();
        }
        if (evento.equals(Mesa.Eventos.retiraJugador)) {
            evaluarSaldoJugadores();
        }
    }

    private void evaluarSaldoJugadores() {
        if (mesa.evaluarSaldoJugador(jugador)) {
            vista.retirarse();
        }
    }

    private void inicializar() {
        mostrarMesa();
    }

    private void iniciarJuego() {
        try {
            vista.limpiarMensajes();
            mesa.jugar();
        } catch (MesaException | UsuarioException | ManoException ex) {
            vista.mostrarMensaje(ex.getMessage());
        }
    }

    private void mostrarMesa() {
        String nombreJugador = jugador.getNombreCompleto();
        int nroMesa = mesa.getNroMesa();
        String estadoMesa = mesa.getEstado().getEstado().toString();
        int cantParticipantes = mesa.getParticipantes().size();
        int cantMaxParticipantes = mesa.getCantMaxParticipantes();
        vista.mostrarMesa(nombreJugador, nroMesa, estadoMesa, cantParticipantes, cantMaxParticipantes);
    }

    private void mostrarMano() {
        ArrayList<String> figurasPosibles = mesa.listarFigurasPosibles();
        int nroMano = mesa.getManoActual().getNroMano();
        int pozo = mesa.getManoActual().getPozo().getMonto();
        String estadoMano = mesa.getManoActual().getEstadoMano().getEstado().toString();
        vista.mostrarMano(figurasPosibles, nroMano, mesa.getParticipantes(), pozo, estadoMano);
        avisarEstado(estadoMano);
    }

    private void avisarEstado(String estadoMano) {
        if (mesa.getEstado().getEstado() != null) {
            switch (estadoMano) {
                case "ESPERANDO_APUESTA" -> {
                    vista.mostrarMensaje("Indique si desea iniciar apuesta o pasar");
                }
                case "APUESTA_INICIADA" -> {
                    vista.mostrarMensaje("Se ha iniciado una apuesta, de $" + mesa.getManoActual().getApuesta().getValor());
                }
                case "PIDIENDO_CARTAS" -> {
                    vista.mostrarMensaje("Indique las cartas que desea descartar");
                }
                case "TERMINADA" -> {
                    mostrarResultadosMano();
                    anunciarGanador();
                }
                default -> {
                }
            }
        }
    }

    private void mostrarResultadosMano() {
        if (mesa.getManoActual().getGanador() != null) {
            if (mesa.getManoActual().getApuesta() != null && !mesa.getManoActual().getApuesta().getPagaron().isEmpty()) {
                vista.mostrarMensaje("Ganador: " + mesa.getManoActual().getGanador().getNombreCompleto() + " - Figura Ganadora: " + mesa.getManoActual().getFigGanadora());
            } else {
                vista.mostrarMensaje("Ganador: " + mesa.getManoActual().getGanador().getNombreCompleto());
            }
        } else {
            vista.mostrarMensaje("Nadie inicio apuesta, se acumula el pozo");
        }
    }

    private void mostrarDetallesJugador() {
        ArrayList<CartaPoker> cartasPokerJugador = new ArrayList<>(jugador.getCartas());
        if (jugador.getCartas().size() == 5) {
            String figuraJugador = Figura.evaluarFigura(jugador.getCartas()).toString();
            vista.mostrarDetallesJugador(cartasPokerJugador, "Tienes: " + figuraJugador, jugador.getSaldo());
        } else {
            vista.mostrarDetallesJugador(cartasPokerJugador, "Esperando nueva ronda. ", jugador.getSaldo());
        }
    }

    public void jugadorPasa() {
        try {
            mesa.jugadorPasa(jugador);
        } catch (ManoException | UsuarioException | MesaException ex) {
            vista.mostrarMensaje(ex.getMessage());
        }
    }

    private void anunciarGanador() {
        if (mesa.getManoActual().getGanador() != null) {
            if (mesa.getManoActual().getGanador().equals(jugador)) {
                vista.mostrarMensaje("FELICIDADES GANASTE $" + mesa.getManoActual().getMontoPagado());
            }
        }
    }

    public void jugadorCreaApuesta(int monto) {
        try {
            mesa.crearApuesta(jugador, monto);
            vista.mostrarMensajeAcciones("Iniciaste apuesta de $" + monto + ".");
        } catch (ManoException | UsuarioException | MesaException ex) {
            vista.mostrarMensaje(ex.getMessage());
        }
    }

    public void jugadorNoApuesta() {
        try {
            mesa.jugadorNoApuesta(jugador);
            vista.mostrarMensajeAcciones("Indicaste que no inicias apuesta.");
        } catch (MesaException | UsuarioException | ManoException ex) {
            vista.mostrarMensaje(ex.getMessage());
        }
    }

    public void jugadorPagaApuesta() {
        try {
            mesa.pagarApuesta(jugador);
            vista.mostrarMensajeAcciones("Pagaste la apuesta.");
        } catch (ManoException | UsuarioException | MesaException ex) {
            vista.mostrarMensaje(ex.getMessage());
        }
    }

    public void jugadorContinua() {
        try {
            mesa.jugadorContinua(jugador);
        } catch (ManoException | MesaException | UsuarioException ex) {
            vista.mostrarMensaje(ex.getMessage());
        }
    }

    public void jugadorPideCartas() {
        try {
            int cantPedida = jugador.getCantCartasPedidas();
            mesa.pedirCartas(jugador);
            if (cantPedida > 1) {
                vista.mostrarMensajeAcciones("Pediste " + cantPedida + " carta(s) nuevas.");
            } else {
                vista.mostrarMensajeAcciones("Indicaste que no necesitas cartas nuevas.");
            }
        } catch (ManoException | UsuarioException | MesaException ex) {
            vista.mostrarMensaje(ex.getMessage());
        }
    }

    public void jugadorAbandonaMesa() {
        try {
            mesa.quitarJugador(jugador);
            vista.retirarse();
        } catch (UsuarioException | MesaException | ManoException ex) {
            vista.mostrarMensaje(ex.getMessage());
        }
    }

}
