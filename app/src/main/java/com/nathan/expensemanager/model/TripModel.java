package com.nathan.expensemanager.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;
import java.io.Serializable;
import java.util.List;

@Entity(tableName = "TripModel")
public class TripModel implements Serializable {
    @PrimaryKey(autoGenerate = true)
    long id = 0;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "destination")
    private String destination;

    @ColumnInfo(name = "dateOfTrip")
    private String dateOfTrip;

    @ColumnInfo(name = "requireRisk")
    private String requireRisk;

    @ColumnInfo(name = "description")
    private String description;

    @TypeConverters(ExpenseConverter.class)
    private List<ExpensesModel> listExpense;

    public TripModel() {
    }

    public TripModel(long id, String name, String destination, String dateOfTrip, String requireRisk, String description, List<ExpensesModel> listExpense) {
        this.id = id;
        this.name = name;
        this.destination = destination;
        this.dateOfTrip = dateOfTrip;
        this.requireRisk = requireRisk;
        this.description = description;
        this.listExpense = listExpense;
    }

    public TripModel(String name, String destination, String dateOfTrip, String requireRisk, String description, List<ExpensesModel> listExpense) {
        this.name = name;
        this.destination = destination;
        this.dateOfTrip = dateOfTrip;
        this.requireRisk = requireRisk;
        this.description = description;
        this.listExpense = listExpense;
    }

    public TripModel(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDateOfTrip() {
        return dateOfTrip;
    }

    public void setDateOfTrip(String dateOfTrip) {
        this.dateOfTrip = dateOfTrip;
    }

    public String getRequireRisk() {
        return requireRisk;
    }

    public void setRequireRisk(String requireRisk) {
        this.requireRisk = requireRisk;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<ExpensesModel> getListExpense() {
        return listExpense;
    }

    public void setListExpense(List<ExpensesModel> listExpense) {
        this.listExpense = listExpense;
    }
}
