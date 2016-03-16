package org.academiadecodigo.bootcamp.milenos.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by milena on 14/03/16.
 */
public class Dog extends Animal {

    private static final String PATH = "dog_run_animation.png";

    private static final int NUM_FRAMES = 4;
    private static final float CYCLE_TIME = 0.5f;

    private Vector2 position;
    private Vector2 velocity;
    private Rectangle bounds;
    private Animation dogAnimation;
    private Texture dog;

    public Dog(int x, int y) {
        position = new Vector2(x, y);
        velocity = new Vector2(0, 0);
        dog = new Texture(PATH);
        dogAnimation = new Animation(new TextureRegion(dog), NUM_FRAMES, CYCLE_TIME);
        bounds = new Rectangle(x, y, dog.getWidth() / NUM_FRAMES, dog.getHeight());
    }

    public Vector2 getPosition() {
        return position;
    }

    public TextureRegion getTexture() {
        return dogAnimation.getFrame();
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public Animation getAnimation() {
        return dogAnimation;
    }

    //reset the position in the game
    public void update(float dt) {
        dogAnimation.update(dt);

        //multiply velocity by a deltatime to scale
        velocity.scl(dt);
        position.add(velocity.x, 0);
        //reverse the scaled velocity
        velocity.scl(1/dt);
        bounds.setPosition(position.x, position.y);
    }

    public void run() {
        velocity.x = 300;
    }

    public void stop() {
        if (velocity.x > 0) {
            velocity.x = velocity.x-10;
        } else {
            velocity.x = 0;
        }
    }

    public void dispose() {
        dog.dispose();
    }

    @Override
    public void move(int speed, int angle) {

    }

    //TODO: react to keyboard input
}
