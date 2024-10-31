package com.spacenav2024.entities;

import com.badlogic.gdx.graphics.Texture;

public abstract class Meteorito extends Entidad {
    protected float velocidad;
    protected int vida;  // Cantidad de impactos necesarios para destruir

    public Meteorito(Texture texture, float x, float y, float velocidad, int vida) {
        super(texture, x, y);
        this.velocidad = velocidad;
        this.vida = vida;
    }

    @Override
    public void update(float delta) {
        y -= velocidad * delta;  // Mover meteorito hacia abajo
    }

    public void recibirImpacto() {
        vida--;
    }

    public boolean estaDestruido() {
        return vida <= 0;
    }
}
