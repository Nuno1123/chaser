package org.academiadecodigo.bootcamp.milenos.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import org.academiadecodigo.bootcamp.milenos.Game;
import org.academiadecodigo.bootcamp.milenos.sprites.Dog;

/**
 * Created by milena on 16/03/16.
 */
public class PlayState extends State{
    public static final int DOG_X = 50;
    public static final int DOG_Y = 300;

    private Dog dog;
    private Texture bg;

    public PlayState(GameStateManager gsm) {
        super(gsm);
        dog = new Dog(DOG_X, DOG_Y);
        bg = new Texture("bg.png");
    }

    @Override
    public void handleInput() {

        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            dog.run();
        } else {
            dog.stop();
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
        dog.update(dt);

    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.draw(bg,  0, 0, Game.WIDTH, Game.HEIGHT);
        sb.draw(dog.getTexture(), dog.getPosition().x, dog.getPosition().y);
        sb.end();
    }

    @Override
    public void dispose() {
        bg.dispose();
        dog.dispose();
    }
}
