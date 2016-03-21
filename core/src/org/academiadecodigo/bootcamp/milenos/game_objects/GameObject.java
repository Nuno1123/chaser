package org.academiadecodigo.bootcamp.milenos.game_objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by milena on 14/03/16.
 */
public interface GameObject {

    Rectangle getBounds();

    Texture getTexture();

    float getX();

    float getY();

    float getWidth();

    float getHeight();

}
