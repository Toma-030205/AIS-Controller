package ais.util;


public class Paginator {
    private int page = 1;
    private final int minPage;
    private final int maxPage;

    public Paginator(int minPage, int maxPage) {
        this.minPage = minPage;
        this.maxPage = maxPage;
        this.page = minPage;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = clamp(page);
    }

    public int next() {
        page++;
        page = clamp(page);
        return page;
    }

    public int prev() {
        page--;
        page = clamp(page);
        return page;
    }

    private int clamp(int p) {
        if (p < minPage) return minPage;
        if (p > maxPage) return maxPage;
        return p;
    }
}
