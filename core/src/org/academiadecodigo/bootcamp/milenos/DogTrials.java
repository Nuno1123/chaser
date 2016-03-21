package org.academiadecodigo.bootcamp.milenos;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import org.academiadecodigo.bootcamp.milenos.sprites.Dog;
import org.academiadecodigo.bootcamp.milenos.sprites.Sheep;
import org.academiadecodigo.bootcamp.milenos.states.GameStateManager;
import org.academiadecodigo.bootcamp.milenos.states.MenuState;

public class DogTrials extends ApplicationAdapter {

    public static final int WIDTH = 1742;
    public static final int HEIGHT = 980;
    public static final String TITLE = "Chaser";
    private Music soundMusic;
    private Music introMusic;
    private SpriteBatch batch;
    private GameStateManager gms;

    @Override
    public void create() {
        batch = new SpriteBatch();
        gms = new GameStateManager();
        Gdx.gl.glClearColor(0, 0, 0, 1);
        gms.push(new MenuState(gms));
        introMusic = Gdx.audio.newMusic(Gdx.files.internal("intro.mp3"));
        introMusic.play();
        soundMusic = Gdx.audio.newMusic(Gdx.files.internal("playstateMusic.mp3"));

    }


    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0.3f, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        gms.update(Gdx.graphics.getDeltaTime());
        gms.render(batch);

        if (introMusic.isPlaying() || soundMusic.isPlaying()) {
            return;
        }
        soundMusic.setVolume(0.6f);
        soundMusic.setLooping(true);
        soundMusic.play();
    }

    @Override
    public void dispose() {
        batch.dispose();
        introMusic.dispose();
        soundMusic.dispose();
    }
}
