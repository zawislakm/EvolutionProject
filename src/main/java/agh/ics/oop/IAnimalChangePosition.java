package agh.ics.oop;

public interface IAnimalChangePosition { // nazwa bardziej na metodę niż interfejs

    void positionChanged(Animal animal, Vector2d oldPosition, Vector2d newPosition);
}
