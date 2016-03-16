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

    //private Texture dogTexture = new Texture("d0000.png");
    //private Sprite dogText = new Sprite(dogTexture);

    private Vector2 position;
    private Vector2 velocity;
    private Animation[] dogAnimations;
    private Animation currentAnimation;
    private Rectangle bounds;


    //private Texture dog;


    public Dog(int x, int y) {
        position = new Vector2(x, y);
        velocity = new Vector2(0, 0);
        //dog = new Texture(PATH_STOP);

        dogAnimations = new Animation[]{
                new Animation(new TextureRegion(new Texture(PATH_STOP)), NUM_FRAMES_STOP, CYCLE_TIME),
                new Animation(new TextureRegion(new Texture(PATH_WALK)), NUM_FRAMES_WALK, CYCLE_TIME),
                new Animation(new TextureRegion(new Texture(PATH_RUN)), NUM_FRAMES_RUN, CYCLE_TIME)
        };

        currentAnimation = dogAnimations[0];

        bounds = new Rectangle(x, y,
                currentAnimation.getSprite().getWidth() / NUM_FRAMES_RUN,
                currentAnimation.getSprite().getHeight());
    }

    public Vector2 getPosition() {
        return position;
    }

    /*public TextureRegion getTexture() {
        return currentAnimation.getSprite();
    }*/

    /*public Sprite getDogText() {
        return dogText;
    }*/

    public Rectangle getBounds() {
        return bounds;
    }

    public Animation getAnimation() {
        return currentAnimation;
    }

    //reset the position in the game
    public void update(float dt) {

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
        //position.add(dogText.getX(), dogText.getY());
        //reverse the scaled velocity
        velocity.scl(1 / dt);
        bounds.setPosition(position.x, position.y);
    }

    public void run() {
        currentAnimation = dogAnimations[2];
        velocity.x = 140 * (float) Math.cos(Math.toRadians(currentAnimation.getSprite().getRotation()));
        velocity.y = 140 * (float) Math.sin(Math.toRadians(currentAnimation.getSprite().getRotation()));
    }

    public void walk() {
        currentAnimation = dogAnimations[1];
        velocity.x = 60 * (float) Math.cos(Math.toRadians(currentAnimation.getSprite().getRotation()));
        velocity.y = 60 * (float) Math.sin(Math.toRadians(currentAnimation.getSprite().getRotation()));
    }

    public void stop() {
        currentAnimation = dogAnimations[0];
        velocity.x = 0;
        velocity.y = 0;
    }

    public void rotateLeft() {

 /*       dogRunAnimation.getSprite().setOrigin(dogRunAnimation.getSprite().getWidth()/2, dogRunAnimation.getSprite().getHeight()/2);
        dogRunAnimation.getSprite().rotate(1);*/
        for (Animation dogAnimation : dogAnimations) {
            rotateAllLeft(dogAnimation);
        }
        /*dogText.setOrigin(dogText.getWidth()/2, dogText.getHeight()/2);
        dogText.rotate(2);
        */

    }

    private void rotateAllLeft(Animation dogAnimation) {
        Iterator<Sprite> it = dogAnimation.iterator();

        Sprite sprite;

        while (it.hasNext()) {
            sprite = it.next();
            sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);
            sprite.rotate(1);
        }

    }


    public void rotateRight() {

/*        dogRunAnimation.getSprite().setOrigin(dogRunAnimation.getSprite().getWidth() / 2, dogRunAnimation.getSprite().getHeight() / 2);
        dogRunAnimation.getSprite().rotate(-1);*/
        for (Animation dogAnimation : dogAnimations) {
            rotateAllRight(dogAnimation);
        }
        /*dogText.setOrigin((dogText.getWidth())/2, dogText.getHeight()/2);
        dogText.rotate(-2);
        */
    }

    private void rotateAllRight(Animation dogAnimation) {
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
        //dog.dispose();
        //dogTexture.dispose();
    }

    @Override
    public void move(int speed, int angle) {

    }

    //TODO: react to keyboard input
}
