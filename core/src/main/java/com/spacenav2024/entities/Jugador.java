package com.spacenav2024.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class Jugador extends Entidad implements Movible {
    private int salud; // Salud del jugador, inicializada en 3 vidas.

    public Jugador(Texture texture, float x, float y) {
        super(texture, x, y);
        this.salud = 3; // Inicia con 3 vidas.
    }

    public void recibirDanio(int cantidad) {
        salud -= cantidad;
        if (salud < 0) {
            salud = 0; // Asegurar que no haya valores negativos
        }
    }

    public int getSalud() {
        return salud;
    }

    public void setSalud(int salud) {
        this.salud = salud;
    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public void update(float delta) {
        // Lógica de movimiento del jugador basada en la entrada del usuario
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            x -= 200 * delta;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            x += 200 * delta;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            y += 200 * delta;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            y -= 200 * delta;
        }

        // Restringir movimiento dentro de los límites de la pantalla
        x = Math.max(0, Math.min(x, Gdx.graphics.getWidth() - getWidth()));
        y = Math.max(0, Math.min(y, Gdx.graphics.getHeight() - getHeight()));
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(texture, x, y);
    }

    @Override
    public Rectangle getRectangulo() {
        return new Rectangle(x, y, texture.getWidth(), texture.getHeight());
    }
}
