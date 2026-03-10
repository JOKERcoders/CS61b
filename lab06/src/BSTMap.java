import java.util.Iterator;
import java.util.Set;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K,V>{
    private int size;
    private BSTNode root;


    private class BSTNode {
        K key;
        V value;
        BSTNode left;
        BSTNode right;

        private BSTNode(K key, V value, BSTNode left, BSTNode right){
            this.key = key;
            this.value = value;
            this.left = left;
            this.right = right;
        }
    }



    @Override
    public void put(K key, V value) {
        root = put(root,key,value);
    }

    private BSTNode put(BSTNode node, K key, V value){
        if(node == null){
            size ++;
            return new BSTNode(key, value, null, null);
        }
        int cmp = key.compareTo(node.key);
        if(cmp < 0){
            node.left = put(node.left, key, value);
        }
        else if(cmp > 0){
            node.right = put(node.right, key, value);
        }
        else {
            node.value = value;
        }
        return node;
    }



    @Override
    public V get(K key) {
       return search(root,key);
    }

    private V search(BSTNode node, K key){
        if(node == null){
            return null;
        }
        int cmp = key.compareTo(node.key);
        if(cmp < 0){
            return search(node.left, key);
        }
        else if(cmp > 0){
            return search(node.right, key);
        }
        return node.value;
    }




    @Override
    public boolean containsKey(K key) {
        return contains(root, key);
    }

    private boolean contains(BSTNode node, K key){
        if(node == null){
            return false;
        }

        int cmp = key.compareTo(node.key);

        if(cmp < 0){
            return contains(node.left, key);
        }
        else if(cmp > 0){
            return contains(node.right, key);
        }

        return true;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    @Override
    public Set<K> keySet() {
        Set<K> set = new java.util.LinkedHashSet<>();
        addKeys(root, set);
        return set;
    }

    private void addKeys(BSTNode node, Set<K> set) {
        if (node == null) {
            return;
        }

        addKeys(node.left, set);
        set.add(node.key);
        addKeys(node.right, set);
    }


    @Override
    public V remove(K key) {
        V val = get(key);
        if (val != null) {
            root = remove(root, key);
            size--;
        }
        return val;
    }

    private BSTNode remove(BSTNode node, K key) {
        if (node == null) {
            return null;
        }

        int cmp = key.compareTo(node.key);

        if (cmp < 0) {
            node.left = remove(node.left, key);
        }
        else if (cmp > 0) {
            node.right = remove(node.right, key);
        }
        else {
            // case 1 & 2
            if (node.left == null) {
                return node.right;
            }
            if (node.right == null) {
                return node.left;
            }

            // case 3 (two children)
            BSTNode min = findMin(node.right);
            node.key = min.key;
            node.value = min.value;
            node.right = remove(node.right, min.key);
        }

        return node;
    }

    private BSTNode findMin(BSTNode node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    @Override
    public Iterator<K> iterator() {
        java.util.List<K> keys = new java.util.ArrayList<>();
        inOrder(root, keys);
        return keys.iterator();
    }

    private void inOrder(BSTNode node, java.util.List<K> keys) {
        if (node == null) {
            return;
        }

        inOrder(node.left, keys);
        keys.add(node.key);
        inOrder(node.right, keys);
    }
}
