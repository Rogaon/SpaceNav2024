package com.spacenav2024.strategies;

import com.badlogic.gdx.Gdx;
import com.spacenav2024.entities.Meteorito;

public class EstrategiaMovimientoDescendente implements EstrategiaMovimiento {
    @Override
    public void mover(Meteorito meteorito, float delta) {
        // Movimiento vertical descendente
        meteorito.setY(meteorito.getY() - meteorito.getVelocidadY() * delta);

        // Movimiento horizontal y rebotes en bordes laterales
        meteorito.setX(meteorito.getX() + meteorito.getVelocidadX() * delta);
        if (meteorito.getX() <= 0 || meteorito.getX() + meteorito.getWidth() >= Gdx.graphics.getWidth()) {
            meteorito.setVelocidadX(-meteorito.getVelocidadX());
        }
    }
}
