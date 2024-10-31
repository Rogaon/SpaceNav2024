package com.spacenav2024.utils;

import com.badlogic.gdx.utils.Array;
import com.spacenav2024.entities.Jugador;
import com.spacenav2024.entities.Meteorito;
import com.spacenav2024.entities.Proyectil;

public class SistemaColisiones {

    /**
     * Detecta colisiones entre el jugador y la lista de meteoritos.
     * 
     * @param jugador El jugador.
     * @param meteoritos La lista de meteoritos en pantalla.
     * @return true si ocurre una colisión entre el jugador y algún meteorito, de lo contrario false.
     */
    public static boolean detectarColisionConMeteorito(Jugador jugador, Array<Meteorito> meteoritos) {
        for (Meteorito meteorito : meteoritos) {
            if (jugador.getRectangulo().overlaps(meteorito.getRectangulo())) {
                meteoritos.removeValue(meteorito, true); // Remover el meteorito tras la colisión
                return true; // Colisión detectada
            }
        }
        return false; // No hubo colisión
    }

    /**
     * Maneja colisiones entre proyectiles y meteoritos, otorgando puntos por cada meteorito destruido.
     * 
     * @param jugador El jugador.
     * @param meteoritos La lista de meteoritos en pantalla.
     * @param proyectiles La lista de proyectiles en pantalla.
     * @return La cantidad de puntos obtenidos por destruir meteoritos.
     */
    public static int manejarColisiones(Jugador jugador, Array<Meteorito> meteoritos, Array<Proyectil> proyectiles) {
        int puntos = 0;

        // Colisiones entre proyectiles y meteoritos
        for (Proyectil proyectil : proyectiles) {
            for (Meteorito meteorito : meteoritos) {
                if (proyectil.getRectangulo().overlaps(meteorito.getRectangulo())) {
                    meteorito.recibirImpacto(); // Reducir vida del meteorito
                    proyectiles.removeValue(proyectil, true); // Eliminar proyectil tras colisión
                    if (meteorito.estaDestruido()) {
                        meteoritos.removeValue(meteorito, true); // Eliminar meteorito si está destruido
                        puntos += 10; // Sumar puntos por meteorito destruido
                    }
                    break;
                }
            }
        }

        return puntos;
    }
}
