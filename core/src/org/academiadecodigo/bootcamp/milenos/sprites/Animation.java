package org.academiadecodigo.bootcamp.milenos.sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

import java.util.Iterator;

/**
 * Created by Milena on 16/03/2016.
 */
public class Animation {

    private Array<TextureRegion> frames;

    //how long a frame stays in view before switch
    private float maxframeTime;

    //time animation has been in the current frame
    private float currentFrameTime;

    //number of frames in our animation
    private int frameCount;

    //current frame
    private int frame;

    private Array<Sprite> sprites; //TODO:

    //cycleTime how long it will take to cycle through all the animation
    public Animation(TextureRegion region, int frameCount, float cycleTime) {
        frames = new Array<TextureRegion>();
        sprites = new Array<Sprite>(); //TODO
        //the width of a single frame
        int frameWidth = region.getRegionWidth() / frameCount;

        for (int i = 0; i < frameCount; i++) {
            //frames.add(new TextureRegion(region, i * frameWidth, 0, frameWidth, region.getRegionHeight()));
            sprites.add(new Sprite(new TextureRegion(region, i * frameWidth, 0, frameWidth, region.getRegionHeight()))); //TODO
        }
        this.frameCount = frameCount;
        maxframeTime = cycleTime / frameCount;
        frame = 0;
    }

    //dt time beetwen render cycles
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

    //TODO: GOOD LORD
    public Iterator<Sprite> iterator() {
        return sprites.iterator();
    }

    /*public TextureRegion getFrame() {
        return frames.get(frame);
    }*/

    public Sprite getSprite() { //TODO
        return sprites.get(frame);
    }

}