package org.example.Model;


import java.util.Objects;

public class LineaEvolutiva {

    private Pokemon faseActual;
    private  int experienciaAcumulada;


    public LineaEvolutiva(Pokemon faseInicial){

        faseActual=faseInicial;
        experienciaAcumulada = 0;

    }

    public void agregarExperiencia(int xp){

        experienciaAcumulada += xp;
        verificarEvolucion();

    }

    private void verificarEvolucion (){

        while (faseActual.getSiguienteEvolucion() != null && experienciaAcumulada >= faseActual.getExperienciaRequerida()){

            faseActual = faseActual.getSiguienteEvolucion();

        }

    }

    public Pokemon getFaseActual() {
        return faseActual;
    }

    public int getExperienciaAcumulada() {
        return experienciaAcumulada;
    }

    @Override
    public String toString() {
        return "LineaEvolutiva{" +
                "faseActual=" + faseActual +
                ", experienciaAcumulada=" + experienciaAcumulada +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        LineaEvolutiva that = (LineaEvolutiva) o;
        return experienciaAcumulada == that.experienciaAcumulada && Objects.equals(faseActual, that.faseActual);
    }

    @Override
    public int hashCode() {
        return Objects.hash(faseActual, experienciaAcumulada);
    }
}

