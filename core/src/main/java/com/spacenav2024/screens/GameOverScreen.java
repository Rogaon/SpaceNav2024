package com.spacenav2024.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.audio.Music;
import com.spacenav2024.MainGame;

public class GameOverScreen implements Screen {
    private MainGame game;
    private SpriteBatch batch;
    private BitmapFont font;
    private Texture fondo;
    private Music musicaGameOver;

    public GameOverScreen(MainGame game) {
        this.game = game;
        this.batch = new SpriteBatch();
        this.font = new BitmapFont();
        this.fondo = new Texture("fondo_gameover.jpg");  // Cargar fondo de Game Over
        this.musicaGameOver = Gdx.audio.newMusic(Gdx.files.internal("musica_gameover.mp3"));  // Cargar música de Game Over

        // Configurar la música de Game Over
        musicaGameOver.setLooping(true);
        musicaGameOver.play();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(fondo, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());  // Dibujar fondo de Game Over
        font.draw(batch, "Presiona ENTER para volver a jugar", Gdx.graphics.getWidth() / 2f - 100, Gdx.graphics.getHeight() / 2f - 10);
        font.draw(batch, "Presiona ESCAPE para salir", Gdx.graphics.getWidth() / 2f - 100, Gdx.graphics.getHeight() / 2f - 40);
        batch.end();

        // Manejar la selección del jugador
        if (Gdx.input.isKeyPressed(Input.Keys.ENTER)) {
            musicaGameOver.stop();             // Detener música de Game Over
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

    @Override
    public void show() {}

    @Override
    public void resize(int width, int height) {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}
}