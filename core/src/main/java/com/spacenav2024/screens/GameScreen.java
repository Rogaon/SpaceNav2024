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
import com.badlogic.gdx.utils.Array;
import com.spacenav2024.MainGame;
import com.spacenav2024.entities.Jugador;
import com.spacenav2024.entities.Meteorito;
import com.spacenav2024.entities.MeteoritoPequeno;
import com.spacenav2024.entities.MeteoritoMediano;
import com.spacenav2024.entities.MeteoritoGrande;
import com.spacenav2024.entities.Proyectil;
import com.spacenav2024.managers.AdministradorMeteoritos;
import com.spacenav2024.managers.AdministradorProyectiles;
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

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.05f, 0.05f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(fondo, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        // Mostrar puntaje y nivel
        font.draw(batch, "Puntaje: " + puntos, Gdx.graphics.getWidth() - 200, Gdx.graphics.getHeight() - 20);
        font.draw(batch, "Nivel: " + nivel, Gdx.graphics.getWidth() - 200, Gdx.graphics.getHeight() - 60);

        // Dibujar corazones (vidas)
        for (int i = 0; i < jugador.getSalud(); i++) {
            batch.draw(corazon, 10 + i * 40, 10);
        }

        jugador.render(batch);
        administradorProyectiles.render(batch);
        administradorMeteoritos.render(batch);
        batch.end();

        jugador.update(delta);
        administradorProyectiles.update(delta);
        administradorMeteoritos.update(delta);

        // Manejar colisiones y actualizar puntaje
        puntos += SistemaColisiones.manejarColisiones(jugador, administradorMeteoritos.getMeteoritos(), administradorProyectiles.getProyectiles(), sonidoDamage);

        // Si el jugador pierde todas las vidas, mostrar pantalla de Game Over
        if (jugador.getSalud() <= 0) {
            musicaFondo.stop();
            sonidoExplosion.play();
            game.setScreen(new GameOverScreen(game));
            return;
        }

        // Incrementar nivel al llegar a 100 puntos
        if (puntos >= 100) {
            nivel++;
            puntos = 0;
            tiempoGeneracionMeteorito *= 0.9f;
            administradorMeteoritos.getMeteoritos().clear();
            administradorProyectiles.getProyectiles().clear();
            jugador.setPosition(Gdx.graphics.getWidth() / 2, 20);
        }

        // Generar meteoritos según nivel
        tiempoAcumulado += delta;
        if (tiempoAcumulado >= tiempoGeneracionMeteorito) {
            generarMeteoritos();
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

    private void generarMeteoritos() {
        int cantidadMeteoritos = MathUtils.random(1, nivel); // Generar más meteoritos según el nivel
        for (int i = 0; i < cantidadMeteoritos; i++) {
            float xAleatorio = MathUtils.random(0, Gdx.graphics.getWidth() - 50);
            float velocidad = MathUtils.random(100 + nivel * 10, 200 + nivel * 20);

            Meteorito meteorito;

            // Determinar el tipo de meteorito según el nivel
            if (nivel <= 3) { // Niveles iniciales: solo meteoritos pequeños
                meteorito = new MeteoritoPequeno(new Texture("meteoritosmall.png"), xAleatorio, Gdx.graphics.getHeight(), velocidad);
            } else if (nivel <= 6) { // Niveles medios: pequeños y medianos
                int tipo = MathUtils.random(0, 1); // 0 para pequeño, 1 para mediano
                if (tipo == 0) {
                    meteorito = new MeteoritoPequeno(new Texture("meteoritosmall.png"), xAleatorio, Gdx.graphics.getHeight(), velocidad);
                } else {
                    meteorito = new MeteoritoMediano(new Texture("meteoritomedium.png"), xAleatorio, Gdx.graphics.getHeight(), velocidad);
                }
            } else { // Niveles avanzados: todos los tipos de meteoritos
                int tipo = MathUtils.random(0, 2); // 0 para pequeño, 1 para mediano, 2 para grande
                if (tipo == 0) {
                    meteorito = new MeteoritoPequeno(new Texture("meteoritosmall.png"), xAleatorio, Gdx.graphics.getHeight(), velocidad);
                } else if (tipo == 1) {
                    meteorito = new MeteoritoMediano(new Texture("meteoritomedium.png"), xAleatorio, Gdx.graphics.getHeight(), velocidad);
                } else {
                    meteorito = new MeteoritoGrande(new Texture("meteoritobig.png"), xAleatorio, Gdx.graphics.getHeight(), velocidad);
                }
            }

            administradorMeteoritos.spawnMeteorito(meteorito);
        }
    }


    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
        corazon.dispose();
        fondo.dispose();
        musicaFondo.dispose();
        sonidoDisparo.dispose();
        sonidoDamage.dispose();
        sonidoExplosion.dispose();
        administradorProyectiles.dispose();
        administradorMeteoritos.dispose();
    }

    @Override public void show() {}
    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
}
