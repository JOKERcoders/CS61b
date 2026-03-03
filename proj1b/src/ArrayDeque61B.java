import java.util.ArrayList;
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

}
