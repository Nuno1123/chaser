package org.academiadecodigo.bootcamp.milenos.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import org.academiadecodigo.bootcamp.milenos.DogTrials;
import org.academiadecodigo.bootcamp.milenos.sprites.Dog;
import org.academiadecodigo.bootcamp.milenos.sprites.Sheep;
import org.academiadecodigo.bootcamp.milenos.sprites.Shepperd;

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
    private Shepperd shepperd;

    OrthographicCamera camera;

    private ShapeRenderer shapeRenderer; // for debug info

    public PlayState(GameStateManager gsm) {
        super(gsm);
        dog = new Dog(DOG_X, DOG_Y);

        for (int i = 0; i < NUM_SHEEPS; i++) {
            sheeps[i] = new Sheep(400 + i * 100, 400);
        }
        bg = new Texture("bg.png");

        camera = new OrthographicCamera();
        camera.setToOrtho(false, DogTrials.WIDTH, DogTrials.HEIGHT);

        shapeRenderer = new ShapeRenderer();
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
        shepperd.update(dt);

        for (int i = 0; i < sheeps.length; i++) { //TODO: check collisions between sheeps, NOT WORKING YET
            Sheep sheep = sheeps[i];
            sheep.collides(sheeps);
        }

        for (int i = 0; i < sheeps.length; i++) {
            sheeps[i].update(dt);
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        Gdx.gl.glClearColor(0, 0.3f, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        sb.setProjectionMatrix(camera.combined);

        sb.begin();
        sb.draw(bg, 0, 0, DogTrials.WIDTH, DogTrials.HEIGHT);

        // Move dog
        dog.getAnimation().getSprite().setPosition(dog.getPosition().x, dog.getPosition().y);
        dog.getAnimation().getSprite().draw(sb);

        shepperd.rotate(dog);
        shepperd.getShepperdAnimation().getSprite().draw(sb);


        // Move Sheep
        moveSheep();

        for (int i = 0; i < sheeps.length; i++) {
            sheeps[i].getCurrentAnimation().getSprite().setPosition(sheeps[i].getPosition().x, sheeps[i].getPosition().y);
            sheeps[i].getCurrentAnimation().getSprite().draw(sb);
        }

        sb.end();

        // DEGUB INFO
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        //shapeRenderer.setColor(Color.BLUE);
        for (int i = 0; i < sheeps.length; i++) {
            shapeRenderer.setColor(Color.RED);
            shapeRenderer.circle(
                    sheeps[i].getPosition().x + sheeps[i].getBounds().getWidth() / 2,
                    sheeps[i].getPosition().y + sheeps[i].getBounds().getHeight() / 2,
                    Sheep.SHEEP_RADIUS);
            shapeRenderer.setColor(Color.YELLOW);
            shapeRenderer.circle(
                    sheeps[i].getPosition().x + sheeps[i].getBounds().getWidth() / 2,
                    sheeps[i].getPosition().y + sheeps[i].getBounds().getHeight() / 2,
                    Sheep.radius1);
            shapeRenderer.setColor(Color.WHITE);
            shapeRenderer.circle(
                    sheeps[i].getPosition().x + sheeps[i].getBounds().getWidth() / 2,
                    sheeps[i].getPosition().y + sheeps[i].getBounds().getHeight() / 2,
                    Sheep.radius2);
        }

        shapeRenderer.rect(dog.getBounds().getX(), dog.getBounds().getY(), dog.getBounds().getWidth(), dog.getBounds().getHeight());

        //shapeRenderer.rect();
        shapeRenderer.end();
    }

    @Override
    public void dispose() {
        bg.dispose();
        dog.dispose();
        shepperd.dispose();
        for (int i = 0; i < sheeps.length; i++) {
            sheeps[i].dispose();
        }
        shapeRenderer.dispose();
    }

    public void moveSheep() {
        for (int i = 0; i < sheeps.length; i++) {
            sheeps[i].move(dog, sheeps);
        }
    }
}
