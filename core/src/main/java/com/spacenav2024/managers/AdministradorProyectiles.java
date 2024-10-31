package com.spacenav2024.managers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;
import com.spacenav2024.entities.Proyectil;

public class AdministradorProyectiles {
    private Array<Proyectil> proyectiles;
    private Texture texturaProyectil;

    public AdministradorProyectiles(Texture texturaProyectil) {
        this.texturaProyectil = texturaProyectil;
        this.proyectiles = new Array<>();
    }

    public void disparar(float x, float y) {
        proyectiles.add(new Proyectil(texturaProyectil, x, y));
    }

    public Array<Proyectil> getProyectiles() {
        return proyectiles;
    }

    public void update(float delta) {
        for (Proyectil proyectil : proyectiles) {
            proyectil.update(delta);
        }
    }

    public void dispose() {
        texturaProyectil.dispose();
    }
}
