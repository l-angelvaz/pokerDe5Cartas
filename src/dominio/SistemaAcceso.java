/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dominio;

import excepciones.UsuarioException;
import java.util.ArrayList;

/**
 *
 * @author angel
 */
public class SistemaAcceso {

    private ArrayList<Administrador> administradores = new ArrayList();
    private ArrayList<Jugador> jugadores = new ArrayList();

    public void agregarAdministrador(String cedula, String password, String nombreCompleto) throws UsuarioException {
        Administrador nuevo = new Administrador(cedula, password, nombreCompleto);
        nuevo.validar();
        administradores.add(nuevo);
    }

    public void agregarJugador(String cedula, String password, String nombreCompleto, int saldo) throws UsuarioException {
        Jugador nuevo = new Jugador(cedula, password, nombreCompleto, saldo);
        nuevo.validar();
        jugadores.add(nuevo);
    }

    public Jugador loginJugador(String cedula, String password) throws UsuarioException {
        Jugador jugador = (Jugador) login(cedula, password, jugadores);
        if (jugador != null) {
            if (jugador.isLogueado()) {
                throw new UsuarioException("Acceso denegado. El usuario ya está logueado");
            } else {
                jugador.setLogueado(true);
                return jugador;
            }
        } else {
            throw new UsuarioException("Credenciales invalidas");
        }
    }

    public Administrador loginAdministrador(String nom, String pwd) throws UsuarioException {
        Administrador admin = (Administrador) login(nom, pwd, administradores);
        if (admin != null) {
            if (admin.isLogueado()) {
                throw new UsuarioException("El Administrador ya se encuentra Logueado");
            } else {
                admin.setLogueado(true);
                return admin;
            }
        } else {
            throw new UsuarioException("Credenciales invalidas");
        }
    }

    private Usuario login(String cedula, String password, ArrayList usuarios) throws UsuarioException {
        if (cedula == null || cedula.isEmpty() || password == null || password.isEmpty()) {
            throw new UsuarioException("Ambos campos son obligatorios");
        }
        Usuario usuario;
        for (Object u : usuarios) {
            usuario = (Usuario) u;
            if (usuario.getCedula().equals(cedula) && usuario.getPassword().equals(password)) {
                return usuario;
            }
        }
        return null;
    }

    public void logoutAdmin(String cedula) {
        logout(cedula, administradores);
    }

    public void logoutJugador(String cedula) throws UsuarioException {
        if (!Fachada.getInstancia().estaEnMesa(new Jugador(cedula, "", "", 0))) {
            logout(cedula, jugadores);
        } else {
            throw new UsuarioException("No puede salir, se encuentra en una mesa.");
        }
    }

    private void logout(String cedula, ArrayList usuarios) {
        for (Object o : usuarios) {
            Usuario u = (Usuario) o;
            if (u.getCedula().equals(cedula)) {
                u.setLogueado(false);
            }
        }
    }

}
