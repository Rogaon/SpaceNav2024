package com.spacenav2024.strategies;

import com.spacenav2024.entities.Meteorito;

public class MovimientoZigzag implements EstrategiaMovimiento {
    private float amplitud = 50; // Amplitud del zigzag
    private float frecuencia = 2; // Frecuencia del zigzag

    @Override
    public void mover(Meteorito meteorito, float delta) {
        meteorito.setX(meteorito.getX() + (float) Math.sin(meteorito.getY() / frecuencia) * amplitud * delta);
        meteorito.setY(meteorito.getY() - meteorito.getVelocidadY() * delta);
    }
}

