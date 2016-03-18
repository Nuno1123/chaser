package org.academiadecodigo.bootcamp.milenos.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import org.academiadecodigo.bootcamp.milenos.DogTrials;
import org.academiadecodigo.bootcamp.milenos.sprites.Dog;
import org.academiadecodigo.bootcamp.milenos.sprites.Sheep;

/**
 * Created by milena on 16/03/16.
 */
public class PlayState extends State {
    public static final int DOG_X = 0;
    public static final int DOG_Y = 0;
    private static final int NUM_SHEEPS = 4;

    private Dog dog;
    private Texture bg;
    private Sheep[] sheeps = new Sheep[NUM_SHEEPS];

    public PlayState(GameStateManager gsm) {
        super(gsm);
        dog = new Dog(DOG_X, DOG_Y);
        for (int i = 0; i < NUM_SHEEPS; i++) {
            sheeps[i] = new Sheep(400 + i * 100, 400);
        }
        bg = new Texture("bg.png");
    }

    @Override
    public void handleInput() {

        // Keys for dog rotation
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            dog.rotateLeft();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            dog.rotateRight();
        }

        // Keys for dog speed
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            dog.run();
        } else if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            dog.walk();
        } else {
            dog.stop();
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
        dog.update(dt);
        for (int i = 0; i < sheeps.length; i++) {
            sheeps[i].update(dt);
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.draw(bg, 0, 0, DogTrials.WIDTH, DogTrials.HEIGHT);

        // Move dog
        dog.getAnimation().getSprite().setPosition(dog.getPosition().x, dog.getPosition().y);
        dog.getAnimation().getSprite().draw(sb);

        // Move Sheep
        moveSheep();

        for (int i = 0; i < sheeps.length; i++) {
            sheeps[i].getCurrentAnimation().getSprite().setPosition(sheeps[i].getPosition().x, sheeps[i].getPosition().y);
            sheeps[i].getCurrentAnimation().getSprite().draw(sb);
        }

        sb.end();
    }

    @Override
    public void dispose() {
        bg.dispose();
        dog.dispose();
        for (int i = 0; i < sheeps.length; i++) {
            sheeps[i].dispose();
        }
    }

    public void moveSheep() {
        for (int i = 0; i < sheeps.length; i++) {
            sheeps[i].move(dog, sheeps);
        }
    }
}
