package org.academiadecodigo.bootcamp.milenos.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import org.academiadecodigo.bootcamp.milenos.DogTrials;

import java.util.Iterator;

/**
 * Created by milena on 16/03/16.
 */
public class MenuState extends State {

    /**
     * Intro animation.
     * TODO: Maybe anim can be only that round "logo" fx and the last transition (fade to image) can be achieved via the alpha channel when render???
     */
    private Animation introAnimation;

    private float elapsedTime = 0f;



    public MenuState(GameStateManager gms) {
        super(gms);

        Array<TextureRegion> introFrames = new Array<TextureRegion>();
        String filename = "";
        for (int i = 0; i <= 91; i++) {
            if (i < 10) {
                filename = "intro-0000";
            } else {
                filename = "intro-000";
            }
            filename = filename + i + ".jpg";
            introFrames.add(new TextureRegion(new Texture(Gdx.files.internal("anims/intro-raw/" + filename))));
        }
        introAnimation = new Animation(1f / 15f, introFrames, Animation.PlayMode.NORMAL);

    }



    @Override
    public void handleInput() {
        if (Gdx.input.justTouched()) {
            getGsm().set(new PlayState(getGsm()));
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
    }

    //the SpriteBatch is a box where we pass all the things we want to render
    //we need to close the box after putting all the things inside
    @Override
    public void render(SpriteBatch sb) {
        elapsedTime += Gdx.graphics.getDeltaTime();
        Gdx.gl.glClearColor(0, 0.3f, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        sb.begin();
        sb.draw(introAnimation.getKeyFrame(elapsedTime), 0, 0);
        sb.end();
    }

    @Override
    public void dispose() {
        for (TextureRegion texReg : introAnimation.getKeyFrames()) {
            texReg.getTexture().dispose();
        }
    }
}
