package com.spacenav2024.managers;

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

    public Array<Meteorito> getMeteoritos() {
        return meteoritos;
    }

    public void update(float delta) {
        for (Meteorito meteorito : meteoritos) {
            meteorito.update(delta);
        }
    }

    public void dispose() {
        for (Meteorito meteorito : meteoritos) {
            meteorito.getTexture().dispose();
        }
        meteoritos.clear();
    }
}

