package com.spacenav2024.managers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.spacenav2024.entities.Proyectil;

public class AdministradorProyectiles {
    private Array<Proyectil> proyectiles;
    private Texture texturaProyectil;

    public AdministradorProyectiles(Texture texturaProyectil) {
        this.proyectiles = new Array<>();
        this.texturaProyectil = texturaProyectil;
    }

    public void disparar(float x, float y) {
        proyectiles.add(new Proyectil(texturaProyectil, x, y));
    }

    public void update(float delta) {
        for (int i = proyectiles.size - 1; i >= 0; i--) {
            Proyectil proyectil = proyectiles.get(i);
            proyectil.update(delta);

            // Eliminar proyectiles que salen de la pantalla
            if (proyectil.getY() > com.badlogic.gdx.Gdx.graphics.getHeight()) {
                proyectiles.removeIndex(i);
            }
        }
    }
    
    public void dispose() {
        for (Proyectil proyectil : proyectiles) {
            proyectil.getTexture().dispose();
        }
    }


    public void render(SpriteBatch batch) {
        for (Proyectil proyectil : proyectiles) {
            proyectil.render(batch);
        }
    }

    public Array<Proyectil> getProyectiles() {
        return proyectiles;
    }
}
