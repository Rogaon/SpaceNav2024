package com.spacenav2024.entities;

import com.badlogic.gdx.graphics.Texture;

public class MeteoritoPequeno extends Meteorito {
    public MeteoritoPequeno(Texture texture, float x, float y, float velocidad) {
        super(texture, x, y, velocidad, 1);  // 1 impacto para destruir
    }
}