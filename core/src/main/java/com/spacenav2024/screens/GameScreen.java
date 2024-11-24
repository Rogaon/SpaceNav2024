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
import com.spacenav2024.managers.AdministradorMeteoritos;
import com.spacenav2024.managers.AdministradorProyectiles;
import com.spacenav2024.strategies.EstrategiaMovimientoDescendente;
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
    private int puntos = 0;
    private int nivel = 1;
    private int vidas;
    private float tiempoDisparo = 0;
    private float cooldownDisparo = 0.5f;
    private float tiempoAcumulado = 0;
    private float tiempoGeneracionMeteorito = 1.5f;

    public GameScreen(MainGame game) {
        this.game = game;
        this.batch = new SpriteBatch();
        this.font = new BitmapFont();
        this.font.getData().setScale(2);
        this.font.setColor(1, 1, 1, 1);

        this.jugador = new Jugador(new Texture("nave.png"), Gdx.graphics.getWidth() / 2, 20);
        this.vidas = jugador.getSalud(); // Salud inicial del jugador

        this.administradorProyectiles = new AdministradorProyectiles(new Texture("proyectil.png"));
        this.administradorMeteoritos = new AdministradorMeteoritos();

        this.corazon = new Texture("corazon.png");
        this.fondo = new Texture("fondo_juego.jpg");

        this.musicaFondo = Gdx.audio.newMusic(Gdx.files.internal("musica_fondo.mp3"));
        this.sonidoDisparo = Gdx.audio.newSound(Gdx.files.internal("disparo.mp3"));
        this.sonidoDamage = Gdx.audio.newSound(Gdx.files.internal("damage.ogg"));
        this.sonidoExplosion = Gdx.audio.newSound(Gdx.files.internal("explosion.ogg"));

        musicaFondo.setLooping(true);
        musicaFondo.play();
    }

    private void generarMeteoritosPorNivel(int nivel) {
        int meteoritosAGenerar = 2 + nivel / 2; // Generar más meteoritos en niveles altos
        for (int i = 0; i < meteoritosAGenerar; i++) {
            float xAleatorio = MathUtils.random(0, Gdx.graphics.getWidth() - 50);
            float velocidadAleatoria = 50 + MathUtils.random(0, nivel * 10); // Velocidad ajustada

            Meteorito meteorito;
            if (nivel >= 3 && MathUtils.randomBoolean(0.4f)) { // 40% probabilidad de meteorito grande
                meteorito = new MeteoritoGrande(new Texture("meteoritobig.png"), xAleatorio, Gdx.graphics.getHeight(), velocidadAleatoria);
            } else if (nivel >= 2 && MathUtils.randomBoolean(0.6f)) { // 60% probabilidad de meteorito mediano
                meteorito = new MeteoritoMediano(new Texture("meteoritomedium.png"), xAleatorio, Gdx.graphics.getHeight(), velocidadAleatoria);
            } else {
                meteorito = new MeteoritoPequeno(new Texture("meteoritosmall.png"), xAleatorio, Gdx.graphics.getHeight(), velocidadAleatoria);
            }
            meteorito.setEstrategia(new EstrategiaMovimientoDescendente());
            administradorMeteoritos.spawnMeteorito(meteorito);
        }
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.05f, 0.05f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(fondo, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        font.draw(batch, "Puntaje: " + puntos, Gdx.graphics.getWidth() - 150, Gdx.graphics.getHeight() - 10);
        font.draw(batch, "Nivel: " + nivel, Gdx.graphics.getWidth() - 150, Gdx.graphics.getHeight() - 40);

        // Dibujar corazones para las vidas
        for (int i = 0; i < vidas; i++) {
            batch.draw(corazon, 10 + i * 40, 10);
        }

        jugador.render(batch);
        administradorProyectiles.render(batch);
        administradorMeteoritos.render(batch);

        batch.end();

        jugador.update(delta);
        administradorProyectiles.update(delta);
        administradorMeteoritos.update(delta);

        puntos += SistemaColisiones.manejarColisiones(jugador, administradorMeteoritos.getMeteoritos(), administradorProyectiles.getProyectiles(), sonidoDamage);
        vidas = jugador.getSalud(); // Sincronizar visualización de vidas

        // Cambiar a Game Over solo cuando vidas llegue a 0
        if (vidas <= 0) {
            musicaFondo.stop();
            sonidoExplosion.play();
            game.setScreen(new GameOverScreen(game));
            return;
        }

        // Incremento de nivel cuando se alcanzan 100 puntos
        if (puntos >= 100) {
            nivel++;
            puntos = 0;
            tiempoGeneracionMeteorito = Math.max(0.5f, 1.5f - 0.1f * nivel); // Reducir generación gradualmente
            administradorMeteoritos.getMeteoritos().clear(); // Limpiar meteoritos de pantalla
            administradorProyectiles.getProyectiles().clear();
            jugador.setPosition(Gdx.graphics.getWidth() / 2 - jugador.getWidth() / 2, 20); // Reposicionar jugador
        }

        // Generación de meteoritos en intervalos de tiempo ajustados
        tiempoAcumulado += delta;
        if (tiempoAcumulado >= tiempoGeneracionMeteorito) {
            generarMeteoritosPorNivel(nivel);
            tiempoAcumulado = 0;
        }

        // Control de disparo con cooldown
        tiempoDisparo += delta;
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE) && tiempoDisparo >= cooldownDisparo) {
            administradorProyectiles.disparar(jugador.getX() + jugador.getWidth() / 2, jugador.getY() + jugador.getHeight());
            sonidoDisparo.play();
            tiempoDisparo = 0;
        }
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
