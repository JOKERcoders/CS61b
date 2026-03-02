import java.util.ArrayList;
import java.util.List;

public class LinkedListDeque61B<T> implements Deque61B<T> {

    @Override
    public void addFirst(T x) {
        Node newNode = new Node(x, sentinel, sentinel.next);
        sentinel.next.prev = newNode;
        sentinel.next = newNode;
        size++;
    }

    @Override
    public void addLast(T x) {
        Node newNode = new Node(x, sentinel.prev, sentinel);
        sentinel.prev.next = newNode;
        sentinel.prev = newNode;
        size++;
    }

    @Override
    public List<T> toList() {
        List<T> returnlist = new ArrayList<T>();
        for (Node current = sentinel.next; current != sentinel; current = current.next){
            returnlist.add(current.value);
        }
        return returnlist;
    }

    @Override
    public boolean isEmpty() {
        return sentinel.next == sentinel && sentinel.prev == sentinel;
    }

    private int size;
    @Override
    public int size() {
        return size;
    }

    @Override
    public T removeFirst() {
        if (sentinel.next == sentinel) {
            return null;
        }

        Node removed = sentinel.next;
        sentinel.next.next.prev = sentinel;
        sentinel.next = sentinel.next.next;

        removed.prev = null;
        removed.next = null;

        size--;
        return removed.value;
    }

    @Override
    public T removeLast() {
        if (sentinel.prev == sentinel) {
            return null;
        }

        Node removed = sentinel.prev;
        sentinel.prev.prev.next = sentinel;
        sentinel.prev = sentinel.prev.prev;

        removed.prev = null;
        removed.next = null;

        size--;
        return removed.value;
    }

    @Override
    public T get(int index) {
        Node current = sentinel;
        if (index < size && index >= 0) {
            for(int i = 0; i <= index; i++) {
                current = current.next;
            }
            return current.value;
        }
        return null;
    }

    @Override
    public T getRecursive(int index) {
        if (index >= size || index < 0) {
            return null;
        }
        return getRecursiveHelper(sentinel.next, index);
    }
        private T getRecursiveHelper(Node node, int index){
            if(index == 0){
                return node.value;
            }
            return getRecursiveHelper(node.next, index-1);
    }

    private Node sentinel;
    public LinkedListDeque61B() {
        sentinel = new Node(null, null, null);
        sentinel.prev = sentinel;
        sentinel.next = sentinel;
        size = 0;
    }

    private class Node{
        public T value;
        public Node next;
        public Node prev;
        public Node(T value, Node prev, Node next){
            this.value = value;
            this.prev = prev;
            this.next = next;
        }
    }

}
