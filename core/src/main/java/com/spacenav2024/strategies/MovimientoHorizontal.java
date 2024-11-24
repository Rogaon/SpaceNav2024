package com.spacenav2024.strategies;

import com.spacenav2024.entities.Meteorito;

public class MovimientoHorizontal implements EstrategiaMovimiento {
    @Override
    public void mover(Meteorito meteorito, float delta) {
        meteorito.setX(meteorito.getX() + meteorito.getVelocidadX() * delta);
        meteorito.setY(meteorito.getY() - meteorito.getVelocidadY() * delta);
    }
}

