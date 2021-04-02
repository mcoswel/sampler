package com.sampler.common;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class LetterTile {
    private float x;
    private float y;
    private TextureRegion textureRegion;

    public LetterTile(float x, float y, TextureRegion textureRegion) {
        this.x = x;
        this.y = y;
        this.textureRegion = textureRegion;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public TextureRegion getTextureRegion() {
        return textureRegion;
    }

    public void setTextureRegion(TextureRegion textureRegion) {
        this.textureRegion = textureRegion;
    }
}
