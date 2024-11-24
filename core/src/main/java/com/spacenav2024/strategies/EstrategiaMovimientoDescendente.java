package com.spacenav2024.strategies;

import com.spacenav2024.entities.Meteorito;

public class EstrategiaMovimientoDescendente implements EstrategiaMovimiento {
    @Override
    public void mover(Meteorito meteorito, float delta) {
        meteorito.setY(meteorito.getY() - meteorito.getVelocidadY() * delta);

        // Rebote lateral
        if (meteorito.getX() <= 0 || meteorito.getX() + meteorito.getWidth() >= com.badlogic.gdx.Gdx.graphics.getWidth()) {
            meteorito.setVelocidadX(-meteorito.getVelocidadX());
        }

        meteorito.setX(meteorito.getX() + meteorito.getVelocidadX() * delta);
    }
}
