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
     * @return a sprites iterator
     */
    public Iterator<Sprite> iterator() {
        return sprites.iterator();
    }

    public Sprite getSprite() { //TODO
        return sprites.get(frame);
    }

}