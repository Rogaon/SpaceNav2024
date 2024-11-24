package com.spacenav2024.managers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.spacenav2024.entities.Meteorito;
import com.spacenav2024.entities.MeteoritoPequeno;
import com.spacenav2024.entities.MeteoritoMediano;
import com.spacenav2024.entities.MeteoritoGrande;
import com.spacenav2024.strategies.EstrategiaMovimiento;
import com.spacenav2024.strategies.EstrategiaMovimientoDescendente;

public class AdministradorMeteoritos {
    private Array<Meteorito> meteoritos;
    private Texture texturaPequeno;
    private Texture texturaMediano;
    private Texture texturaGrande;

    public AdministradorMeteoritos() {
        this.meteoritos = new Array<>();
        this.texturaPequeno = new Texture("meteoritosmall.png");
        this.texturaMediano = new Texture("meteoritomedium.png");
        this.texturaGrande = new Texture("meteoritobig.png");
    }

    public void spawnMeteorito(Meteorito meteorito) {
        meteoritos.add(meteorito);
    }

    public void generarMeteoritos(int cantidad, float nivel) {
        for (int i = 0; i < cantidad; i++) {
            float xAleatorio = MathUtils.random(0, com.badlogic.gdx.Gdx.graphics.getWidth() - texturaPequeno.getWidth());
            float velocidadAleatoria = 100 + MathUtils.random(0, nivel * 20);

            Meteorito meteorito;
            int tipoMeteorito = MathUtils.random(0, 2); // Generar tipo aleatorio

            if (tipoMeteorito == 0) {
                meteorito = new MeteoritoPequeno(texturaPequeno, xAleatorio, com.badlogic.gdx.Gdx.graphics.getHeight(), velocidadAleatoria);
            } else if (tipoMeteorito == 1) {
                meteorito = new MeteoritoMediano(texturaMediano, xAleatorio, com.badlogic.gdx.Gdx.graphics.getHeight(), velocidadAleatoria);
            } else {
                meteorito = new MeteoritoGrande(texturaGrande, xAleatorio, com.badlogic.gdx.Gdx.graphics.getHeight(), velocidadAleatoria);
            }

            // Asignar estrategia de movimiento
            meteorito.setEstrategia(new EstrategiaMovimientoDescendente());
            spawnMeteorito(meteorito);
        }
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
        texturaPequeno.dispose();
        texturaMediano.dispose();
        texturaGrande.dispose();
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

