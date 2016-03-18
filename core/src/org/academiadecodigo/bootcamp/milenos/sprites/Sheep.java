package org.academiadecodigo.bootcamp.milenos.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import org.academiadecodigo.bootcamp.milenos.DogTrials;
import org.academiadecodigo.bootcamp.milenos.states.MenuState;
import org.academiadecodigo.bootcamp.milenos.utils.Direction;

import java.util.Iterator;

/**
 * Created by milena on 14/03/16.
 */
public class Sheep extends Animal {

    private static final String PATH = "sheep_animation.png";
    private static final int NUM_FRAMES = 4;

    private static final float CYCLE_TIME = 1f;

    private static final int radius1 = 50; // in pixels
    private static final int radius2 = 60; // in pixels
    private static final int SHEEP_RADIUS = 80; // in pixels

    //TODO: move this to the super class (?!)
    private static final int VELOCITY_RUNNING = 100;
    private static final int VELOCITY_WALKING = 40;
    private static final int VELOCITY_GRAZE = 10;

    //TODO: see which properties should be in the super class
    private Vector2 position;
    private Vector2 velocity;
    private Animation sheepAnimation;
    private Rectangle bounds;

    private Direction direction;
    private Vector2 lastPosition;
    private int currentVelocity;

    /**
     * Constructor
     *
     * @param x position in the x axis (in pixels)
     * @param y position in the y axis (in pixels)
     */
    public Sheep(int x, int y) {

        position = new Vector2(x, y);
        velocity = new Vector2(0, 0);

        direction = Direction.EIGHT_OCTANT;
        lastPosition = position.cpy();
        currentVelocity = VELOCITY_GRAZE;

        sheepAnimation = new Animation(new TextureRegion(new Texture(PATH)), NUM_FRAMES, CYCLE_TIME);
        bounds = new Rectangle(x, y, sheepAnimation.getSprite().getWidth(), sheepAnimation.getSprite().getHeight());
    }

    public Vector2 getPosition() {
        return position;
    }

    public Animation getSheepAnimation() {
        return sheepAnimation;
    }

    //TODO: maybe, implement in the super class Animal (?!) since it's exactly the same
    public void update(float dt) {

        lastPosition = position.cpy(); // scp gives a new object instead of pointing to the same object.

        sheepAnimation.update(dt);

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

        //multiply velocity by a deltaTime to scale
        velocity.scl(dt);
        position.add(velocity.x, velocity.y);
        //reverse the scaled velocity
        velocity.scl(1 / dt);
        bounds.setPosition(position.x, position.y);

        direction = Direction.getDirectionByAngle(sheepAnimation.getSprite().getRotation());
    }

    public void move(Dog dog, Sheep[] sheeps) {

        double tempX = Math.abs(dog.getPosition().x + dog.getAnimation().getSprite().getWidth() / 2 - position.x + sheepAnimation.getSprite().getWidth() / 2);
        double tempY = Math.abs(dog.getPosition().y + dog.getAnimation().getSprite().getHeight() / 2 - position.y + sheepAnimation.getSprite().getHeight() / 2);

        // if dog is out of radius2:
        if (tempX > radius2 && tempY > radius2) {
            moveQuietly(sheeps);
            return;
        }

        // if dog is inside of radius1:
        if (tempX <= radius1 && tempY <= radius1) {
            moveHopelessly(dog);
            return;
        }

        // dog is inside of radius2 and out of radius1:
        // if dog is running:
        if (dog.getCurrentVelocity() == dog.getVelocityRunning()) {
            moveHopelessly(dog);
            return;
        }

        // if distance is smaller AND dog direction is opposite to sheep's, regardless of dog's speed:
        if (dog.getPosition().dst(position) < dog.getLastPosition().dst(lastPosition) &&
                Direction.isOpposite(direction, dog.getDirection())) {
            moveHopelessly(dog);
            return;
        }

        // if distance is smaller AND dog is walking:
        if (dog.getCurrentVelocity() == dog.getVelocityWalking() &&
                dog.getPosition().dst(position) < dog.getLastPosition().dst(lastPosition)) {
            moveHalfSpeed(dog);
            //System.out.println("sheep moving half speed");
            return;
        }

        moveQuietly(sheeps);

    }

    private void moveQuietly(Sheep[] sheeps) {

        rotateToOtherSheep(sheeps);

        currentVelocity = VELOCITY_GRAZE;

        velocity.x = currentVelocity * (float) Math.cos(Math.toRadians(sheepAnimation.getSprite().getRotation()));
        velocity.y = currentVelocity * (float) Math.sin(Math.toRadians(sheepAnimation.getSprite().getRotation()));
    }


    private Vector2 findCenter(Sheep[] sheeps) {

        float x = 0;
        float y = 0;

        for (Sheep sheep : sheeps) {
            x += sheep.getPosition().x;
            y += sheep.getPosition().y;
        }

        x = x / sheeps.length;
        y = y / sheeps.length;

        return new Vector2(x, y);
    }


    private void rotateToOtherSheep(Sheep[] sheeps) {

        boolean farAway = true;

        // find sheep flock center point
        Vector2 centerPoint = findCenter(sheeps);

        // check if sheep is to far from the found center point
        if (Math.abs(position.x - centerPoint.x) < SHEEP_RADIUS &&
                Math.abs(position.y - centerPoint.y) < SHEEP_RADIUS) {
            farAway = false;
        }

        // if the sheep is far away from the center sheep point
        if (farAway) {
            //rotate in direction of the center point
            double angleToTurn = Math.atan2((double) centerPoint.y - position.y, (double) centerPoint.x - position.x);
            rotateSprites((float) Math.toDegrees(angleToTurn));
            return;
        }

        int randomRotation = -1 + (int) (Math.random() * 1) + 1;
        Iterator<Sprite> it = sheepAnimation.iterator();
        Sprite sprite;

        while (it.hasNext()) {
            sprite = it.next();
            sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);

            // careful: use ROTATE in this one, not SET_ROTATION
            sprite.rotate(randomRotation);
            sprite.setRotation((sprite.getRotation() + 360) % 360);
        }
    }


    private void moveHopelessly(Dog dog) {

        rotateHopelessly(dog);

        currentVelocity = VELOCITY_RUNNING;

        velocity.x = currentVelocity * (float) Math.cos(Math.toRadians(sheepAnimation.getSprite().getRotation()));
        velocity.y = currentVelocity * (float) Math.sin(Math.toRadians(sheepAnimation.getSprite().getRotation()));
    }

    private void rotateHopelessly(Dog dog) {

        if (currentVelocity != VELOCITY_RUNNING) {
            rotateSprites(dog.getAnimation().getSprite().getRotation() - 90);
        }

        Iterator<Sprite> it = sheepAnimation.iterator();
        Sprite sprite;

        while (it.hasNext()) {
            sprite = it.next();
            sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);

            // careful: use ROTATE in this one, not SET_ROTATION
            sprite.rotate(randomRotation);
            sprite.setRotation((sprite.getRotation() + 360) % 360);
        }




    }

    private void moveHalfSpeed(Dog dog) {

        rotateAwayFromDog(dog);

        currentVelocity = VELOCITY_WALKING;

        velocity.x = currentVelocity * (float) Math.cos(Math.toRadians(sheepAnimation.getSprite().getRotation()));
        velocity.y = currentVelocity * (float) Math.sin(Math.toRadians(sheepAnimation.getSprite().getRotation()));
    }


    private void rotateAwayFromDog(Dog dog) {

        rotateSprites(dog.getAnimation().getSprite().getRotation());
    }

    /**
     * Rotates all animation sprites to a given angle
     *
     * @param angle rotation angle
     */
    private void rotateSprites(float angle) {

        Iterator<Sprite> it = sheepAnimation.iterator();
        Sprite sprite;

        while (it.hasNext()) {
            sprite = it.next();
            sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);
            sprite.setRotation(angle);
            sprite.setRotation((sprite.getRotation() + 360) % 360);
        }
    }


    public void dispose() {
        sheepAnimation.getSprite().getTexture().dispose();
    }

    @Override
    public void move(int speed, int angle) {
    }

}
