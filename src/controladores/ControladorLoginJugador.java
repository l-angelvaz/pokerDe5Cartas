/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controladores;

import dominio.Fachada;
import excepciones.UsuarioException;

/**
 *
 * @author angel
 */
public class ControladorLoginJugador extends ControladorLogin {

    public ControladorLoginJugador(VistaLogin vista) {
        super(vista);
    }

    @Override
    public Object llamarLogin(String cedula, String password) {
        try {
            return Fachada.getInstancia().loginJugador(cedula, password);
        } catch (UsuarioException ex) {
            vista.mostrarMensaje(ex.getMessage());
            return null;
        }
    }

}
