package org.example.Model;

import java.util.Objects;

public class Pokemon {

    private String nombre;
    private int puntosDeVidaMaximos;
    private int ataque;
    private int defensa;
    private int experienciaRequerida;
    private Pokemon siguienteEvolucion;

    public Pokemon(String nombre, int puntosDeVidaMaximos, int ataque, int defensa, int experienciaRequerida) {
        this.nombre = nombre;
        this.puntosDeVidaMaximos = puntosDeVidaMaximos;
        this.ataque = ataque;
        this.defensa = defensa;
        this.experienciaRequerida = experienciaRequerida;
        this.siguienteEvolucion = null;
    }

    public String getNombre() {
        return nombre;
    }

    public int getPuntosDeVidaMaximos() {
        return puntosDeVidaMaximos;
    }

    public int getAtaque() {
        return ataque;
    }

    public int getDefensa() {
        return defensa;
    }

    public int getExperienciaRequerida() {
        return experienciaRequerida;
    }

    public Pokemon getSiguienteEvolucion() {
        return siguienteEvolucion;
    }


    public void setSiguienteEvolucion(Pokemon siguienteEvolucion) {
        this.siguienteEvolucion = siguienteEvolucion;
    }

    @Override
    public String toString() {
        return "Pokemon{" +
                "nombre='" + nombre + '\'' +
                ", puntosDeVidaMaximos=" + puntosDeVidaMaximos +
                ", ataque=" + ataque +
                ", defensa=" + defensa +
                ", experienciaRequerida=" + experienciaRequerida +
                ", siguienteEvolucion=" + siguienteEvolucion +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Pokemon pokemon = (Pokemon) o;
        return puntosDeVidaMaximos == pokemon.puntosDeVidaMaximos && ataque == pokemon.ataque && defensa == pokemon.defensa && experienciaRequerida == pokemon.experienciaRequerida && Objects.equals(nombre, pokemon.nombre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombre, puntosDeVidaMaximos, ataque, defensa, experienciaRequerida);
    }
}
