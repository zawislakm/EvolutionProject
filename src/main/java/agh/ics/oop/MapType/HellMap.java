package agh.ics.oop.MapType;

import agh.ics.oop.Animal;
import agh.ics.oop.MapType.IMapType;
import agh.ics.oop.Vector2d;
import agh.ics.oop.WorldMap;

public class HellMap implements IMapType {
    public Vector2d mapSpecyfication(Animal animal, Vector2d lowerLeft, Vector2d upperRight, Vector2d newPosition, int breedCost) {


        int rangeY = upperRight.y;
        int rangeX = upperRight.x;

        int randomY = (int) (Math.random() * rangeY + 1);
        int randomX = (int) (Math.random() * rangeX + 1);
        animal.lowerEnergy(breedCost);


        return new Vector2d(randomX, randomY);
    }
}
