package puppy.code.lwjgl3;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.spacenav2024.MainGame;

public class Lwjgl3Launcher {
    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = getDefaultConfiguration();
        new Lwjgl3Application(new MainGame(), config);  // Aquí también usas la clase principal del juego
    }

    private static Lwjgl3ApplicationConfiguration getDefaultConfiguration() {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setTitle("SpaceNav2024");
        config.setWindowedMode(800, 600);  // Tamaño de la ventana del juego
        return config;
    }
}
