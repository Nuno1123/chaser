package org.academiadecodigo.bootcamp.milenos.utils;

import com.badlogic.gdx.utils.Array;
import org.academiadecodigo.bootcamp.milenos.GameObject;
import org.academiadecodigo.bootcamp.milenos.sprites.Animal;

/**
 * Created by Joana Falcão on 21/03/16
 */
public class CollisionDetector {

    private Array<Animal> animals;
    private Array<GameObject> objects;

    public CollisionDetector(Array<Animal> animals, Array<GameObject> objects) {
        this.animals = animals;
        this.objects = objects;
    }

    public boolean checkCollisionWithSheeps(Animal animal) {
        return false;
    }

    public boolean checkCollisionWithObjects(Animal animal) {
        return false;
    }

}
