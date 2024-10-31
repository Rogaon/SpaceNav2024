package com.spacenav2024.entities;

import com.badlogic.gdx.graphics.Texture;

public class MeteoritoPequeno extends Meteorito {
    public MeteoritoPequeno(Texture texture, float x, float y, float velocidad) {
        super(texture, x, y, 1, velocidad); // Necesita 1 impacto para destruirse
    }
}
