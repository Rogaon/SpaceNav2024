package com.spacenav2024.utils;

import com.badlogic.gdx.utils.Array;
import com.spacenav2024.entities.Jugador;
import com.spacenav2024.entities.Meteorito;
import com.spacenav2024.entities.Proyectil;
import com.badlogic.gdx.audio.Sound;

public class SistemaColisiones {

    public static int manejarColisiones(Jugador jugador, Array<Meteorito> meteoritos, Array<Proyectil> proyectiles, Sound sonidoDamage) {
        int puntos = 0;

        // Colisiones entre proyectiles y meteoritos
        for (Proyectil proyectil : proyectiles) {
            for (Meteorito meteorito : meteoritos) {
                if (proyectil.getRectangulo().overlaps(meteorito.getRectangulo())) {
                    meteorito.recibirImpacto();
                    proyectiles.removeValue(proyectil, true);
                    if (meteorito.estaDestruido()) {
                        meteoritos.removeValue(meteorito, true);
                        puntos += 10;
                    }
                    break;
                }
            }
        }

        // Colisiones entre meteoritos
        for (int i = 0; i < meteoritos.size; i++) {
            Meteorito m1 = meteoritos.get(i);
            for (int j = i + 1; j < meteoritos.size; j++) {
                Meteorito m2 = meteoritos.get(j);
                if (m1.getRectangulo().overlaps(m2.getRectangulo())) {
                    // Intercambiar direcciones usando getters y setters
                    float tempX = m1.getVelocidadX();
                    float tempY = m1.getVelocidadY();
                    m1.setVelocidadX(m2.getVelocidadX());
                    m1.setVelocidadY(m2.getVelocidadY());
                    m2.setVelocidadX(tempX);
                    m2.setVelocidadY(tempY);
                }
            }
        }

        // Colisiones entre meteoritos y el jugador
        for (Meteorito meteorito : meteoritos) {
            if (jugador.getRectangulo().overlaps(meteorito.getRectangulo())) {
                if (jugador.getSalud() > 0) {
                    jugador.recibirDanio(1);
                    sonidoDamage.play();
                }
                meteoritos.removeValue(meteorito, true);
                break;
            }
        }

        return puntos;
    }
}
