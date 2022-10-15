package com.nathan.expensemanager.db;

import android.content.Context;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import com.nathan.expensemanager.model.ExpensesModel;
import com.nathan.expensemanager.model.TripModel;
import com.nathan.expensemanager.model.ExpenseConverter;

@androidx.room.Database(entities = {TripModel.class, ExpensesModel.class}, version = 1)
@TypeConverters(ExpenseConverter.class)
//abstract class dùng để tạo DB với version là 1
public abstract class Database extends RoomDatabase {
    //Phương thức abstract để giao tiếp với lớp ITripDao để get data
    public abstract ITripDao getTripDao();
    private static  Database instance;

    public static synchronized Database getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), Database.class,"manager_expense")
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }
}
