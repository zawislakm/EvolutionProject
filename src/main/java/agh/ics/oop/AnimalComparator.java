package agh.ics.oop;

import java.util.Comparator;

public class AnimalComparator implements Comparator<Animal> {

    @Override
    public int compare(Animal ani1, Animal ani2) {

        if (ani1.withMostCommon && ani2.withMostCommon) {
            return 0; // ??
        }

        if (ani1.withMostCommon) {
            return 1;
        }
        if (ani2.withMostCommon) {
            return -1;
        }

        if (ani1.energy != ani2.energy) {
            return ani2.energy - ani1.energy; //-1
        } else {
            if (ani1.age > ani2.age) {
                return -1; //-1
            } else if (ani1.age < ani2.age) {
                return 1;
            } else {
                if (ani1.child > ani2.child) {
                    return -1; // -1
                } else if (ani1.child < ani2.child) {
                    return 1;
                }
            }
        }
        return 0;
    }
}
