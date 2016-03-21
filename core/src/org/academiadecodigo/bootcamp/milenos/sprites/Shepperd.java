package org.academiadecodigo.bootcamp.milenos.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.Iterator;

/**
 * Created by codecadet on 20/03/16.
 */
public class Shepperd extends Animal {

    @Override
    public void move(int speed, int angle) {

    }

    private static final String PATH_SHEPPERD = "shepperd.png";
    private static final int NUM_FRAMES = 10;
    private static final float CYCLE_TIME = 5.00f;

    private Animation shepperdAnimation;
    private Rectangle bounds;
    private Vector2 position;


    public Shepperd(float x, float y) {

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
        shepperdAnimation.getSprite().setPosition(bounds.getX(), bounds.getY());
    }


    public void rotate(Dog dog) {
        double dogPositionY = dog.getAnimation().getSprite().getY();
        double dogPositionX = dog.getAnimation().getSprite().getX();
        double angleToTurn = Math.atan2(dogPositionY - position.y, dogPositionX - position.x);
        rotateSprites((float) Math.toDegrees(angleToTurn));
    }


    public void dispose() {

        shepperdAnimation.getSprite().getTexture().dispose();
    }

    private void rotateSprites(float angle) {

        Iterator<Sprite> it = shepperdAnimation.iterator();
        Sprite sprite;

        while (it.hasNext()) {
            sprite = it.next();
            sprite.setOrigin(sprite.getWidth() / 5, sprite.getHeight() / 2);
            angle = (angle + 360) % 360;
            sprite.setRotation(angle);
        }
    }

    public Rectangle getBounds() {
        return bounds;
    }
}
