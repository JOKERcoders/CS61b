import java.util.Arrays;

public class UnionFind {
    // TODO: Instance variables

    /* Creates a UnionFind data structure holding N items. Initially, all
       items are in disjoint sets. */
    private int[] UnionFindSets;
    public UnionFind(int N) {
        UnionFindSets = new int[N];
        Arrays.fill(UnionFindSets, -1);
    }

    /* Returns the size of the set V belongs to. */
    public int sizeOf(int v) {
        return -UnionFindSets[find(v)];
    }

    /* Returns the parent of V. If V is the root of a tree, returns the
       negative size of the tree for which V is the root. */
    public int parent(int v) {
            return UnionFindSets[v];
    }

    /* Returns true if nodes/vertices V1 and V2 are connected. */
    public boolean connected(int v1, int v2) {
        return find(v1) == find(v2);
    }

    /* Returns the root of the set V belongs to. Path-compression is employed
       allowing for fast search-time. If invalid items are passed into this
       function, throw an IllegalArgumentException. */
    public int find(int v) {
        if(v < 0 || v >= UnionFindSets.length){
            throw new IllegalArgumentException();
        }
        else if (parent(v) < 0){
            return v;
        }else{
            int root = find(parent(v));
            UnionFindSets[v] = root;
            return root;
        }
    }

    /* Connects two items V1 and V2 together by connecting their respective
       sets. V1 and V2 can be any element, and a union-by-size heuristic is
       used. If the sizes of the sets are equal, tie break by connecting V1's
       root to V2's root. Union-ing an item with itself or items that are
       already connected should not change the structure. */
    public void union(int v1, int v2) {
        int root1 = find(v1);
        int root2 = find(v2);

        if (root1 == root2) {
            return;
        }

        int size1 = -UnionFindSets[root1];
        int size2 = -UnionFindSets[root2];

        if (size1 <= size2) {  // 关键：<=
            UnionFindSets[root2] -= size1;
            UnionFindSets[root1] = root2;
        } else {
            UnionFindSets[root1] -= size2;
            UnionFindSets[root2] = root1;
        }
    }

}
