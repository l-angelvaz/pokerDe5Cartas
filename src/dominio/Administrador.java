/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dominio;

import excepciones.UsuarioException;

/**
 *
 * @author angel
 */
public class Administrador extends Usuario {

    public Administrador(String cedula, String password, String nombreCompleto) {
        super(cedula, password, nombreCompleto);
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
    }

    @Override
    public String toString() {
        return "Administrador{ Nombre= " + super.getNombreCompleto() + " }";
    }

}
