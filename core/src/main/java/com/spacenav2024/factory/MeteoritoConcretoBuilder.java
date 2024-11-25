package com.spacenav2024.factory;

import com.badlogic.gdx.graphics.Texture;
import com.spacenav2024.entities.Meteorito;
import com.spacenav2024.entities.MeteoritoGrande;
import com.spacenav2024.entities.MeteoritoMediano;
import com.spacenav2024.entities.MeteoritoPequeno;

public class MeteoritoConcretoBuilder implements MeteoritoBuilder {
    private String tipo;
    private float x;
    private float y;
    private float velocidad;
    private int impactos;

    @Override
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @Override
    public void setPosicion(float x, float y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public void setVelocidad(float velocidad) {
        this.velocidad = velocidad;
    }

    @Override
    public void setImpactosNecesarios(int impactos) {
        this.impactos = impactos;
    }

    @Override
    public Meteorito construir() {
        switch (tipo) {
            case "pequeno":
                return new MeteoritoPequeno(new Texture("meteoritosmall.png"), x, y, velocidad);
            case "mediano":
                return new MeteoritoMediano(new Texture("meteoritomedium.png"), x, y, velocidad);
            case "grande":
                return new MeteoritoGrande(new Texture("meteoritobig.png"), x, y, velocidad);
            default:
                throw new IllegalArgumentException("Tipo de meteorito desconocido: " + tipo);
        }
    }
}

