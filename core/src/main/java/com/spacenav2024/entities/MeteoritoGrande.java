package com.spacenav2024.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class MeteoritoGrande extends Meteorito {

    public MeteoritoGrande(Texture texture, float x, float y, float velocidad) {
        super(texture, x, y, 3, velocidad); // Requiere 3 impactos para destruirse
    }

    @Override
    public Rectangle getRectangulo() {
        // Reduce el área de colisión un 20% de cada lado para el meteorito grande
        float reductionFactor = 0.2f;
        return new Rectangle(
            x + texture.getWidth() * reductionFactor / 2,
            y + texture.getHeight() * reductionFactor / 2,
            texture.getWidth() * (1 - reductionFactor),
            texture.getHeight() * (1 - reductionFactor)
        );
    }
}

