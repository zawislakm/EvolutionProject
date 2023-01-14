package agh.ics.oop;


public class Plant extends WorldMapElement {

    public Plant(Vector2d position) {
        this.position = position;
    }

    public String toString() {
        return "* ";
    }

    public String getImageName() {
        return "src/main/resources/Images/PLANT.png";
    }
}
