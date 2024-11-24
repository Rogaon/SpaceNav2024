package com.spacenav2024.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public abstract class Entidad {
    protected Texture texture;
    protected float x, y;

    public Entidad(Texture texture, float x, float y) {
        this.texture = texture;
        this.x = x;
        this.y = y;
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, x, y);
    }

    public Rectangle getRectangulo() {
        return new Rectangle(x, y, texture.getWidth(), texture.getHeight());
    }

    public float getX() {
        return x;
    }
    
    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }
    
    public float getY() {
        return y;
    }

    public float getWidth() {
        return texture.getWidth();
    }

    public float getHeight() {
        return texture.getHeight();
    }

    public Texture getTexture() {
        return texture;
    }

    public abstract void update(float delta);
}

