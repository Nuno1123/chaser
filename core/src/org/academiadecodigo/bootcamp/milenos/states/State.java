package org.academiadecodigo.bootcamp.milenos.states;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by milena on 15/03/16.
 */

//we can use screen in the game class instead of states
//and use spritenaps instead of textures
public abstract class State {
    private Vector3 mouse;
    private GameStateManager gsm;

    public State(GameStateManager gsm) {
        this.gsm = gsm;
        mouse = new Vector3();
    }

    public GameStateManager getGsm() {
        return gsm;
    }


    public abstract void handleInput();

    //dt: difference beetwen the rendered frame and the next one
    public abstract void update(float dt);

    //container of everything we need to render (textures etc)
    public abstract void render(SpriteBatch sb);

    public abstract void dispose();


}
