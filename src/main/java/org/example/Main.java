package org.example;

import java.util.LinkedList;
import java.util.Random;

import org.example.Model.CajaNegra;
import org.example.Model.Pokemon;
import org.example.Model.LineaEvolutiva;
import org.example.Service.SimulacionService;
import org.example.Service.Log;

public class Main {

    public static LineaEvolutiva crearLineaCharmander() {

        Pokemon charizard = new Pokemon("Charizard", 78, 84, 78, -1);
        Pokemon charmeleon = new Pokemon("Charmeleon", 58, 64, 58, 5000);
        Pokemon charmander = new Pokemon("Charmander", 39, 52, 43, 1500);

        charmander.setSiguienteEvolucion(charmeleon);
        charmeleon.setSiguienteEvolucion(charizard);

        return new LineaEvolutiva(charmander);

    }

    public static LineaEvolutiva crearLineaBulbasaur() {

        Pokemon venusaur = new Pokemon("Venusaur", 80, 82, 83, -1);
        Pokemon ivysaur = new Pokemon("Ivysaur", 60, 62, 63, 5000);
        Pokemon bulbasaur = new Pokemon("Bulbasaur", 45, 49, 49, 1500);

        bulbasaur.setSiguienteEvolucion(ivysaur);
        ivysaur.setSiguienteEvolucion(venusaur);

        return new LineaEvolutiva(bulbasaur);

    }

    public static LineaEvolutiva crearLineaSquirtle() {

        Pokemon blastoise = new Pokemon("Blastoise", 79, 83, 100, -1);
        Pokemon wartortle = new Pokemon("Wartortle", 59, 63, 80, 5000);
        Pokemon squirtle = new Pokemon("Squirtle", 44, 48, 65, 1500);

        squirtle.setSiguienteEvolucion(wartortle);
        wartortle.setSiguienteEvolucion(blastoise);

        return new LineaEvolutiva(squirtle);

    }

    public static LinkedList<LineaEvolutiva> crearEquipo() {

        LinkedList<LineaEvolutiva> equipo = new LinkedList<LineaEvolutiva>();

        equipo.addLast(crearLineaBulbasaur());
        equipo.addLast(crearLineaCharmander());
        equipo.addLast(crearLineaSquirtle());

        return equipo;

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
        LineaEvolutiva prueba = crearLineaCharmander();
        Pokemon rattata = new Pokemon("Rattata", 30, 56, 35, 0);

        boolean gano = servicio.batalla(prueba, rattata, true);

        Log.escribir("Gano la batalla " + gano);
        Log.escribir("Experiencia acumulada " + prueba.getExperienciaAcumulada() + " XP");
        Log.escribir("Fase actual " + prueba.getFaseActual().getNombre());
        Log.escribir();

        Log.escribir("ENTRENAMIENTO MASIVO CON HORDA ALEATORIA");
        LineaEvolutiva pokemonAleatorio = crearLineaCharmander();
        Pokemon[] hordaAleatoria = crearHordaAleatoria(100000);

        Log.escribir("Fase inicial " + pokemonAleatorio.getFaseActual().getNombre());
        Log.escribir();

        servicio.iniciarEntrenamientoMasivo(pokemonAleatorio, hordaAleatoria);
        Log.escribir();

        Log.escribir("CASO DE PRUEBA CON ROTACION DE EQUIPO Y 250 CATERPIE");
        LinkedList<LineaEvolutiva> equipoPrueba = crearEquipo();
        Pokemon[] hordaPrueba = crearHordaCaterpie(250);
        CajaNegra cajaPrueba = new CajaNegra(3);

        Log.escribir("Cola inicial del equipo " + servicio.nombresDelEquipo(equipoPrueba));
        Log.escribir("Turno de campo de 50 enemigos y caja negra de 3 registros");
        Log.escribir();

        servicio.iniciarHordaConRotacion(equipoPrueba, hordaPrueba, 50, cajaPrueba, true);
        Log.escribir();

        Log.escribir("HORDA MASIVA CON ROTACION DE EQUIPO Y 100000 CATERPIE");
        LinkedList<LineaEvolutiva> equipo = crearEquipo();
        Pokemon[] hordaMasiva = crearHordaCaterpie(100000);
        CajaNegra caja = new CajaNegra(10);

        Log.escribir("Cola inicial del equipo " + servicio.nombresDelEquipo(equipo));
        Log.escribir("Turno de campo de 50 enemigos y caja negra de 10 registros");
        Log.escribir();

        servicio.iniciarHordaConRotacion(equipo, hordaMasiva, 50, caja, false);

        Log.cerrar();

    }

}
