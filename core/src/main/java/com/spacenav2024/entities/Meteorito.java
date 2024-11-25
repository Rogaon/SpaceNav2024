package com.spacenav2024.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.spacenav2024.strategies.EstrategiaMovimiento;

public abstract class Meteorito extends Entidad implements Movible {
    protected int impactosNecesarios;
    protected float velocidadY;
    protected float velocidadX;
    private EstrategiaMovimiento estrategia;
    protected boolean eliminar; // Indica si el meteorito debe ser eliminado

    public Meteorito(Texture texture, float x, float y, int impactosNecesarios, float velocidadY) {
        super(texture, x, y);
        this.impactosNecesarios = impactosNecesarios;
        this.velocidadY = velocidadY;
        this.velocidadX = MathUtils.random(-50, 50);
        this.eliminar = false; // Inicialmente no se elimina
    }

    public void setEstrategia(EstrategiaMovimiento estrategia) {
        this.estrategia = estrategia;
    }

    @Override
    public void update(float delta) {
        if (estrategia != null) {
            estrategia.mover(this, delta);
        }
    }

    public boolean estaFueraPantalla() {
        return y + getHeight() < 0;
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
    public Rectangle getRectangulo() {
        return new Rectangle(x, y, getWidth(), getHeight());
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

    public boolean isEliminar() {
        return eliminar;
    }

    public void setEliminar(boolean eliminar) {
        this.eliminar = eliminar;
    }
}
