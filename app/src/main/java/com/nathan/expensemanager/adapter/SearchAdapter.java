package com.nathan.expensemanager.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.nathan.expensemanager.R;
import com.nathan.expensemanager.model.TripModel;
import java.util.ArrayList;
import java.util.List;

//Class này dùng để thiết lập data cho view là R.layout.item_search, với đối tượng là TRIP đối với view search item trip
public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchAdapterVH> implements Filterable {
    List<TripModel> items;
    List<TripModel> oldItems;
    //Hàm set lại data cho Recycler view
    public  void setData(List<TripModel> listTripModel){
        this.items = listTripModel;
        this.oldItems = listTripModel;
        notifyDataSetChanged();
    }

    //Set up view cho item
    @NonNull
    @Override
    public SearchAdapterVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search, parent, false);
        return new SearchAdapterVH(view);
    }

    //Binding data cho item
    @Override
    public void onBindViewHolder(@NonNull SearchAdapterVH holder, int position) {
        TripModel tripModel = items.get(position);
        if (tripModel == null)
            return;
        //set data for item
        holder.tvNumber.setText((position+1) + "");
        holder.tvNameTrip.setText(tripModel.getName());
        holder.tvDestination.setText(tripModel.getDestination());
        holder.tvDateOfTrip.setText(tripModel.getDateOfTrip());
    }

    //Get số lượng item của items
    @Override
    public int getItemCount() {
        if (items == null)
        return 0;
        else return items.size();
    }

    // function search item Trip
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String strSearch = charSequence.toString();
                // strSearch == "" don't display
                if (strSearch.isEmpty()) {
                    items = null;
                }
                else{
                    // search for name, destination, dateOfTrip
                    List<TripModel> listTrip = new ArrayList<>();
                    for (TripModel tripModel : oldItems){
                        if (tripModel.getName().toLowerCase().trim().contains(strSearch.toLowerCase())
                        || tripModel.getDestination().toLowerCase().trim().contains(strSearch.toLowerCase())
                        || tripModel.getDateOfTrip().toLowerCase().trim().contains(strSearch.toLowerCase())){
                            listTrip.add(tripModel);
                        }
                    }
                    items = listTrip;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = items;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                items = (List<TripModel>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
    //View Holder dùng để ánh xạ view
    class SearchAdapterVH extends RecyclerView.ViewHolder{
        TextView tvNameTrip, tvDestination, tvDateOfTrip, tvNumber;
        public SearchAdapterVH(@NonNull View itemView) {
            super(itemView);

            tvNameTrip = itemView.findViewById(R.id.tvNameTrip);
            tvDestination = itemView.findViewById(R.id.tvDestination);
            tvDateOfTrip = itemView.findViewById(R.id.tvDateOfTrip);
            tvNumber = itemView.findViewById(R.id.tvNumber);
        }
    }
}
