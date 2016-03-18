package org.academiadecodigo.bootcamp.milenos;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import org.academiadecodigo.bootcamp.milenos.sprites.Dog;
import org.academiadecodigo.bootcamp.milenos.sprites.Sheep;
import org.academiadecodigo.bootcamp.milenos.states.GameStateManager;
import org.academiadecodigo.bootcamp.milenos.states.MenuState;

public class DogTrials extends ApplicationAdapter {

    public static final int WIDTH = 1920;
    public static final int HEIGHT = 1080;
    public static final String TITLE = "Chaser";

    private SpriteBatch batch;
    private GameStateManager gms;

    @Override
    public void create() {
        batch = new SpriteBatch();
        gms = new GameStateManager();
        Gdx.gl.glClearColor(0, 0, 0, 1);
        gms.push(new MenuState(gms));
    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        gms.update(Gdx.graphics.getDeltaTime());
        gms.render(batch);
    }
}
