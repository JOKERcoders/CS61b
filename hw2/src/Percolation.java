import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private int n;
    private boolean[][] grid;
    private int openSites;

    private WeightedQuickUnionUF uf;
    private WeightedQuickUnionUF ufFull;

    private int virtualTop;
    private int virtualBottom;

    public Percolation(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException();
        }

        n = N;
        grid = new boolean[n][n];
        openSites = 0;

        uf = new WeightedQuickUnionUF(n * n + 2);
        ufFull = new WeightedQuickUnionUF(n * n + 1);

        virtualTop = n * n;
        virtualBottom = n * n + 1;
    }

    public void open(int row, int col) {
        validate(row, col);

        if (grid[row][col]) {
            return;
        }

        grid[row][col] = true;
        openSites++;

        int index = xyTo1D(row, col);

        if (row == 0) {
            uf.union(index, virtualTop);
            ufFull.union(index, virtualTop);
        }

        if (row == n - 1) {
            uf.union(index, virtualBottom);
        }

        if (row > 0 && isOpen(row - 1, col)) {
            uf.union(index, xyTo1D(row - 1, col));
            ufFull.union(index, xyTo1D(row - 1, col));
        }

        if (row < n - 1 && isOpen(row + 1, col)) {
            uf.union(index, xyTo1D(row + 1, col));
            ufFull.union(index, xyTo1D(row + 1, col));
        }

        if (col > 0 && isOpen(row, col - 1)) {
            uf.union(index, xyTo1D(row, col - 1));
            ufFull.union(index, xyTo1D(row, col - 1));
        }

        if (col < n - 1 && isOpen(row, col + 1)) {
            uf.union(index, xyTo1D(row, col + 1));
            ufFull.union(index, xyTo1D(row, col + 1));
        }
    }

    public boolean isOpen(int row, int col) {
        validate(row, col);
        return grid[row][col];
    }

    public boolean isFull(int row, int col) {
        validate(row, col);

        if (!isOpen(row, col)) {
            return false;
        }

        int index = xyTo1D(row, col);

        return ufFull.connected(index, virtualTop);
    }

    public int numberOfOpenSites() {
        return openSites;
    }

    public boolean percolates() {
        return uf.connected(virtualTop, virtualBottom);
    }

    private int xyTo1D(int row, int col) {
        return row * n + col;
    }

    private void validate(int row, int col) {
        if (row < 0 || row >= n || col < 0 || col >= n) {
            throw new IndexOutOfBoundsException();
        }
    }
}