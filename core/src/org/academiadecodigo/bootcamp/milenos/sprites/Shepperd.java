package org.academiadecodigo.bootcamp.milenos.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by codecadet on 20/03/16.
 */
public class Shepperd implements Character {

    private static final String PATH_SHEPPERD = "shepperd.png";
    private static final int NUM_FRAMES = 8;
    private static final float CYCLE_TIME = 5.00f;

    private Animation shepperdAnimation;
    private Rectangle bounds;
    private Vector2 position;


    public Shepperd(float x, float y) {

        position = new Vector2(x, y);

        shepperdAnimation = new Animation(new TextureRegion(new Texture(PATH_SHEPPERD)), NUM_FRAMES, CYCLE_TIME);

        bounds = new Rectangle(x, y,
                shepperdAnimation.getWidth() / NUM_FRAMES,
                shepperdAnimation.getHeight());

    }


    public Animation getShepperdAnimation() {
        return shepperdAnimation;
    }

    public void update(float dt) {
        shepperdAnimation.update(dt);
        shepperdAnimation.getSprite().setPosition(bounds.getX(), bounds.getY());
    }


    public void rotate(Dog dog) {
        double dogPositionY = dog.getAnimation().getSprite().getY();
        double dogPositionX = dog.getAnimation().getSprite().getX();
        double angleToTurn = Math.atan2(dogPositionY - position.y, dogPositionX - position.x);

        // kind of overrides to the Animation method because we need to set a different origin rotation
        shepperdAnimation.setAllSpritesRotation((float) Math.toDegrees(angleToTurn),
                shepperdAnimation.getWidth() / 5, shepperdAnimation.getHeight() / 2);
    }

    public void dispose() {

        shepperdAnimation.getSprite().getTexture().dispose();
    }

    public Rectangle getBounds() {
        return bounds;
    }
}
