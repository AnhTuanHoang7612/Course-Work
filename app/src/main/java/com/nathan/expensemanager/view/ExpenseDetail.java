package com.nathan.expensemanager.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.nathan.expensemanager.R;
import com.nathan.expensemanager.Ultil;
import com.nathan.expensemanager.adapter.ExpenseAdapter;
import com.nathan.expensemanager.db.Database;
import com.nathan.expensemanager.model.ExpensesModel;
import com.nathan.expensemanager.model.TripModel;
import java.util.List;

public class ExpenseDetail extends AppCompatActivity {
    FloatingActionButton fab;
    RecyclerView rcvExpenseDetail;
    TripModel tripModel;
    ExpenseAdapter expenseAdapter;
    ImageView ivBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense_detail);

        init();
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // switch screen AddExpense and id TripModel
        fab.setOnClickListener(view -> {
            Intent intent = new Intent(this, AddExpense.class);
            Bundle bundle = new Bundle();
            bundle.putLong(Ultil.ID_TRIP, tripModel.getId());
            intent.putExtras(bundle);
            startActivity(intent);
        });
    }

    //Hàm ánh xạ view và khởi tạo data
    private void init(){
        //Nhận dữ liệu từ activity khác
        Bundle bundle = getIntent().getExtras();
        tripModel =(TripModel) bundle.getSerializable(Ultil.TRIP_MODEL);
        fab = findViewById(R.id.fabExpense);
        rcvExpenseDetail = findViewById(R.id.rcvExpenseDetail);
        ivBack = findViewById(R.id.ivBack);
        onRefresh();
    }

    //Hàm set lại adapter
    private void onRefresh(){
        rcvExpenseDetail.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        expenseAdapter = new ExpenseAdapter( this);
        expenseAdapter.setData(getListExpense());
        rcvExpenseDetail.setAdapter(expenseAdapter);
    };

    //Hàm get list Expense
    private List<ExpensesModel> getListExpense(){
       TripModel tripModel1 =  Database.getInstance(this).getTripDao().getDetailTrip(tripModel.getId());
       return tripModel1.getListExpense();
    }

    @Override
    protected void onResume() {
        onRefresh();
        super.onResume();
    }
}