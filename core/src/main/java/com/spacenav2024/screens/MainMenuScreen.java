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

public class MainMenuScreen implements Screen {
    private MainGame game;
    private SpriteBatch batch;
    private BitmapFont font;
    private Texture fondo;
    private Music musicaMenu;
    private float alpha = 1.0f;  // Control de transparencia del texto

    public MainMenuScreen(MainGame game) {
        this.game = game;
        this.batch = new SpriteBatch();
        
        // Configurar fuente para que sea más gruesa y fácil de leer
        this.font = new BitmapFont();
        this.font.getData().setScale(3);  // Escala para mayor grosor
        this.font.setColor(1, 1, 1, alpha);  // Color blanco con transparencia inicial
        
        this.fondo = new Texture("fondo_menu.jpg");  // Cargar fondo de menú
        this.musicaMenu = Gdx.audio.newMusic(Gdx.files.internal("musica_menu.mp3"));  // Cargar música del menú

        // Configurar la música del menú
        musicaMenu.setLooping(true);
        musicaMenu.play();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(fondo, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());  // Dibujar fondo

        // Mensajes a mostrar en el menú de inicio
        String mensajeBienvenida = "Bienvenido a Space Navigation!";
        String mensajeComenzar = "Presiona cualquier tecla para comenzar...";

        // Usar GlyphLayout para calcular el ancho del texto y centrarlo
        GlyphLayout layoutBienvenida = new GlyphLayout(font, mensajeBienvenida);
        GlyphLayout layoutComenzar = new GlyphLayout(font, mensajeComenzar);

        float xBienvenida = (Gdx.graphics.getWidth() - layoutBienvenida.width) / 2;
        float xComenzar = (Gdx.graphics.getWidth() - layoutComenzar.width) / 2;

        // Dibujar texto centrado
        font.draw(batch, layoutBienvenida, xBienvenida, Gdx.graphics.getHeight() / 2 + 50);
        font.draw(batch, layoutComenzar, xComenzar, Gdx.graphics.getHeight() / 2 - 20);
        
        batch.end();

        // Si se presiona cualquier tecla, inicia el juego
        if (Gdx.input.isKeyJustPressed(Input.Keys.ANY_KEY)) {
            musicaMenu.stop();
            game.setScreen(new GameScreen(game));  // Cambiar a la pantalla del juego
        }
    }

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
        fondo.dispose();
        musicaMenu.dispose();
    }

    @Override public void show() {}
    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
}
