/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dominio;

import excepciones.MesaException;
import excepciones.UsuarioException;
import java.util.ArrayList;
import observador.Observable;

/**
 *
 * @author angel
 */
public class Fachada extends Observable {

    private SistemaAcceso sistemaAcceso = new SistemaAcceso();
    private SistemaJuego sistemaJuego = new SistemaJuego();

    // ** Singleton
    private static Fachada instancia = new Fachada();

    public static Fachada getInstancia() {
        return instancia;
    }

    private Fachada() {
    }

    // ** Metodos Delegados
    public void agregarAdministrador(String cedula, String password, String nombreCompleto) throws UsuarioException {
        sistemaAcceso.agregarAdministrador(cedula, password, nombreCompleto);
    }

    public void agregarJugador(String cedula, String password, String nombreCompleto, int saldo) throws UsuarioException {
        sistemaAcceso.agregarJugador(cedula, password, nombreCompleto, saldo);
    }

    public Jugador loginJugador(String cedula, String password) throws UsuarioException {
        return sistemaAcceso.loginJugador(cedula, password);
    }

    public Administrador loginAdministrador(String cedula, String password) throws UsuarioException {
        return sistemaAcceso.loginAdministrador(cedula, password);
    }

    public void logoutAdmin(String cedula) {
        sistemaAcceso.logoutAdmin(cedula);
    }

    public void logoutJugador(String cedula) throws UsuarioException {
        sistemaAcceso.logoutJugador(cedula);
    }

    public ArrayList<Mesa> getTodasLasMesas() {
        return sistemaJuego.getTodasLasMesas();
    }

    public ArrayList<Mesa> getMesasAbiertas() {
        return sistemaJuego.getMesasAbiertas();
    }

    public Mesa crearMesa(int apuestaBase, float comision, int cantJugadores) throws MesaException, UsuarioException {
        return sistemaJuego.crearMesa(apuestaBase, comision, cantJugadores);
    }

    public Object[][] listarMesasAbiertas() {
        return sistemaJuego.listarMesasAbiertas();
    }

    public Object[][] listarTodasLasMesas() {
        return sistemaJuego.listarTodasLasMesas();
    }

    public void agregarJugadorAmesa(int nroMesa, Jugador j) throws MesaException, UsuarioException {
        sistemaJuego.agregarJugadorAmesa(nroMesa, j);
    }

    public Mesa obtenerMesa(int nroMesa) {
        return sistemaJuego.obtenerMesa(nroMesa);
    }

    public boolean estaEnMesa(Jugador jugador) {
        return sistemaJuego.estaEnMesa(jugador);
    }

    public ArrayList<String> mostrarDetallesMesa(int nroMesa) {
        return sistemaJuego.mostrarDetallesMesa(nroMesa);
    }

    public Figura[] getFigurasDefinidas() {
        return sistemaJuego.getFigurasDefinidas();
    }

    public int getTotalRecaudadoEnTodasLasMesas() {
        return sistemaJuego.getTotalRecaudadoEnTodasLasMesas();
    }
}
