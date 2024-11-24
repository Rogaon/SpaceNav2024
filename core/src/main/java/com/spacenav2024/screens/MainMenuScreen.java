package com.spacenav2024.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.audio.Music;
import com.spacenav2024.MainGame;
import com.spacenav2024.utils.ConfiguracionJuego;

public class MainMenuScreen implements Screen {
    private MainGame game;
    private SpriteBatch batch;
    private BitmapFont font;
    private Texture fondo;
    private Music musicaMenu;

    public MainMenuScreen(MainGame game) {
        this.game = game;
        this.batch = new SpriteBatch();
        this.font = new BitmapFont();
        this.fondo = new Texture("fondo_menu.jpg");
        this.musicaMenu = Gdx.audio.newMusic(Gdx.files.internal("musica_menu.mp3"));

        // Reproducir música si los sonidos están activados
        if (ConfiguracionJuego.getInstancia().isSonidosActivados()) {
            musicaMenu.setLooping(true);
            musicaMenu.play();
        }
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(fondo, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        // Configurar texto
        font.getData().setScale(2);

        String mensajeBienvenida = "Bienvenido a Space Navigation!";
        String mensajeIniciar = "Presiona ENTER para comenzar";
        String mensajeSonido = "Presiona S para activar/desactivar sonidos";

        GlyphLayout layoutBienvenida = new GlyphLayout(font, mensajeBienvenida);
        GlyphLayout layoutIniciar = new GlyphLayout(font, mensajeIniciar);
        GlyphLayout layoutSonido = new GlyphLayout(font, mensajeSonido);

        float xBienvenida = (Gdx.graphics.getWidth() - layoutBienvenida.width) / 2;
        float xIniciar = (Gdx.graphics.getWidth() - layoutIniciar.width) / 2;
        float xSonido = (Gdx.graphics.getWidth() - layoutSonido.width) / 2;

        // Ajustar las posiciones 
        font.draw(batch, layoutBienvenida, xBienvenida, Gdx.graphics.getHeight() / 3f + 50);
        font.draw(batch, layoutIniciar, xIniciar, Gdx.graphics.getHeight() / 3f);
        font.draw(batch, layoutSonido, xSonido, Gdx.graphics.getHeight() / 3f - 50);

        batch.end();

        // Manejar entrada del usuario
        if (Gdx.input.isKeyPressed(Input.Keys.ENTER)) {
            musicaMenu.stop();
            game.setScreen(new GameScreen(game));
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.S)) {
            ConfiguracionJuego config = ConfiguracionJuego.getInstancia();
            config.setSonidosActivados(!config.isSonidosActivados());
            if (config.isSonidosActivados()) {
                musicaMenu.play();
            } else {
                musicaMenu.pause();
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
