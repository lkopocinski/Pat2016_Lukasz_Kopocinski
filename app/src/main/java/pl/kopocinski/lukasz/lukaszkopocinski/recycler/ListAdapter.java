package pl.kopocinski.lukasz.lukaszkopocinski.recycler;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import pl.kopocinski.lukasz.lukaszkopocinski.R;
import pl.kopocinski.lukasz.lukaszkopocinski.json.models.JsonRowData;

/**
 * Created by Łukasz on 2016-01-03.
 */
public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {
    private List<JsonRowData> rowsList;
    private Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView titleText;
        //public TextView descriptionText;

        public ViewHolder(View itemView) {
            super(itemView);

            imageView = (ImageView) itemView.findViewById(R.id.element_image);
            titleText = (TextView) itemView.findViewById(R.id.list_row_title);
            //descriptionText = (TextView) itemView.findViewById(R.id.list_row_description);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public ListAdapter(List<JsonRowData> rowsList, Context context) {
        this.rowsList = rowsList;
        this.context = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        // create a new view
        View view = LayoutInflater.from(parent.getContext())
                /* In case of list view change to fragment_main_list_row.xml */
                //.inflate(R.layout.fragment_main_list_row, parent, false);
                .inflate(R.layout.fragment_main_grid_element, parent, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element


        setImage(rowsList.get(position).getUrl(),holder.imageView);
        holder.titleText.setText(rowsList.get(position).getTitle());
       // holder.descriptionText.setText(rowsList.get(position).getDesc());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return rowsList.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    private void setImage(String url, ImageView imageView){
        Glide.with(context)
                .load(url)
                .placeholder(R.drawable.no_image_available_gray)
                .error(R.drawable.no_image_available_gray)
                .centerCrop()
                .into(imageView);
    }
}
