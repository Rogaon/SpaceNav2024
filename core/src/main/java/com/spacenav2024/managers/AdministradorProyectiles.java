package com.spacenav2024.managers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;
import com.spacenav2024.entities.Proyectil;

public class AdministradorProyectiles {
    private Array<Proyectil> proyectiles = new Array<>();
    private Texture texturaProyectil;  // Textura para el proyectil

    public AdministradorProyectiles(Texture texturaProyectil) {
        this.texturaProyectil = texturaProyectil;
    }

    public void disparar(float x, float y) {
        // Crear un nuevo proyectil con las coordenadas proporcionadas
        Proyectil nuevoProyectil = new Proyectil(texturaProyectil, x, y);
        proyectiles.add(nuevoProyectil);
    }

    public void update(float delta) {
        for (Proyectil proyectil : proyectiles) {
            proyectil.update(delta);
        }
        
        // Eliminar proyectiles que están fuera de la pantalla
        Array<Proyectil> proyectilesAEliminar = new Array<>();
        for (Proyectil proyectil : proyectiles) {
            if (proyectil.getY() > com.badlogic.gdx.Gdx.graphics.getHeight()) {
                proyectilesAEliminar.add(proyectil);
            }
        }
        
        proyectiles.removeAll(proyectilesAEliminar, true);
    }

    public Array<Proyectil> getProyectiles() {
        return proyectiles;
    }
}
