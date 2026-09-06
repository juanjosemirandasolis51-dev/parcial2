package org.example;

import org.example.Model.Pokemon;
import org.example.Model.LineaEvolutiva;
import org.example.Service.SimulacionService;

public class Main {

    public static void main(String[] args) {

        // --- Crear los nodos (de atrás hacia adelante) ---
        Pokemon charizard = new Pokemon("Charizard", 78, 84, 78, -1);
        Pokemon charmeleon = new Pokemon("Charmeleon", 58, 64, 58, 5000);
        Pokemon charmander = new Pokemon("Charmander", 39, 52, 43, 1500);

        // --- Armar la cadena evolutiva ---
        charmander.setSiguienteEvolucion(charmeleon);
        charmeleon.setSiguienteEvolucion(charizard);

        // --- Crear la línea evolutiva ---
        LineaEvolutiva miPokemon = new LineaEvolutiva(charmander);

        SimulacionService servicio = new SimulacionService();

        // ========== PRUEBA UNITARIA ==========
        System.out.println("=== PRUEBA UNITARIA: Charmander vs Rattata ===");
        System.out.println("Antes: " + miPokemon.getFaseActual().getNombre());

        Pokemon rattata = new Pokemon("Rattata", 30, 56, 35, 0);
        Pokemon[] pruebaUnitaria = { rattata };
        servicio.iniciarEntrenamientoMasivo(miPokemon, pruebaUnitaria);

        System.out.println("XP acumulada: " + miPokemon.getExperienciaAcumulada());
        System.out.println("Fase actual: " + miPokemon.getFaseActual().getNombre());

        // ========== PRUEBA DE ESTRÉS ==========
        // Reiniciar con nueva línea evolutiva
        charizard = new Pokemon("Charizard", 78, 84, 78, -1);
        charmeleon = new Pokemon("Charmeleon", 58, 64, 58, 5000);
        charmander = new Pokemon("Charmander", 39, 52, 43, 1500);
        charmander.setSiguienteEvolucion(charmeleon);
        charmeleon.setSiguienteEvolucion(charizard);
        miPokemon = new LineaEvolutiva(charmander);

        System.out.println("\n=== PRUEBA DE ESTRÉS: 100,000 Caterpie ===");
        System.out.println("Fase inicial: " + miPokemon.getFaseActual().getNombre());

        // Generar horda de 100,000 Caterpie
        Pokemon[] horda = new Pokemon[100000];
        for (int i = 0; i < horda.length; i++) {
            horda[i] = new Pokemon("Caterpie", 45, 30, 35, 0);
        }

        servicio.iniciarEntrenamientoMasivo(miPokemon, horda);

        System.out.println("Fase final: " + miPokemon.getFaseActual().getNombre());
        System.out.println("XP total: " + miPokemon.getExperienciaAcumulada());
    }
}