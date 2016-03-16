package org.academiadecodigo.bootcamp.milenos.sprites;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

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

    //cycleTime how long it will take to cycle through all the animation
    public Animation(TextureRegion region, int frameCount, float cycleTime) {
        frames = new Array<TextureRegion>();

        //the width of a single frame
        int frameWidth = region.getRegionWidth() / frameCount;

        for (int i = 0; i < frameCount; i++) {
            frames.add(new TextureRegion(region, i * frameWidth, 0, frameWidth, region.getRegionHeight()));
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

    public TextureRegion getFrame() {
        return frames.get(frame);
    }

}