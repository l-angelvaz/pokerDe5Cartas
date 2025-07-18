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
public class ControladorLoginAdministrador extends ControladorLogin {

    public ControladorLoginAdministrador(VistaLogin vista) {
        super(vista);
    }

    @Override
    public Object llamarLogin(String cedula, String password) {
        try {
            return Fachada.getInstancia().loginAdministrador(cedula, password);
        } catch (UsuarioException ex) {
            vista.mostrarMensaje(ex.getMessage());
            return null;
        }
    }

}
