package org.academiadecodigo.bootcamp.milenos.utils;

/**
 * Created by milena on 18/03/16.
 */
public enum Direction {

    FIRST_OCTANT(0f, 45f),
    SECOND_OCTANT(45f, 90f),
    THIRD_OCTANT(90f, 135f),
    FOURTH_OCTANT(135f, 180f),
    FIFTH_OCTANT(180f, 225f),
    SIXTH_OCTANT(225f, 270f),
    SEVENTH_OCTANT(270f, 315f),
    EIGHT_OCTANT(315f, 360f);

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

        angle = (angle + 360) % 360;


        for (Direction direction : Direction.values()) {

            if (angle <= direction.maxAngle && angle >= direction.minAngle) {
                return direction;
            }
        }

        System.out.println("null, ang: " + angle);
        return null;
    }

    public static boolean isOpposite(Direction d1, Direction d2) {

        if (d1 == null || d2 == null) {
            return false;
        }

        return (d1.minAngle + 180) % 360 == d2.minAngle
                || (d1.minAngle + 225) % 360 == d2.minAngle
                || (d1.minAngle + 135) % 360 == d2.minAngle;
    }


}
