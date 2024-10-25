package com.spacenav2024.entities;

import com.badlogic.gdx.graphics.Texture;

public class Proyectil extends Entidad {
    private float velocidad = 400;

    public Proyectil(Texture texture, float x, float y) {
        super(texture, x, y);
    }

    @Override
    public void update(float delta) {
        y += velocidad * delta;
    }
}
