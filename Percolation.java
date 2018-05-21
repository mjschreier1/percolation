package com.percolation;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private int scale;
    private WeightedQuickUnionUF sites;
    private WeightedQuickUnionUF sitesWithoutVirtual;
    private int virtualTop;
    private int virtualBottom;
    private boolean[] open;
    private int openCounter;

    public Percolation(int n) {
        scale = n;
        sites = new WeightedQuickUnionUF(n * n + 2);
        sitesWithoutVirtual = new WeightedQuickUnionUF(n * n + 1);
        virtualTop = 0;
        virtualBottom = n * n + 1;
        for (int i = 1; i <= n; i++) {
            sites.union(virtualTop, i);
            sitesWithoutVirtual.union(virtualTop, i);
        }
        for (int i = n * n - n + 1; i < virtualBottom; i++) sites.union(virtualBottom, i);
        open = new boolean[n * n + 2];
        for (int i = 0; i < n * n + 2; i++) open[i] = false;
    }

    public void open(int row, int col) {
        int id = encodeCoordinates(row, col);
        if (open[id]) return;
        else {
            open[id] = true;
            openCounter++;
        }

        if (scale > 1) {
            if (id % scale != 1 && open[id - 1]) {
                sites.union(id, id - 1);
                sitesWithoutVirtual.union(id, id - 1);
            }
            if (id % scale != 0 && open[id + 1]) {
                sites.union(id, id + 1);
                sitesWithoutVirtual.union(id, id + 1);
            }
            if (scale - id > 0 && open[id - scale]) {
                sites.union(id, id - scale);
                sitesWithoutVirtual.union(id, id - scale);
            }
            if (scale + id <= scale * scale && open[id + scale]) {
                sites.union(id, id + scale);
                sitesWithoutVirtual.union(id, id + scale);
            }
        }
    }

    public boolean isOpen(int row, int col) {
        return open[encodeCoordinates(row, col)];
    }

    public boolean isFull(int row, int col) {
        return sitesWithoutVirtual.connected(virtualTop, encodeCoordinates(row, col));
    }

    public int numberOfOpenSites() {
        return openCounter;
    }

    public boolean percolates() {
        return sites.connected(virtualTop, virtualBottom);
    }

    private int encodeCoordinates(int i, int j) {
        return scale * (i - 1) + j;
    }

    public static void main(String[] args) {

    }   // test client (optional)
}
