package com.nathan.expensemanager.model;

import androidx.room.TypeConverter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.List;

//Class này dùng để convert data qua Json or String , byte,...
public class ExpenseConverter {
    @TypeConverter
    public static String expenseModelToString(List<ExpensesModel> expensesModel) {
        Gson gson = new Gson();
        return gson.toJson(expensesModel);
    }

    @TypeConverter
    public static List<ExpensesModel> fromString(String value) {
        Type listType = new TypeToken<List<ExpensesModel>>() {}.getType();
        List<ExpensesModel> expensesModel = new Gson().fromJson(value, listType);
        return expensesModel;
    }
}
