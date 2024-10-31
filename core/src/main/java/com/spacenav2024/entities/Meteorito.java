package com.spacenav2024.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.MathUtils;

public class Meteorito extends Entidad {
    protected int impactosNecesarios;
    protected float velocidadY;
    protected float velocidadX;
    public boolean eliminar;
    public boolean impactoReciente;

    public Meteorito(Texture texture, float x, float y, int impactosNecesarios, float velocidadY) {
        super(texture, x, y);
        this.impactosNecesarios = impactosNecesarios;
        this.velocidadY = velocidadY;
        this.velocidadX = MathUtils.random(-50, 50);
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

        if (x <= 0 || x + getWidth() >= Gdx.graphics.getWidth()) {
            velocidadX = -velocidadX;
        }

        if (y <= 0) {
            eliminar = true;
        }

        impactoReciente = false;
    }

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
