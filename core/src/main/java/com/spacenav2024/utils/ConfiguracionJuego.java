package com.spacenav2024.utils;

public class ConfiguracionJuego {
    private static ConfiguracionJuego instancia;
    private int nivelActual;
    private int puntajeActual;
    private boolean sonidosActivados;

    private ConfiguracionJuego() {
        nivelActual = 1;
        puntajeActual = 0;
        sonidosActivados = true;
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

    public int getPuntajeActual() {
        return puntajeActual;
    }

    public void setPuntajeActual(int puntaje) {
        this.puntajeActual = puntaje;
    }

    public boolean isSonidosActivados() {
        return sonidosActivados;
    }

    public void setSonidosActivados(boolean estado) {
        this.sonidosActivados = estado;
    }
}
