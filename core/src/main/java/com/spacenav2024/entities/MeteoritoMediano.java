package com.spacenav2024.entities;

import com.badlogic.gdx.graphics.Texture;

public class MeteoritoMediano extends Meteorito {
    public MeteoritoMediano(Texture texture, float x, float y, float velocidad) {
        super(texture, x, y, velocidad, 2);  // 2 impactos para destruir
    }
}