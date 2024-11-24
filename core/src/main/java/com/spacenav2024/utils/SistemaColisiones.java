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
        for (Proyectil proyectil : new Array<>(proyectiles)) { // Evitar errores concurrentes
            for (Meteorito meteorito : new Array<>(meteoritos)) { // Iterar sobre una copia
                if (proyectil.getRectangulo().overlaps(meteorito.getRectangulo())) {
                    meteorito.recibirImpacto();
                    proyectiles.removeValue(proyectil, true);
                    if (meteorito.estaDestruido()) {
                        meteoritos.removeValue(meteorito, true);
                        puntos += 10; // Sumar puntos por destruir el meteorito
                    }
                    break; // Salir del bucle interno después de manejar la colisión
                }
            }
        }

        // Colisiones entre meteoritos y el jugador
        for (Meteorito meteorito : new Array<>(meteoritos)) { // Evitar errores concurrentes
            if (jugador.getRectangulo().overlaps(meteorito.getRectangulo())) {
                if (jugador.getSalud() > 0) {
                    jugador.recibirDanio(1); // Reducir la salud del jugador
                    sonidoDamage.play();    // Reproducir sonido de daño
                }
                meteoritos.removeValue(meteorito, true); // Eliminar meteorito tras colisión
                break; // Salir del bucle después de manejar la colisión
            }
        }

        // Colisiones entre meteoritos (rebote)
        for (int i = 0; i < meteoritos.size; i++) {
            Meteorito m1 = meteoritos.get(i);
            for (int j = i + 1; j < meteoritos.size; j++) {
                Meteorito m2 = meteoritos.get(j);
                if (m1.getRectangulo().overlaps(m2.getRectangulo())) {
                    // Intercambiar velocidades entre los meteoritos
                    float tempVelX = m1.getVelocidadX();
                    float tempVelY = m1.getVelocidadY();
                    m1.setVelocidadX(m2.getVelocidadX());
                    m1.setVelocidadY(m2.getVelocidadY());
                    m2.setVelocidadX(tempVelX);
                    m2.setVelocidadY(tempVelY);
                }
            }
        }

        return puntos; // Devolver puntos obtenidos por destruir meteoritos
    }
}

