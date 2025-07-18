/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vistas;

import controladores.ControladorLogin;
import controladores.ControladorLoginAdministrador;
import dominio.Administrador;
import iuGrafica.InicioAdministrador;
import java.awt.Frame;

/**
 *
 * @author angel
 */
public class LoginAdministrador extends LoginAbstracto {

    public LoginAdministrador(Frame parent, boolean modal) {
        super(parent, modal, "Inicio de Sesion Administrador");
    }

    @Override
    public ControladorLogin crearControlador() {
        return new ControladorLoginAdministrador(this);
    }

    @Override
    public void accionSiguiente(Object u) {
        new InicioAdministrador((Administrador) u).setVisible(true);
    }

}
