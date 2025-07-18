/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dominio;

import excepciones.ManoException;
import java.util.ArrayList;

/**
 *
 * @author angel
 */
public class Apuesta {

    private int valor;
    private ArrayList<Jugador> pagaron;
    private Jugador creador;

    public Apuesta(int monto, Jugador jugador) {
        valor = monto;
        creador = jugador;
        pagaron = new ArrayList();
    }

    public void pagarApuesta(Jugador j) {
        pagaron.add(j);
    }

    public int getValor() {
        return valor;
    }

    public ArrayList<Jugador> getPagaron() {
        return pagaron;
    }

    public Jugador getCreador() {
        return creador;
    }

    public void agregarPagador(Jugador jugador) throws ManoException {
        if (!getPagaron().contains(jugador)) {
            this.pagaron.add(jugador);
        } else {
            throw new ManoException("Ya ha pagado la apuesta.");
        }
    }
}
