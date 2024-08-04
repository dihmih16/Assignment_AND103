package com.example.assignment_and103.fragment;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignment_and103.R;
import com.example.assignment_and103.adapter.FruitAdapter;
import com.example.assignment_and103.adapter.Fruit_User_Adapter;
import com.example.assignment_and103.model.Fruit;
import com.example.assignment_and103.model.Page;
import com.example.assignment_and103.model.Response;
import com.example.assignment_and103.model.Responses;
import com.example.assignment_and103.services.HttpRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;

public class HomeFragment extends Fragment {
    private View view;
    RecyclerView rcv_ds;
    Fruit_User_Adapter adapter;
    ArrayList<Fruit> fruits = new ArrayList<>();
    private HttpRequest httpRequest;
    private int page = 1;
    EditText edt_search;
    private int totalPage = 0;
    SharedPreferences sharedPreferences;
    String token;
    private String sort="";
    ImageView imageView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);

        rcv_ds = view.findViewById(R.id.rcv_popular);
        edt_search = view.findViewById(R.id.edt_searchfood);
        imageView = view.findViewById(R.id.imgSearch);

        sharedPreferences = getActivity().getSharedPreferences("INFO", Context.MODE_PRIVATE);
        token = sharedPreferences.getString("token", "");
        httpRequest = new HttpRequest(token);

        httpRequest = new HttpRequest(token);
        Map<String, String> map = getMapFilter(page,"","0","-1");
        httpRequest.callAPI().getPageFruit(map).enqueue(getListFruitResponse);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rcv_ds.setLayoutManager(linearLayoutManager);
        adapter = new Fruit_User_Adapter(fruits, getContext());
        rcv_ds.setAdapter(adapter);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                page = 1;
                fruits.clear();
                FilterFruit();
            }
        });

        return view;
    }

    private void getData () {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rcv_ds.setLayoutManager(linearLayoutManager);
        adapter = new Fruit_User_Adapter(fruits, getContext());
        rcv_ds.setAdapter(adapter);
    }

    private void updateData(ArrayList<Fruit> newFruits) {
        fruits.clear();
        fruits.addAll(newFruits);
        adapter.notifyDataSetChanged();
    }

    private Map<String, String> getMapFilter(int _page,String _name, String _price, String _sort){
        Map<String,String> map = new HashMap<>();

        map.put("page", String.valueOf(_page));
        map.put("name", String.valueOf(_name));
        map.put("price", String.valueOf(_price));
        map.put("sort", String.valueOf(_sort));


        return map;
    }

    Callback<Response<Page<ArrayList<Fruit>>>> getListFruitResponse = new Callback<Response<Page<ArrayList<Fruit>>>>() {
        @Override
        public void onResponse(Call<Response<Page<ArrayList<Fruit>>>> call, retrofit2.Response<Response<Page<ArrayList<Fruit>>>> response) {
            if (response.isSuccessful()) {
                if (response.body().getStatus() ==200) {
                    totalPage = response.body().getData().getTotalPage();
                    ArrayList<Fruit> _ds = response.body().getData().getData();
                    updateData(_ds);
                }
            }
        }

        @Override
        public void onFailure(Call<Response<Page<ArrayList<Fruit>>>> call, Throwable t) {
            Log.e(TAG, "API call failed", t);
        }
    };

    private void FilterFruit(){
        String _name = edt_search.getText().toString().equals("")? "" : edt_search.getText().toString();

        Map<String,String> map =getMapFilter(page, _name,"","");
        httpRequest.callAPI().getPageFruit( map).enqueue(getListFruitResponse);

    }

//    Callback<Responses<ArrayList<Fruit>>> getListFruitResponse = new Callback<Responses<ArrayList<Fruit>>>() {
//        @Override
//        public void onResponse(Call<Responses<ArrayList<Fruit>>> call, retrofit2.Response<Responses<ArrayList<Fruit>>> response) {
//            if (response.isSuccessful() && response.body() != null) {
//                if (response.body().getStatus() == 200) {
//                    ArrayList<Fruit> newFruits = response.body().getData();
//                    updateData(newFruits);
//                } else {
//                    Log.e(TAG, "Error: " + response.body().getStatus());
//                }
//            } else {
//                Log.e(TAG, "Response not successful");
//            }
//        }
//
//        @Override
//        public void onFailure(Call<Responses<ArrayList<Fruit>>> call, Throwable t) {
//            Log.e(TAG, "API call failed", t);
//        }
//    };

}
