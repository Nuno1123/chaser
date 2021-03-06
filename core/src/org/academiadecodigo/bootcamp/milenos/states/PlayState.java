package org.academiadecodigo.bootcamp.milenos.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;
import org.academiadecodigo.bootcamp.milenos.game_objects.*;
import org.academiadecodigo.bootcamp.milenos.DogTrials;
import org.academiadecodigo.bootcamp.milenos.sprites.Dog;
import org.academiadecodigo.bootcamp.milenos.sprites.MoveType;
import org.academiadecodigo.bootcamp.milenos.sprites.Sheep;
import org.academiadecodigo.bootcamp.milenos.sprites.Shepperd;

/**
 * Created by milena on 16/03/16.
 */
public class PlayState extends State {
    public static final int DOG_X = 0;
    public static final int DOG_Y = 0;
    private static final int NUM_SHEEPS = 8;

    private Dog dog;
    private Texture bg;
    private Array<Sheep> sheeps = new Array<Sheep>();
    private Shepperd shepperd;

    private Box box;
    private Fences fences;
    private Pillar pillar;

    private Music sheepSound;
    private OrthographicCamera camera;

    private ShapeRenderer shapeRenderer; // for debug info

    public PlayState(GameStateManager gsm) {
        super(gsm);
        dog = new Dog(DOG_X, DOG_Y);
        for (int i = 0; i < NUM_SHEEPS; i++) {
            sheeps.add(new Sheep(400 + i * 100, 400));
        }

        bg = new Texture("bg.png");

        camera = new OrthographicCamera();
        camera.setToOrtho(false, DogTrials.WIDTH, DogTrials.HEIGHT);

        box = (Box) ObjectFactory.getObject(ObjectType.BOX, new Texture("box.png"), 400, 250);

        //TODO: we need to have an array of gameObjects... this is just to show in presentation
        //TODO: the box is special!! there can be only one
        //TODO: maybe put the image path and sizes in the factory (?!)
        fences = (Fences) ObjectFactory.getObject(ObjectType.FENCES, new Texture("fence.png"), 400, 9);
        pillar = (Pillar) ObjectFactory.getObject(ObjectType.PILLAR, new Texture("pole.png"), 25, 25);

        //TODO: in the future this should be set randomly on the field
        box.getBounds().setPosition(400, 600);
        fences.getBounds().setPosition(1000, 150);
        pillar.getBounds().setPosition(1500, 650);

        shepperd = new Shepperd(box.getX() + box.getWidth(), box.getY() - 70);

        sheepSound = Gdx.audio.newMusic(Gdx.files.internal("sheep.mp3"));
        sheepSound.setLooping(true);
        sheepSound.setVolume(0.7f);
        sheepSound.play();

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
            dog.move(MoveType.RUN);
        } else if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            dog.move(MoveType.WALK);
        } else {
            dog.move(MoveType.STOP);
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
        dog.update(dt);
        shepperd.update(dt);

        //TODO: check collisions between sheeps, NOT WORKING YET
/*        for (int i = 0; i < sheeps.length; i++) {
            Sheep sheep = sheeps[i];
            sheep.collides(sheeps);
        }*/

        for (int i = 0; i < sheeps.size; i++) {
            sheeps.get(i).update(dt);
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

        // add GameObjects to the sb
        sb.draw(box.getTexture(), box.getX(), box.getY());
        sb.draw(fences.getTexture(), fences.getX(), fences.getY());
        sb.draw(pillar.getTexture(), pillar.getX(), pillar.getY());


        // Move Sheep
        moveSheep();


        for (int i = 0; i < sheeps.size; i++) {
            sheeps.get(i).getCurrentAnimation().getSprite().setPosition(sheeps.get(i).getPosition().x, sheeps.get(i).getPosition().y);
            sheeps.get(i).getCurrentAnimation().getSprite().draw(sb);
        }


        sb.end();


      //TODO: use the commented code bellow for debug info
        // DEGUB INFO
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        //shapeRenderer.setColor(Color.BLUE);
/*

        for (int i = 0; i < sheeps.size; i++) {
            shapeRenderer.setColor(Color.RED);
            shapeRenderer.circle(
                    sheeps.get(i).getPosition().x + sheeps.get(i).getBounds().getWidth() / 2,
                    sheeps.get(i).getPosition().y + sheeps.get(i).getBounds().getHeight() / 2,
                    Sheep.SHEEP_RADIUS);
            shapeRenderer.setColor(Color.YELLOW);
            shapeRenderer.circle(
                    sheeps.get(i).getPosition().x + sheeps.get(i).getBounds().getWidth() / 2,
                    sheeps.get(i).getPosition().y + sheeps.get(i).getBounds().getHeight() / 2,
                    Sheep.radius1);
            shapeRenderer.setColor(Color.WHITE);
            shapeRenderer.circle(
                    sheeps.get(i).getPosition().x + sheeps.get(i).getBounds().getWidth() / 2,
                    sheeps.get(i).getPosition().y + sheeps.get(i).getBounds().getHeight() / 2,
                    Sheep.radius2);

            // sheep collisions with box
            if (box.getBounds().contains(sheeps.get(i).getBounds())) {
                shapeRenderer.setColor(Color.MAGENTA);
                sheeps.removeIndex(i);
            } else {
                shapeRenderer.setColor(Color.BLUE);
                shapeRenderer.rect(sheeps.get(i).getBounds().getX(), sheeps.get(i).getBounds().getY(),
                        sheeps.get(i).getBounds().getWidth(), sheeps.get(i).getBounds().getHeight());
            }

        }

*/
        shapeRenderer.setColor(Color.WHITE);
        shapeRenderer.rect(dog.getBounds().getX(), dog.getBounds().getY(), dog.getBounds().getWidth(), dog.getBounds().getHeight());
        shapeRenderer.rect(shepperd.getBounds().getX(), shepperd.getBounds().getY(), 5, 5);

        shapeRenderer.setColor(Color.ORANGE);
        shapeRenderer.rect(box.getX(), box.getY(), box.getWidth(), box.getHeight());
        shapeRenderer.end();

    }

    @Override
    public void dispose() {
        bg.dispose();

        // dispose animals
        dog.dispose();
        shepperd.dispose();
        sheepSound.dispose();
        for (int i = 0; i < sheeps.size; i++) {
            sheeps.get(i).dispose();
        }

        // dispose game objects
        box.dispose();
        pillar.dispose();
        fences.dispose();

        shapeRenderer.dispose();
    }

    public void moveSheep() {
        for (int i = 0; i < sheeps.size; i++) {
            sheeps.get(i).move(dog, sheeps);
        }
    }
}
