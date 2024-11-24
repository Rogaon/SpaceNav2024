// Clase Singleton: ConfiguracionJuego.java
public class ConfiguracionJuego {
    private static ConfiguracionJuego instancia;
    private int nivelActual;
    private boolean sonidosActivados;

    private ConfiguracionJuego() {
        nivelActual = 1; // Nivel inicial
        sonidosActivados = true; // Sonidos activados por defecto
    }

    public static ConfiguracionJuego getInstancia() {
        if (instancia == null) {
            instancia = new ConfiguracionJuego();
        }
        return instancia;
    }

    public int getNivelActual() {
        return nivelActual;
    }

    public void setNivelActual(int nivel) {
        this.nivelActual = nivel;
    }

    public boolean isSonidosActivados() {
        return sonidosActivados;
    }

    public void setSonidosActivados(boolean estado) {
        this.sonidosActivados = estado;
    }
}
