package pl.kopocinski.lukasz.lukaszkopocinski.recycler;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import pl.kopocinski.lukasz.lukaszkopocinski.R;
import pl.kopocinski.lukasz.lukaszkopocinski.models.JsonRowData;
import pl.kopocinski.lukasz.lukaszkopocinski.models.JsonServerArray;

public class Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<JsonRowData> rowsList;
    private Context context;

    private boolean noMoreData = false;

    private final int ITEM = 1;
    /* To show progressBar on last line */
    private final int PROGRESS_BAR = 0;
    /* To show information on last line */
    private final int NO_DATA = 2;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView titleText;
        public TextView descriptionText;

        public ViewHolder(View itemView) {
            super(itemView);

            imageView = (ImageView) itemView.findViewById(R.id.element_image);
            titleText = (TextView) itemView.findViewById(R.id.list_row_title);
            descriptionText = (TextView) itemView.findViewById(R.id.list_row_description);
        }
    }

    public static class ProgressViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;

        public ProgressViewHolder(View itemView) {
            super(itemView);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar);
        }
    }

    public static class InformationViewHolder extends RecyclerView.ViewHolder {
        public TextView informationTextView;

        public InformationViewHolder(View itemView) {
            super(itemView);
            informationTextView = (TextView) itemView.findViewById(R.id.recycler_footer_text);
        }
    }

    public Adapter(List<JsonRowData> rowsList, Context context) {
        this.rowsList = rowsList;
        this.context = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == ITEM) {
            View view = LayoutInflater.from(parent.getContext())
                    /* In case of list view change to fragment_main_list_row.xml */
                    //.inflate(R.layout.fragment_main_list_row, parent, false);
                    .inflate(R.layout.fragment_main_grid_element, parent, false);

            return new ViewHolder(view);
        }

        if (viewType == PROGRESS_BAR) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.progress_bar_row, parent, false);

            return new ProgressViewHolder(view);
        }

        if (viewType == NO_DATA) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.recycler_footer_information, parent, false);

            return new InformationViewHolder(view);
        }

        return null;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder) {
            setImage(rowsList.get(position).getUrl(), ((ViewHolder) holder).imageView);
            ((ViewHolder) holder).titleText.setText(rowsList.get(position).getTitle());
            ((ViewHolder) holder).descriptionText.setText(rowsList.get(position).getDesc());
        } else if (holder instanceof ProgressViewHolder) {
            ((ProgressViewHolder) holder).progressBar.setIndeterminate(true);
        } else {
            ((InformationViewHolder) holder).informationTextView.setText(R.string.no_more_data);
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return rowsList.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        return selectType(position);
    }

    private int selectType(int position) {
        if (!(position >= rowsList.size())) {
            return ITEM;
        } else {
            if (noMoreData) {
                return NO_DATA;
            } else {
                return PROGRESS_BAR;
            }
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    private void setImage(String url, ImageView imageView) {
        Glide.with(context)
                .load(url)
                .centerCrop()
                .placeholder(R.drawable.no_image_available)
                .error(R.drawable.no_image_available)
                .into(imageView);
    }

    public void addAll(JsonServerArray array) {
        rowsList.addAll(array.getArray());
    }

    public void setNoMoreData(boolean moreData) {
        this.noMoreData = moreData;
    }
}
