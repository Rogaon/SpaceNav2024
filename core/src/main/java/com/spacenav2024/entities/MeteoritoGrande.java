package com.spacenav2024.entities;

import com.badlogic.gdx.graphics.Texture;

public class MeteoritoGrande extends Meteorito {
    public MeteoritoGrande(Texture texture, float x, float y, float velocidad) {
        super(texture, x, y, velocidad, 3);  // 3 impactos para destruir
        texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
    }
}