package chapter03.case08;

import java.util.*;

public class LoadTheArk {

    private ARK ark;

    public int loadTheArk(Collection<Animal> candidates) {
        SortedSet<Animal> animals;
        int numPairs = 0;
        Animal candidate = null;

        animals = new TreeSet<Animal>(new SpeciesGenderComparator());
        animals.addAll(candidates);

        for (Animal a : animals) {
            if (candidate == null || !candidate.isPotentialMate(a))
                candidate = a;
            else {
                ark.load(new AnimalPair(candidate, a));
                ++numPairs;
                candidate = null;
            }
        }

        return numPairs;
    }

    public class Animal {

        public boolean isPotentialMate(Animal a) {
            return false;
        }

    }

    private class AnimalPair {

        public AnimalPair(Animal candidate, Animal a) {
        }

    }

    private class ARK {

        public void load(AnimalPair animalPair) {
        }

    }

    private class SpeciesGenderComparator implements SortedSet<Animal> {
        @Override
        public Comparator<? super Animal> comparator() {
            return null;
        }

        @Override
        public SortedSet<Animal> subSet(Animal fromElement, Animal toElement) {
            return null;
        }

        @Override
        public SortedSet<Animal> headSet(Animal toElement) {
            return null;
        }

        @Override
        public SortedSet<Animal> tailSet(Animal fromElement) {
            return null;
        }

        @Override
        public Animal first() {
            return null;
        }

        @Override
        public Animal last() {
            return null;
        }

        @Override
        public int size() {
            return 0;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public boolean contains(Object o) {
            return false;
        }

        @Override
        public Iterator<Animal> iterator() {
            return null;
        }

        @Override
        public Object[] toArray() {
            return new Object[0];
        }

        @Override
        public <T> T[] toArray(T[] a) {
            return null;
        }

        @Override
        public boolean add(Animal animal) {
            return false;
        }

        @Override
        public boolean remove(Object o) {
            return false;
        }

        @Override
        public boolean containsAll(Collection<?> c) {
            return false;
        }

        @Override
        public boolean addAll(Collection<? extends Animal> c) {
            return false;
        }

        @Override
        public boolean retainAll(Collection<?> c) {
            return false;
        }

        @Override
        public boolean removeAll(Collection<?> c) {
            return false;
        }

        @Override
        public void clear() {

        }
    }

}
