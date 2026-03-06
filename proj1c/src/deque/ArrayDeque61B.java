package deque;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.lang.Math;

public class ArrayDeque61B<T> implements Deque61B<T> {
    private int size;
    private T[] items;
    private int nextFirst;
    private int nextLast;

    public ArrayDeque61B(){
        this.items = (T[]) new Object[8];
        this.size = 0;
        this.nextFirst = 0;
        this.nextLast = 1;
    }

    @Override
    public void addFirst(T x) {
        if(size == items.length){
            resize(2*items.length);
        }
        items[nextFirst] = x;
        nextFirst--;
        nextFirst = Math.floorMod(nextFirst, items.length);
        size++;
    }

    @Override
    public void addLast(T x) {
        if(size == items.length){
            resize(2*items.length);
        }
        items[nextLast] = x;
        nextLast++;
        nextLast = Math.floorMod(nextLast, items.length);
        size++;
    }

    @Override
    public List<T> toList() {
        List<T> list = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            int realIndex = Math.floorMod(nextFirst + 1 + i, items.length);
            list.add(items[realIndex]);
        }

        return list;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public T removeFirst() {
        if(size == 0){
            return null;
        }

        int removedIndex = Math.floorMod(nextFirst + 1, items.length);
        T removed = items[removedIndex];
        items[removedIndex] = null;
        nextFirst = removedIndex;
        size --;

        if((double) size /items.length < 0.25){
            resize(items.length/2);
        }
        return removed;
    }

    @Override
    public T removeLast() {
        if (size == 0){
            return null;
        }
        int removedIndex = Math.floorMod(nextLast - 1, items.length);
        T removed = items[removedIndex];
        items[removedIndex] = null;
        nextLast = removedIndex;
        size --;

        if((double) size /items.length < 0.25){
            resize(items.length/2);
        }

        return removed;
    }

    @Override
    public T get(int index) {
        if(index < 0 || index >= size){
            return null;
        }
        int realIndex = Math.floorMod(nextFirst + 1 + index, items.length);
        return items[realIndex];
    }

    @Override
    public T getRecursive(int index) {
        throw new UnsupportedOperationException("No need to implement getRecursive for proj 1b");
    }

    private void resize(int capacity){
        T[] newItems = (T[]) new Object[capacity];
        for (int i = 0; i < size; i++) {
            newItems[i] = get(i);
        }
        items = newItems;
        nextFirst = items.length - 1;
        nextLast = size;
    }

    @Override
    public Iterator<T> iterator() {
        return new ArrayDeque61BIterator();
    }

    private class ArrayDeque61BIterator implements Iterator<T>{
        private int currentIndex;
        private int count;
        public ArrayDeque61BIterator(){
            currentIndex = Math.floorMod(nextFirst + 1, items.length);
            count = 0;
        }
        @Override
        public boolean hasNext() {
            return count < size;
        }
        @Override
        public T next() {
            T item =  items[currentIndex];
            currentIndex = Math.floorMod(currentIndex + 1, items.length);
            count ++;
            return item;
        }
    }

    @Override
    public boolean equals(Object other) {
        if(this == other){return true; }
        if(!(other instanceof ArrayDeque61B<?> otherDeque)){return false;}

        if(this.size() != otherDeque.size()){return false;}

        Iterator<T> it1 = this.iterator();
        Iterator<?> it2 = otherDeque.iterator();
        while(it1.hasNext() && it2.hasNext()){
            T e1 = it1.next();
            Object e2 = it2.next();
            if (e1 == null ? e2 != null : !e1.equals(e2)) {
                return false;
            }
        }

        return true;
    }

    @Override
    public String toString() {
        return this.toList().toString();
    }
}

