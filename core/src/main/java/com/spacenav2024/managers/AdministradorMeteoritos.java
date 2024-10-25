package com.spacenav2024.managers;

import com.badlogic.gdx.utils.Array;
import com.spacenav2024.entities.Meteorito;

public class AdministradorMeteoritos {
    private Array<Meteorito> meteoritos = new Array<>();

    public void spawnMeteorito(Meteorito meteorito) {
        meteoritos.add(meteorito);
    }

    public void update(float delta) {
        for (Meteorito meteorito : meteoritos) {
            meteorito.update(delta);
        }
    }

    public Array<Meteorito> getMeteoritos() {
        return meteoritos;
    }
}
