/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dominio;

import excepciones.ManoException;
import excepciones.UsuarioException;
import java.util.ArrayList;
/**
 *
 * @author angel
 */
public class Jugador extends Usuario {

    private int saldo;
    private Situacion situacionJugador = Situacion.accionPendiente;
    private ArrayList<Carta> cartas;
    private Mano manoActual;
    private boolean pidioCartas;
    private boolean noApuesta;

    public enum Situacion {
        accionPendiente, apuestaIniciada, apuestaPagada, fuera
    };

    public Jugador(String cedula, String password, String nombreCompleto, int saldo) {
        super(cedula, password, nombreCompleto);
        this.saldo = saldo;
        this.cartas = new ArrayList<>(5);
    }

    public void setManoActual(Mano mano) {
        this.setPidioCartas(false);
        this.setNoApuesta(false);
        this.manoActual = mano;
    }

    public int getSaldo() {
        return saldo;
    }

    public ArrayList<Carta> getCartas() {
        return cartas;
    }

    protected void devolverCartas() {
        this.cartas = new ArrayList<>(5);
    }

    public int getCantCartasPedidas() {
        int contador = 0;
        for (Carta c : cartas) {
            if (!c.estaVisible()) {
                contador++;
            }
        }
        return contador;
    }

    public void recibirCarta(Carta carta) {
        if (cartas.size() < 5) {
            cartas.add(carta);
        }
    }

    public void descartarCartas(ArrayList<Carta> aModificar) {
        for (Carta c : aModificar) {
            if (this.getCartas().contains(c)) {
                getCartas().remove(c);
            }
        }
    }

    public void iniciarApuesta(int monto) throws UsuarioException, ManoException {
        if (saldo < monto) {
            throw new UsuarioException(this.getNombreCompleto() + " no tiene saldo suficiente para iniciar apuesta.");
        } else {
            reducirSaldo(monto);
            situacionJugador = Situacion.apuestaIniciada;
        }
    }

    public void pagarApuesta() throws UsuarioException, ManoException {
        int montoApostado = getManoActual().getApuesta().getValor();
        if (this.saldo < montoApostado) {
            throw new UsuarioException(this.getNombreCompleto() + " no tiene saldo suficiente para pagar la apuesta.");
        } else {
            reducirSaldo(getManoActual().getApuesta().getValor());
            this.situacionJugador = Situacion.apuestaPagada;
        }
    }

    public void aumentarSaldo(int monto) {
        saldo += monto;
    }

    private void reducirSaldo(int monto) {
        saldo -= monto;
    }

    public Situacion getSituacion() {
        return situacionJugador;
    }

    public void setCartas(ArrayList<Carta> cartas) {
        this.cartas = cartas;
    }

    public void setPidioCartas(boolean pidioCartas) {
        this.pidioCartas = pidioCartas;
    }

    public boolean isPidioCartas() {
        return pidioCartas;
    }

    public boolean isNoApuesta() {
        return noApuesta;
    }

    public void setNoApuesta(boolean noApuesta) {
        this.noApuesta = noApuesta;
    }

    public Mano getManoActual() {
        return this.manoActual;
    }

    public int aportarApuestaBase(int valor) {
        if (this.saldo < valor) {
            return -1;
        } else {
            reducirSaldo(valor);
            return valor;
        }
    }

    public void setSituacion(Situacion situacionJugador) {
        this.situacionJugador = situacionJugador;
    }

    public void retirarse() {
        setSituacion(Situacion.fuera);
    }

    @Override
    public void validar() throws UsuarioException {
        if (this.getCedula() == null || this.getCedula().isBlank()) {
            throw new UsuarioException("Cedula invalida.");
        }
        if (this.getPassword() == null || this.getPassword().isBlank()) {
            throw new UsuarioException("Password invalida.");
        }
        if (this.getNombreCompleto() == null || this.getNombreCompleto().isBlank()) {
            throw new UsuarioException("Nombre completo invalido.");
        }
        if (this.getSaldo() < 0) {
            throw new UsuarioException("El saldo no puede ser negativo.");
        }
    }

    @Override
    public String toString() {
        return "Jugador{ Nombre= " + super.getNombreCompleto() + ", saldo=" + saldo + " }";
    }

}
