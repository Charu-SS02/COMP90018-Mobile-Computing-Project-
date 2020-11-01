package com.example.findcoffee.ui.search;

import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.findcoffee.R;
import com.example.findcoffee.ui.home.HomeViewModel;
import com.example.findcoffee.zomatoApiGetter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SearchFragment extends Fragment {

    private SearchViewModel dashboardViewModel;
    private static RecyclerView recyclerView;
    private static ArrayList<HomeViewModel> data;
//    public RequestQueue queue;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                ViewModelProviders.of(this).get(SearchViewModel.class);
        View root = inflater.inflate(R.layout.fragment_search, container, false);

        final EditText searchEdit = root.findViewById(R.id.editTextSearch);
        searchEdit.getText().toString();

        recyclerView = root.findViewById(R.id.recycler_Search);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));

        data = new ArrayList<HomeViewModel>();
//        queue = Volley.newRequestQueue(getActivity());


        searchEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void afterTextChanged(Editable editable) {
                String searchInputString = editable.toString();
                if(searchInputString.length() >= 3){

                    Log.d("Search", searchInputString);

                    Map<String, String> map = new HashMap<String, String>();
                    map.put("entity_id=", "259");
                    map.put("entity_type=", "city");
                    map.put("establishment_type=", "1");
                    map.put("category=", "6");


//        apiGetter.search(map, this, 10);
//
                    zomatoApiGetter zomato = new zomatoApiGetter();
                    zomato.search(map,getFragmentManager(),9999);
                }

            }
        });

//        final TextView textView = root.findViewById(R.id.text_search);
        dashboardViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
//                textView.setText(s);
            }
        });



        return root;
    }

    public void drawShop(String name, String address, String thumb){
        data.add(new HomeViewModel(
                name,
                address,
                thumb
        ));
    }

    public static RecyclerView getRecyclerView() {
        return recyclerView;
    }

    public static ArrayList<HomeViewModel> getData() {
        return data;
    }
}