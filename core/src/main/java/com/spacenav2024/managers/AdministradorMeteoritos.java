package com.spacenav2024.managers;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.spacenav2024.entities.Meteorito;

public class AdministradorMeteoritos {
    private Array<Meteorito> meteoritos;

    public AdministradorMeteoritos() {
        this.meteoritos = new Array<>();
    }

    public void spawnMeteorito(Meteorito meteorito) {
        meteoritos.add(meteorito);
    }

    public void update(float delta) {
        for (int i = meteoritos.size - 1; i >= 0; i--) {
            Meteorito meteorito = meteoritos.get(i);
            meteorito.update(delta);

            // Eliminar meteorito si está fuera de la pantalla
            if (meteorito.estaFueraPantalla()) {
                meteoritos.removeIndex(i);
            }
        }
    }
    
    public void dispose() {
        for (Meteorito meteorito : meteoritos) {
            meteorito.getTexture().dispose();
        }
    }


    public void render(SpriteBatch batch) {
        for (Meteorito meteorito : meteoritos) {
            meteorito.render(batch);
        }
    }

    public Array<Meteorito> getMeteoritos() {
        return meteoritos;
    }
}

