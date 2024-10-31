package com.spacenav2024.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Jugador extends Entidad {
    private int salud;

    public Jugador(Texture texture, float x, float y) {
        super(texture, x, y);
        this.salud = 3;  // Establece la salud inicial del jugador (ej. 3 vidas)
    }

    // Método para actualizar la posición del jugador
    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public void update(float delta) {
        // Aquí podrías agregar lógica de movimiento o controles del jugador si es necesario
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(texture, x, y);
    }

    // Método para recibir daño y reducir la salud del jugador
    public void recibirDanio() {
        salud--;
    }

    // Método para obtener la salud del jugador
    public int getSalud() {
        return salud;
    }

    // Método para verificar si el jugador ha perdido todas las vidas
    public boolean estaMuerto() {
        return salud <= 0;
    }

    public Rectangle getRectangulo() {
        return new Rectangle(x, y, texture.getWidth(), texture.getHeight());
    }
}

