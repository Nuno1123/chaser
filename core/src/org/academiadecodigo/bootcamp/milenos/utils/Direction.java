package org.academiadecodigo.bootcamp.milenos.utils;

/**
 * Created by milena on 18/03/16.
 */
public enum Direction {

    FIRST_OCTANT(1f, 45f),
    SECOND_OCTANT(46f, 90f),
    THIRD_OCTANT(91f, 135f),
    FOURTH_OCTANT(136f, 180f),
    FIFTH_OCTANT(181f, 225f),
    SIXTH_OCTANT(226f, 270f),
    SEVENTH_OCTANT(271f, 315f),
    EIGHT_OCTANT(316f, 0f);

    private float minAngle;
    private float maxAngle;

    Direction(float minAngle, float maxAngle) {
        this.minAngle = minAngle;
        this.maxAngle = maxAngle;
    }

    public float getMinAngle() {
        return minAngle;
    }

    public float getMaxAngle() {
        return maxAngle;
    }

    public static Direction getDirectionByAngle(float angle) {

        for (Direction direction : Direction.values()) {

            if (angle <= direction.maxAngle && angle >= direction.minAngle) {
                return direction;
            }
        }

        return null;
    }

    public static boolean isOpposite(Direction d1, Direction d2) {

        return d1.minAngle + 180 == d2.minAngle;
    }


}
