package org.example.Service;

import org.example.Model.LineaEvolutiva;
import org.example.Model.Pokemon;

public class SimulacionService {

    public void iniciarEntrenamientoMasivo (LineaEvolutiva miPokemon, Pokemon[] hordaEnemigos){

        for (int  i= 0;  i < hordaEnemigos.length; i++) {

            int miHp = miPokemon.getFaseActual().getPuntosDeVidaMaximos();
            int enemHp = hordaEnemigos [i].getPuntosDeVidaMaximos();

            while (miHp > 0 && enemHp > 0){

                int danoMiPokemon = Math.max( 1, miPokemon.getFaseActual().getAtaque() - hordaEnemigos[i].getDefensa());
                enemHp -= danoMiPokemon;

                if (enemHp <= 0) {
                    miPokemon.agregarExperiencia(50);

                    break;
                }

                int danoEnemigo = Math.max( 1, hordaEnemigos[i].getAtaque() - miPokemon.getFaseActual().getDefensa());
                miHp -= danoEnemigo;

            }

        }

    }

}
