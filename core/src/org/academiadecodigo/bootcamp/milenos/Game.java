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

public class Game extends ApplicationAdapter {

    public static final int WIDTH = 800;
    public static final int HEIGHT = 800;
    public static final String TITLE = "Doggy";

    private Field field;
    private Sheep[] sheeps;
    private Dog dog;

    private SpriteBatch batch;
    private GameStateManager gms;

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
