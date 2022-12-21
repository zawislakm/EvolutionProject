package agh.ics.oop.MutationType;

import agh.ics.oop.MutationType.IMutationType;

import java.util.List;

public class SlightFix implements IMutationType {

    public List<Integer> mutateGenom(List<Integer> genom, int numberOfmutations){
        for (int i = 0; i < numberOfmutations; i++) {
            int indexMutate = (int) (Math.random() * genom.size());
            int nowGen = genom.get(indexMutate);
            int newGen = changeGen(nowGen);
            genom.set(indexMutate, newGen);
        }

        return genom;
    }

    private int changeGen(int genNum) {
        return switch (genNum) {
            case 0 -> returnRandom(7, 1);
            case 1 -> returnRandom(0, 2);
            case 2 -> returnRandom(1, 3);
            case 3 -> returnRandom(2, 4);
            case 4 -> returnRandom(3, 5);
            case 5 -> returnRandom(4, 6);
            case 6 -> returnRandom(5, 7);
            case 7 -> returnRandom(7, 0);
            default -> returnRandom(0, 0); //never will happen
        };
    }

    private int returnRandom(int v1, int v2) {
        int pickRandom = (int) (Math.random() * 2);
        return switch (pickRandom) {
            case 0 -> v1;
            case 1 -> v2;
            default -> v1; //never will happen
        };

    }
}
