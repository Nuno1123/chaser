package org.academiadecodigo.bootcamp.milenos.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import org.academiadecodigo.bootcamp.milenos.DogTrials;

import java.util.Iterator;

/**
 * Created by milena on 14/03/16.
 */
public class Dog extends Animal {

    private static final String PATH_STOP = "dog_stop.png";
    private static final int NUM_FRAMES_STOP = 1;

    private static final String PATH_WALK = "dog.png";
    private static final int NUM_FRAMES_WALK = 8;

    private static final String PATH_RUN = "dog_run_animation.png";
    private static final int NUM_FRAMES_RUN = 4;

    private static final float CYCLE_TIME = 0.5f;

    private final int VELOCITY_RUNNING = 140;
    private final int VELOCITY_WALKING = 60;

    public int getVelocityRunning() {
        return VELOCITY_RUNNING;
    }
    public int getVelocityWalking() {
        return VELOCITY_WALKING;
    }

    private Vector2 position;
    private Vector2 velocity;
    private Animation[] dogAnimations;
    private Animation currentAnimation;
    private Rectangle bounds;

    private int currentVelocity;


    /**
     * Constructor
     * @param x position in the x axis (in pixels)
     * @param y position in the y axis (in pixels)
     */
    public Dog(int x, int y) {

        position = new Vector2(x, y);
        velocity = new Vector2(0, 0);

        dogAnimations = new Animation[]{
                new Animation(new TextureRegion(new Texture(PATH_STOP)), NUM_FRAMES_STOP, CYCLE_TIME),
                new Animation(new TextureRegion(new Texture(PATH_WALK)), NUM_FRAMES_WALK, CYCLE_TIME),
                new Animation(new TextureRegion(new Texture(PATH_RUN)), NUM_FRAMES_RUN, CYCLE_TIME)
        };

        currentAnimation = dogAnimations[0];
        currentVelocity = 0;

        bounds = new Rectangle(x, y,
                currentAnimation.getSprite().getWidth() / NUM_FRAMES_RUN,
                currentAnimation.getSprite().getHeight());
    }

    public Vector2 getPosition() {
        return position;
    }

    public int getCurrentVelocity() {
        return currentVelocity;
    }

    public Animation getAnimation() {
        return currentAnimation;
    }

    //reset the position in the game
    public void update(float dt) {

        //TODO: Correct the limits with the width and height of the image

        currentAnimation.update(dt);

        if (position.y < 0) {
            position.y = 0;
        }

        if (position.y >= DogTrials.HEIGHT) {
            position.y = DogTrials.HEIGHT;
        }

        if (position.x < 0) {
            position.x = 0;
        }

        if (position.x >= DogTrials.WIDTH) {
            position.x = DogTrials.WIDTH;
        }

        //multiply velocity by a deltatime to scale
        velocity.scl(dt);
        position.add(velocity.x, velocity.y);
        //reverse the scaled velocity
        velocity.scl(1 / dt);
        bounds.setPosition(position.x, position.y);
    }

    public void run() {
        // Sets the current animation and velocity to the running dog
        currentAnimation = dogAnimations[2];
        currentVelocity = VELOCITY_RUNNING;

        velocity.x = currentVelocity * (float) Math.cos(Math.toRadians(currentAnimation.getSprite().getRotation()));
        velocity.y = currentVelocity * (float) Math.sin(Math.toRadians(currentAnimation.getSprite().getRotation()));
    }

    public void walk() {
        // Sets the current animation and velocity to the walking dog
        currentAnimation = dogAnimations[1];
        currentVelocity = VELOCITY_WALKING;

        velocity.x = currentVelocity * (float) Math.cos(Math.toRadians(currentAnimation.getSprite().getRotation()));
        velocity.y = currentVelocity * (float) Math.sin(Math.toRadians(currentAnimation.getSprite().getRotation()));
    }

    public void stop() {
        // Sets the current animation and velocity to the stop dog
        currentAnimation = dogAnimations[0];
        currentVelocity = 0;

        velocity.x = 0;
        velocity.y = 0;
    }

    public void rotateLeft() {
        // Rotate all animations
        for (Animation dogAnimation : dogAnimations) {
            rotateAllSpritesLeft(dogAnimation);
        }
    }

    public void rotateRight() {
        // Rotate all animations
        for (Animation dogAnimation : dogAnimations) {
            rotateAllSpritesRight(dogAnimation);
        }
    }

    private void rotateAllSpritesLeft(Animation dogAnimation) {

        Iterator<Sprite> it = dogAnimation.iterator();
        Sprite sprite;

        while (it.hasNext()) {
            sprite = it.next();
            sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);
            sprite.rotate(1);
        }
    }

    private void rotateAllSpritesRight(Animation dogAnimation) {

        Iterator<Sprite> it = dogAnimation.iterator();
        Sprite sprite;

        while (it.hasNext()) {
            sprite = it.next();
            sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);
            sprite.rotate(-1);
        }
    }

    public void dispose() {
        for (Animation anim : dogAnimations) {
            anim.getSprite().getTexture().dispose();
        }
    }

    @Override
    public void move(int speed, int angle) {

    }
}