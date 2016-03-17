package org.academiadecodigo.bootcamp.milenos.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import org.academiadecodigo.bootcamp.milenos.DogTrials;

/**
 * Created by milena on 14/03/16.
 */
public class Sheep extends Animal {

    private int radius1;
    private int radius2;

    private Texture sheepTexture = new Texture("bird1.png");
    private Sprite sheep = new Sprite(sheepTexture);
    private Vector2 position;
    private Vector2 velocity;
    private Rectangle bounds;

    public Sheep(int x, int y) {
        position = new Vector2(x, y);
        velocity = new Vector2(0, 0);
        bounds = new Rectangle(x, y, sheep.getWidth(), sheep.getHeight());
    }

    public Vector2 getPosition() {
        return position;
    }

    public Sprite getSheep() {
        return sheep;
    }

    public void update(float dt) {

    }

    public void dispose() {
        sheepTexture.dispose();
    }

    @Override
    public void move(int speed, int angle) {
    }

}
