package com.nathan.expensemanager.view;

import androidx.appcompat.app.AppCompatActivity;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.nathan.expensemanager.R;
import com.nathan.expensemanager.Ultil;
import com.nathan.expensemanager.db.Database;
import com.nathan.expensemanager.model.ExpensesModel;
import com.nathan.expensemanager.model.TripModel;
import com.skydoves.powerspinner.OnSpinnerItemSelectedListener;
import com.skydoves.powerspinner.PowerSpinnerView;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AddExpense extends AppCompatActivity {
    DatePickerDialog picker;
    PowerSpinnerView powerSpinner;
    EditText edtAmount, edtTimeOfExpense;
    Button btnAddExpense;
    ImageView ivBack;
    TripModel tripModel;
    List<ExpensesModel> listExpenseModel = new ArrayList<>();
    long idTrip;
    String type = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);
        init();
        pickerTimeOfExpense();
        ivBack.setOnClickListener(view -> onBackPressed());

        // chose type expense
        powerSpinner.setOnSpinnerItemSelectedListener((OnSpinnerItemSelectedListener<String>)
                (oldIndex, oldItem, newIndex, newText) -> {
                    if (newText == null){
                        type = "";
                    }else {
                        type = newText;
                    }
                });

        // add new data to expense
        btnAddExpense.setOnClickListener(view -> {
            validateExpense();
        });
        getDetailTrip();

    }

    //Hàm ánh xạ view và khởi tạo data
    private void init(){
        // get Data Trip
        Bundle bundle = getIntent().getExtras();
        idTrip =  bundle.getLong(Ultil.ID_TRIP);
        powerSpinner = findViewById(R.id.powerSpinner);
        edtAmount = findViewById(R.id.edtAmount);
        edtTimeOfExpense = findViewById(R.id.edtTimeOfExpense);
        btnAddExpense = findViewById(R.id.btnAddExpense);
        ivBack = findViewById(R.id.ivBack);
    }

    //Hàm kiểm tra validate input
    private  void validateExpense(){
        String strAmount = edtAmount.getText().toString();
        String strTimeOfExpense = edtTimeOfExpense.getText().toString();

        if (strAmount.isEmpty() || strTimeOfExpense.isEmpty() || type.isEmpty()){
            Toast.makeText(this, "Fill all required fields", Toast.LENGTH_SHORT).show();
        }else{
            // get list Expense after add into listExpenseModel
            if(tripModel.getListExpense() != null)
                listExpenseModel = tripModel.getListExpense();
            // add new data into listExpense
            ExpensesModel expensesModel = new ExpensesModel(idTrip,type, strAmount, strTimeOfExpense);
            listExpenseModel.add(expensesModel);

            tripModel = new TripModel(idTrip, tripModel.getName(), tripModel.getDestination(), tripModel.getDateOfTrip(), tripModel.getRequireRisk(), tripModel.getDescription(), listExpenseModel);
            Database.getInstance(this).getTripDao().update(tripModel);
            Toast.makeText(this, "Expense Added Successfully !!!", Toast.LENGTH_SHORT).show();
            resetValue();
        }
    }

    //Hàm show popup calendar
    private void pickerTimeOfExpense(){
        edtTimeOfExpense.setOnClickListener(view12 -> {
            final Calendar cldr = Calendar.getInstance();
            int day = cldr.get(Calendar.DAY_OF_MONTH);
            int month = cldr.get(Calendar.MONTH);
            int year = cldr.get(Calendar.YEAR);
            // date picker dialog
            picker = new DatePickerDialog(view12.getContext(), (datePicker, year1, month1, day1) -> edtTimeOfExpense.setText((day1 < 10 ? "0" + day1: day1) + "/" + ((month1 + 1) < 10 ? ("0" + (month1 + 1)): (month1 + 1)) + "/" + year1), year, month, day);
            picker.show();
        });
    }

    @Override
    protected void onResume() {
        resetValue();
        super.onResume();
    }

    //Hàm get thông tin chi tiết của trip
    private TripModel getDetailTrip(){
        tripModel = Database.getInstance(this).getTripDao().getDetailTrip(idTrip);
        return tripModel;
    }

    //Hàm reset value về blank
    private void resetValue(){
        type = "";
        edtAmount.setText("");
        edtTimeOfExpense.setText("");
        powerSpinner.clearSelectedItem();
    }
}