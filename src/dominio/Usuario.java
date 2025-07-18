/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dominio;

import excepciones.UsuarioException;
import java.util.Objects;

/**
 *
 * @author angel
 */
public abstract class Usuario {
    private String cedula;
    private String password;
    private String nombreCompleto;
    private boolean logueado;

    public Usuario(String cedula, String password, String nombreCompleto) {
        this.cedula = cedula;
        this.password = password;
        this.nombreCompleto = nombreCompleto;
        this.logueado = false;
    }

    public String getCedula() {
        return cedula;
    }

    public String getPassword() {
        return password;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public boolean isLogueado() {
        return logueado;
    }

    public void setLogueado(boolean logueado) {
        this.logueado = logueado;
    }
    
    public abstract void validar() throws UsuarioException; 

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Usuario other = (Usuario) obj;
        return Objects.equals(this.cedula, other.cedula);
    }
    
    
}
