package com.nathan.expensemanager.fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import com.nathan.expensemanager.R;
import com.nathan.expensemanager.adapter.SearchAdapter;
import com.nathan.expensemanager.db.Database;
import com.nathan.expensemanager.model.TripModel;
import java.util.List;

//Fragment này dùng để search show ra các item cần tìm
public class  SearchFragment extends Fragment {
    EditText edtSearch;
    Button btnSearch;
    RecyclerView rcvSearch;
    SearchAdapter searchAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);

        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // when click button handle search on edtSearch
                btnSearch.setOnClickListener(view1 -> {
                    // set data recyclerView
                    setData();
                    // search data
                    searchAdapter.getFilter().filter(charSequence);
                });
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    //Hàm này dùng để ánh xạ view và thiết lập view
    private void init(View view){
        edtSearch = view.findViewById(R.id.edtSearch);
        btnSearch = view.findViewById(R.id.btnSearch);
        rcvSearch = view.findViewById(R.id.rcvSearch);
        searchAdapter = new SearchAdapter();
        rcvSearch.setLayoutManager(new LinearLayoutManager(getContext()));
        searchAdapter = new SearchAdapter();
    }

    //Hàm này dùng để set lại data cho recyclerview
    public void setData(){
        searchAdapter.setData(getListTrip());
        rcvSearch.setAdapter(searchAdapter);
    }

    //Hàm này dùng để get data trips
    public List<TripModel> getListTrip(){
        return Database.getInstance(getContext()).getTripDao().getListTrip();
    }
}
