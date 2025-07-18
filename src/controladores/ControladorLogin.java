/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controladores;

/**
 *
 * @author angel
 */
public abstract class ControladorLogin {

    VistaLogin vista;

    public ControladorLogin(VistaLogin vista) {
        this.vista = vista;
    }

    public void login(String cedula, String password) {
        Object usuario = llamarLogin(cedula, password);
        if (usuario != null) {
            vista.cerrar();
            vista.accionSiguiente(usuario);
        }
    }

    public abstract Object llamarLogin(String cedula, String password);
}
