package com.spacenav2024.utils;

import com.badlogic.gdx.utils.Array;
import com.spacenav2024.entities.Jugador;
import com.spacenav2024.entities.Meteorito;
import com.spacenav2024.entities.Proyectil;

public class SistemaColisiones {
    public static int manejarColisiones(Jugador jugador, Array<Meteorito> meteoritos, Array<Proyectil> proyectiles) {
        int puntosGanados = 0;

        for (int i = proyectiles.size - 1; i >= 0; i--) {
            Proyectil proyectil = proyectiles.get(i);
            for (int j = meteoritos.size - 1; j >= 0; j--) {
                Meteorito meteorito = meteoritos.get(j);
                if (proyectil.getRectangulo().overlaps(meteorito.getRectangulo())) {
                    proyectiles.removeIndex(i);
                    meteoritos.removeIndex(j);
                    puntosGanados += 10;  // Sumar puntos por cada colisión
                    break;
                }
            }
        }

        return puntosGanados;
    }
}
