package com.spacenav2024.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Meteorito extends Entidad {
    private float velocidad;

    public Meteorito(Texture texture, float x, float y, float velocidad) {
        super(texture, x, y);
        this.velocidad = velocidad;
    }

    @Override
    public void update(float delta) {
        y -= velocidad * delta;
        if (y < -texture.getHeight()) {
            y = Gdx.graphics.getHeight(); // Reinicia en la parte superior
        }
    }
}
