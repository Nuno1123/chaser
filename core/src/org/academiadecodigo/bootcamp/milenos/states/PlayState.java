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
    public static final int DOG_X = 50;
    public static final int DOG_Y = 200;

    private Dog dog;
    private Texture bg;
    private Sheep sheep;

    public PlayState(GameStateManager gsm) {
        super(gsm);
        dog = new Dog(DOG_X, DOG_Y);
        sheep = new Sheep(50, 50);
        bg = new Texture("bg.png");
    }

    @Override
    public void handleInput() {

        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            dog.rotateLeft();
        }

        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            dog.rotateRight();
        }

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

    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.draw(bg, 0, 0, DogTrials.WIDTH, DogTrials.HEIGHT);
        //sb.draw(dog.getTexture(), dog.getPosition().x, dog.getPosition().y);
        dog.getAnimation().getSprite().setPosition(dog.getPosition().x, dog.getPosition().y);
        dog.getAnimation().getSprite().draw(sb);
        /*dog.getDogText().setPosition(dog.getPosition().x, dog.getPosition().y);
        dog.getDogText().draw(sb);
        */
        moveSheep();
        //sb.draw(dog.getDogText(), dog.getPosition().x, dog.getPosition().y);
        sb.end();
    }

    @Override
    public void dispose() {
        bg.dispose();
        dog.dispose();
    }

    public void moveSheep() {
        /*
        if dog !insideRadius2 -> sheep.move(quietly, random angle)

		if dog insideRadius2 && dog !insideRadius1 ->

			if dog running -> sheep.move(desperately, random angle [-45,+45])
			if dog walking -> sheep.move(half speed, angle same as dog)
			if dog stop -> sheep.move(quietly, random angle)

		if dog insideRadius1 -> sheep.move(desperately, random angle [-45,+45])

		*/
    }
}
