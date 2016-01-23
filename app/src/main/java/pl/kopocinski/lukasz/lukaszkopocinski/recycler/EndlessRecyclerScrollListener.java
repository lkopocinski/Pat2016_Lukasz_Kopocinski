package pl.kopocinski.lukasz.lukaszkopocinski.recycler;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by Łukasz on 2016-01-21.
 */
public abstract class EndlessRecyclerScrollListener extends RecyclerView.OnScrollListener {
    /* Elements "below" screen, but loaded */
    private int visibleThreshold = 1;

    private int currentPage = 0;
    private int previousTotalItemCount = 0;
    private int startingPageIndex = 0;

    private boolean loading = true;
    private RecyclerView.LayoutManager layoutManager;

    public EndlessRecyclerScrollListener(LinearLayoutManager layoutManager) {
        this.layoutManager = layoutManager;
    }

    public EndlessRecyclerScrollListener(GridLayoutManager layoutManager) {
        this.layoutManager = layoutManager;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        int lastVisibleItemPosition = 0;

        /* Check the type of layout to get last visible item */
        if (layoutManager instanceof LinearLayoutManager) {
            lastVisibleItemPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
        } else if (layoutManager instanceof GridLayoutManager) {
            lastVisibleItemPosition = ((GridLayoutManager) layoutManager).findLastVisibleItemPosition();
        }

        int totalItemCount = layoutManager.getItemCount();

        if (totalItemCount < previousTotalItemCount) {
            this.currentPage = this.startingPageIndex;
            this.previousTotalItemCount = totalItemCount;

            if (totalItemCount == 0) {
                this.loading = true;
            }
        }

        /* When is still loading */
        if (loading && (totalItemCount > previousTotalItemCount)) {
            loading = false;
            previousTotalItemCount = totalItemCount;
        }

        /* When new data is needed */
        if (!loading && (lastVisibleItemPosition + visibleThreshold) >= totalItemCount) {
            currentPage += 1;
            loadMore(currentPage, totalItemCount);
            loading = true;
        }
    }

    public abstract void loadMore(int page, int totalItemsCount);
}
