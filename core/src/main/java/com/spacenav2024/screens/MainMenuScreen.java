package com.spacenav2024.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.spacenav2024.MainGame;
import com.spacenav2024.utils.ConfiguracionJuego;

public class MainMenuScreen implements Screen {
    private MainGame game;
    private SpriteBatch batch;
    private BitmapFont font;
    private Texture fondo;
    private Music musicaMenu;
    private ConfiguracionJuego config;

    public MainMenuScreen(MainGame game) {
        this.game = game;
        this.batch = new SpriteBatch();
        this.font = new BitmapFont();
        this.fondo = new Texture("fondo_menu.jpg");
        this.musicaMenu = Gdx.audio.newMusic(Gdx.files.internal("musica_menu.mp3"));

        // Configuración del Singleton
        this.config = ConfiguracionJuego.getInstancia();

        // Configurar la música del menú
        if (config.isSonidosActivados()) {
            musicaMenu.setLooping(true);
            musicaMenu.play();
        }
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(fondo, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        // Posicionar texto más abajo
        font.getData().setScale(2);
        font.draw(batch, "Presiona ENTER para iniciar el juego", Gdx.graphics.getWidth() / 2f - 150, Gdx.graphics.getHeight() / 2f - 50);
        font.draw(batch, "Presiona S para activar/desactivar sonidos", Gdx.graphics.getWidth() / 2f - 200, Gdx.graphics.getHeight() / 2f - 100);

        batch.end();

        // Manejo de entrada del jugador
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            game.setScreen(new GameScreen(game));
            musicaMenu.stop();
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.S)) {
            boolean estadoSonidos = config.isSonidosActivados();
            config.setSonidosActivados(!estadoSonidos);
            if (!estadoSonidos) {
                musicaMenu.play();
            } else {
                musicaMenu.stop();
            }
        }
        
    }

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
        fondo.dispose();
        musicaMenu.dispose();
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

