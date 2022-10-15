package com.nathan.expensemanager.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.nathan.expensemanager.R;
import com.nathan.expensemanager.itf.IOnClick;
import com.nathan.expensemanager.model.TripModel;
import java.util.List;

//Class này dùng để thiết lập data cho view là R.layout.item_search, với đối tượng là TRIP
public class TripAdapter extends RecyclerView.Adapter<TripAdapter.TripAdapterVH>{
    private List<TripModel> items;
    private Context mContext;
    private IOnClick iOnClick;

    //Hàm khởi tạo adapter với tham số là môi trường contex
    public TripAdapter(Context context, IOnClick iOnClick){
        this.mContext = context;
        // iOnClick use for pass data
        this.iOnClick = iOnClick;
    }

    //Hàm set lại data cho Recycler view
    public void setData(List<TripModel> items){
        this.items = items;
        notifyDataSetChanged();
    }

    //Set up view cho item
    @NonNull
    @Override
    public TripAdapterVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_trip, parent, false);
        return new TripAdapterVH(view);
    }

    //Binding data cho item
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull TripAdapterVH holder, int position) {
        TripModel tripModel = items.get(position);
        if (tripModel == null){
            return;
        }

        // set data for item
        holder.tvNumber.setText((position + 1) + "");
        holder.tvNameTrip.setText(tripModel.getName());
        holder.tvDestination.setText(tripModel.getDestination());
        holder.tvDateOfTrip.setText(tripModel.getDateOfTrip());
        holder.tvAssessment.setText("Require Assessment: " +tripModel.getRequireRisk());
        holder.onClickEditTrip(tripModel);
    }

    //Get số lượng item của items
    @Override
    public int getItemCount() {
        return items.size();
    }

    //View Holder dùng để ánh xạ view
    class TripAdapterVH extends RecyclerView.ViewHolder{
        TextView tvNumber, tvNameTrip, tvDestination, tvDateOfTrip, tvAssessment;
        public TripAdapterVH(@NonNull View itemView) {
            super(itemView);
            //Ánh xạ View
            tvNumber = itemView.findViewById(R.id.tvNumber);
            tvNameTrip = itemView.findViewById(R.id.tvNameTrip);
            tvDestination = itemView.findViewById(R.id.tvDestination);
            tvDateOfTrip = itemView.findViewById(R.id.tvDateOfTrip);
            tvAssessment = itemView.findViewById(R.id.tvAssessment);
        }
        // handle onclick pass Data
        public void onClickEditTrip(TripModel tripModel){
            itemView.setOnClickListener(view -> iOnClick.onClickEditTrip(tripModel));
        }
    }
}
