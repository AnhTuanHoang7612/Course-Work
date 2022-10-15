package com.nathan.expensemanager.view;

import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import com.nathan.expensemanager.R;
import com.nathan.expensemanager.Ultil;
import com.nathan.expensemanager.db.Database;
import com.nathan.expensemanager.model.TripModel;
import java.util.Calendar;

public class EditTrip extends AppCompatActivity {
    DatePickerDialog picker;
    EditText edtNameTrip, edtDestination, edtDateOfTrip, edtDescription;
    RadioButton rbYes, rbNo;
    String checkAssessment = Ultil.VALUE_NONE;
    Button btnEditTrip, btnSeeAllExpense;
    ImageView ivBack;
    RadioGroup radioGroup;
    TripModel tripModel;
    ImageView ivDelete;
    long idTrip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_trip);

        init();
        selectAssessment();
        pickerDateOfTrip();
        ivBack.setOnClickListener(view -> onBackPressed());
        btnEditTrip.setOnClickListener(view -> updateTrip());

        // switch screen ExpenseDetail and data tripModel
        btnSeeAllExpense.setOnClickListener(view ->{
            Intent intent = new Intent(EditTrip.this, ExpenseDetail.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable(Ultil.TRIP_MODEL, tripModel);
            intent.putExtras(bundle);
            startActivity(intent);
        });
    }

    //Hàm ánh xạ view và khởi tạo init data
    private void init(){
        Bundle bundle = getIntent().getExtras();
        tripModel =(TripModel) bundle.getSerializable(Ultil.TRIP_MODEL);
        idTrip = tripModel.getId();
        edtNameTrip = findViewById(R.id.edtNameTrip);
        edtDestination = findViewById(R.id.edtDestination);
        edtDateOfTrip = findViewById(R.id.edtDateOfTrip);
        edtDescription = findViewById(R.id.edtDescription);
        rbYes = findViewById(R.id.rbYes1);
        rbNo = findViewById(R.id.rbNo1);
        btnEditTrip = findViewById(R.id.btnEditTrip);
        btnSeeAllExpense = findViewById(R.id.btnSeeAllExpense);
        ivDelete = findViewById(R.id.ivDelete);
        ivBack = findViewById(R.id.ivBack);
        radioGroup = findViewById(R.id.radioGroup);
        setValue(tripModel);

        // Delete single item Trip
        ivDelete.setOnClickListener(view -> {
           showMessageDeleteItem();
        });
    }

    //Hàm set value detail của trip lên input
    public void setValue(TripModel tripModel){
        edtNameTrip.setText(tripModel.getName());
        edtDestination.setText(tripModel.getDestination());
        edtDateOfTrip.setText(tripModel.getDateOfTrip());
        edtDescription.setText(tripModel.getDescription());

        if (tripModel.getRequireRisk().equals(Ultil.VALUE_YES)){
            radioGroup.check(R.id.rbYes1);
            checkAssessment = Ultil.VALUE_YES;
        }else {
            radioGroup.check(R.id.rbNo1);
            checkAssessment = Ultil.VALUE_NO;
        }
    }

    //Hàm check radio button
    public void selectAssessment() {
        rbYes.setOnClickListener(view ->{
            checkAssessment = Ultil.VALUE_YES;
        } );
        rbNo.setOnClickListener(view1 -> {
            checkAssessment = Ultil.VALUE_NO;
        });
    }

    //Hàm này để update trip vào sqlite
    public void updateTrip(){
        String strName = edtNameTrip.getText().toString();
        String strDestination = edtDestination.getText().toString();
        String strDateOfTrip = edtDateOfTrip.getText().toString();
        String strDescription = edtDescription.getText().toString();
        String requiredRiskAssessment = checkAssessment;
        if(strName.isEmpty() || strDateOfTrip.isEmpty() || strDestination.isEmpty() || requiredRiskAssessment.equals(Ultil.VALUE_NONE)){
            showAlertDialog();
        }else{
            // update data trip
            tripModel = new TripModel(idTrip,strName, strDestination, strDateOfTrip, requiredRiskAssessment, strDescription, tripModel.getListExpense());
            Database.getInstance(getApplicationContext()).getTripDao().update(tripModel);
            setValue(tripModel);
            showAlertDialogEdit();
        }
    }

    //Hàm để show popup calendar
    @SuppressLint("SetTextI18n")
    private void pickerDateOfTrip(){
        edtDateOfTrip.setOnClickListener(view12 -> {
            final Calendar cldr = Calendar.getInstance();
            int day = cldr.get(Calendar.DAY_OF_MONTH);
            int month = cldr.get(Calendar.MONTH);
            int year = cldr.get(Calendar.YEAR);
            // date picker dialog
            picker = new DatePickerDialog(view12.getContext(), (datePicker, year1, month1, day1) -> edtDateOfTrip.setText((day1 < 10 ? "0" + day1: day1) + "/" + ((month1 + 1) < 10 ? ("0" + (month1 + 1)): (month1 + 1)) + "/" + year1), year, month, day);
            picker.show();
        });
    }

    //Hàm show popup thông báo update thành công
    private void showAlertDialogEdit(){
        new AlertDialog.Builder(this)
                .setMessage("Edit success")
                .setCancelable(false)
                .setNegativeButton("Close", (dialogInterface, i) -> {}).show();
    }

    //Hàm show message thông báo validate required
    private void showAlertDialog(){
        new AlertDialog.Builder(this)
                .setMessage("You need to fill all required fields")
                .setCancelable(false)
                .setNegativeButton("Close", (dialogInterface, i) -> {}).show();
    }

    //Hàm show popup thông báo xóa item trong list
    private void showMessageDeleteItem(){
        new AlertDialog.Builder(this)
                .setTitle("Warning!")
                .setMessage("Do you want delete this data")
                .setCancelable(false)
                .setPositiveButton("Accept", (dialogInterface, i) -> {
                    Database.getInstance(this).getTripDao().delete(tripModel);
                    Toast.makeText(this, "Delete Success!!!", Toast.LENGTH_SHORT).show();
                    onBackPressed();
                })
                .setNegativeButton("Cancel", (dialogInterface, i) -> {}).show();
    }
}