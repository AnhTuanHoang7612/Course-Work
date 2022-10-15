package com.nathan.expensemanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.nathan.expensemanager.fragment.SearchFragment;
import com.nathan.expensemanager.fragment.TripFragment;
import com.nathan.expensemanager.fragment.UploadFragment;
import com.nathan.expensemanager.itf.IOnClick;
import com.nathan.expensemanager.model.TripModel;
import com.nathan.expensemanager.view.EditTrip;

public class MainActivity extends AppCompatActivity  implements IOnClick{
    BottomNavigationView bnv;
    NavController navController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        setUpNav();
    }

    //Hàm ánh xạ và khởi tạo data
    private void init(){
        bnv = findViewById(R.id.bottomNavigation);
        navController =  Navigation.findNavController(this,R.id.fragment_host);
        NavigationUI.setupWithNavController(bnv, navController);
    }

    //Hàm navigation khi selected bottom navigation
    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.tripFragment:
                replaceFragment(new TripFragment());
                break;
            case R.id.searchFragment:
                replaceFragment(new SearchFragment());
                break;
            case R.id.uploadFragment:
                replaceFragment(new UploadFragment());
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    //Hàm khởi tạo menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_bottom, menu);
        return true;
    }

    //Hàm replace fragment
    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_host, fragment);
        fragmentTransaction.commit();
    }

    private void setUpNav(){
        navController.addOnDestinationChangedListener((navController1, navDestination, bundle) -> {
            switch (navDestination.getId()){
                case R.id.tripFragment:
                case R.id.searchFragment:
                case R.id.uploadFragment:
                    showBottomNav();
                    break;
                default:
                    hideBottomNav();
                    break;
            }
        });
    }

    private void showBottomNav(){
        bnv.setVisibility(View.VISIBLE);
    }

    private void hideBottomNav(){
        bnv.setVisibility(View.GONE);
    }

    // receiver data from Trip Fragment and pass data(TripModel) to EditTrip
    @Override
    public void onClickEditTrip(TripModel tripModel) {
        Intent intent = new Intent(this, EditTrip.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(Ultil.TRIP_MODEL, tripModel);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}