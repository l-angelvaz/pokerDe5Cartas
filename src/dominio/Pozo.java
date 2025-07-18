/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dominio;

/**
 *
 * @author angel
 */
public class Pozo {

    private int monto;
    
    public Pozo(int valor) {
        this.monto = valor;
    }

    public void incrementarMonto(int valor) {
        monto += valor;
    }

    public int getMonto() {
        return monto;
    }

    public void vaciar() {
        this.monto = 0;
    }

}
