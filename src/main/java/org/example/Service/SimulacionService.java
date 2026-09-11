package org.example.Service;

import java.util.LinkedList;

import org.example.Model.CajaNegra;
import org.example.Model.LineaEvolutiva;
import org.example.Model.Pokemon;
import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.info.GraphLayout;
import oshi.SystemInfo;

public class SimulacionService {

    public boolean batalla (LineaEvolutiva miPokemon, Pokemon enemigo, boolean mostrarDetalle){

        int miHp = miPokemon.getFaseActual().getPuntosDeVidaMaximos();
        int enemHp = enemigo.getPuntosDeVidaMaximos();
        int turno = 1;

        while (miHp > 0 && enemHp > 0){

            int danoMiPokemon = Math.max( 1, miPokemon.getFaseActual().getAtaque() - enemigo.getDefensa());
            enemHp -= danoMiPokemon;

            if (enemHp < 0) {
                enemHp = 0;
            }

            if (mostrarDetalle) {
                Log.escribir("Turno " + turno + " " + miPokemon.getFaseActual().getNombre() + " ataca y hace " + danoMiPokemon + " de dano y " + enemigo.getNombre() + " queda con " + enemHp + " HP");
            }

            turno++;

            if (enemHp == 0) {
                miPokemon.agregarExperiencia(50);
                return true;
            }

            int danoEnemigo = Math.max( 1, enemigo.getAtaque() - miPokemon.getFaseActual().getDefensa());
            miHp -= danoEnemigo;

            if (miHp < 0) {
                miHp = 0;
            }

            if (mostrarDetalle) {
                Log.escribir("Turno " + turno + " " + enemigo.getNombre() + " ataca y hace " + danoEnemigo + " de dano y " + miPokemon.getFaseActual().getNombre() + " queda con " + miHp + " HP");
            }

            turno++;

        }

        return false;

    }

    public void iniciarEntrenamientoMasivo (LineaEvolutiva miPokemon, Pokemon[] hordaEnemigos){

        Log.escribir("MEDICION DE MEMORIA CON JOL");
        medirMemoria(miPokemon);
        Log.escribir();

        long ramAntes = new SystemInfo().getHardware().getMemory().getAvailable();
        Log.escribir("RAM disponible antes de las batallas " + (ramAntes / 1048576) + " MB");
        Log.escribir();

        long tiempoInicio = System.nanoTime();
        int victorias = 0;

        for (int  i= 0;  i < hordaEnemigos.length; i++) {

            String faseAntes = miPokemon.getFaseActual().getNombre();
            boolean gano = batalla(miPokemon, hordaEnemigos[i], false);

            if (gano) {

                victorias++;
                String faseDespues = miPokemon.getFaseActual().getNombre();

                if (!faseDespues.equals(faseAntes)) {
                    Log.escribir("En la batalla numero " + (i + 1) + " con " + miPokemon.getExperienciaAcumulada() + " XP evoluciono a " + faseDespues);
                }

            }

        }

        long tiempoFin = System.nanoTime();
        long tiempoTotal = tiempoFin - tiempoInicio;
        long ramDespues = new SystemInfo().getHardware().getMemory().getAvailable();

        Log.escribir();
        Log.escribir("Batallas ganadas " + victorias);
        Log.escribir("Experiencia acumulada " + miPokemon.getExperienciaAcumulada() + " XP");
        Log.escribir("Fase final " + miPokemon.getFaseActual().getNombre());
        Log.escribir("Tiempo total " + tiempoTotal + " nanosegundos");
        Log.escribir("Tiempo total " + (tiempoTotal / 1000000) + " milisegundos");
        Log.escribir("RAM disponible despues de las batallas " + (ramDespues / 1048576) + " MB");
        Log.escribir("Diferencia de RAM " + (ramAntes - ramDespues) + " bytes");

    }

    public void medirMemoria (LineaEvolutiva miPokemon){

        Log.escribir("Tamano de un nodo Pokemon " + ClassLayout.parseInstance(miPokemon.getFaseActual()).instanceSize() + " bytes");
        Log.escribir("Tamano de LineaEvolutiva " + ClassLayout.parseInstance(miPokemon).instanceSize() + " bytes");
        Log.escribir("Peso total de la linea evolutiva completa " + GraphLayout.parseInstance(miPokemon).totalSize() + " bytes");

    }


    public String nombresDelEquipo (LinkedList<LineaEvolutiva> equipo){

        String texto = "";

        for (int i = 0; i < equipo.size(); i++) {
            texto = texto + equipo.get(i).getNombre() + " ";
        }

        return texto;

    }

    public void iniciarHordaConRotacion (LinkedList<LineaEvolutiva> equipo, Pokemon[] hordaEnemigos, int bloque, CajaNegra caja, boolean mostrarDetalle){

        long ramAntes = new SystemInfo().getHardware().getMemory().getAvailable();
        Log.escribir("RAM disponible antes de la horda " + (ramAntes / 1048576) + " MB");
        Log.escribir();

        long tiempoInicio = System.nanoTime();
        int victorias = 0;
        int enemigo = 0;
        int turnoDeCampo = 1;

        while (enemigo < hordaEnemigos.length) {

            LineaEvolutiva miPokemon = equipo.removeFirst();
            int peleas = 0;

            while (peleas < bloque && enemigo < hordaEnemigos.length) {

                String faseAntes = miPokemon.getFaseActual().getNombre();
                boolean gano = batalla(miPokemon, hordaEnemigos[enemigo], false);

                if (gano) {

                    victorias++;
                    caja.registrarVictoria(miPokemon.getNombre(), hordaEnemigos[enemigo].getNombre());
                    String faseDespues = miPokemon.getFaseActual().getNombre();

                    if (!faseDespues.equals(faseAntes)) {
                        Log.escribir(miPokemon.getNombre() + " evoluciono a " + faseDespues + " con " + miPokemon.getExperienciaAcumulada() + " XP");
                    }

                }

                enemigo++;
                peleas++;

            }

            equipo.addLast(miPokemon);

            if (mostrarDetalle) {

                Log.escribir("Turno de campo " + turnoDeCampo + " salio " + miPokemon.getNombre() + " y derroto " + peleas + " enemigos");
                Log.escribir("Cola del equipo " + nombresDelEquipo(equipo));
                Log.escribir("Caja negra");
                caja.mostrar();
                Log.escribir();

            }

            turnoDeCampo++;

        }

        long tiempoFin = System.nanoTime();
        long tiempoTotal = tiempoFin - tiempoInicio;
        long ramDespues = new SystemInfo().getHardware().getMemory().getAvailable();

        Log.escribir("Enemigos derrotados " + victorias);
        Log.escribir("Turnos de campo usados " + (turnoDeCampo - 1));
        Log.escribir("Cola final del equipo " + nombresDelEquipo(equipo));
        Log.escribir("Tiempo total " + tiempoTotal + " nanosegundos");
        Log.escribir("Tiempo total " + (tiempoTotal / 1000000) + " milisegundos");
        Log.escribir("RAM disponible despues de la horda " + (ramDespues / 1048576) + " MB");
        Log.escribir("Diferencia de RAM " + (ramAntes - ramDespues) + " bytes");
        Log.escribir();

        Log.escribir("Estado final de la caja negra con capacidad " + caja.getCapacidad());
        caja.mostrar();
        Log.escribir();

        for (int i = 0; i < equipo.size(); i++) {
            LineaEvolutiva integrante = equipo.get(i);
            Log.escribir(integrante.getNombre() + " termino como " + integrante.getFaseActual().getNombre() + " con " + integrante.getExperienciaAcumulada() + " XP");
        }

    }

}
