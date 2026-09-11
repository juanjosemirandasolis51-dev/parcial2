package org.example;

import java.util.Random;

import org.example.Model.Pokemon;
import org.example.Model.LineaEvolutiva;
import org.example.Service.SimulacionService;
import org.example.Service.Log;

public class Main {

    public static LineaEvolutiva crearLineaEvolutiva() {

        Pokemon charizard = new Pokemon("Charizard", 78, 84, 78, -1);
        Pokemon charmeleon = new Pokemon("Charmeleon", 58, 64, 58, 5000);
        Pokemon charmander = new Pokemon("Charmander", 39, 52, 43, 1500);

        charmander.setSiguienteEvolucion(charmeleon);
        charmeleon.setSiguienteEvolucion(charizard);

        return new LineaEvolutiva(charmander);

    }

    public static Pokemon[] crearHordaAleatoria(int cantidad) {

        Pokemon[] horda = new Pokemon[cantidad];
        Random random = new Random();

        for (int i = 0; i < cantidad; i++) {

            int vida = 20 + random.nextInt(31);
            int ataque = 20 + random.nextInt(16);
            int defensa = 20 + random.nextInt(21);

            horda[i] = new Pokemon("Enemigo", vida, ataque, defensa, 0);

        }

        return horda;

    }

    public static Pokemon[] crearHordaCaterpie(int cantidad) {

        Pokemon[] horda = new Pokemon[cantidad];

        for (int i = 0; i < cantidad; i++) {
            horda[i] = new Pokemon("Caterpie", 45, 30, 35, 0);
        }

        return horda;

    }

    public static void main(String[] args) {

        Log.iniciar();

        SimulacionService servicio = new SimulacionService();

        Log.escribir("PRUEBA UNITARIA CHARMANDER CONTRA RATTATA");
        LineaEvolutiva prueba = crearLineaEvolutiva();
        Pokemon rattata = new Pokemon("Rattata", 30, 56, 35, 0);

        boolean gano = servicio.batalla(prueba, rattata, true);

        Log.escribir("Gano la batalla " + gano);
        Log.escribir("Experiencia acumulada " + prueba.getExperienciaAcumulada() + " XP");
        Log.escribir("Fase actual " + prueba.getFaseActual().getNombre());
        Log.escribir();

        Log.escribir("ENTRENAMIENTO MASIVO CON HORDA ALEATORIA");
        LineaEvolutiva pokemonAleatorio = crearLineaEvolutiva();
        Pokemon[] hordaAleatoria = crearHordaAleatoria(100000);

        Log.escribir("Fase inicial " + pokemonAleatorio.getFaseActual().getNombre());
        Log.escribir();

        servicio.iniciarEntrenamientoMasivo(pokemonAleatorio, hordaAleatoria);
        Log.escribir();

        Log.escribir("PRUEBA DE ESTRES CON 100000 CATERPIE");
        LineaEvolutiva miPokemon = crearLineaEvolutiva();
        Pokemon[] hordaCaterpie = crearHordaCaterpie(100000);

        Log.escribir("Fase inicial " + miPokemon.getFaseActual().getNombre());
        Log.escribir();

        servicio.iniciarEntrenamientoMasivo(miPokemon, hordaCaterpie);

        Log.cerrar();

    }

}
