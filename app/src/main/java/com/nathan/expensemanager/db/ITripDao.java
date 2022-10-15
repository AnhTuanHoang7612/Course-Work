package com.nathan.expensemanager.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import com.nathan.expensemanager.model.TripModel;
import java.util.List;

@Dao
public interface ITripDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(TripModel tripModel);

    @Update()
    void update(TripModel tripModel);

    @Delete
    void delete(TripModel tripModel);

    @Query("SELECT * FROM TripModel")
     List<TripModel> getListTrip();

    @Query("DELETE FROM TripModel")
    void deleteAllTrip();

    @Query("SELECT * FROM TripModel WHERE TripModel.id= :id")
    TripModel getDetailTrip(long id);
}
