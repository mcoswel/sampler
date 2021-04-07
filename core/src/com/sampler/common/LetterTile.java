package com.sampler.common;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class LetterTile {
    private float x;
    private float y;
    private Image image;
    private String letter;
    public LetterTile(float x, float y, TextureRegion textureRegion, String letter) {
        this.x = x;
        this.y = y;
        this.image = new Image(textureRegion);
        if (!letter.equals("blank")){
            this.letter = letter;
        }else{
            this.letter = "";
        }
        image.setPosition(x,y);
        image.setSize(1f, 1f);
    }

    public String getLetter() {
        return letter;
    }

    public void setLetter(String letter) {
        this.letter = letter;
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

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }
}
