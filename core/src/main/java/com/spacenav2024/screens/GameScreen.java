package com.spacenav2024.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.spacenav2024.MainGame;
import com.spacenav2024.entities.*;
import com.spacenav2024.managers.AdministradorProyectiles;
import com.spacenav2024.managers.AdministradorMeteoritos;
import com.spacenav2024.utils.ConfiguracionJuego;
import com.spacenav2024.utils.SistemaColisiones;

public class GameScreen implements Screen {
    private MainGame game;
    private SpriteBatch batch;
    private BitmapFont font;
    private Jugador jugador;
    private AdministradorProyectiles administradorProyectiles;
    private AdministradorMeteoritos administradorMeteoritos;
    private Texture corazon;
    private Texture fondo;
    private Music musicaFondo;
    private Sound sonidoDisparo;
    private Sound sonidoDamage;
    private Sound sonidoExplosion;
    private float tiempoDisparo = 0;
    private float cooldownDisparo = 0.5f;
    private float tiempoAcumulado = 0;
    private float tiempoGeneracionMeteorito = 1.5f;
    private ConfiguracionJuego config;

    public GameScreen(MainGame game) {
        this.game = game;
        this.batch = new SpriteBatch();
        this.font = new BitmapFont();
        this.font.getData().setScale(2);
        this.font.setColor(1, 1, 1, 1);

        // Configuración global
        this.config = ConfiguracionJuego.getInstancia();
        config.setNivelActual(1);
        config.setPuntajeActual(0);

        this.jugador = new Jugador(new Texture("nave.png"), Gdx.graphics.getWidth() / 2, 20);
        this.administradorProyectiles = new AdministradorProyectiles(new Texture("proyectil.png"));
        this.administradorMeteoritos = new AdministradorMeteoritos();

        this.corazon = new Texture("corazon.png");
        this.fondo = new Texture("fondo_juego.jpg");

        this.musicaFondo = Gdx.audio.newMusic(Gdx.files.internal("musica_fondo.mp3"));
        this.sonidoDisparo = Gdx.audio.newSound(Gdx.files.internal("disparo.mp3"));
        this.sonidoDamage = Gdx.audio.newSound(Gdx.files.internal("damage.ogg"));
        this.sonidoExplosion = Gdx.audio.newSound(Gdx.files.internal("explosion.ogg"));

        if (config.isSonidosActivados()) {
            musicaFondo.setLooping(true);
            musicaFondo.play();
        }
    }

    @Override
    
    public void render(float delta) {
        Gdx.gl.glClearColor(0.05f, 0.05f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(fondo, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        font.draw(batch, "Puntaje: " + config.getPuntajeActual(), Gdx.graphics.getWidth() - 150, Gdx.graphics.getHeight() - 10);
        font.draw(batch, "Nivel: " + config.getNivelActual(), Gdx.graphics.getWidth() - 150, Gdx.graphics.getHeight() - 40);

        for (int i = 0; i < jugador.getSalud(); i++) {
            batch.draw(corazon, 10 + i * 40, Gdx.graphics.getHeight() - 60);
        }

        jugador.render(batch);
        administradorProyectiles.render(batch);
        administradorMeteoritos.render(batch);
        batch.end();

        jugador.update(delta);
        administradorProyectiles.update(delta);
        administradorMeteoritos.update(delta);

        int puntosGanados = SistemaColisiones.manejarColisiones(jugador, administradorMeteoritos.getMeteoritos(), administradorProyectiles.getProyectiles(), sonidoDamage);
        config.setPuntajeActual(config.getPuntajeActual() + puntosGanados);

        if (jugador.getSalud() <= 0) {
            if (config.isSonidosActivados()) {
                musicaFondo.stop();
                sonidoExplosion.play();
            }
            game.setScreen(new GameOverScreen(game));
            return;
        }

    if (config.getPuntajeActual() >= 100) {
        config.setNivelActual(config.getNivelActual() + 1);
        config.setPuntajeActual(0);
        tiempoGeneracionMeteorito *= 0.9f;
        administradorMeteoritos.getMeteoritos().clear();
        administradorProyectiles.getProyectiles().clear();
        jugador.setPosition(Gdx.graphics.getWidth() / 2 - jugador.getWidth() / 2, 20);
    }

        tiempoAcumulado += delta;
        if (tiempoAcumulado >= tiempoGeneracionMeteorito) {
            int cantidadMeteoritos = MathUtils.random(1, config.getNivelActual());
            for (int i = 0; i < cantidadMeteoritos; i++) {
                generarMeteoritos();
            }
            tiempoAcumulado = 0;
        }

        tiempoDisparo += delta;
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE) && tiempoDisparo >= cooldownDisparo) {
            administradorProyectiles.disparar(jugador.getX() + jugador.getWidth() / 2, jugador.getY() + jugador.getHeight());
            if (config.isSonidosActivados()) sonidoDisparo.play();
            tiempoDisparo = 0;
        }
    }


    private void generarMeteoritos() {
         float xAleatorio = MathUtils.random(0, Gdx.graphics.getWidth() - 40);
         float velocidadBase = 100 + (config.getNivelActual() - 1) * 20; // Incrementar velocidad con el nivel

         // Determinar el tipo de meteorito a generar según el nivel
         int tipoMeteorito;
         if (config.getNivelActual() <= 2) {
             tipoMeteorito = 0; // Solo meteoritos pequeños en los primeros niveles
        } else if (config.getNivelActual() <= 4) {
            tipoMeteorito = MathUtils.random(0, 1); // Meteoritos pequeños y medianos
      } else {
         tipoMeteorito = MathUtils.random(0, 2); // Meteoritos pequeños, medianos y grandes
     }

         Meteorito meteorito;
          switch (tipoMeteorito) {
            case 0:
                    meteorito = new MeteoritoPequeno(new Texture("meteoritosmall.png"), xAleatorio, Gdx.graphics.getHeight(), velocidadBase);
                   break;
            case 1:
                    meteorito = new MeteoritoMediano(new Texture("meteoritomedium.png"), xAleatorio, Gdx.graphics.getHeight(), velocidadBase * 0.8f); // Mediano más lento
                    break;
            case 2:
                   default:
                    meteorito = new MeteoritoGrande(new Texture("meteoritobig.png"), xAleatorio, Gdx.graphics.getHeight(), velocidadBase * 0.6f); // Grande aún más lento
                     break;
        }

        administradorMeteoritos.spawnMeteorito(meteorito);
    }


    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
        corazon.dispose();
        jugador.getTexture().dispose();
        administradorProyectiles.dispose();
        administradorMeteoritos.dispose();
        fondo.dispose();
        musicaFondo.dispose();
        sonidoDisparo.dispose();
        sonidoDamage.dispose();
        sonidoExplosion.dispose();
    }

    @Override public void show() {}
    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
}
