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
import com.spacenav2024.entities.Jugador;
import com.spacenav2024.entities.Meteorito;
import com.spacenav2024.entities.Proyectil;
import com.spacenav2024.managers.AdministradorProyectiles;
import com.spacenav2024.managers.AdministradorMeteoritos;
import com.spacenav2024.utils.SistemaColisiones;

public class GameScreen implements Screen {
    private MainGame game;
    private SpriteBatch batch;
    private BitmapFont font;
    private Jugador jugador;
    private AdministradorProyectiles administradorProyectiles;
    private AdministradorMeteoritos administradorMeteoritos;
    private Texture corazon;
    private Texture texturaJugador;
    private Texture texturaProyectil;
    private Texture texturaMeteorito;
    private Texture fondo;  // Imagen de fondo
    private Music musicaFondo;  // Música de fondo
    private Sound sonidoDisparo;
    private Sound sonidoPop;
    private Sound sonidoDamage;
    private Sound sonidoExplosion;
    private int puntos = 0;
    private int nivel = 1;
    private int vidas = 3;
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

        this.texturaJugador = new Texture("nave.png");
        this.jugador = new Jugador(texturaJugador, 100, 100);

        this.texturaProyectil = new Texture("proyectil.png");
        this.administradorProyectiles = new AdministradorProyectiles(texturaProyectil);

        this.texturaMeteorito = new Texture("meteoritosmall.png");
        this.administradorMeteoritos = new AdministradorMeteoritos();

        this.corazon = new Texture("corazon.png");
        this.fondo = new Texture("fondo_juego.jpg");  // Cargar la imagen de fondo

        // Cargar música y sonidos
        this.musicaFondo = Gdx.audio.newMusic(Gdx.files.internal("musica_fondo.mp3"));
        this.sonidoDisparo = Gdx.audio.newSound(Gdx.files.internal("disparo.mp3"));
        this.sonidoPop = Gdx.audio.newSound(Gdx.files.internal("pop-sound.mp3"));
        this.sonidoDamage = Gdx.audio.newSound(Gdx.files.internal("damage.ogg"));
        this.sonidoExplosion = Gdx.audio.newSound(Gdx.files.internal("explosion.ogg"));

        // Configurar la música de fondo para que se reproduzca en bucle
        musicaFondo.setLooping(true);
        musicaFondo.setVolume(0.5f);  // Volumen ajustado (ajústalo según prefieras)
        musicaFondo.play();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.05f, 0.05f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();

        // Dibujar la imagen de fondo
        batch.draw(fondo, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        // Mostrar puntaje y nivel en la parte superior derecha de la pantalla
        font.draw(batch, "Puntaje: " + puntos, Gdx.graphics.getWidth() - 150, Gdx.graphics.getHeight() - 10);
        font.draw(batch, "Nivel: " + nivel, Gdx.graphics.getWidth() - 150, Gdx.graphics.getHeight() - 40);

        // Dibujar corazones en la parte inferior izquierda de la pantalla
        for (int i = 0; i < vidas; i++) {
            batch.draw(corazon, 10 + i * 40, 10);  // Coordenada inicial en (10, 10)
        }

        // Renderizar jugador, meteoritos y proyectiles
        jugador.render(batch);
        for (Meteorito meteorito : administradorMeteoritos.getMeteoritos()) {
            meteorito.render(batch);
        }
        for (Proyectil proyectil : administradorProyectiles.getProyectiles()) {
            proyectil.render(batch);
        }

        batch.end();

        jugador.update(delta);
        administradorMeteoritos.update(delta);
        administradorProyectiles.update(delta);

        // Manejo de colisiones con proyectiles y actualización de puntos
        puntos += SistemaColisiones.manejarColisiones(jugador, administradorMeteoritos.getMeteoritos(), administradorProyectiles.getProyectiles());

        // Comprobar colisiones entre meteoritos y jugador para reducir vidas
        for (Meteorito meteorito : administradorMeteoritos.getMeteoritos()) {
            if (meteorito.getRectangulo().overlaps(jugador.getRectangulo())) {
                if (vidas > 1) {
                    sonidoDamage.play(); // Sonido de daño
                } else {
                    sonidoExplosion.play(); // Sonido de explosión si es la última vida
                }
                vidas--;  // Reducir vida
                administradorMeteoritos.getMeteoritos().removeValue(meteorito, true);  // Remover meteorito
                if (vidas <= 0) {
                    musicaFondo.stop();  // Detener la música de fondo
                    game.setScreen(new GameOverScreen(game));  // Ir a la pantalla de Game Over
                }
                break;
            }
        }

        // Verificar si se ha alcanzado el puntaje para cambiar de nivel
        if (puntos >= 100) {
            nivel++;
            puntos = 0; // Reiniciar el puntaje para el nuevo nivel
            tiempoGeneracionMeteorito *= 0.9f; // Reducir intervalo de generación de meteoritos

            // Limpiar todos los meteoritos y proyectiles para reiniciar el nivel
            administradorMeteoritos.getMeteoritos().clear();
            administradorProyectiles.getProyectiles().clear();
        }

        // Generación aleatoria de meteoritos a intervalos regulares
        tiempoAcumulado += delta;
        if (tiempoAcumulado >= tiempoGeneracionMeteorito) {
            int meteoritosGenerados = MathUtils.random(1, nivel); // Número de meteoritos depende del nivel
            for (int i = 0; i < meteoritosGenerados; i++) {
                float xAleatorio = MathUtils.random(0, Gdx.graphics.getWidth() - texturaMeteorito.getWidth());
                float velocidadAleatoria = 100 + MathUtils.random(0, nivel * 20); // Velocidad aumenta con el nivel
                Meteorito nuevoMeteorito = new Meteorito(texturaMeteorito, xAleatorio, Gdx.graphics.getHeight(), velocidadAleatoria);
                administradorMeteoritos.spawnMeteorito(nuevoMeteorito);
            }
            tiempoAcumulado = 0;
        }

        // Control de disparo con cooldown
        tiempoDisparo += delta;
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE) && tiempoDisparo >= cooldownDisparo) {
            administradorProyectiles.disparar(jugador.getX() + jugador.getWidth() / 2, jugador.getY() + jugador.getHeight());
            sonidoDisparo.play();  // Sonido de disparo
            tiempoDisparo = 0;
        }
    }

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
        corazon.dispose();
        texturaJugador.dispose();
        texturaProyectil.dispose();
        texturaMeteorito.dispose();
        fondo.dispose();  // Liberar imagen de fondo
        musicaFondo.dispose();  // Liberar música de fondo

        // Liberar sonidos
        sonidoDisparo.dispose();
        sonidoPop.dispose();
        sonidoDamage.dispose();
        sonidoExplosion.dispose();
    }

    @Override public void show() {}
    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
}
