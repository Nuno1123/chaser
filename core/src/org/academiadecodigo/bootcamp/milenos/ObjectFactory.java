package org.academiadecodigo.bootcamp.milenos;

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
                //gameObject = new Box();
                break;
            case PILAR:
                //gameObject = new Box();
                break;
        }
        return gameObject;
    }

}
