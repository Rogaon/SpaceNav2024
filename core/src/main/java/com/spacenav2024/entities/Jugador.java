package com.spacenav2024.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;

public class Jugador extends Entidad {
    private float velocidad = 200;
    private int salud = 3;

    public Jugador(Texture texture, float x, float y) {
        super(texture, x, y);
    }

    @Override
    public void update(float delta) {
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            x -= velocidad * delta;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            x += velocidad * delta;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            y += velocidad * delta;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            y -= velocidad * delta;
        }

        x = Math.max(0, Math.min(x, Gdx.graphics.getWidth() - texture.getWidth()));
        y = Math.max(0, Math.min(y, Gdx.graphics.getHeight() - texture.getHeight()));
    }

    public int getSalud() {
        return salud;
    }

    public void reducirSalud() {
        if (salud > 0) {
            salud--;
        }
    }
}
