package com.spacenav2024.factory;

import com.badlogic.gdx.math.MathUtils;
import com.spacenav2024.entities.Meteorito;

public class MeteoritoDirector {
    private MeteoritoBuilder builder;

    public MeteoritoDirector(MeteoritoBuilder builder) {
        this.builder = builder;
    }

    public Meteorito crearMeteorito(String tipo, int nivel) {
        float x = MathUtils.random(0, 800); // Posición horizontal aleatoria
        float y = 600;                      // Posición inicial en la parte superior
        float velocidad = 50 + nivel * 10;  // Incrementar velocidad por nivel
        int impactos = tipo.equals("grande") ? 3 : tipo.equals("mediano") ? 2 : 1;

        builder.setTipo(tipo);
        builder.setPosicion(x, y);
        builder.setVelocidad(velocidad);
        builder.setImpactosNecesarios(impactos);

        return builder.construir();
    }
}

