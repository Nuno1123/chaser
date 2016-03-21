package org.academiadecodigo.bootcamp.milenos.game_objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Disposable;

/**
 * Created by Joana Falcão on 21/03/16
 */
public class Pillar implements GameObject, Disposable {

    private Rectangle bounds;
    private Texture image;

    public Pillar(Texture image, float width, float height) {
        this.image = image;
        bounds = new Rectangle();
        bounds.setWidth(width);
        bounds.setHeight(height);
    }

    // just for simplicity now, in the future use the get/setX....
    public Rectangle getBounds() {
        return bounds;
    }

    public Texture getTexture() {
        return image;
    }

    public float getX() {
        return bounds.getX();
    }

    public float getY() {
        return bounds.getY();
    }

    public float getWidth() {
        return bounds.getWidth();
    }

    public float getHeight() {
        return bounds.getHeight();
    }


    @Override
    public void dispose() {
        image.dispose();
    }


}
