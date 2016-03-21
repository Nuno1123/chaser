package org.academiadecodigo.bootcamp.milenos.game_objects;

import com.badlogic.gdx.graphics.Texture;

/**
 * Created by milena on 14/03/16.
 */
public class ObjectFactory {

    public static GameObject getObject(ObjectType objectType, Texture image, float width, float height) {
        GameObject gameObject = null;

        switch (objectType) {
            case BOX:
                gameObject = new Box(image, width, height);
                break;
            case FENCES:
                gameObject = new Fences(image, width, height);
                break;
            case PILLAR:
                gameObject = new Pillar(image, width, height);
                break;
        }
        return gameObject;
    }

}
