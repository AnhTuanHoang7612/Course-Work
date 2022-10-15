package com.nathan.expensemanager.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import java.io.Serializable;

@Entity(tableName = "ExpensesModel")
public class ExpensesModel implements Serializable {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    int id = 0;
    long tripId;
    String type;
    String amount;
    String timeOfExpense;

    public ExpensesModel(long tripId, String type, String amount, String timeOfExpense) {
        this.tripId = tripId;
        this.type = type;
        this.amount = amount;
        this.timeOfExpense = timeOfExpense;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getTripId() {
        return tripId;
    }

    public void setTripId(long tripId) {
        this.tripId = tripId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getTimeOfExpense() {
        return timeOfExpense;
    }

    public void setTimeOfExpense(String timeOfExpense) {
        this.timeOfExpense = timeOfExpense;
    }
}
