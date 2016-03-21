package org.academiadecodigo.bootcamp.milenos.utils;

import com.badlogic.gdx.utils.Array;
import org.academiadecodigo.bootcamp.milenos.GameObject;
import org.academiadecodigo.bootcamp.milenos.sprites.Animal;
import org.academiadecodigo.bootcamp.milenos.sprites.Sheep;

/**
 * Created by Joana Falcão on 21/03/16
 */
public class CollisionDetector {

    private Array<Sheep> sheeps;
    private Array<GameObject> objects;

    public CollisionDetector(Array<Sheep> sheeps, Array<GameObject> objects) {
        this.sheeps = sheeps;
        this.objects = objects;
    }

    public boolean checkCollisionWithSheeps(Animal animal) {
        return false;
    }

    public boolean checkCollisionWithObjects(Animal animal) {
        return false;
    }

}
