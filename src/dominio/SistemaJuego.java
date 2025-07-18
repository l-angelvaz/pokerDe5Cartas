/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dominio;

import excepciones.MesaException;
import excepciones.UsuarioException;
import java.util.ArrayList;

/**
 *
 * @author angel
 */
public class SistemaJuego {

    private ArrayList<Mesa> todasLasMesas = new ArrayList();
    public Figura[] figuras = {new Poker(), new Escalera(), new Pierna(), new Par(), new SinFigura()};

    public ArrayList<Mesa> getTodasLasMesas() {
        return todasLasMesas;
    }

    public ArrayList<Mesa> getMesasAbiertas() {
        ArrayList<Mesa> mesasAbiertas = new ArrayList();
        for (Mesa m : todasLasMesas) {
            if (m.getEstado().getEstado().equals(Mesa.Estados.ABIERTA)) {
                mesasAbiertas.add(m);
            }
        }
        return mesasAbiertas;
    }

    public Figura[] getFigurasDefinidas() {
        return figuras;
    }

    public Mesa crearMesa(int apuestaBase, float comision, int cantJugadores) throws MesaException, UsuarioException {
        if (cantJugadores < 2 || cantJugadores > 5) {
            throw new MesaException("Cantidad de jugadores no valida.");
        }
        if (apuestaBase < 1) {
            throw new MesaException("Apuesta base invalida.");
        }
        if (comision < 1 || comision > 50) {
            throw new MesaException("Comision invalida.");
        }
        Mesa nueva = new Mesa(apuestaBase, comision, cantJugadores);
        nueva.validar();
        todasLasMesas.add(nueva);
        Fachada.getInstancia().avisar(EventosGenerales.Eventos.cambioListaTodasMesas);
        return nueva;

    }

    public void terminarMesa(int posicion) {
        if (posicion != -1) {
            Fachada.getInstancia().avisar(EventosGenerales.Eventos.cambioListaTodasMesas);
        }
    }

    public Mesa obtenerMesa(int nroMesa) {
        Mesa mesa = null;
        for (Mesa m : todasLasMesas) {
            if (m.getNroMesa() == nroMesa) {
                return m;
            }
        }
        return mesa;
    }

    public void agregarJugadorAmesa(int nroMesa, Jugador j) throws MesaException, UsuarioException {
        Mesa m = obtenerMesa(nroMesa);
        if (m != null && !m.getParticipantes().contains(j) && !estaEnMesa(j)) {
            m.agregarJugador(j);
        }
    }

    public Object[][] listarTodasLasMesas() {
        Object[][] datos = new Object[getTodasLasMesas().size()][9];

        for (int i = 0; i < getTodasLasMesas().size(); i++) {
            Mesa m = getTodasLasMesas().get(i);
            datos[i][0] = m.getNroMesa();
            datos[i][1] = m.getApuestaBase();
            datos[i][2] = m.getCantMaxParticipantes();
            datos[i][3] = m.getParticipantes().size();
            datos[i][4] = m.getManoActual() != null ? m.getManoActual().getNroMano() : "N/A";
            datos[i][5] = m.getTotalApostado();
            datos[i][6] = m.getComision();
            datos[i][7] = m.getTotalRecaudado();
            datos[i][8] = m.getEstado().getEstado();
        }
        return datos;
    }

    public Object[][] listarMesasAbiertas() {
        Object[][] datos = new Object[getMesasAbiertas().size()][5];

        for (int i = 0; i < getMesasAbiertas().size(); i++) {
            Mesa m = getMesasAbiertas().get(i);
            datos[i][0] = m.getNroMesa();
            datos[i][1] = m.getCantMaxParticipantes();
            datos[i][2] = m.getApuestaBase();
            datos[i][3] = m.getParticipantes().size();
            datos[i][4] = m.getComision();
        }
        return datos;
    }

    protected boolean estaEnMesa(Jugador jugador) {
        for (Mesa m : getTodasLasMesas()) {
            if (!m.getEstado().getEstado().equals(Mesa.Estados.FINALIZADA) && m.getParticipantes().contains(jugador)) {
                return true;
            }
        }
        return false;
    }

    protected ArrayList<String> mostrarDetallesMesa(int nroMesa) {
        Mesa mesaSeleccionada = obtenerMesa(nroMesa);
        ArrayList<String> detalles = new ArrayList<>();

        if (mesaSeleccionada.getTodasLasManos().isEmpty()) {
            detalles.add("Aún no se han finalizado manos en esta mesa.");
        } else {
            for (Mano m : mesaSeleccionada.getTodasLasManos()) {
                StringBuilder detalle = new StringBuilder("[ ");
                detalle.append("NroMano: ").append(m.getNroMano());
                detalle.append(", Cant Participantes: ").append(m.getJugadoresMano().size());
                detalle.append(", Total Apostado: ").append(m.getPozo().getMonto());
                detalle.append(", Estado: ").append(m.getEstadoMano() != null ? m.getEstadoMano().getEstado() : "Desconocido");
                detalle.append(", Ganador: ").append(m.getGanador() != null ? m.getGanador().getNombreCompleto() : "No hubo ganador");
                detalle.append(", Figura Ganadora: ").append(m.getFigGanadora() != null ? m.getFigGanadora().getNombre() : "N/A");
                detalle.append(" ]");

                detalles.add(detalle.toString());
            }
        }
        return detalles;
    }

    protected int getTotalRecaudadoEnTodasLasMesas() {
        int total = 0;
        for (Mesa m : getTodasLasMesas()) {
            total += m.getTotalRecaudado();
        }
        return total;
    }

}
