package org.academiadecodigo.bootcamp.milenos.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import org.academiadecodigo.bootcamp.milenos.Game;

/**
 * Created by milena on 16/03/16.
 */
public class MenuState extends State {
    private Texture background;


    public MenuState(GameStateManager gms) {
        super(gms);
        background = new Texture("bg.png");
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
        sb.begin();
        sb.draw(background, 0, 0, Game.WIDTH, Game.HEIGHT);
        sb.end();
    }

    @Override
    public void dispose() {
        background.dispose();
    }
}
