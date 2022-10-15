package com.nathan.expensemanager.fragment;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nathan.expensemanager.HttpUtils;
import com.nathan.expensemanager.R;
import com.nathan.expensemanager.db.Database;
import com.nathan.expensemanager.model.TripModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.List;


public class UploadFragment extends Fragment {
    private Button updateBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_upload,
                container, false);
        init(view);
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    sendPayLoad();
                } catch (JSONException | UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        });
        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public void sendPayLoad() throws JSONException, UnsupportedEncodingException {
        List<TripModel> list = getListTrip();

        JSONObject jdata = new JSONObject();

        jdata.put("userId", "user123");

        JSONArray jArry=new JSONArray();
        for (int i=0;i<list.size();i++)
        {
            JSONObject jObjd=new JSONObject();
            jObjd.put("name", list.get(i).getName());
            jArry.put(jObjd);
        }

        jdata.put("detailList", jArry);

        RequestParams rp = new RequestParams();
        rp.put("jsonpayload", jdata.toString());

        HttpUtils.post("sendPayLoad",
                rp,
                new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers,JSONObject response) {
                        System.out.println("Received event with data: " + response);
                        showMessageUpload(response.toString());
                    }
                });
    }

    private void init(View view) {
        updateBtn = view.findViewById(R.id.upCloud);
    }

    private void showMessageUpload(String response){
        new AlertDialog.Builder(getContext())
                .setMessage(response)
                .setCancelable(true)
                .show();
    }

    private List<TripModel> getListTrip(){
        return Database.getInstance(getContext()).getTripDao().getListTrip();
    }
}