package org.academiadecodigo.bootcamp.milenos.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by codecadet on 20/03/16.
 */
public class Shepperd extends Animal {

    @Override
    public void move(int speed, int angle) {

    }

    private static final String PATH_SHEPPERD = "dog.png";
    private static final int NUM_FRAMES = 10;
    private static final float CYCLE_TIME = 0.75f;

    private Animation shepperdAnimation;
    private Rectangle bounds;
    private Vector2 position;


    public Shepperd(int x, int y) {

        position = new Vector2(x, y);

        shepperdAnimation = new Animation(new TextureRegion(new Texture(PATH_SHEPPERD)), NUM_FRAMES, CYCLE_TIME);

        bounds = new Rectangle(x, y,
                shepperdAnimation.getSprite().getWidth() / NUM_FRAMES,
                shepperdAnimation.getSprite().getHeight());

    }


    public Animation getShepperdAnimation() {
        return shepperdAnimation;
    }

    public void update(float dt) {
        shepperdAnimation.update(dt);

    }


    public void rotate(Dog dog) {
        double dogPositionY = dog.getAnimation().getSprite().getY();
        double dogPositionX = dog.getAnimation().getSprite().getX();
        double angleToTurn = Math.atan2(dogPositionY - position.y, dogPositionX - position.x);

        shepperdAnimation.getSprite().setRotation((float) Math.toDegrees(angleToTurn));
    }


    public void dispose() {

        shepperdAnimation.getSprite().getTexture().dispose();
    }

    public Rectangle getBounds() {
        return bounds;
    }
}
