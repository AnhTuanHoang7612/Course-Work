package com.nathan.expensemanager.itf;

import com.nathan.expensemanager.model.TripModel;

//Interface này dùng để bắt sự kiên Onclick Item Trip truyền data qua fragment hay activity khác
public interface IOnClick {
     void onClickEditTrip(TripModel tripModel);
}
