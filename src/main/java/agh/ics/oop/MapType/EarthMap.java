package agh.ics.oop.MapType; // pakiety raczej camelCase

import agh.ics.oop.Animal;
import agh.ics.oop.MapType.IMapType;
import agh.ics.oop.Vector2d;
import agh.ics.oop.WorldMap;

public class EarthMap implements IMapType {

    public Vector2d mapSpecyfication(Animal animal, Vector2d lowerLeft, Vector2d upperRight, Vector2d newPosition, int breedCost) {

        int y = pickY(newPosition.y, lowerLeft.y, upperRight.y);
        if (y != newPosition.y) { // it means that animal tried running away on northpole/southpole
            animal.animalRotate180();
            return animal.getPosition();
        }
        int x = pickX(newPosition.x, lowerLeft.x, upperRight.x);
        animal.lowerEnergy(1);
        return new Vector2d(x, y);

    }

    private int pickY(int newY, int downY, int upY) {

        if (newY > upY) {
            newY = upY;
        }
        if (newY < downY) {
            newY = downY;
        }
        return newY;
    }

    private int pickX(int newX, int leftX, int rightX) {
        if (newX < leftX) {
            return rightX;
        }
        if (newX > rightX) {
            return leftX;
        }
        return newX;
    }
}
