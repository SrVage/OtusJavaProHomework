package org.example;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Box implements Iterable<String> {
    private final List<String> firstList;
    private final List<String> secondList;
    private final List<String> thirdList;
    private final List<String> fourthList;

    public Box() {
        this.firstList = new ArrayList<>();
        this.secondList = new ArrayList<>();
        this.thirdList = new ArrayList<>();
        this.fourthList = new ArrayList<>();
    }

    public void addInFirstList(String string) {
        firstList.add(string);
    }

    public void addInSecondList(String string) {
        secondList.add(string);
    }

    public void addInThirdList(String string) {
        thirdList.add(string);
    }

    public void addInFourthList(String string) {
        fourthList.add(string);
    }

    @Override
    public Iterator<String> iterator() {
        return new BoxIterator();
    }

    private class BoxIterator implements Iterator<String> {
        private Iterator<String> currentIterator;
        private int indexCurrentList;

        public BoxIterator() {
            currentIterator = firstList.iterator();
            indexCurrentList = 0;
        }

        @Override
        public boolean hasNext() {
            if (currentIterator.hasNext()) {
                return true;
            } else {
                if (indexCurrentList == 0) {
                    currentIterator = secondList.iterator();
                } else if (indexCurrentList == 1) {
                    currentIterator = thirdList.iterator();
                } else if (indexCurrentList == 2) {
                    currentIterator = fourthList.iterator();
                } else {
                    return false;
                }
                indexCurrentList++;
            }
            return currentIterator.hasNext();
        }

        @Override
        public String next() {
            return currentIterator.next();
        }
    }
}


