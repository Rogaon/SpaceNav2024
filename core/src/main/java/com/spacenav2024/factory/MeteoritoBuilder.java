package com.spacenav2024.factory;

import com.spacenav2024.entities.Meteorito;

public interface MeteoritoBuilder {
    void setTipo(String tipo);
    void setPosicion(float x, float y);
    void setVelocidad(float velocidad);
    void setImpactosNecesarios(int impactos);
    Meteorito construir();
}

