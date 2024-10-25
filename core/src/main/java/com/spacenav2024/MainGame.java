package com.spacenav2024;

import com.badlogic.gdx.Game;
import com.spacenav2024.screens.MainMenuScreen;

public class MainGame extends Game {
    @Override
    public void create() {
        this.setScreen(new MainMenuScreen(this));  // Iniciar con el menú principal
    }
}