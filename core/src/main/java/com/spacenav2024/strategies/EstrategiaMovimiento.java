package com.spacenav2024.strategies;

import com.spacenav2024.entities.Meteorito;

public interface EstrategiaMovimiento {
    void mover(Meteorito meteorito, float delta);
}

