package com.spacenav2024.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.Gdx;

public class Meteorito extends Entidad implements Movible {
    protected int impactosNecesarios; // Número de impactos necesarios para destruir el meteorito.
    protected float velocidadY;       // Velocidad vertical del meteorito.
    protected float velocidadX;       // Velocidad horizontal del meteorito.
    public boolean eliminar;          // Indica si el meteorito debe ser eliminado.
    public boolean impactoReciente;   // Marca si el meteorito ha colisionado recientemente.

    public Meteorito(Texture texture, float x, float y, int impactosNecesarios, float velocidadY) {
        super(texture, x, y);
        this.impactosNecesarios = impactosNecesarios;
        this.velocidadY = velocidadY;
        this.velocidadX = MathUtils.random(-50, 50); // Movimiento horizontal aleatorio.
        this.eliminar = false;
        this.impactoReciente = false;
    }

    public void recibirImpacto() {
        impactosNecesarios--;
        if (impactosNecesarios <= 0) {
            eliminar = true;
        }
    }

    public boolean estaDestruido() {
        return eliminar;
    }

    @Override
    public void update(float delta) {
        x += velocidadX * delta;
        y -= velocidadY * delta;

        // Rebote en los bordes laterales
        if (x <= 0 || x + getWidth() >= Gdx.graphics.getWidth()) {
            velocidadX = -velocidadX;
        }

        // Eliminar meteorito al alcanzar el borde inferior
        if (y <= 0) {
            eliminar = true;
        }

        impactoReciente = false; // Restablece el estado de impacto reciente.
    }

    @Override
    public Rectangle getRectangulo() {
        return new Rectangle(x, y, texture.getWidth(), texture.getHeight());
    }

    // Métodos getter y setter para velocidadX y velocidadY
    public float getVelocidadX() {
        return velocidadX;
    }

    public void setVelocidadX(float velocidadX) {
        this.velocidadX = velocidadX;
    }

    public float getVelocidadY() {
        return velocidadY;
    }

    public void setVelocidadY(float velocidadY) {
        this.velocidadY = velocidadY;
    }
}
