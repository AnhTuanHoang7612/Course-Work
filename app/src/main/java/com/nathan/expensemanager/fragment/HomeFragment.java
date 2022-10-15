package com.nathan.expensemanager.fragment;

import android.annotation.SuppressLint;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import com.nathan.expensemanager.R;
import com.nathan.expensemanager.Ultil;
import com.nathan.expensemanager.db.Database;
import com.nathan.expensemanager.model.TripModel;
import java.util.Calendar;

//Fragment này dùng để insert 1 trip
public class HomeFragment extends Fragment {
    DatePickerDialog picker;
    EditText edtNameTrip, edtDestination, edtDateOfTrip, edtDescription;
    RadioButton rbYes, rbNo;
    String checkAssessment = Ultil.VALUE_NONE;
    Button btnAddToDB;
    ImageView ivBack;
    RadioGroup radioGroup;

    //Khởi tạo view
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    //Khởi tạo view
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
        selectAssessment();
        pickerDateOfTrip();
        btnAddToDB.setOnClickListener(view1 -> {
            validateAddTrip();
        });

        // back to Trip Fragment
        ivBack.setOnClickListener(view12 -> Navigation.findNavController(view12).navigate(R.id.tripFragment));
    }

    //Ánh xạ view cho HomeFragment
    private void init(View view){
        edtNameTrip = view.findViewById(R.id.edtNameTrip);
        edtDestination = view.findViewById(R.id.edtDestination);
        edtDateOfTrip = view.findViewById(R.id.edtDateOfTrip);
        edtDescription = view.findViewById(R.id.edtDescription);
        rbYes = view.findViewById(R.id.rbYes);
        rbNo = view.findViewById(R.id.rbNo);
        btnAddToDB = view.findViewById(R.id.btnAddToDB);
        ivBack = view.findViewById(R.id.ivBack);
        radioGroup = view.findViewById(R.id.radioGroup);
    }

    //Hàm này dùng để check radio button
    public void selectAssessment() {
        rbYes.setOnClickListener(view ->{
            checkAssessment = Ultil.VALUE_YES;
        } );
        rbNo.setOnClickListener(view1 -> {
            checkAssessment = Ultil.VALUE_NO;
        });
    }

    // add new value for trip
    private void validateAddTrip(){
        String strName = edtNameTrip.getText().toString();
        String strDestination = edtDestination.getText().toString();
        String strDateOfTrip = edtDateOfTrip.getText().toString();
        String strDescription = edtDescription.getText().toString();
        String requiredRiskAssessment = checkAssessment;
        // check (strName, strDateOfTrip, strDestination, requiredRiskAssessment)  != ""
        if(strName.isEmpty() || strDateOfTrip.isEmpty() || strDestination.isEmpty() || requiredRiskAssessment.equals(Ultil.VALUE_NONE)){
            showAlertDialog();
        }else{
            TripModel tripModel = new TripModel(strName, strDestination, strDateOfTrip, requiredRiskAssessment, strDescription, null);
            //insert to data
            Database.getInstance(getContext()).getTripDao().insert(tripModel);
            showAlertDialogSuccess(tripModel.getName(),
                    tripModel.getDestination(),
                    tripModel.getDateOfTrip(),
                    tripModel.getRequireRisk(),
                    tripModel.getDescription());

            // Reset data when add new trip
            setResetData();
        }
    }

    //Hàm để show popup khi lỗi data nhập sai or blank
    private void showAlertDialog(){
        new AlertDialog.Builder(getContext())
                .setMessage("You need to fill all required fields")
                .setCancelable(false)
                .setNegativeButton("Close", (dialogInterface, i) -> {}).show();
    }

    //Hàm để show popup khi insert là success
    private void showAlertDialogSuccess(String name, String destination, String dateOfTrip, String rra, String description){
        new AlertDialog.Builder(getContext())
                .setMessage("Name: " + name + "\n"
                            + "Destination: " + destination + "\n"
                            + "Date of the Trip: " + dateOfTrip + "\n"
                            + "Risk Assessment: " + rra + "\n"
                            + (description.isEmpty() ? "" : ("Description: " + description))
                )
                .setCancelable(false)
                .setNegativeButton("Close", (dialogInterface, i) -> {}).show();
    }

    @SuppressLint("SetTextI18n")
    // Set data date of Trip
    private void pickerDateOfTrip(){
        edtDateOfTrip.setOnClickListener(view12 -> {
            final Calendar cldr = Calendar.getInstance();
            int day = cldr.get(Calendar.DAY_OF_MONTH);
            int month = cldr.get(Calendar.MONTH);
            int year = cldr.get(Calendar.YEAR);

            // day < 10 or month < 10  add 0 before day or month else no add 0 (example: 1/1/2000 -> 01/01/2000 )
            picker = new DatePickerDialog(view12.getContext(), (datePicker, year1, month1, day1) -> edtDateOfTrip.setText((day1 < 10 ? "0" + day1: day1) + "/" + ((month1 + 1) < 10 ? ("0" + (month1 + 1)): (month1 + 1)) + "/" + year1), year, month, day);
            picker.show();
        });
    }

    //Hàm reset lại input về trạng thái blank
    private void setResetData(){
        edtNameTrip.setText("");
        edtDestination.setText("");
        edtDateOfTrip.setText("");
        edtDescription.setText("");
        checkAssessment = Ultil.VALUE_NONE;
        radioGroup.clearCheck();
    }
}
