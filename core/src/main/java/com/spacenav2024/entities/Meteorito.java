package com.spacenav2024.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.Gdx;

public abstract class Meteorito extends Entidad implements Movible {
    protected int impactosNecesarios;
    protected float velocidadY;
    protected float velocidadX;
    public boolean eliminar;

    public Meteorito(Texture texture, float x, float y, int impactosNecesarios, float velocidadY) {
        super(texture, x, y);
        this.impactosNecesarios = impactosNecesarios;
        this.velocidadY = velocidadY;
        this.velocidadX = MathUtils.random(-50, 50);
        this.eliminar = false;
    }

    @Override
    public void update(float delta) {
        x += velocidadX * delta;
        y -= velocidadY * delta;

        // Rebote en bordes laterales
        if (x <= 0 || x + getWidth() >= Gdx.graphics.getWidth()) {
            velocidadX = -velocidadX;
        }

        // Marcar para eliminar si llega al borde inferior
        if (y <= 0) {
            eliminar = true;
        }
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

    public boolean estaFueraPantalla() {
        return y + getHeight() < 0;
    }

    @Override
    public Rectangle getRectangulo() {
        return new Rectangle(x, y, texture.getWidth(), texture.getHeight());
    }

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
