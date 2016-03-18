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
    private static final int SHEEP_RADIUS = 80;

    //TODO: move this to the super class (?!)
    private static final int VELOCITY_RUNNING = 80;
    private static final int VELOCITY_WALKING = 40;
    private static final int VELOCITY_GRAZE = 10;

    //TODO: see which properties should be in the super class
    private Vector2 position;
    private Vector2 velocity;
    private Animation sheepAnimation;
    private Rectangle bounds;

    private Direction direction;
    private Vector2 lasPosition;

    /**
     * Constructor
     *
     * @param x position in the x axis (in pixels)
     * @param y position in the y axis (in pixels)
     */
    public Sheep(int x, int y) {

        position = new Vector2(x, y);
        velocity = new Vector2(0, 0);

        sheepAnimation = new Animation(new TextureRegion(new Texture(PATH)), NUM_FRAMES, CYCLE_TIME);

        bounds = new Rectangle(x, y, sheepAnimation.getSprite().getWidth(), sheepAnimation.getSprite().getHeight());
    }

    public Vector2 getPosition() {
        return position;
    }

    public Animation getSheepAnimation() {
        return sheepAnimation;
    }

    public Direction getDirection() {
        return direction;
    }

    //TODO: maybe, implement in the super class Animal (?!) since it's exactly the same
    public void update(float dt) {

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

        //multiply velocity by a deltatime to scale
        velocity.scl(dt);
        position.add(velocity.x, velocity.y);
        //reverse the scaled velocity
        velocity.scl(1 / dt);
        bounds.setPosition(position.x, position.y);
    }

    public void move(Dog dog, Sheep[] sheeps) {

        double tempX = Math.abs(dog.getPosition().x + dog.getAnimation().getSprite().getWidth() / 2 - position.x + sheepAnimation.getSprite().getWidth() / 2);
        double tempY = Math.abs(dog.getPosition().y + dog.getAnimation().getSprite().getHeight() / 2 - position.y + sheepAnimation.getSprite().getHeight() / 2);

        if (tempX > radius2 && tempY > radius2) {
            moveQuietly(sheeps);
            System.out.println("sheep moving quietly");
            return;
        }

        if (tempX <= radius1 && tempY <= radius1) {
            moveHopelessly(dog);
            System.out.println("sheep moving hopelessly");
            return;
        }

        if (dog.getCurrentVelocity() == dog.getVelocityRunning()) {
            moveHopelessly(dog);
            System.out.println("sheep moving hopelessly and running");
            return;
        }

        if (dog.getCurrentVelocity() == dog.getVelocityWalking()) {
            moveHalfSpeed(dog);
            System.out.println("sheep moving half speed");
            return;
        }

        moveQuietly(sheeps);

    }

    private void moveQuietly(Sheep[] sheeps) {

        rotateToOtherSheep(sheeps);

        velocity.x = VELOCITY_GRAZE * (float) Math.cos(Math.toRadians(sheepAnimation.getSprite().getRotation()));
        velocity.y = VELOCITY_GRAZE * (float) Math.sin(Math.toRadians(sheepAnimation.getSprite().getRotation()));
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
        Vector2 centerPoint = findCenter(sheeps);

        if (Math.abs(position.x - centerPoint.x) < SHEEP_RADIUS &&
                Math.abs(position.y - centerPoint.y) < SHEEP_RADIUS) {
            farAway = false;
        }

        /*for (Sheep s : sheeps) {

            if (this.equals(s)) {
                continue;
            }

            if (Math.abs(position.x - s.getPosition().x) < SHEEP_RADIUS &&
                    Math.abs(position.y - s.getPosition().y) < SHEEP_RADIUS) {
                farAway = false;
                break;
            }
        }
*/
        int sheepToGoTo = (int) (Math.random() * sheeps.length);
        while (sheeps[sheepToGoTo].equals(this)) {
            sheepToGoTo = (int) (Math.random() * sheeps.length);
        }


        System.out.println(farAway);

        if (farAway) {

            double angleToTurn = Math.atan2((double) centerPoint.y - position.y, (double) centerPoint.x - position.x);

            Iterator<Sprite> it = sheepAnimation.iterator();
            Sprite sprite;

            while (it.hasNext()) {
                sprite = it.next();
                sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);
                sprite.setRotation((float) Math.toDegrees(angleToTurn)); // TODO: DOES THIS WORK?
            }
            return;
        }

        int randomRotation = -1 + (int) (Math.random() * 1) + 1;

        Iterator<Sprite> it = sheepAnimation.iterator();
        Sprite sprite;

        while (it.hasNext()) {
            sprite = it.next();
            sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);
            sprite.rotate(randomRotation); // TODO: DOES THIS WORK?
        }

    }


    private void moveHopelessly(Dog dog) {

        rotateHopelessly(dog);

        velocity.x = VELOCITY_RUNNING * (float) Math.cos(Math.toRadians(sheepAnimation.getSprite().getRotation()));
        velocity.y = VELOCITY_RUNNING * (float) Math.sin(Math.toRadians(sheepAnimation.getSprite().getRotation()));
    }

    private void rotateHopelessly(Dog dog) {

        Iterator<Sprite> it = sheepAnimation.iterator();
        Sprite sprite;

        int extraDegree = -45 + (int) (Math.random() * 45 + 1);

        while (it.hasNext()) {
            sprite = it.next();
            sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);
            sprite.setRotation(dog.getAnimation().getSprite().getRotation() + extraDegree); // TODO: DOES THIS WORK?
        }

    }

    private void moveHalfSpeed(Dog dog) {

        rotateAwayFromDog(dog);

        velocity.x = VELOCITY_WALKING * (float) Math.cos(Math.toRadians(sheepAnimation.getSprite().getRotation()));
        velocity.y = VELOCITY_WALKING * (float) Math.sin(Math.toRadians(sheepAnimation.getSprite().getRotation()));
    }


    private void rotateAwayFromDog(Dog dog) {

        Iterator<Sprite> it = sheepAnimation.iterator();
        Sprite sprite;

        while (it.hasNext()) {
            sprite = it.next();
            sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);
            sprite.setRotation(dog.getAnimation().getSprite().getRotation()); // TODO: DOES THIS WORK?
        }
    }


    public void dispose() {
        sheepAnimation.getSprite().getTexture().dispose();
    }

    @Override
    public void move(int speed, int angle) {
    }

}
