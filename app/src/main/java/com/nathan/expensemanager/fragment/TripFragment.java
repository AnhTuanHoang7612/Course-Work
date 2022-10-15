package com.nathan.expensemanager.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.nathan.expensemanager.R;
import com.nathan.expensemanager.adapter.TripAdapter;
import com.nathan.expensemanager.db.Database;
import com.nathan.expensemanager.itf.IOnClick;
import com.nathan.expensemanager.model.TripModel;
import java.util.List;

public class TripFragment extends Fragment implements IOnClick {
    FloatingActionButton fab;
    RecyclerView rcvTrip;
    ImageView ivDeleteAll;
    TripAdapter tripAdapter;
    IOnClick iOnClick;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof IOnClick ){
            iOnClick= (IOnClick) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement onViewSelected");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_trip, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);

        // pass new screen to screen add trip
        fab.setOnClickListener(view1 -> {
            Navigation.findNavController(view).navigate(R.id.homeFragment);
        });

        ivDeleteAll.setOnClickListener(view12 -> {
            showMessageDeleteAll();
        });
    }

    @Override
    public void onResume() {
        onRefresh();
        super.onResume();
    }

    //Hàm ánh xạ và khởi tạo data
    private void init(View view){
        fab = view.findViewById(R.id.fab);
        rcvTrip = view.findViewById(R.id.rcvTrip);
        ivDeleteAll = view.findViewById(R.id.ivDeleteAll);
        onRefresh();
    }

    // refresh data for recyclerView
    private void onRefresh(){
        rcvTrip.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        tripAdapter = new TripAdapter(getContext(), this);
        tripAdapter.setData(getListTrip());
        rcvTrip.setAdapter(tripAdapter);
    }

    // Get all list trip
    private List<TripModel> getListTrip(){
       return Database.getInstance(getContext()).getTripDao().getListTrip();
    }

    // pass data (tripModel) to MainActivity
    @Override
    public void onClickEditTrip(TripModel tripModel) {
        iOnClick.onClickEditTrip(tripModel);
    }

    //Hàm này dùng để show thông báo muốn xóa hết list hay không?
    private void showMessageDeleteAll(){
        new AlertDialog.Builder(getContext())
                .setTitle("Warning!")
                .setMessage("Do you want delete all data")
                .setCancelable(false)
                .setPositiveButton("Accept", (dialogInterface, i) -> {
                    Database.getInstance(getContext()).getTripDao().deleteAllTrip();
                    Toast.makeText(getContext(), "Delete Success", Toast.LENGTH_SHORT).show();
                    onRefresh();
                })
                .setNegativeButton("Cancel", (dialogInterface, i) -> {}).show();
    }
}
