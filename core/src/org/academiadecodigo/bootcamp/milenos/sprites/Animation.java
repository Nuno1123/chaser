package org.academiadecodigo.bootcamp.milenos.sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

import java.util.Iterator;

/**
 * Created by Milena on 16/03/2016.
 */
public class Animation {

    private Array<Sprite> sprites; //TODO:

    //how long a frame stays in view before switch
    private float maxframeTime;

    //time animation has been in the current frame
    private float currentFrameTime;

    //number of frames in our animation
    private int frameCount;

    //current frame
    private int frame;


    //cycleTime how long it will take to cycle through all the animation
    public Animation(TextureRegion region, int frameCount, float cycleTime) {

        sprites = new Array<Sprite>();
        //the width of a single frame
        int frameWidth = region.getRegionWidth() / frameCount;

        for (int i = 0; i < frameCount; i++) {
            sprites.add(new Sprite(new TextureRegion(region, i * frameWidth, 0, frameWidth, region.getRegionHeight())));
        }

        this.frameCount = frameCount;
        maxframeTime = cycleTime / frameCount;
        frame = 0;
    }

    //dt time between render cycles
    public void update(float dt) {

        currentFrameTime += dt;
        if (currentFrameTime > maxframeTime) {
            frame++;
            currentFrameTime = 0;
        }

        if (frame >= frameCount) {
            frame = 0;
        }
    }


    /**
     * Rotates all animation sprites to a given angle
     *
     * @param angle rotation angle
     */
    public void rotateAllSprites(float angle) {

        Iterator<Sprite> it = sprites.iterator();
        Sprite sprite;

        while (it.hasNext()) {
            sprite = it.next();
            sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);
            sprite.rotate(angle);
            sprite.setRotation((sprite.getRotation() + 360) % 360); //normalize rotation
        }

    }

    /**
     * Sets the rotation of all the animation sprites to a given angle
     *
     * @param angle angle to rotate to
     */
    public void setAllSpritesRotation(float angle) {

        Iterator<Sprite> it = sprites.iterator();
        Sprite sprite;

        while (it.hasNext()) {
            sprite = it.next();
            sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);
            angle = (angle + 360) % 360; //normalize angle
            sprite.setRotation(angle);
        }
    }

    /**
     * Gets the actual rotation of the sprites
     *
     * @return rotation in degrees
     */
    public float getRotation() {

        return sprites.get(frame).getRotation();
    }


    /**
     * @return a sprites iterator
     */
    public Iterator<Sprite> iterator() {
        return sprites.iterator();
    }

    public Sprite getSprite() {
        return sprites.get(frame);
    }

    /**
     *
     * @return the sprites width
     */
    public float getWidth() {
        return sprites.get(frame).getWidth();
    }

    /**
     *
     * @return the sprites height
     */
    public float getHeight() {
        return sprites.get(frame).getHeight();
    }

}