package org.academiadecodigo.bootcamp.milenos.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import org.academiadecodigo.bootcamp.milenos.DogTrials;
import org.academiadecodigo.bootcamp.milenos.utils.Direction;

import java.util.Iterator;

/**
 * Created by milena on 14/03/16.
 */
public class Sheep implements Character, Movable {

    private static final float Y_LIMIT_CORRECTION = 25;

    private static final String PATH_WALK = "sheep_animation.png";
    private static final int NUM_FRAMES_WALK = 4;

    private static final String PATH_STOP = "sheep.png";
    private static final int NUM_FRAMES_STOP = 1;

    private static final float CYCLE_TIME = 1f;

    private static final long CHANGE_DIRECTION_TIMER = 750; // in milliseconds
    private long lastDirectionChangeTime; // in milliseconds


    //TODO: public to private or put in some config file.
    public static final int radius1 = 200; // in game units
    public static final int radius2 = 300; // in game units
    public static final int SHEEP_RADIUS = 80; // in game units

    private static final int VELOCITY_RUNNING = 75;
    private static final int VELOCITY_WALKING = 30;
    private static final int VELOCITY_GRAZE = 10;

    private Vector2 position;
    private Vector2 velocity;
    private Animation currentAnimation;
    private Animation[] sheepAnimations;
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

        position = new Vector2(x, y + Y_LIMIT_CORRECTION);
        velocity = new Vector2(0, 0);

        direction = Direction.EIGHT_OCTANT;
        lastPosition = position.cpy();
        currentVelocity = VELOCITY_GRAZE;

        sheepAnimations = new Animation[]{
                new Animation(new TextureRegion(new Texture(PATH_STOP)), NUM_FRAMES_STOP, CYCLE_TIME),
                new Animation(new TextureRegion(new Texture(PATH_WALK)), NUM_FRAMES_WALK, CYCLE_TIME)
        };

        currentAnimation = sheepAnimations[0]; // Only 1 frame

        bounds = new Rectangle(x, y + Y_LIMIT_CORRECTION, currentAnimation.getWidth(), currentAnimation.getHeight());
        lastDirectionChangeTime = System.currentTimeMillis();
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void setCurrentVelocity(int currentVelocity) {
        this.currentVelocity = currentVelocity;
    }

    public void setCurrentAnimation(Animation currentAnimation) {
        this.currentAnimation = currentAnimation;
    }

    public Vector2 getPosition() {
        return position;
    }

    public Animation getCurrentAnimation() {
        return currentAnimation;
    }

    public void update(float dt) {

        lastPosition = position.cpy(); // scp gives a new object instead of pointing to the same object.

        currentAnimation.update(dt);

        if (position.y < Y_LIMIT_CORRECTION) {
            position.y = Y_LIMIT_CORRECTION;
        }

        if (position.y >= (DogTrials.HEIGHT - currentAnimation.getHeight() - Y_LIMIT_CORRECTION)) {
            position.y = DogTrials.HEIGHT - currentAnimation.getHeight() - Y_LIMIT_CORRECTION;
        }

        if (position.x < 0) {
            position.x = 0;
        }

        if (position.x >= (DogTrials.WIDTH - currentAnimation.getWidth())) {
            position.x = DogTrials.WIDTH - currentAnimation.getWidth();
        }

        //multiply velocity by a deltaTime to scale
        velocity.scl(dt);
        position.add(velocity.x, velocity.y);
        //reverse the scaled velocity
        velocity.scl(1 / dt);
        bounds.setPosition(position.x, position.y);

        direction = Direction.getDirectionByAngle(currentAnimation.getRotation());
    }

    public void move(Dog dog, Array<Sheep> sheeps) {


        // if dog is out of radius2:
        if (position.dst(dog.getPosition()) > radius2) {
            moveQuietly(sheeps);
            return;
        }

        // if dog is inside of radius1:
        if (position.dst(dog.getPosition()) <= radius1) {
            moveHopelessly(dog, sheeps);
            return;
        }

        // dog is inside of radius2 and out of radius1:
        // if dog is running:
        if (dog.getCurrentVelocity() == dog.getVelocityRunning()) {
            moveHopelessly(dog, sheeps);
            return;
        }

        // if distance is smaller AND dog direction is opposite to sheep's, regardless of dog's speed:
        if (dog.getPosition().dst(position) < dog.getLastPosition().dst(lastPosition) &&
                Direction.isOpposite(direction, dog.getDirection())) {
            moveHopelessly(dog, sheeps);
            return;
        }

        // if distance is smaller AND dog is walking:
        if (dog.getCurrentVelocity() == dog.getVelocityWalking() &&
                dog.getPosition().dst(position) < dog.getLastPosition().dst(lastPosition)) {
            moveHalfSpeed(dog, sheeps);
            return;
        }

        moveQuietly(sheeps);
    }


    private boolean sheepHasCollidedWithSheeps(Array<Sheep> sheeps) {
        Iterator<Sheep> sheepIterator = sheeps.iterator();
        Sheep currentSheep = null;

        while (sheepIterator.hasNext()) {
            currentSheep = sheepIterator.next();
            if (currentSheep.equals(this)) {
                continue;
            }
            if (currentSheep.getBounds().overlaps(this.bounds)) { // TODO: Fix this tomorrow!
                //    this.currentAnimation.getSprite().setRotation(currentSheep.getCurrentAnimation().getRotation());
                return false;
            }
        }
        return false;
    }

    private void moveQuietly(Array<Sheep> sheeps) {

        rotateToOtherSheep(sheeps);

        velocity.x = currentVelocity * (float) Math.cos(Math.toRadians(currentAnimation.getRotation()));
        velocity.y = currentVelocity * (float) Math.sin(Math.toRadians(currentAnimation.getRotation()));
    }


    private Vector2 findCenter(Array<Sheep> sheeps) {

        float x = 0;
        float y = 0;

        for (Sheep sheep : sheeps) {
            x += sheep.getPosition().x;
            y += sheep.getPosition().y;
        }

        x = x / sheeps.size;
        y = y / sheeps.size;

        return new Vector2(x, y);
    }


    private boolean changeDirection() {
        return System.currentTimeMillis() - lastDirectionChangeTime > CHANGE_DIRECTION_TIMER;
    }


    private void rotateToOtherSheep(Array<Sheep> sheeps) {


        if (!changeDirection()) {
            return;
        }

        if (!sheepHasCollidedWithSheeps(sheeps)) {
            boolean farAway = true;

            // find sheep flock center point
            Vector2 centerPoint = findCenter(sheeps);

            // check if sheep is to far from the found center point
            if (position.dst(centerPoint) < SHEEP_RADIUS) {
                farAway = false;
            }

            // if the sheep is far away from the center sheep point
            if (farAway) {
                //rotate in direction of the center point
                currentVelocity = VELOCITY_WALKING;
                currentAnimation = sheepAnimations[1];
                double angleToTurn = Math.atan2((double) centerPoint.y - position.y, (double) centerPoint.x - position.x);

                setAllAnimationsRotation((float) Math.toDegrees(angleToTurn));
                //currentAnimation.setAllSpritesRotation((float) Math.toDegrees(angleToTurn));
                lastDirectionChangeTime = System.currentTimeMillis();
                return;
            }

            currentVelocity = VELOCITY_GRAZE;


            if (Math.random() * 10 > 3) {
                currentVelocity = 0;
                currentAnimation = sheepAnimations[0];
                return;
            }

            int randomRotation = generateRandom(-5,5);
            rotateAllAnimations(randomRotation);
            System.out.println(randomRotation);
            //currentAnimation.rotateAllSprites(randomRotation);
            lastDirectionChangeTime = System.currentTimeMillis();

        }

    }

    public static int generateRandom(int min, int max) {
        return ((int) (Math.random() * (max - min + 1) + min));
    }

    private void setAllAnimationsRotation(float angle) {
        for (Animation animation : sheepAnimations ) {
            animation.setAllSpritesRotation(angle);
        }
    }

    private void rotateAllAnimations(float angle) {
        for (Animation animation : sheepAnimations) {
            animation.rotateAllSprites(angle);
        }

    }


    private void moveHopelessly(Dog dog, Array<Sheep> sheeps) {

        rotateHopelessly(dog, sheeps);

        currentVelocity = VELOCITY_RUNNING;
        currentAnimation = sheepAnimations[1];

        velocity.x = currentVelocity * (float) Math.cos(Math.toRadians(currentAnimation.getRotation()));
        velocity.y = currentVelocity * (float) Math.sin(Math.toRadians(currentAnimation.getRotation()));
    }

    private void rotateHopelessly(Dog dog, Array<Sheep> sheeps) {

        if (!changeDirection()) {
            return;
        }

        if (!sheepHasCollidedWithSheeps(sheeps)) {
            double angleToTurn = Math.atan2((double) dog.getPosition().y - position.y, (double) dog.getPosition().x - position.x);

            setAllAnimationsRotation((float) Math.toDegrees(angleToTurn) + 180);

            //currentAnimation.setAllSpritesRotation((float) Math.toDegrees(angleToTurn) + 180);
            lastDirectionChangeTime = System.currentTimeMillis();
        }

    }

    private void moveHalfSpeed(Dog dog, Array<Sheep> sheeps) {

        rotateHalfSpeedDog(dog, sheeps);

        currentVelocity = VELOCITY_WALKING;
        currentAnimation = sheepAnimations[1];

        velocity.x = currentVelocity * (float) Math.cos(Math.toRadians(currentAnimation.getRotation()));
        velocity.y = currentVelocity * (float) Math.sin(Math.toRadians(currentAnimation.getRotation()));
    }


    private void rotateHalfSpeedDog(Dog dog, Array<Sheep> sheeps) {

        if (!changeDirection()) {
            return;
        }

        if (!sheepHasCollidedWithSheeps(sheeps)) {

            if (!Direction.isOpposite(direction, dog.getDirection())) {

                if (dog.getDirection() == direction) {

                    setAllAnimationsRotation(dog.getAnimation().getRotation());
                    //currentAnimation.setAllSpritesRotation(dog.getAnimation().getRotation());
                    lastDirectionChangeTime = System.currentTimeMillis();
                    return;
                }

                double angleToTurn = Math.atan2((double) dog.getPosition().y - position.y, (double) dog.getPosition().x - position.x);
                //currentAnimation.setAllSpritesRotation((float) Math.toDegrees(angleToTurn) + 180);
                setAllAnimationsRotation((float) Math.toDegrees(angleToTurn) + 180);

                lastDirectionChangeTime = System.currentTimeMillis();
                return;
            }

            setAllAnimationsRotation(dog.getAnimation().getRotation());
            //currentAnimation.setAllSpritesRotation(dog.getAnimation().getRotation());
            lastDirectionChangeTime = System.currentTimeMillis();
        }

    }

    public void dispose() {
        currentAnimation.getSprite().getTexture().dispose();
    }

    @Override
    public void move(MoveType moveType) {

    }

}
