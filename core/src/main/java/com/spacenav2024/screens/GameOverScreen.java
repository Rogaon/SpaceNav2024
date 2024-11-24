package com.spacenav2024.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.spacenav2024.MainGame;
import com.spacenav2024.utils.ConfiguracionJuego;

public class GameOverScreen implements Screen {
    private MainGame game;
    private SpriteBatch batch;
    private BitmapFont font;
    private Texture fondo;
    private Music musicaGameOver;
    private ConfiguracionJuego config;

    public GameOverScreen(MainGame game) {
        this.game = game;
        this.batch = new SpriteBatch();
        this.font = new BitmapFont();
        this.fondo = new Texture("fondo_gameover.jpg");  // Cargar fondo de Game Over
        this.musicaGameOver = Gdx.audio.newMusic(Gdx.files.internal("musica_gameover.mp3"));  // Cargar música de Game Over

        // Configuración del Singleton
        this.config = ConfiguracionJuego.getInstancia();

        // Configurar la música de Game Over
        musicaGameOver.setLooping(true);
        musicaGameOver.play();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(fondo, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());  // Dibujar fondo de Game Over

        // Crear instancias de GlyphLayout para calcular el ancho del texto
        GlyphLayout layoutReiniciar = new GlyphLayout(font, "Presiona ENTER para volver a jugar");
        GlyphLayout layoutSalir = new GlyphLayout(font, "Presiona ESCAPE para salir");

        // Calcular posiciones centradas
        float xReiniciar = (Gdx.graphics.getWidth() - layoutReiniciar.width) / 2f;
        float xSalir = (Gdx.graphics.getWidth() - layoutSalir.width) / 2f;
        float yInstrucciones = Gdx.graphics.getHeight() / 2f - 100;

        font.getData().setScale(1.5f);
        font.draw(batch, layoutReiniciar, xReiniciar, yInstrucciones);
        font.draw(batch, layoutSalir, xSalir, yInstrucciones - 40);

        batch.end();

        // Manejar la selección del jugador
        if (Gdx.input.isKeyPressed(Input.Keys.ENTER)) {
            musicaGameOver.stop();             // Detener música de Game Over

            // Reiniciar configuración del juego utilizando el Singleton
            config.setNivelActual(1);         // Reiniciar nivel
            config.setPuntajeActual(0);       // Reiniciar puntaje
            game.setScreen(new GameScreen(game));  // Reiniciar el juego
        } else if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit();  // Salir del juego
        }
    }

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
        fondo.dispose();
        musicaGameOver.dispose();
    }

    @Override public void show() {}
    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
}

