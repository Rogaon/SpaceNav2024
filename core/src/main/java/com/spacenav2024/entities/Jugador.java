package com.spacenav2024.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class Jugador extends Entidad {
    private int salud;

    public Jugador(Texture texture, float x, float y) {
        super(texture, x, y);
        this.salud = 3; // Salud inicial del jugador (3 vidas)
    }

    public void recibirDanio(int cantidad) {
        salud -= cantidad;
        if (salud < 0) salud = 0;  // Asegura que no sea negativa
    }

    public int getSalud() {
        return salud;
    }

    @Override
    public void update(float delta) {
        // Movimiento del jugador en las cuatro direcciones
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) {
            x -= 200 * delta;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) {
            x += 200 * delta;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W)) {
            y += 200 * delta;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S)) {
            y -= 200 * delta;
        }

        // Limitar el movimiento del jugador dentro de los límites de la pantalla
        if (x < 0) x = 0;
        if (x + getWidth() > Gdx.graphics.getWidth()) x = Gdx.graphics.getWidth() - getWidth();
        if (y < 0) y = 0;
        if (y + getHeight() > Gdx.graphics.getHeight()) y = Gdx.graphics.getHeight() - getHeight();
    }

    public Rectangle getRectangulo() {
        return new Rectangle(x, y, texture.getWidth(), texture.getHeight());
    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }
}
