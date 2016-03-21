package org.academiadecodigo.bootcamp.milenos.utils;

import com.badlogic.gdx.utils.Array;
import org.academiadecodigo.bootcamp.milenos.game_objects.GameObject;
import org.academiadecodigo.bootcamp.milenos.sprites.Character;

/**
 * Created by Joana Falcão on 21/03/16
 */
public class CollisionDetector {

    private Array<Character> animals;
    private Array<GameObject> objects;

    public CollisionDetector(Array<Character> animals, Array<GameObject> objects) {
        this.animals = animals;
        this.objects = objects;
    }

    public boolean checkCollisionWithAnimals(Character character) {
        return false;
    }

    public boolean checkCollisionWithObjects(Character character) {
        return false;
    }

}
