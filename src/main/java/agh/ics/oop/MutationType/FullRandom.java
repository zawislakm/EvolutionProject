package agh.ics.oop.MutationType;

import agh.ics.oop.MutationType.IMutationType;

import java.util.List;

public class FullRandom implements IMutationType {

    public List<Integer> mutateGenom(List<Integer> genom, int numberOfmutations) {

        for (int i = 0; i < numberOfmutations; i++) {
            int indexMutate = (int) (Math.random() * genom.size());
            int nowGen = genom.get(indexMutate);
            while (nowGen == genom.get(indexMutate)) {
                int newGen = (int) (Math.random() * 8);
                genom.set(indexMutate, newGen);
            }
        }
        return genom;
    }
}
