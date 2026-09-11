package org.example.Model;

import java.util.LinkedList;

import org.example.Service.Log;

public class CajaNegra {

    private LinkedList<ReporteBatalla> registros;
    private int capacidad;

    public CajaNegra(int capacidad) {

        this.registros = new LinkedList<ReporteBatalla>();
        this.capacidad = capacidad;

    }

    public void registrarVictoria(String nombrePokemon, String nombreEnemigo) {

        if (registros.size() > 0) {

            ReporteBatalla ultimo = registros.getLast();

            if (ultimo.getNombrePokemon().equals(nombrePokemon) && ultimo.getNombreEnemigo().equals(nombreEnemigo)) {

                ultimo.sumarDerrotado();
                return;

            }

        }

        if (registros.size() == capacidad) {
            registros.removeFirst();
        }

        registros.addLast(new ReporteBatalla(nombrePokemon, nombreEnemigo));

    }

    public void mostrar() {

        for (int i = 0; i < registros.size(); i++) {
            Log.escribir(registros.get(i).toString());
        }

    }

    public int getCapacidad() {
        return capacidad;
    }

    public LinkedList<ReporteBatalla> getRegistros() {
        return registros;
    }

}
