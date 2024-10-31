package com.spacenav2024.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Jugador extends Entidad {
    private int salud;
    private float velocidad = 200;  // Velocidad de movimiento en píxeles por segundo

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
        // Controles de movimiento para teclas WASD y flechas
        if (Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.W) || Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.UP)) {
            y += velocidad * delta;  // Mover hacia arriba
        }
        if (Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.S) || Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.DOWN)) {
            y -= velocidad * delta;  // Mover hacia abajo
        }
        if (Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.A) || Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.LEFT)) {
            x -= velocidad * delta;  // Mover hacia la izquierda
        }
        if (Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.D) || Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.RIGHT)) {
            x += velocidad * delta;  // Mover hacia la derecha
        }

        // Limitar la nave a los bordes de la pantalla
        if (x < 0) x = 0;
        if (x > Gdx.graphics.getWidth() - texture.getWidth()) x = Gdx.graphics.getWidth() - texture.getWidth();
        if (y < 0) y = 0;
        if (y > Gdx.graphics.getHeight() - texture.getHeight()) y = Gdx.graphics.getHeight() - texture.getHeight();
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

    public void reiniciarSalud() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void reiniciarEstado() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}

